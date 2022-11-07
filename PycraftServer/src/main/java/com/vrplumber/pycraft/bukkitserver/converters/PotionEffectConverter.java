package com.vrplumber.pycraft.bukkitserver.converters;

import java.security.InvalidParameterException;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import com.vrplumber.pycraft.bukkitserver.converters.Converter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class PotionEffectConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry;

    PotionEffectConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) throws InvalidParameterException {
        if (value instanceof Map) {
            Map asMap = (Map) value;
            PotionEffectType potionType;
            if (asMap.get("type") != null) {
                potionType = (PotionEffectType) this.registry.toJava(PotionEffectType.class, api, asMap.get("type"));
                if (potionType == null) {
                    throw new InvalidParameterException(
                        String.format("PotionEffect type is not known %s", asMap.toString()));
                }
            } else {
                throw new InvalidParameterException(
                        String.format("PotionEffect %s does not have type key", asMap.toString()));
            }
            Integer duration = 8000;
            Integer amplifier = 1;
            Boolean ambient = false;
            Boolean particles = false;
            Boolean icon = false;
            if (asMap.get("duration") != null) {
                duration = (Integer) this.registry.toJava(Integer.class, api, asMap.get("duration"));
            }
            if (asMap.get("amplifier") != null) {
                amplifier = (Integer) this.registry.toJava(Integer.class, api, asMap.get("amplifier"));
            }
            if (asMap.containsKey("ambient")) {
                ambient = (Boolean) this.registry.toJava(Boolean.class, api, asMap.get("ambient"));
                if (asMap.containsKey("particles")) {
                    particles = (Boolean) this.registry.toJava(Boolean.class, api, asMap.get("particles"));
                    if (asMap.containsKey("icon")) {
                        icon = (Boolean) this.registry.toJava(Boolean.class, api, asMap.get("icon"));
                    }
                }
            }
            return new PotionEffect(potionType, duration, amplifier, ambient, particles, icon);
            
        }
        throw new InvalidParameterException(
                String.format("Expect a dictionary/mapping for potion type: %s", value.toString()));

    }

    public String fromJava(PycraftAPI api, Object value) {
        PotionEffect potionEffect = (PotionEffect) value;
        Map<String, Object> asMap = new HashMap<String, Object>();
        asMap.put("__type__", potionEffect.getClass().getSimpleName());
        asMap.put("__namespace__", potionEffect.getClass().getSimpleName());
        asMap.put("type", potionEffect.getType());
        asMap.put("duration",potionEffect.getDuration());
        asMap.put("amplifier",potionEffect.getAmplifier());
        asMap.put("particles", potionEffect.hasParticles());
        asMap.put("ambient", potionEffect.isAmbient());
        asMap.put("icon", potionEffect.hasIcon());
        return registry.fromJava(api, asMap);
    }
}
