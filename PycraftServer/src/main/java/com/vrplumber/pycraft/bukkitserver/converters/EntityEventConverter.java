package com.vrplumber.pycraft.bukkitserver.converters;

import java.security.InvalidParameterException;
import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class EntityEventConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry = null;
    Class targetClass = null;

    EntityEventConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) {
        throw new InvalidParameterException(String.format("Do not current support python-side event generation"));
    }

    public String fromJava(PycraftAPI api, Object value) {
        EntityEvent asEvent = (EntityEvent) value;
        Map<String, Object> asMap = new HashMap<String, Object>();

        asMap.put("event_type", asEvent.getEventName());
        asMap.put("entity", asEvent.getEntity());
        asMap.put("entity_type", asEvent.getEntityType());
        if (asEvent instanceof EntityDamageEvent) {
            asMap.put("final_damage", ((EntityDamageEvent) asEvent).getFinalDamage());
        }
        if (asEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent asEDEE = (EntityDamageByEntityEvent) asEvent;
            asMap.put("damager", asEDEE.getDamager());
        }
        if (asEvent instanceof EntityDamageByBlockEvent) {
            EntityDamageByBlockEvent asEDBE = (EntityDamageByBlockEvent) asEvent;
            asMap.put("damager", asEDBE.getDamager());
        }

        return registry.fromJava(api, asMap);
    }
}
