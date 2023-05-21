package com.vrplumber.pycraft.bukkitserver.converters;

import java.security.InvalidParameterException;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import com.vrplumber.pycraft.bukkitserver.converters.Converter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import java.util.Arrays;

public class AttributeModifierConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry;

    AttributeModifierConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) throws InvalidParameterException {
        if (value instanceof Map<?, ?>) {
            Map<String,Object> asMap = (Map<String,Object>)value;
            String name = (String)this.registry.toJava(Attribute.class, api, asMap.get("name"));
            Double amount =  (Double)this.registry.toJava(Double.class, api, asMap.get("amount"));
            String operationName = (String)this.registry.toJava(String.class, api, asMap.get("operation"));
            if (name == null || amount == null || operationName == null) {
                throw new InvalidParameterException(String.format(
                        "Need {name:str, amount: float, operation: str} for AttributeModifier: %s",
                        asMap.toString()));
            }
            AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(operationName);
            AttributeModifier modifier =null;
            if (asMap.containsKey("uuid")) {
                UUID uuid = (UUID)this.registry.toJava(UUID.class, api, asMap.get("uuid"));
                if (asMap.containsKey("slot")) {
                    EquipmentSlot slot = (EquipmentSlot)this.registry.toJava(EquipmentSlot.class, api, asMap.get("slot"));
                    modifier = new AttributeModifier(uuid, name, (double)amount, operation, slot);
                } else {
                    modifier = new AttributeModifier(uuid, name, (double)amount, operation);
                }
            } else {
                modifier = new AttributeModifier(name, (double)amount, operation);
            }
            return (Object) modifier;


        } else {
            throw new InvalidParameterException(String.format(
                "Need {name:str, amount: float, operation: str} for AttributeModifier: %s",
                value.toString()));

        }
    }

    public String fromJava(PycraftAPI api, Object value) {
        AttributeModifier modifier = (AttributeModifier) value;
        Map<String, Object> asMap = new HashMap<String, Object>();
        asMap.put("__type__", modifier.getClass().getSimpleName());
        Class[] interfaces = modifier.getClass().getInterfaces();
        if (interfaces.length > 0) {
            asMap.put("__namespace__", modifier.getClass().getSimpleName());
        }
        asMap.put("name",modifier.getName());
        asMap.put("amount",modifier.getAmount());
        asMap.put("operation",modifier.getOperation().name());
        asMap.put("slot",modifier.getSlot().name());
        asMap.put("uuid", modifier.getUniqueId());

        return registry.fromJava(api, asMap);
    }
}
