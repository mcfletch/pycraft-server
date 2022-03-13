package com.vrplumber.pycraft.bukkitserver.converters;

import java.security.InvalidParameterException;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import com.vrplumber.pycraft.bukkitserver.converters.Converter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class ItemMetaConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry;

    ItemMetaConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) throws InvalidParameterException {
        ItemStack stack = (ItemStack) this.registry.toJava(ItemStack.class, api, value);
        return stack.getItemMeta();
    }

    public String fromJava(PycraftAPI api, Object value) {
        ItemMeta meta = (ItemMeta) value;
        Map<String, Object> asMap = new HashMap<String, Object>();
        asMap.put("__type__", meta.getClass().getSimpleName());
        Class[] interfaces = meta.getClass().getInterfaces();
        if (interfaces.length > 0) {
            asMap.put("__namespace__", interfaces[0].getSimpleName());
        }
        if (meta.hasAttributeModifiers()) {
            Map<String, Object> modifiers = new HashMap<String, Object>();
            meta.getAttributeModifiers().asMap().forEach((key, collection) -> {
                modifiers.put(key.toString(), collection.toArray());
            });
            asMap.put("modifiers", modifiers);
        }
        // if (meta.hasEnchants()) {
        // asMap.put("enchants", meta.getEnchants());
        // }
        // if (meta.hasCustomModelData()) {
        // asMap.put("customModelData", (Integer) meta.getCustomModelData());
        // }

        return registry.fromJava(api, asMap);
    }
}
