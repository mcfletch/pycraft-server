package com.vrplumber.pycraft.bukkitserver.converters;

import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.potion.PotionData;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.lang.Class;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.security.InvalidParameterException;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.GameRule;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.Inventory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;

import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import com.vrplumber.pycraft.bukkitserver.converters.Converter;
import com.vrplumber.pycraft.bukkitserver.converters.StringConverter;
import com.vrplumber.pycraft.bukkitserver.converters.BooleanConverter;
import com.vrplumber.pycraft.bukkitserver.converters.ListConverter;
import com.vrplumber.pycraft.bukkitserver.converters.FloatConverter;
import com.vrplumber.pycraft.bukkitserver.converters.DoubleConverter;

import com.vrplumber.pycraft.bukkitserver.converters.ArrayConverter;
import com.vrplumber.pycraft.bukkitserver.converters.EnumConverter;
import com.vrplumber.pycraft.bukkitserver.converters.BlockConverter;
import com.vrplumber.pycraft.bukkitserver.converters.EntityConverter;
import com.vrplumber.pycraft.bukkitserver.converters.WorldConverter;
import com.vrplumber.pycraft.bukkitserver.converters.UUIDConverter;
import com.vrplumber.pycraft.bukkitserver.converters.GameRuleConverter;
import com.vrplumber.pycraft.bukkitserver.converters.ItemMetaConverter;
import com.vrplumber.pycraft.bukkitserver.converters.PotionDataConverter;

public class PycraftConverterRegistry {
    /* Registers .class => Converter.toJava(api, value, finalType) converter */
    public Map<Class<?>, Converter> mapping;
    public ArrayConverter arrayConverter;

    private class InterfaceConverter {
        Class cls;
        Converter converter;

        InterfaceConverter(Class cls, Converter converter) {
            this.cls = cls;
            this.converter = converter;
        }

        boolean target(Class finalCls) {
            return this.cls.isAssignableFrom(finalCls);
        }

        boolean match(Object value) {
            return cls.isInstance(value);
        }

    }

    public List<InterfaceConverter> interfaceConverters;

    public PycraftConverterRegistry() {
        mapping = new HashMap<Class<?>, Converter>();
        interfaceConverters = new ArrayList<InterfaceConverter>();
        arrayConverter = new ArrayConverter(this);
        mapping.put(String.class, new StringConverter());
        mapping.put(Boolean.class, new BooleanConverter());
        mapping.put(Integer.class, new IntegerConverter());
        mapping.put(Double.class, new DoubleConverter());
        mapping.put(Float.class, new FloatConverter());
        mapping.put(UUID.class, new UUIDConverter(this));
        mapping.put(int.class, new IntegerConverter());
        mapping.put(double.class, new DoubleConverter());
        mapping.put(boolean.class, new BooleanConverter());

        mapping.put(HashMap.class, new MapConverter(this));
        mapping.put(ArrayList.class, new ListConverter(this));
        mapping.put(String[].class, this.arrayConverter);
        mapping.put(Integer[].class, this.arrayConverter);
        mapping.put(Double[].class, this.arrayConverter);
        mapping.put(int[].class, this.arrayConverter);
        mapping.put(float[].class, this.arrayConverter);
        mapping.put(double[].class, this.arrayConverter);

        mapping.put(Vector.class, new VectorConverter(this, 3, Vector.class));
        mapping.put(Location.class, new LocationConverter(this, Location.class));
        mapping.put(AsyncPlayerChatEvent.class, new AsyncPlayerChatEventConverter(this, AsyncPlayerChatEvent.class));

        mapping.put(World.class, new WorldConverter(this));
        mapping.put(Player.class, new PlayerConverter(this));
        mapping.put(ItemStack.class, new ItemStackConverter(this));
        mapping.put(Enchantment.class, new EnchantmentConverter(this));
        mapping.put(EnchantmentWrapper.class, new EnchantmentConverter(this));
        mapping.put(GameRule.class, new GameRuleConverter(this));
        mapping.put(PotionData.class, new PotionDataConverter(this));

        // Now the interfaces, which require a linear scan, so we want to reduce
        // usage...
        interfaceConverters.add(new InterfaceConverter(List.class, new ListConverter(this)));
        interfaceConverters.add(new InterfaceConverter(Map.class, new MapConverter(this)));
        interfaceConverters.add(new InterfaceConverter(Enum.class, new EnumConverter(this)));
        interfaceConverters.add(new InterfaceConverter(Block.class, new BlockConverter(this)));
        interfaceConverters.add(new InterfaceConverter(Player.class, new PlayerConverter(this)));
        interfaceConverters.add(new InterfaceConverter(Entity.class, new EntityConverter(this)));
        // This is really *just* for the test server's mocked worlds
        interfaceConverters.add(new InterfaceConverter(World.class, new WorldConverter(this)));
        interfaceConverters.add(new InterfaceConverter(BlockData.class, new BlockDataConverter(this)));
        interfaceConverters.add(new InterfaceConverter(Inventory.class, new InventoryConverter(this)));
        interfaceConverters.add(new InterfaceConverter(Server.class, new ServerConverter(this)));
        interfaceConverters.add(new InterfaceConverter(ItemStack.class, new ItemStackConverter(this)));
        interfaceConverters.add(new InterfaceConverter(Enchantment.class, new EnchantmentConverter(this)));
        interfaceConverters.add(new InterfaceConverter(Collection.class, new CollectionConverter(this)));
        interfaceConverters.add(new InterfaceConverter(BlockEvent.class, new BlockEventConverter(this)));
        interfaceConverters.add(new InterfaceConverter(PlayerEvent.class, new PlayerEventConverter(this)));
        interfaceConverters.add(new InterfaceConverter(GameRule.class, new GameRuleConverter(this)));
        interfaceConverters.add(new InterfaceConverter(ItemMeta.class, new ItemMetaConverter(this)));

    }

