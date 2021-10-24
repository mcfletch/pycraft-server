package com.vrplumber.pycraft.bukkitserver;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Event;

// From generate_listeners
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
// end from generate_listeners

public class PycraftListener implements Listener {
    PycraftServerPlugin plugin = null;

    PycraftListener(PycraftServerPlugin plugin) {
        this.plugin = plugin;
    }

    public void handleEvent(Event event) {
        try {
            for (PycraftAPI client : this.plugin.getClients()) {
                client.onEvent(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // from generate_listeners
    @EventHandler(priority = EventPriority.NORMAL)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        AsyncPlayerChatEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        AsyncPlayerPreLoginEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent event) {
        PlayerAdvancementDoneEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerAnimationEvent(PlayerAnimationEvent event) {
        PlayerAnimationEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent event) {
        PlayerArmorStandManipulateEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
        PlayerBedEnterEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {
        PlayerBedLeaveEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        PlayerBucketEmptyEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBucketFillEvent(PlayerBucketFillEvent event) {
        PlayerBucketFillEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChangedMainHandEvent(PlayerChangedMainHandEvent event) {
        PlayerChangedMainHandEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
        PlayerChangedWorldEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChannelEvent(PlayerChannelEvent event) {
        PlayerChannelEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        PlayerCommandPreprocessEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        PlayerDropItemEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerEditBookEvent(PlayerEditBookEvent event) {
        PlayerEditBookEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerEggThrowEvent(PlayerEggThrowEvent event) {
        PlayerEggThrowEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
        PlayerExpChangeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerFishEvent(PlayerFishEvent event) {
        PlayerFishEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {
        PlayerGameModeChangeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        PlayerInteractAtEntityEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        PlayerInteractEntityEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        PlayerInteractEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerItemBreakEvent(PlayerItemBreakEvent event) {
        PlayerItemBreakEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        PlayerItemConsumeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
        PlayerItemDamageEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
        PlayerItemHeldEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        PlayerJoinEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerKickEvent(PlayerKickEvent event) {
        PlayerKickEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLevelChangeEvent(PlayerLevelChangeEvent event) {
        PlayerLevelChangeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLocaleChangeEvent(PlayerLocaleChangeEvent event) {
        PlayerLocaleChangeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        PlayerLoginEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        PlayerMoveEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerPickupArrowEvent(PlayerPickupArrowEvent event) {
        PlayerPickupArrowEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerPortalEvent(PlayerPortalEvent event) {
        PlayerPortalEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        PlayerQuitEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRegisterChannelEvent(PlayerRegisterChannelEvent event) {
        PlayerRegisterChannelEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerResourcePackStatusEvent(PlayerResourcePackStatusEvent event) {
        PlayerResourcePackStatusEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        PlayerRespawnEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerShearEntityEvent(PlayerShearEntityEvent event) {
        PlayerShearEntityEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerStatisticIncrementEvent(PlayerStatisticIncrementEvent event) {
        PlayerStatisticIncrementEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        PlayerSwapHandItemsEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        PlayerTeleportEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent event) {
        PlayerToggleFlightEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        PlayerToggleSneakEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        PlayerToggleSprintEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerUnleashEntityEvent(PlayerUnleashEntityEvent event) {
        PlayerUnleashEntityEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerUnregisterChannelEvent(PlayerUnregisterChannelEvent event) {
        PlayerUnregisterChannelEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerVelocityEvent(PlayerVelocityEvent event) {
        PlayerVelocityEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreakEvent(BlockBreakEvent event) {
        BlockBreakEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBurnEvent(BlockBurnEvent event) {
        BlockBurnEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockCanBuildEvent(BlockCanBuildEvent event) {
        BlockCanBuildEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockDamageEvent(BlockDamageEvent event) {
        BlockDamageEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockDispenseEvent(BlockDispenseEvent event) {
        BlockDispenseEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockExpEvent(BlockExpEvent event) {
        BlockExpEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockExplodeEvent(BlockExplodeEvent event) {
        BlockExplodeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockFadeEvent(BlockFadeEvent event) {
        BlockFadeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockFormEvent(BlockFormEvent event) {
        BlockFormEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockFromToEvent(BlockFromToEvent event) {
        BlockFromToEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockGrowEvent(BlockGrowEvent event) {
        BlockGrowEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockIgniteEvent(BlockIgniteEvent event) {
        BlockIgniteEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockMultiPlaceEvent(BlockMultiPlaceEvent event) {
        BlockMultiPlaceEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
        BlockPhysicsEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {
        BlockPistonExtendEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {
        BlockPistonRetractEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        BlockPlaceEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockRedstoneEvent(BlockRedstoneEvent event) {
        BlockRedstoneEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockSpreadEvent(BlockSpreadEvent event) {
        BlockSpreadEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCauldronLevelChangeEvent(CauldronLevelChangeEvent event) {
        CauldronLevelChangeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityBlockFormEvent(EntityBlockFormEvent event) {
        EntityBlockFormEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLeavesDecayEvent(LeavesDecayEvent event) {
        LeavesDecayEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onNotePlayEvent(NotePlayEvent event) {
        NotePlayEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChangeEvent(SignChangeEvent event) {
        SignChangeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAreaEffectCloudApplyEvent(AreaEffectCloudApplyEvent event) {
        AreaEffectCloudApplyEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        CreatureSpawnEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreeperPowerEvent(CreeperPowerEvent event) {
        CreeperPowerEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEnderDragonChangePhaseEvent(EnderDragonChangePhaseEvent event) {
        EnderDragonChangePhaseEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityBreakDoorEvent(EntityBreakDoorEvent event) {
        EntityBreakDoorEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityBreedEvent(EntityBreedEvent event) {
        EntityBreedEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        EntityChangeBlockEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityCombustByBlockEvent(EntityCombustByBlockEvent event) {
        EntityCombustByBlockEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityCombustByEntityEvent(EntityCombustByEntityEvent event) {
        EntityCombustByEntityEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityCombustEvent(EntityCombustEvent event) {
        EntityCombustEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent event) {
        EntityDamageByBlockEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        EntityDamageByEntityEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamageEvent(EntityDamageEvent event) {
        EntityDamageEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeathEvent(EntityDeathEvent event) {
        EntityDeathEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplodeEvent(EntityExplodeEvent event) {
        EntityExplodeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityInteractEvent(EntityInteractEvent event) {
        EntityInteractEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
        EntityPickupItemEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityPortalEnterEvent(EntityPortalEnterEvent event) {
        EntityPortalEnterEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityPortalEvent(EntityPortalEvent event) {
        EntityPortalEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityPortalExitEvent(EntityPortalExitEvent event) {
        EntityPortalExitEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityRegainHealthEvent(EntityRegainHealthEvent event) {
        EntityRegainHealthEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityResurrectEvent(EntityResurrectEvent event) {
        EntityResurrectEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityShootBowEvent(EntityShootBowEvent event) {
        EntityShootBowEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntitySpawnEvent(EntitySpawnEvent event) {
        EntitySpawnEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityTameEvent(EntityTameEvent event) {
        EntityTameEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityTargetEvent(EntityTargetEvent event) {
        EntityTargetEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event) {
        EntityTargetLivingEntityEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityTeleportEvent(EntityTeleportEvent event) {
        EntityTeleportEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityToggleGlideEvent(EntityToggleGlideEvent event) {
        EntityToggleGlideEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityUnleashEvent(EntityUnleashEvent event) {
        EntityUnleashEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onExpBottleEvent(ExpBottleEvent event) {
        ExpBottleEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onExplosionPrimeEvent(ExplosionPrimeEvent event) {
        ExplosionPrimeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onFireworkExplodeEvent(FireworkExplodeEvent event) {
        FireworkExplodeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        FoodLevelChangeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onHorseJumpEvent(HorseJumpEvent event) {
        HorseJumpEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onItemDespawnEvent(ItemDespawnEvent event) {
        ItemDespawnEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onItemMergeEvent(ItemMergeEvent event) {
        ItemMergeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onItemSpawnEvent(ItemSpawnEvent event) {
        ItemSpawnEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLingeringPotionSplashEvent(LingeringPotionSplashEvent event) {
        LingeringPotionSplashEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPigZapEvent(PigZapEvent event) {
        PigZapEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        PlayerDeathEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLeashEntityEvent(PlayerLeashEntityEvent event) {
        PlayerLeashEntityEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPotionSplashEvent(PotionSplashEvent event) {
        PotionSplashEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        ProjectileHitEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        ProjectileLaunchEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSheepDyeWoolEvent(SheepDyeWoolEvent event) {
        SheepDyeWoolEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSheepRegrowWoolEvent(SheepRegrowWoolEvent event) {
        SheepRegrowWoolEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSlimeSplitEvent(SlimeSplitEvent event) {
        SlimeSplitEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSpawnerSpawnEvent(SpawnerSpawnEvent event) {
        SpawnerSpawnEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVillagerAcquireTradeEvent(VillagerAcquireTradeEvent event) {
        VillagerAcquireTradeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVillagerReplenishTradeEvent(VillagerReplenishTradeEvent event) {
        VillagerReplenishTradeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent event) {
        HangingBreakByEntityEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onHangingBreakEvent(HangingBreakEvent event) {
        HangingBreakEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onHangingPlaceEvent(HangingPlaceEvent event) {
        HangingPlaceEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBrewEvent(BrewEvent event) {
        BrewEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBrewingStandFuelEvent(BrewingStandFuelEvent event) {
        BrewingStandFuelEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCraftItemEvent(CraftItemEvent event) {
        CraftItemEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onFurnaceBurnEvent(FurnaceBurnEvent event) {
        FurnaceBurnEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onFurnaceExtractEvent(FurnaceExtractEvent event) {
        FurnaceExtractEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onFurnaceSmeltEvent(FurnaceSmeltEvent event) {
        FurnaceSmeltEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClickEvent(InventoryClickEvent event) {
        InventoryClickEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        InventoryCloseEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryCreativeEvent(InventoryCreativeEvent event) {
        InventoryCreativeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryDragEvent(InventoryDragEvent event) {
        InventoryDragEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryInteractEvent(InventoryInteractEvent event) {
        InventoryInteractEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) {
        InventoryMoveItemEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        InventoryOpenEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPrepareAnvilEvent(PrepareAnvilEvent event) {
        PrepareAnvilEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent event) {
        PrepareItemCraftEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleBlockCollisionEvent(VehicleBlockCollisionEvent event) {
        VehicleBlockCollisionEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleCreateEvent(VehicleCreateEvent event) {
        VehicleCreateEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleDamageEvent(VehicleDamageEvent event) {
        VehicleDamageEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleDestroyEvent(VehicleDestroyEvent event) {
        VehicleDestroyEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleEnterEvent(VehicleEnterEvent event) {
        VehicleEnterEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleEntityCollisionEvent(VehicleEntityCollisionEvent event) {
        VehicleEntityCollisionEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleExitEvent(VehicleExitEvent event) {
        VehicleExitEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleMoveEvent(VehicleMoveEvent event) {
        VehicleMoveEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleUpdateEvent(VehicleUpdateEvent event) {
        VehicleUpdateEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLightningStrikeEvent(LightningStrikeEvent event) {
        LightningStrikeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onThunderChangeEvent(ThunderChangeEvent event) {
        ThunderChangeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWeatherChangeEvent(WeatherChangeEvent event) {
        WeatherChangeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChunkLoadEvent(ChunkLoadEvent event) {
        ChunkLoadEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChunkPopulateEvent(ChunkPopulateEvent event) {
        ChunkPopulateEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChunkUnloadEvent(ChunkUnloadEvent event) {
        ChunkUnloadEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPortalCreateEvent(PortalCreateEvent event) {
        PortalCreateEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSpawnChangeEvent(SpawnChangeEvent event) {
        SpawnChangeEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onStructureGrowEvent(StructureGrowEvent event) {
        StructureGrowEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldInitEvent(WorldInitEvent event) {
        WorldInitEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldLoadEvent(WorldLoadEvent event) {
        WorldLoadEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldSaveEvent(WorldSaveEvent event) {
        WorldSaveEvent.getHandlerList();
        handleEvent(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldUnloadEvent(WorldUnloadEvent event) {
        WorldUnloadEvent.getHandlerList();
        handleEvent(event);
    }
    // end from generate_listeners

}
