package com.vrplumber.pycraft.bukkitserver.converters;

import java.lang.Math;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.Enum;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.UUID;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.OfflinePlayer;

import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import com.vrplumber.pycraft.bukkitserver.converters.Converter;

public class OfflinePlayerConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry;

    OfflinePlayerConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) {
        if (value instanceof String) {
            try {
                UUID identifier = UUID.fromString((String) value);
                if (identifier != null) {
                    OfflinePlayer player = api.getServer().getOfflinePlayer(identifier);
                    return api.getServer().getEntity(identifier);
                }
            } catch (Exception e) {
            }
            String asString = (String) value;
            for (OfflinePlayer player : api.getServer().getOfflinePlayers()) {
                if (player.getName().toLowerCase().indexOf(asString) > -1) {
                    return player;
                }
            }
            throw new InvalidParameterException(
                    String.format("Could not find offline player by uuid or name: %s", value.toString()));
        }
        throw new InvalidParameterException(
                String.format("Need a UUID or name for player lookup in String format, got %s", value.toString()));
    }

    public Map<String, Object> offlinePlayerAsMapping(PycraftAPI api, Object value) {
        OfflinePlayer asPlayer = (OfflinePlayer) value;
        Map<String, Object> asMap = new HashMap<String, Object>();
        asMap.put("__type__", asPlayer.getClass().getSimpleName());
        asMap.put("__namespace__", "OfflinePlayer");
        try {
            asMap.put("uuid", asPlayer.getUniqueId());
        } catch (Exception e) {
            // Mock bukkit exception...
        }
        asMap.put("name", asPlayer.getName());
        asMap.put("banned",asPlayer.isBanned());
        asMap.put("online", asPlayer.isBanned());
        asMap.put("whitelisted", asPlayer.isWhitelisted());
        asMap.put("first_played",asPlayer.getFirstPlayed());
        asMap.put("last_played", (Double) ((double) asPlayer.getLastPlayed()));
        return asMap;
    }

    public String fromJava(PycraftAPI api, Object value) {
        return registry.fromJava(api, offlinePlayerAsMapping(api, value));
    }
}
