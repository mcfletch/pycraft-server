package com.vrplumber.pycraft.bukkitserver;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import com.vrplumber.pycraft.bukkitserver.APIServer;
import com.vrplumber.pycraft.bukkitserver.MessageHandler;
import com.vrplumber.pycraft.bukkitserver.EchoHandler;
import com.vrplumber.pycraft.bukkitserver.WorldHandler;
// import com.vrplumber.pycraft.bukkitserver.EntityHandler;
import com.vrplumber.pycraft.bukkitserver.PycraftMessage;
import com.vrplumber.pycraft.bukkitserver.IHandlerRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.Fluid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.entity.Damageable;

import java.util.logging.Handler;
import org.bukkit.block.data.type.*;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.meta.*;

class HandlerRegistry implements IHandlerRegistry {
    private static List<Class> handlers;
    public Set<String> notExposed;

    static {
        handlers = new ArrayList<Class>();
        // handlers.add(EntityHandler.class);
    }

    private static HandlerRegistry instance = null;

    static public HandlerRegistry getInstance() {
        if (instance == null) {
            instance = new HandlerRegistry();
        }
        return instance;
    }

    public HashMap<String, MessageHandler> implementations;

    public HandlerRegistry() {
        implementations = new HashMap<String, MessageHandler>();
        notExposed = new HashSet<String>();
        notExposed.add("org.bukkit.material.Directional");
        notExposed.add("org.bukkit.material.MaterialData");
    }

    public void registerImplementation(String name, MessageHandler payload) {
        implementations.put(name, payload);
        payload.register(this);
    }

    public Map<String, Object> describePlugin(Plugin plugin) {
        Map<String, Object> plugin_desc = new HashMap<String, Object>();
        PluginDescriptionFile description = plugin.getDescription();
        plugin_desc.put("version", description.getVersion());
        plugin_desc.put("api", description.getAPIVersion());
        plugin_desc.put("name", description.getName());
        return plugin_desc;

    }

    public Map<String, Object> getDescription(PycraftAPI api) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("type", "namespace");
        result.put("name", "");