    public Object toJava(Class targetType, PycraftAPI api, Object value) {
        Object ref = fromRef(api, value);
        if (ref != null) {
            if (targetType.isAssignableFrom(ref.getClass())) {
                return ref;
            } else {
                throw new InvalidParameterException(String.format(
                        "Reference to %s cannot be converted to a %s",
                        ref.getClass().getName(), targetType.getName()));
            }
        }
        Converter converter = mapping.get(targetType);
        if (converter == null) {
            converter = getConverterByTarget(targetType);
        }
        if (converter != null) {
            return converter.toJava(api, value, targetType);
        }
        if (value instanceof Boolean) {
            return value;
        }

        return (Object) null;
    }

    public Converter getConverterByTarget(Class targetType) {
        if (targetType.isArray()) {
            return this.arrayConverter;
        }
        for (InterfaceConverter converter : interfaceConverters) {
            if (converter.target(targetType)) {
                return converter.converter;
            }
        }
        return null;

    }

    public Converter getConverterByInterface(Object value) {
        /* Scan interfaceConverters looking for a match */
        for (InterfaceConverter converter : interfaceConverters) {
            if (converter.match(value)) {
                return converter.converter;
            }
        }
        return null;
    }

    public Object fromRef(PycraftAPI api, Object value) {
        if (value instanceof Map<?, ?>) {
            Map<String, Object> mapValue = (Map<String, Object>) value;
            Object name = mapValue.get("ref");
            if (name == null) {
                return null;
            }
            Object ref = api.namespace.get(name);
            if (ref == null) {
                throw new InvalidParameterException(
                        String.format("Unable to find reference with name %s", name));
            }
            return ref;
        }
        return null;
    }

    public String fromJava(PycraftAPI api, Object value) {
        if (value == null) {
            return "null";
        }
        Class cls = value.getClass();
        Converter converter = mapping.get(cls);

        if (converter == null) {
            if (cls.isArray()) {
                /* Use the String array's Converter */
                converter = this.arrayConverter;
            } else {
                converter = getConverterByInterface(value);
            }
        }
        if (converter != null) {
            return converter.fromJava(api, value);
        }
        api.getLogger().warning(String.format("No converter found for type %s", value.getClass().getName()));
        return "null";

    }
}
