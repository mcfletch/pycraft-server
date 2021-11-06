package com.vrplumber.pycraft.bukkitserver.converters;

import java.security.InvalidParameterException;
import org.bukkit.GameRule;

import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import com.vrplumber.pycraft.bukkitserver.converters.Converter;

public class GameRuleConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry;

    GameRuleConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) throws InvalidParameterException {
        if (value instanceof String) {
            GameRule result = GameRule.getByName((String) value);
            if (result == null) {
                throw new InvalidParameterException(String.format("Uknown GameRule: %s", (String) value));
            }
            return result;
        }
        throw new InvalidParameterException("Need the string name for a known GameRule");
    }

    public String fromJava(PycraftAPI api, Object value) {
        return registry.fromJava(api, ((GameRule) value).getName());
    }
}
