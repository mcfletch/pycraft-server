package com.vrplumber.pycraft.bukkitserver.converters;

import java.security.InvalidParameterException;

import org.bukkit.potion.PotionEffectType;
import java.util.List;
import java.util.ArrayList;

import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import com.vrplumber.pycraft.bukkitserver.converters.Converter;

public class PotionEffectTypeConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry;

    PotionEffectTypeConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) throws InvalidParameterException {
        if (value instanceof String) {
            PotionEffectType[] options = PotionEffectType.values();
            String test = ((String)value).toUpperCase();
            for (PotionEffectType option: options) {
                if (option.getName().equals(test)) {
                    return option;
                }
            }
            for (PotionEffectType option: options) {
                if (option.getKey().getKey().equals(test)) {
                    return option;
                }
            }
            List<String> optionNames = new ArrayList<String>();
            for (PotionEffectType option: options) {
                optionNames.add(option.getName());
            }
            throw new InvalidParameterException(
                String.format("PotionEffectType does not have member %s, known members: %s", (String)test,String.join(", ",optionNames)));
        }
        throw new InvalidParameterException(
            String.format("Need a string for PotionEffectType, got %s", value.toString()));
    }
    public String fromJava(PycraftAPI api, Object value) {
        return ((PotionEffectType)value).getName();
    }
}
