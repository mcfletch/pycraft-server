package com.vrplumber.pycraft.bukkitserver.converters;

import java.security.InvalidParameterException;
import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerEventConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry = null;
    Class targetClass = null;

    PlayerEventConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) {
        throw new InvalidParameterException(String.format("Do not current support python-side event generation"));
    }

    public String fromJava(PycraftAPI api, Object value) {
        PlayerEvent asEvent = (PlayerEvent) value;
        Map<String, Object> asMap = new HashMap<String, Object>();

        asMap.put("event_type", asEvent.getEventName());
        asMap.put("player", asEvent.getPlayer());
        if (asEvent instanceof PlayerAdvancementDoneEvent) {
            asMap.put("advancement", ((PlayerAdvancementDoneEvent) asEvent).getAdvancement());
        }
        if (asEvent instanceof PlayerBedEnterEvent) {
            asMap.put("bed", ((PlayerBedEnterEvent) asEvent).getBed());
        }
        if (asEvent instanceof PlayerBedLeaveEvent) {
            asMap.put("bed", ((PlayerBedLeaveEvent) asEvent).getBed());
        }
        if (asEvent instanceof PlayerBucketEvent) {
            PlayerBucketEvent asBucket = (PlayerBucketEvent) asEvent;
            asMap.put("block_clicked", asBucket.getBlockClicked());
            asMap.put("block_face", asBucket.getBlockFace());
            asMap.put("material", asBucket.getBucket());
            asMap.put("item_in_hand", asBucket.getItemStack());
        }
        if (asEvent instanceof PlayerInteractEvent) {
            PlayerInteractEvent asInteract = (PlayerInteractEvent) asEvent;
            asMap.put("action", asInteract.getAction());
            asMap.put("hand", asInteract.getHand());
            if (asInteract.hasBlock()) {
                asMap.put("block_face", asInteract.getBlockFace());
                asMap.put("block_clicked", asInteract.getClickedBlock());
                asMap.put("block_placement", asInteract.isBlockInHand());
            }
            if (asInteract.hasItem()) {
                asMap.put("item_in_hand", asInteract.getItem());
            }
            asMap.put("material", asInteract.getMaterial());
        }
        if (asEvent instanceof PlayerInteractAtEntityEvent) {
            asMap.put("clicked_position", ((PlayerInteractAtEntityEvent) asEvent).getClickedPosition());
        }
        return registry.fromJava(api, asMap);
    }
}
