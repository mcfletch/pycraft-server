package com.vrplumber.pycraft.bukkitserver.converters;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.util.Vector;
import org.bukkit.Location;

import com.vrplumber.pycraft.bukkitserver.NamespaceHandler;
import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import com.vrplumber.pycraft.bukkitserver.converters.Converter;

public class ReferenceConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry;

    ReferenceConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) {
        if (value instanceof List<?>) {
            Block block = (Block) registry.toJava(Block.class, api, value);
            return block.getState();
        } 
        if (value instanceof Integer) {
            Integer key = (Integer) value;
            Object reference = api.getReference(key);
            if (reference == null) {
                throw new InvalidParameterException(String.format("Reference %d is no longer held on the server", value));
            }
            if (!(finalType.isAssignableFrom(reference.getClass()))) {
                throw new InvalidParameterException(String.format("Reference %d does not reference a %s, references %s", value, finalType.getName(), reference.getClass().getName()));
            }
            return finalType.cast(reference);
        }

        throw new InvalidParameterException(String.format("Need an integer reference to a previously referenced object, got %s", value.toString()));
    }

    public String fromJava(PycraftAPI api, Object value) {
        int ref = api.holdReference(value);
        Map<String, Object> asMap = new HashMap<String, Object>();

        asMap.put("__type__", value.getClass().getCanonicalName());
        asMap.put("__namespace__", value.getClass().getSimpleName());
        asMap.put("__reference__", (Integer)ref);

        List<String> interfaces = new ArrayList<String>();
        for (Class provided : value.getClass().getInterfaces()) {
            interfaces.add(provided.getSimpleName());
        }
        asMap.put("interfaces", interfaces);
        return registry.fromJava(api, asMap);
    }
}
