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

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
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

  private boolean matchEntity(Event event, String filterName) {
    if (event instanceof EntityEvent) {
      if (filterName.equals("*")) {
        return true;
      } else {
        Entity entity = ((EntityEvent) event).getEntity();
        if (entity.getName().startsWith(filterName)) {
          return true;
        } else if (entity.getUniqueId().toString().startsWith(filterName)) {
          return true;
        }
        return false;
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
    if (this.payload.size() > 2) {
      // Has a filter...
      Integer filterType = api.expectInteger(this, 2);
      if (filterType == 1) {
        /* Filter by username */
        String filterName = api.expectString(this, 3);
        return matchEntity(event, filterName);
      } else if (filterType == 2) {
        /* Filter by block area [minx,miny,minz],[maxx,maxy,maxz] */
        Vector minimum = (Vector) (api.expectType(this, 3, Vector.class));
        Vector maximum = (Vector) (api.expectType(this, 4, Vector.class));
        if (event instanceof BlockEvent) {
          Location loc = ((BlockEvent) event).getBlock().getLocation();
          if (filterLocation(loc, minimum, maximum)) {
            return true;
          }
        } else if (event instanceof EntityTeleportEvent) {
          Location loc = ((EntityTeleportEvent) event).getFrom();
          if (filterLocation(loc, minimum, maximum)) {
            return true;
          }
          loc = ((EntityTeleportEvent) event).getTo();
          if (filterLocation(loc, minimum, maximum)) {
            return true;
          }
        } else if (event instanceof EntityEvent) {
          Location loc = ((EntityEvent) event).getEntity().getLocation();
          if (filterLocation(loc, minimum, maximum)) {
            return true;
          }
        }
        if (event instanceof PlayerBedEnterEvent) {
          Location loc = ((PlayerBedEnterEvent) event).getBed().getLocation();
          if (filterLocation(loc, minimum, maximum)) {
            return true;
          }
        }
        if (event instanceof PlayerBedLeaveEvent) {
          Location loc = ((PlayerBedLeaveEvent) event).getBed().getLocation();
          if (filterLocation(loc, minimum, maximum)) {
            return true;
          }
        }
        if (event instanceof PlayerBucketEvent) {
          Location loc = ((PlayerBucketEvent) event).getBlock().getLocation();
          if (filterLocation(loc, minimum, maximum)) {
            return true;
          }
          loc = ((PlayerBucketEvent) event).getBlockClicked().getLocation();
          if (filterLocation(loc, minimum, maximum)) {
            return true;
          }
        }
        if (event instanceof PlayerInteractEvent) {
          PlayerInteractEvent pie = (PlayerInteractEvent) event;
          if (pie.hasBlock()) {
            Location loc = pie.getClickedBlock().getLocation();
            if (filterLocation(loc, minimum, maximum)) {
              return true;
            }
          }
        }
      }
      return false;
    }
    return true;
  }

}