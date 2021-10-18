#! /usr/bin/env python3
template = """@EventHandler(priority = EventPriority.NORMAL)
public void on%(class_name)s(%(class_name)s event) {
    %(class_name)s.getHandlerList();
    handleEvent(event);
}
"""
class_names = [s.strip() for s in '''
player.AsyncPlayerChatEvent
player.AsyncPlayerPreLoginEvent
# player.PlayerAchievementAwardedEvent
player.PlayerAdvancementDoneEvent
player.PlayerAnimationEvent
player.PlayerArmorStandManipulateEvent
player.PlayerBedEnterEvent
player.PlayerBedLeaveEvent
player.PlayerBucketEmptyEvent
# player.PlayerBucketEvent
player.PlayerBucketFillEvent
player.PlayerChangedMainHandEvent
player.PlayerChangedWorldEvent
player.PlayerChannelEvent
# player.PlayerChatEvent
# player.PlayerChatTabCompleteEvent
player.PlayerCommandPreprocessEvent
player.PlayerDropItemEvent
player.PlayerEditBookEvent
player.PlayerEggThrowEvent
# player.PlayerEvent
player.PlayerExpChangeEvent
player.PlayerFishEvent
player.PlayerGameModeChangeEvent
player.PlayerInteractAtEntityEvent
player.PlayerInteractEntityEvent
player.PlayerInteractEvent
player.PlayerItemBreakEvent
player.PlayerItemConsumeEvent
player.PlayerItemDamageEvent
player.PlayerItemHeldEvent
player.PlayerJoinEvent
player.PlayerKickEvent
player.PlayerLevelChangeEvent
player.PlayerLocaleChangeEvent
player.PlayerLoginEvent
player.PlayerMoveEvent
player.PlayerPickupArrowEvent
# player.PlayerPickupItemEvent
player.PlayerPortalEvent
# player.PlayerPreLoginEvent
player.PlayerQuitEvent
player.PlayerRegisterChannelEvent
player.PlayerResourcePackStatusEvent
player.PlayerRespawnEvent
player.PlayerShearEntityEvent
player.PlayerStatisticIncrementEvent
player.PlayerSwapHandItemsEvent
player.PlayerTeleportEvent
player.PlayerToggleFlightEvent
player.PlayerToggleSneakEvent
player.PlayerToggleSprintEvent
player.PlayerUnleashEntityEvent
player.PlayerUnregisterChannelEvent
player.PlayerVelocityEvent
block.BlockBreakEvent
block.BlockBurnEvent
block.BlockCanBuildEvent
block.BlockDamageEvent
block.BlockDispenseEvent
# block.BlockEvent
block.BlockExpEvent
block.BlockExplodeEvent
block.BlockFadeEvent
block.BlockFormEvent
block.BlockFromToEvent
block.BlockGrowEvent
block.BlockIgniteEvent
block.BlockMultiPlaceEvent
block.BlockPhysicsEvent
# block.BlockPistonEvent
block.BlockPistonExtendEvent
block.BlockPistonRetractEvent
block.BlockPlaceEvent
block.BlockRedstoneEvent
block.BlockSpreadEvent
block.CauldronLevelChangeEvent
block.EntityBlockFormEvent
block.LeavesDecayEvent
block.NotePlayEvent
block.SignChangeEvent
# entity.EnchantItemEvent
# entity.PrepareItemEnchantEvent
entity.AreaEffectCloudApplyEvent
entity.CreatureSpawnEvent
entity.CreeperPowerEvent
entity.EnderDragonChangePhaseEvent
entity.EntityAirChangeEvent
entity.EntityBreakDoorEvent
entity.EntityBreedEvent
entity.EntityChangeBlockEvent
entity.EntityCombustByBlockEvent
entity.EntityCombustByEntityEvent
entity.EntityCombustEvent
# entity.EntityCreatePortalEvent
entity.EntityDamageByBlockEvent
entity.EntityDamageByEntityEvent
entity.EntityDamageEvent
entity.EntityDeathEvent
# entity.EntityEvent
entity.EntityExplodeEvent
entity.EntityInteractEvent
entity.EntityPickupItemEvent
entity.EntityPortalEnterEvent
entity.EntityPortalEvent
entity.EntityPortalExitEvent
entity.EntityRegainHealthEvent
entity.EntityResurrectEvent
entity.EntityShootBowEvent
entity.EntitySpawnEvent
entity.EntityTameEvent
entity.EntityTargetEvent
entity.EntityTargetLivingEntityEvent
entity.EntityTeleportEvent
entity.EntityToggleGlideEvent
entity.EntityUnleashEvent
entity.ExpBottleEvent
entity.ExplosionPrimeEvent
entity.FireworkExplodeEvent
entity.FoodLevelChangeEvent
entity.HorseJumpEvent
entity.ItemDespawnEvent
entity.ItemMergeEvent
entity.ItemSpawnEvent
entity.LingeringPotionSplashEvent
entity.PigZapEvent
entity.PlayerDeathEvent
entity.PlayerLeashEntityEvent
entity.PotionSplashEvent
entity.ProjectileHitEvent
entity.ProjectileLaunchEvent
entity.SheepDyeWoolEvent
entity.SheepRegrowWoolEvent
entity.SlimeSplitEvent
entity.SpawnerSpawnEvent
entity.VillagerAcquireTradeEvent
entity.VillagerReplenishTradeEvent
hanging.HangingBreakByEntityEvent
hanging.HangingBreakEvent
# hanging.HangingEvent
hanging.HangingPlaceEvent
inventory.BrewEvent
inventory.BrewingStandFuelEvent
inventory.CraftItemEvent
inventory.FurnaceBurnEvent
inventory.FurnaceExtractEvent
inventory.FurnaceSmeltEvent
inventory.InventoryClickEvent
inventory.InventoryCloseEvent
inventory.InventoryCreativeEvent
inventory.InventoryDragEvent
# inventory.InventoryEvent
inventory.InventoryInteractEvent
inventory.InventoryMoveItemEvent
inventory.InventoryOpenEvent
# inventory.InventoryPickupItemEvent
inventory.PrepareAnvilEvent
inventory.PrepareItemCraftEvent
vehicle.VehicleBlockCollisionEvent
# vehicle.VehicleCollisionEvent
vehicle.VehicleCreateEvent
vehicle.VehicleDamageEvent
vehicle.VehicleDestroyEvent
vehicle.VehicleEnterEvent
vehicle.VehicleEntityCollisionEvent
# vehicle.VehicleEvent
vehicle.VehicleExitEvent
vehicle.VehicleMoveEvent
vehicle.VehicleUpdateEvent
weather.LightningStrikeEvent
weather.ThunderChangeEvent
weather.WeatherChangeEvent
# weather.WeatherEvent
# world.ChunkEvent
world.ChunkLoadEvent
world.ChunkPopulateEvent
world.ChunkUnloadEvent
world.PortalCreateEvent
world.SpawnChangeEvent
world.StructureGrowEvent
# world.WorldEvent
world.WorldInitEvent
world.WorldLoadEvent
world.WorldSaveEvent
world.WorldUnloadEvent
'''.strip().splitlines() if not s.startswith('#')]
def main():
    for module,class_name in [x.split('.') for x in class_names]:
        print('import org.bukkit.event.%(module)s.%(class_name)s;'%locals())
    for module,class_name in [x.split('.') for x in class_names]:
        print(template % locals())
    
    for module,class_name in [x.split('.') for x in class_names]:
        print(class_name)


if __name__ == '__main__':
    main()
