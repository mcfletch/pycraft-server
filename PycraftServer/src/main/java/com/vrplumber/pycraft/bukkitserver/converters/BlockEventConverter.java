package com.vrplumber.pycraft.bukkitserver.converters;

import java.security.InvalidParameterException;
import com.vrplumber.pycraft.bukkitserver.PycraftAPI;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockEventConverter implements Converter {
    /* Given a message and an index convert the value to an instance of T */
    PycraftConverterRegistry registry = null;
    Class targetClass = null;

    BlockEventConverter(PycraftConverterRegistry registry) {
        this.registry = registry;
    }

    public Object toJava(PycraftAPI api, Object value, Class finalType) {
        throw new InvalidParameterException(String.format("Do not current support python-side event generation"));
    }

    public String fromJava(PycraftAPI api, Object value) {
        BlockEvent asEvent = (BlockEvent) value;
        Map<String, Object> asMap = new HashMap<String, Object>();
        asMap.put("__type__", "Event");
        asMap.put("event_type", asEvent.getEventName());
        asMap.put("block", asEvent.getBlock());
        if (asEvent instanceof BlockExpEvent) {
            BlockExpEvent asExp = (BlockExpEvent) asEvent;
            asMap.put("exp_to_drop", asExp.getExpToDrop());
        }
        if (asEvent instanceof BlockBreakEvent) {
            BlockBreakEvent asBreak = (BlockBreakEvent) asEvent;
            asMap.put("will_drop", asBreak.isDropItems());
        }
        if (asEvent instanceof BlockPlaceEvent) {
            BlockPlaceEvent asPlace = (BlockPlaceEvent) asEvent;
            asMap.put("can_build", asPlace.canBuild());
            asMap.put("block_against", asPlace.getBlockAgainst());
            asMap.put("item_in_hand", asPlace.getItemInHand());
            asMap.put("hand", asPlace.getHand());
            asMap.put("player", asPlace.getPlayer());
        }
        if (asEvent instanceof BlockDamageEvent) {
            BlockDamageEvent asPlace = (BlockDamageEvent) asEvent;
            asMap.put("item_in_hand", asPlace.getItemInHand());
            asMap.put("player", asPlace.getPlayer());
        }
        if (asEvent instanceof BlockRedstoneEvent) {
            BlockRedstoneEvent asRedstone = (BlockRedstoneEvent) asEvent;
            asMap.put("new_current", asRedstone.getNewCurrent());
            asMap.put("old_current", asRedstone.getOldCurrent());
        }
        if (asEvent instanceof BlockSpreadEvent) {
            BlockSpreadEvent asSpread = (BlockSpreadEvent) asEvent;
            asMap.put("source", asSpread.getSource());
        }
        if (asEvent instanceof BlockPistonEvent) {
            BlockPistonEvent asPiston = (BlockPistonEvent) asEvent;
            asMap.put("direction", asPiston.getDirection());
            asMap.put("sticky", asPiston.isSticky());
        }

        return registry.fromJava(api, asMap);
    }
}