        Map<String, Object> plugins = new HashMap<String, Object>();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            plugins.put(plugin.getName(), describePlugin(plugin));
        }
        result.put("plugins", plugins);

        Map<String, Object> server_desc = new HashMap<String, Object>();
        Server server = api.getServer();
        server_desc.put("version", server.getVersion());
        server_desc.put("bukkit_version", server.getBukkitVersion());
        result.put("server", server_desc);

        List<Map<String, Object>> commands = new ArrayList<Map<String, Object>>();
        for (String key : implementations.keySet()) {
            MessageHandler handler = implementations.get(key);
            commands.add(handler.getDescription());
        }
        result.put("commands", commands);
        return result;
    }

    public boolean shouldExpose(Class cls) {
        /* Should we expose the given class namespace??? */
        if (cls.getPackage() == null) {
            return false;
        }
        String packageName = cls.getPackage().getName();

        if (!packageName.startsWith("org.bukkit")) {
            return false;
        } else if (packageName.equals("org.bukkit.material") && cls.getSimpleName().equals("Colorable")) {
            return true;
        } else if (packageName.startsWith("org.bukkit.material")) {
            /* all of it is deprecated */
            return false;
        }
        /* Now some special cases.. */
        if (notExposed.contains(cls.getCanonicalName())) {
            return false;
        }
        return true;
    }

    public void exposeClass(Class cls) {
        /* Expose the class, its interfaces and any return types it needs */
        if (cls == null) {
            return;
        } else if (!shouldExpose((cls))) {

            return;
        }
        String name = cls.getSimpleName();
        if (implementations.get(name) == null) {
            registerImplementation(cls.getSimpleName(), new GenericHandler(cls));
            for (Class interfaceClass : cls.getInterfaces()) {
                if (shouldExpose(interfaceClass)) {
                    exposeClass(interfaceClass);
                }
            }
            for (java.lang.reflect.Method method : cls.getMethods()) {
                Class returnType = method.getReturnType();
                if (!returnType.isPrimitive()) {
                    if (shouldExpose(returnType)) {
                        exposeClass(returnType);
                    }
                }
                for (Class paramType : method.getParameterTypes()) {
                    if (!paramType.isPrimitive()) {
                        if (shouldExpose(paramType)) {
                            exposeClass(paramType);
                        }
                    }
                }
            }
        }

    }

    public void registerHandlers() {

        for (EntityType entityType : EntityType.values()) {
            exposeClass(entityType.getEntityClass());
        }
        exposeClass(Player.class);
        exposeClass(OfflinePlayer.class);
        exposeClass(BlockData.class);
        exposeClass(Block.class);
        exposeClass(Material.class);
        exposeClass(Location.class);
        exposeClass(Vector.class);
        exposeClass(ItemStack.class);
        exposeClass(Inventory.class);
        exposeClass(InventoryHolder.class);
        exposeClass(Enchantment.class);
        exposeClass(Entity.class);
        exposeClass(Damageable.class);
        exposeClass(EntityType.class);
        exposeClass(Server.class);
        exposeClass(Bukkit.class);
        exposeClass(Biome.class);
        exposeClass(BlockFace.class);
        exposeClass(PistonMoveReaction.class);
        exposeClass(Art.class);
        exposeClass(Fluid.class);
        exposeClass(Bamboo.class);
        exposeClass(Bed.class);
        exposeClass(Beehive.class);
        exposeClass(Bell.class);
        exposeClass(BrewingStand.class);
        exposeClass(BubbleColumn.class);
        exposeClass(Cake.class);
        exposeClass(Campfire.class);
        exposeClass(Chain.class);
        exposeClass(Chest.class);
        exposeClass(Cocoa.class);
        exposeClass(CommandBlock.class);
        exposeClass(Comparator.class);
        exposeClass(CoralWallFan.class);
        exposeClass(DaylightDetector.class);
        exposeClass(Dispenser.class);
        exposeClass(Door.class);
        exposeClass(EnderChest.class);
        exposeClass(EndPortalFrame.class);
        exposeClass(Farmland.class);
        exposeClass(Fence.class);
        exposeClass(Fire.class);
        exposeClass(Furnace.class);
        exposeClass(Gate.class);
        exposeClass(GlassPane.class);
        exposeClass(Grindstone.class);
        exposeClass(Hopper.class);
        exposeClass(Jigsaw.class);
        exposeClass(Jukebox.class);
        exposeClass(Ladder.class);
        exposeClass(Lantern.class);
        exposeClass(Leaves.class);
        exposeClass(Lectern.class);
        exposeClass(NoteBlock.class);
        exposeClass(Observer.class);
        exposeClass(Piston.class);
        exposeClass(PistonHead.class);
        exposeClass(RedstoneRail.class);
        exposeClass(RedstoneWallTorch.class);
        exposeClass(RedstoneWire.class);
        exposeClass(Repeater.class);
        exposeClass(RespawnAnchor.class);
        exposeClass(Sapling.class);
        exposeClass(Scaffolding.class);
        exposeClass(SeaPickle.class);
        exposeClass(Sign.class);
        exposeClass(Slab.class);
        exposeClass(Snow.class);
        exposeClass(Stairs.class);
        exposeClass(StructureBlock.class);
        exposeClass(Switch.class);
        exposeClass(TechnicalPiston.class);
        exposeClass(TNT.class);
        exposeClass(TrapDoor.class);
        exposeClass(Tripwire.class);
        exposeClass(TripwireHook.class);
        exposeClass(TurtleEgg.class);
        exposeClass(Wall.class);
        exposeClass(WallSign.class);
        exposeClass(MemorySection.class);

        // exposeClass(AxolotlBucketMeta.class);
        exposeClass(BannerMeta.class);
        exposeClass(BlockDataMeta.class);
        exposeClass(BlockStateMeta.class);
        exposeClass(BookMeta.class);
        // exposeClass(BundleMeta.class);
        exposeClass(CompassMeta.class);
        exposeClass(CrossbowMeta.class);
        exposeClass(Damageable.class);
        exposeClass(EnchantmentStorageMeta.class);
        exposeClass(FireworkEffectMeta.class);
        exposeClass(FireworkMeta.class);
        exposeClass(KnowledgeBookMeta.class);
        exposeClass(LeatherArmorMeta.class);
        exposeClass(MapMeta.class);
        exposeClass(PotionMeta.class);
        exposeClass(SkullMeta.class);
        exposeClass(SpawnEggMeta.class);
        exposeClass(SuspiciousStewMeta.class);
        exposeClass(TropicalFishBucketMeta.class);

        exposeClass(EntityEvent.class);
        exposeClass(PlayerEvent.class);
        exposeClass(PlayerInteractEvent.class);
        exposeClass(PlayerInteractAtEntityEvent.class);

        exposeClass(AbstractArrow.class);
        exposeClass(AbstractArrow.class);
        exposeClass(AbstractHorse.class);
        exposeClass(AbstractSkeleton.class);
        exposeClass(AbstractVillager.class);
        exposeClass(Ageable.class);
        exposeClass(Ambient.class);
        exposeClass(Animals.class);
        exposeClass(AnimalTamer.class);
        exposeClass(AreaEffectCloud.class);
        exposeClass(ArmorStand.class);
        exposeClass(Arrow.class);
        exposeClass(Axolotl.class);
        exposeClass(Bat.class);
        exposeClass(Bee.class);
        exposeClass(Blaze.class);
        exposeClass(Boat.class);
        exposeClass(Boss.class);
        exposeClass(Breedable.class);
        exposeClass(Cat.class);
        exposeClass(CaveSpider.class);
        exposeClass(ChestedHorse.class);
        exposeClass(Chicken.class);
        exposeClass(Cod.class);
        exposeClass(ComplexEntityPart.class);
        exposeClass(ComplexLivingEntity.class);
        exposeClass(Cow.class);
        exposeClass(Creature.class);
        exposeClass(Creeper.class);
        exposeClass(Damageable.class);
        exposeClass(Dolphin.class);
        exposeClass(Donkey.class);
        exposeClass(DragonFireball.class);
        exposeClass(Drowned.class);
        exposeClass(Egg.class);
        exposeClass(ElderGuardian.class);
        exposeClass(EnderCrystal.class);
        exposeClass(EnderDragon.class);
        exposeClass(EnderDragonPart.class);
        exposeClass(Enderman.class);
        exposeClass(Endermite.class);
        exposeClass(EnderPearl.class);
        exposeClass(EnderSignal.class);
        exposeClass(Entity.class);
        exposeClass(Evoker.class);
        exposeClass(EvokerFangs.class);
        exposeClass(ExperienceOrb.class);
        exposeClass(Explosive.class);
        exposeClass(FallingBlock.class);
        exposeClass(Fireball.class);
        exposeClass(Firework.class);
        exposeClass(Fish.class);
        exposeClass(FishHook.class);
        exposeClass(Flying.class);
        exposeClass(Fox.class);
        exposeClass(Ghast.class);
        exposeClass(Giant.class);
        exposeClass(GlowItemFrame.class);
        exposeClass(GlowSquid.class);
        exposeClass(Goat.class);
        exposeClass(Golem.class);
        exposeClass(Guardian.class);
        exposeClass(Hanging.class);
        exposeClass(Hoglin.class);
        exposeClass(Horse.class);
        exposeClass(HumanEntity.class);
        exposeClass(Husk.class);
        exposeClass(Illager.class);
        exposeClass(Illusioner.class);
        exposeClass(IronGolem.class);
        exposeClass(Item.class);
        exposeClass(ItemFrame.class);
        exposeClass(LargeFireball.class);
        exposeClass(LeashHitch.class);
        exposeClass(LightningStrike.class);
        exposeClass(LivingEntity.class);
        exposeClass(Llama.class);
        exposeClass(LlamaSpit.class);
        exposeClass(MagmaCube.class);
        exposeClass(Marker.class);
        exposeClass(Minecart.class);
        exposeClass(Mob.class);
        exposeClass(Monster.class);
        exposeClass(Mule.class);
        exposeClass(MushroomCow.class);
        exposeClass(NPC.class);
        exposeClass(Ocelot.class);
        exposeClass(Painting.class);
        exposeClass(Panda.class);
        exposeClass(Parrot.class);
        exposeClass(Phantom.class);
        exposeClass(Pig.class);
        exposeClass(Piglin.class);
        exposeClass(PiglinAbstract.class);
        exposeClass(PiglinBrute.class);
        exposeClass(PigZombie.class);
        exposeClass(Pillager.class);
        exposeClass(Player.class);
        exposeClass(PolarBear.class);
        exposeClass(Pose.class);
        exposeClass(Projectile.class);
        exposeClass(PufferFish.class);
        exposeClass(Rabbit.class);
        exposeClass(Raider.class);
        exposeClass(Ravager.class);
        exposeClass(Salmon.class);
        exposeClass(Sheep.class);
        exposeClass(Shulker.class);
        exposeClass(ShulkerBullet.class);
        exposeClass(Silverfish.class);
        exposeClass(Sittable.class);
        exposeClass(SizedFireball.class);
        exposeClass(Skeleton.class);
        exposeClass(SkeletonHorse.class);
        exposeClass(Slime.class);
        exposeClass(SmallFireball.class);
        exposeClass(Snowball.class);
        exposeClass(Snowman.class);
        exposeClass(SpawnCategory.class);
        exposeClass(SpectralArrow.class);
        exposeClass(Spellcaster.class);
        exposeClass(Spider.class);
        exposeClass(Squid.class);
        exposeClass(Steerable.class);
        exposeClass(Stray.class);
        exposeClass(Strider.class);
        exposeClass(Tameable.class);
        exposeClass(ThrowableProjectile.class);
        exposeClass(ThrownExpBottle.class);
        exposeClass(ThrownPotion.class);
        exposeClass(TNTPrimed.class);
        exposeClass(TraderLlama.class);
        exposeClass(Trident.class);
        exposeClass(TropicalFish.class);
        exposeClass(Turtle.class);
        exposeClass(Vehicle.class);
        exposeClass(Vex.class);
        exposeClass(Villager.class);
        exposeClass(Vindicator.class);
        exposeClass(WanderingTrader.class);
        exposeClass(WaterMob.class);
        exposeClass(Witch.class);
        exposeClass(Wither.class);
        exposeClass(WitherSkeleton.class);
        exposeClass(WitherSkull.class);
        exposeClass(Wolf.class);
        exposeClass(Zoglin.class);
        exposeClass(Zombie.class);
        exposeClass(ZombieHorse.class);
        exposeClass(ZombieVillager.class);

        registerImplementation("World", new WorldHandler());
        registerImplementation("echo", new EchoHandler());
        registerImplementation("subscribe", new SubscriptionHandler());
    }

    public MessageHandler getHandler(String name) {
        return implementations.get(name);
    }
}