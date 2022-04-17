package com.vrplumber.pycraft.bukkitserver;

import com.vrplumber.pycraft.bukkitserver.APIServer;
import com.vrplumber.pycraft.bukkitserver.MessageHandler;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class PycraftMessage {

  static private Pattern headerPattern = Pattern.compile("^(\\d+),([a-z_A-Z0-9.]+),(.*)$");

  public Integer messageId = 0;
  public List<String> method = null;
  public List<Object> payload = null;
  public List<MessageHandler> implementation = null;
  public Object instance = null;
  public boolean finished = false;

  public void addImplementation(MessageHandler handler) {
    if (implementation == null) {
      implementation = new ArrayList<MessageHandler>();
    }
    implementation.add(handler);
  }

  public String nextName() {
    if (method != null) {
      if (implementation == null) {
        return method.get(0);
      } else if (method.size() > implementation.size()) {
        return method.get(implementation.size());
      }
    }
    return null;
  }

  static public PycraftMessage parseHeader(String line, PycraftAPI api) {
    /* Parse message header from the raw over-the-wire line */
    Matcher match = headerPattern.matcher(line);
    if (match.find()) {
      PycraftMessage result = new PycraftMessage();
      result.messageId = Integer.parseInt(match.group(1));
      String fullMethod = match.group(2);
      result.method = new ArrayList<String>();
      for (String fragment : fullMethod.split("[.]")) {
        result.method.add(fragment);
      }
      result.payload = api.encoder.decode(match.group(3));
      return result;
    } else {
      return null;
    }
  }

  private List<Entity> eventEntities(Event event) {
    /* Return a list of entities associated with this event */
    List<Entity> entities = new ArrayList<Entity>();
    if (event instanceof PlayerEvent) {
      entities.add(((PlayerEvent) event).getPlayer());
    }
    if (event instanceof EntityEvent) {
      entities.add(((EntityEvent) event).getEntity());
    }
    if (event instanceof InventoryEvent) {
      InventoryHolder holder = ((InventoryEvent) event).getInventory().getHolder();
      if (holder instanceof Entity) {
        entities.add((Entity) holder);
      }
    }
    return entities;
  }

  private List<Location> eventLocations(Event event) {
    /* Return a list of locations associated with this event */
    List<Location> locations = new ArrayList<Location>();
    List<Entity> entities = eventEntities(event);
    for (Entity entity : entities) {
      locations.add(entity.getLocation());
    }
    if (event instanceof BlockEvent) {
      locations.add(((BlockEvent) event).getBlock().getLocation());
    }
    if (event instanceof EntityTeleportEvent) {
      EntityTeleportEvent ete = (EntityTeleportEvent) event;
      locations.add(ete.getFrom());
      locations.add(ete.getTo());
    }
    if (event instanceof PlayerBedEnterEvent) {
      locations.add(((PlayerBedEnterEvent) event).getBed().getLocation());
    }
    if (event instanceof PlayerBedLeaveEvent) {
      locations.add(((PlayerBedLeaveEvent) event).getBed().getLocation());
    }
    if (event instanceof PlayerBucketEvent) {
      locations.add(((PlayerBucketEvent) event).getBlock().getLocation());
      locations.add(((PlayerBucketEvent) event).getBlockClicked().getLocation());
    }
    if (event instanceof PlayerInteractEvent) {
      PlayerInteractEvent pie = (PlayerInteractEvent) event;
      if (pie.hasBlock()) {
        locations.add(pie.getClickedBlock().getLocation());
      }
    }
    return locations;
  }

  private boolean matchEntity(Event event, String filterName) {
    List<Entity> entities = eventEntities(event);
    String testValue = filterName.toLowerCase();
    for (Entity entity : entities) {
      if (filterName.equals("*")) {
        return true;
      } else {
        if (entity.getName().toLowerCase().startsWith(testValue)) {
          return true;
        } else if (entity.getUniqueId().toString().toLowerCase().startsWith(testValue)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean matchLocation(Event event, Vector minimum, Vector maximum) {
    List<Location> locations = eventLocations(event);
    for (Location location : locations) {
      if (filterLocation(location, minimum, maximum)) {
        return true;
      }
    }
    return false;
  }

  private boolean filterLocation(Location loc, Vector minimum, Vector maximum) {
    /* is loc in area minimum => maximum inclusive ? */
    int x = loc.getBlockX();
    int y = loc.getBlockY();
    int z = loc.getBlockZ();
    if (x >= minimum.getBlockX() && x <= maximum.getBlockX()) {
      if (y >= minimum.getBlockY() && y <= maximum.getBlockY()) {
        if (z >= minimum.getBlockZ() && z <= maximum.getBlockZ()) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean filterEvent(Event event, PycraftAPI api) {
    Logger log = api.getLogger();
    log.info(String.format("Filter %s for %d", event.getEventName(), messageId));
    if (this.payload.size() > 2) {
      // Has a filter...
      Integer filterType = api.expectInteger(this, 2);
      if (filterType == 1) {
        /* Filter by username */
        String filterName = api.expectString(this, 3);
        log.info(String.format("  Username filter %s", filterName));
        return matchEntity(event, filterName);
      } else if (filterType == 2) {
        /* Filter by block area [minx,miny,minz],[maxx,maxy,maxz] */
        Vector minimum = (Vector) (api.expectType(this, 3, Vector.class));
        Vector maximum = (Vector) (api.expectType(this, 4, Vector.class));
        return matchLocation(event, minimum, maximum);
      }
      return false;
    }
    log.info(String.format("  No filtering, matched"));
    return true;
  }

}