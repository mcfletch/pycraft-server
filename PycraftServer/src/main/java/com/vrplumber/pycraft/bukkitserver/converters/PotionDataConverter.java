package com.vrplumber.pycraft.bukkitserver.converters;

import java.security.InvalidParameterException;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import com.vrplumber.pycraft.bukkitserver.converters.Converter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class PotionDataConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry;

    PotionDataConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) throws InvalidParameterException {
        if (value instanceof Map) {
            Map asMap = (Map) value;
            PotionType potionType;
            if (asMap.get("type") != null) {
                potionType = (PotionType) this.registry.toJava(PotionType.class, api, asMap.get("type"));
            } else {
                throw new InvalidParameterException(
                        String.format("PotionData %s does not have type key", asMap.toString()));
            }
            Boolean extended = true;
            Boolean upgraded = true;
            if (asMap.get("extended") != null) {
                extended = (Boolean) this.registry.toJava(Boolean.class, api, asMap.get("extended"));
            }
            if (asMap.get("upgraded") != null) {
                upgraded = (Boolean) this.registry.toJava(Boolean.class, api, asMap.get("upgraded"));
            }
            if (!potionType.isExtendable()) {
                extended = false;
            }
            if (!potionType.isUpgradeable()) {
                upgraded = false;
            }
            return new PotionData(potionType, extended, upgraded);
        }
        throw new InvalidParameterException(
                String.format("Expect a dictionary/mapping for potion type: %s", value.toString()));

    }

    public String fromJava(PycraftAPI api, Object value) {
        PotionData potionData = (PotionData) value;
        Map<String, Object> asMap = new HashMap<String, Object>();
        asMap.put("__type__", potionData.getClass().getSimpleName());
        asMap.put("__namespace__", potionData.getClass().getSimpleName());
        asMap.put("type", potionData.getType());
        asMap.put("extended", potionData.isExtended());
        asMap.put("upgraded", potionData.isUpgraded());
        return registry.fromJava(api, asMap);
    }
}
