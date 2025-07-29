package net.karen.mccoursemod.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.Result;
import net.minecraftforge.event.level.BlockEvent;

import java.util.Map;

public class Util {
    public static boolean IGNORE_LAPIS = false; // Avoid consumption of LAPIS LAZULI

    private static final ThreadLocal<ItemStack> LAST_BOW_USED = new ThreadLocal<>(); // Store Miner Bow

    public static void setLastBowUsed(ItemStack stack) { LAST_BOW_USED.set(stack); } // Set Miner Bow

    public static ItemStack getLastBowUsed() { return LAST_BOW_USED.get(); } // Get Miner Bow

    public static void clear() { LAST_BOW_USED.remove(); } // Clear Miner Bow

    public static ClipContext.Block collider = ClipContext.Block.COLLIDER;
    public static ClipContext.Fluid none = ClipContext.Fluid.NONE, any = ClipContext.Fluid.ANY;

    public static HitResult.Type hitMiss = HitResult.Type.MISS, hitBlock = HitResult.Type.BLOCK;

    public static ItemStack empty = ItemStack.EMPTY;

    public static InteractionHand mainHand = InteractionHand.MAIN_HAND, offhand = InteractionHand.OFF_HAND;

    // CUSTOM METHOD - Player item sounds
    public static void sound(Player player, SoundEvent sound, float volume, float pitch) {
        player.level().playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch);
    }

    // CUSTOM METHOD - Player block sounds
    public static void soundBlock(Player player, SoundEvent sound, float volume, float pitch) {
        player.level().playSound(null, player.blockPosition(), sound, SoundSource.BLOCKS, volume, pitch);
    }

    // CUSTOM METHOD - Level neutral sounds
    public static void neutralSound(Level level, Player player,
                                    SoundEvent sound, float volume, float pitch) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundSource.NEUTRAL, volume, pitch);
    }

    // CUSTOM METHOD - Level neutral sounds with PITCH value
    public static void neutralSoundValue(Level level, Player player, SoundEvent sound, float volume) {
        double x = player.getX(), y = player.getY(), z = player.getZ();
        float pitch = 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F);
        level.playSound(null, x, y, z, sound, SoundSource.NEUTRAL, 0.5F + volume, pitch);
    }

    // CUSTOM METHOD - Level
    public static void playerSound(Level level, Player player,
                                   SoundEvent sound, float volume, float pitch) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundSource.PLAYERS, volume, pitch);
    }

    public static void particle(Player player, Entity entity) {
        ((ServerLevel) player.level()).sendParticles(ParticleTypes.CRIT, entity.getX(), entity.getY(0.5),
                entity.getZ(), 5, 0.2, 0.2, 0.2, 0.1);
    }

    // CUSTOM METHOD - NETWORK message (CLIENT -> SERVER)
//    public static void network(Object message) {
//        ModNetworks.PACKET_HANDLER.sendToServer(message);
//    }

    // CUSTOM METHOD - NETWORK message (CLIENT -> SERVER) [Dimension]
//    public static void sendPacket(Player player,
//                                  GlowingBlocksNetworkMessage.SyncedSavedData data) {
//        ModNetworks.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() ->
//                (ServerPlayer) player), new GlowingBlocksNetworkMessage.SavedDataSyncMessage(data));
//    }

    // CUSTOM METHOD - Glowing Blocks Map and World variables
//    public static GlowingBlocksNetworkMessage.SyncedSavedData var(Player player, int type) {
//        GlowingBlocksNetworkMessage.SyncedSavedData number = null;
//        switch (type) { case 1 -> number = GlowingBlocksNetworkMessage.Map.get(player.level());
//            case 2 -> number = GlowingBlocksNetworkMessage.World.get(player.level()); }
//        return number;
//    }

    // CUSTOM METHOD - Change stage of world variable
//    public static void change(GlowingBlocksNetworkMessage.World worldVar, boolean item,
//                              LevelAccessor world) {
//        if (worldVar.xray != item) { worldVar.xray = item; worldVar.syncData(world); }
//    }

    // CUSTOM METHOD - Item used has on slot
    public static ItemStack has(Player player, EquipmentSlot slot) {
        return player.getItemBySlot(slot); // Xray items - Enchanted Helmet or Metal Detector
    }

    // CUSTOM METHOD - Item used has on slot
    public static boolean slot(Player player, EquipmentSlot slot, TagKey<Item> item) {
        return player.getItemBySlot(slot).is(item); // Active Fly with Item
    }

    // CUSTOM METHOD - Item used on MAIN HAND
    public static boolean item(Player player, Item item) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == item; // Used item
    }

    // CUSTOM METHOD - Item used is on offhand or main hand
    public static ItemStack hasItem(Player player, InteractionHand hand) {
        return player.getItemInHand(hand);
    }

    // CUSTOM METHOD - Added Effect on full Armor
//    public static MobEffectInstance effect(MobEffect effect, int duration, int amplifier) {
//        return new MobEffectInstance(effect, duration, amplifier, true, false, false);
//    }

    // CUSTOM METHOD - Added effects on Helmet armor piece
//    public static MobEffectInstance helmet(MobEffect effect, int duration, int amplifier) {
//        return new MobEffectInstance(effect, duration, amplifier, false, false);
//    }

    // CUSTOM METHOD - Activated IMMORTAL enchantment
//    public static void activatedImmortalEnchantment(ItemEntity entity, ItemStack item) {
//        if (enchant(item, ModEnchantments.IMMORTAL.get()) > 0) {
//            entity.setInvulnerable(true);
//            entity.setUnlimitedLifetime(); // Does not disappear over time
//            entity.setPickUpDelay(10); // It can be collected after 0.5s
//        }
//    }

    // CUSTOM METHOD - Drop enchanted book and base item on ground [world]
    public static void dropItem(ServerLevel world, BlockPos pos, ItemStack stack) {
        ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
        item.setDeltaMovement(Vec3.ZERO);
        world.addFreshEntity(item);
    }

    // CUSTOM METHOD - Drop Multiplier enchantment items on ground [world]
//    public static void dropWorld(LivingDropsEvent event, Level level,
//                                 double x, double y, double z, ItemStack stack) {
//        ItemEntity drops = new ItemEntity(level, x, y, z, stack);
//        drops.setDeltaMovement(Vec3.ZERO);
//        event.getDrops().add(drops);
//    }

    // CUSTOM METHOD - Drop fish items
//    public static void dropFish(Level level,
//                                double x, double y, double z, ItemStack item) {
//        ItemEntity drop = new ItemEntity(level, x, y, z, item);
//        drop.setDeltaMovement(Vec3.ZERO);
//        level.addFreshEntity(drop);
//    }

    // CUSTOM METHOD - Drop ENCHANTED BOOK and BASE ITEM on ground
    public static void dropEnchanted(Level world, BlockPos pos, ItemStack item) {
        ItemEntity drop = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, item);
        drop.setDeltaMovement(Vec3.ZERO);
        world.addFreshEntity(drop);
    }

    // CUSTOM METHOD - GET items on placed above or below block
//    public static List<ItemEntity> getItem(Level level, BlockPos pos) {
//        return level.getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(0.5));
//    }

    // CUSTOM METHOD - GET player item
//    public static List<LivingEntity> getPlayer(Player player, TagKey<EntityType<?>> tag) {
//        return player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10),
//                entity -> entity.getType().is(tag) && entity != player);
//    }

    // CUSTOM METHOD - GET radius item
//    public static List<Entity> getRadiusItem(TickEvent.LevelTickEvent event) {
//        double x = 0, y = -100, z = 0, xSize = 10000, ySize = 500, zSize = 10000;
//        return event.level.getEntities(null, AABB.ofSize(new Vec3(x, y, z), xSize, ySize, zSize));
//    }

    // CUSTOM METHOD - GET hit block when broken blocks
//    public static BlockHitResult hitBlock(Player player, Vec3 eye, Vec3 reach) {
//        return player.level().clip(new ClipContext(eye, reach, collider, none, player));
//    }

    // CUSTOM METHOD - HURT tool
//    public static void hurtTool(ItemStack tool, int logCount, Player player) {
//        tool.hurtAndBreak(logCount, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
//    }

    // CUSTOM METHOD - Villager Profession and Villager Wandering trades
//    public static VillagerTrades.ItemListing createTrade(List<Item> items,
//                                                         List<Integer> levelCount, float multiplier) {
//        return (pTrader, pRandom) -> new MerchantOffer(new ItemStack(items.get(0), levelCount.get(0)),
//                new ItemStack(items.get(1), levelCount.get(1)), levelCount.get(2), levelCount.get(3), multiplier);
//    }

    // CUSTOM METHOD - Villager Profession trades
//    public static void normal(Int2ObjectMap<List<VillagerTrades.ItemListing>> trade,
//                              List<Item> items, int level, List<Integer> levelCount, float multiplier) {
//        trade.get(level).add(createTrade(items, levelCount, multiplier));
//    }

    // CUSTOM METHOD - Villager Profession trades with Mccourse Bottle XP
//    public static VillagerTrades.ItemListing normalTrade(ItemStack costStack1,
//                                                         ItemStack costStack2, Supplier<ItemStack> resultSupplier,
//                                                         int maxUses, int villagerXp, float priceMultiplier) {
//        return (pTrader, pRandom) -> {
//            ItemStack result = resultSupplier.get();
//            return new MerchantOffer(costStack1.copy(), costStack2.copy(), result.copy(), maxUses, villagerXp, priceMultiplier);
//        };
//    }

    // CUSTOM METHOD - Villager Wandering trades
//    public static void wandering(List<VillagerTrades.ItemListing> trade,
//                                 List<Item> items, List<Integer> levelCount, float multiplier) {
//        trade.add(createTrade(items, levelCount, multiplier));
//    }

    // CUSTOM METHOD - More Ores enchantment -> Block, chance ore drop, More Ores level and required level enchantment
    public static boolean is(BlockState state, Block block, float chance) {
        return state.is(block) && (Math.random() < chance);
    }

    // CUSTOM METHOD - Cancel vanilla drop
    public static void block(LevelAccessor world, BlockPos pos, Block block,
                             BlockEvent.BreakEvent event) {
        event.setResult(Result.DENY);
        if (world instanceof ServerLevel serverLevel) { serverLevel.setBlockAndUpdate(pos, block.defaultBlockState()); }
        else { world.setBlock(pos, block.defaultBlockState(), 3); }
    }

    // CUSTOM METHOD - Manually drops XP from blocks and items that have XP in vanilla
    public static void dropXp(BlockState state, ServerLevel serverLevel,
                              BlockPos pos, int fortune) {
        int exp = state.getExpDrop(serverLevel, serverLevel.random, pos, fortune, 0);
        if (exp > 0) { state.getBlock().popExperience(serverLevel, pos, exp); }
    }

    // CUSTOM METHOD - Manually drops XP from blocks and items that normally have no XP
    public static void setPlayerXP(Player player, Level level, int xp) {
        Vec3 position = new Vec3(player.getBlockX(), player.getBlockY(), player.getBlockZ());
        ExperienceOrb.award((ServerLevel) level, position, xp);
    }

    // CUSTOM METHOD - Check if it is a block
//    public static boolean isBlock(BlockState state, TagKey<Block> block) {
//        return state.is(block); // Check if it is a log or a leaf
//    }

    // CUSTOM METHOD - Check if it is a block using Block Tag
//    public static boolean isBlockTag(ITagManager<Block> registry, TagKey<Block> blocks, Block block) {
//        return registry.getTag(blocks).contains(block);
//    }

    // CUSTOM METHOD - Screen Overlay
//    public static void screen(RenderGuiOverlayEvent.Pre event, Minecraft mc, String message,
//                              int x, int y, int color) {
//        event.getGuiGraphics().drawString(mc.font, Component.literal(message).setStyle(Style.EMPTY.withColor(color)
//                .applyFormat(ChatFormatting.BOLD)), x, y, color, false);
//    }

    // CUSTOM METHOD - Light type
//    public static int light(Minecraft mc, LightLayer type, BlockPos pos) {
//        return mc.level != null ? mc.level.getLightEngine().getLayerListener(type).getLightValue(pos) : 0;
//    }

    // CUSTOM METHOD - Set newSpeed adapt with Efficiency enchantment -> Fixed speed mining
//    public static void newSpeed(PlayerEvent.BreakSpeed event, boolean hasEnchant,
//                                Player player, int value) {
//        if (hasEnchant) {
//            BlockState state = event.getState();
//            if ((!player.onGround() && !player.isUnderWater()) || player.isUnderWater()) {
//                event.setNewSpeed(event.getOriginalSpeed() * ((float) Math.sqrt(value) + 1));
//            }
//            if (state.is(ModTags.Blocks.BLOCK_FLY_BLOCK_SPEED)) {
//                event.setNewSpeed(event.getOriginalSpeed() * 2.5F + ((float) Math.sqrt(value) + 1));
//            }
//        }
//    }

    // CUSTOM METHOD - Block and age property are equals
//    public static boolean cropAge(BlockState state, Block block,
//                                  IntegerProperty property, int value) {
//        return state.getBlock().equals(block) && state.getValue(property).equals(value);
//    }

    // CUSTOM METHOD - Damage tool
//    public static void damageToolIfHoe(ItemStack tool, Player player) {
//        if (tool.getItem() instanceof HoeItem) {
//            tool.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
//        }
//    }

    // CUSTOM METHOD - Grow block
//    public static void grow(Level level, BlockPos pos, BlockState state, int flag) {
//        level.setBlock(pos, state, flag);
//    }

    // CUSTOM METHOD - Crop break
//    public static void crop(Block block, BlockState state, Level level, BlockPos pos,
//                            Player player, BlockEvent.BreakEvent event, ItemStack tool) {
//        event.setCanceled(true); // Cancels pattern break
//        Block.dropResources(state, level, pos, null, player, tool); // Drops items as if they had broken normally
//        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3); // Removes block crop
//        level.setBlock(pos, block.defaultBlockState(), 3); // Replants the initial stage of the plantation
//        damageToolIfHoe(tool, player); // Spend tool durability
//    }

    // CUSTOM METHOD - Stored Vault item
//    public static void setPreservedVault(NonNullList<ItemStack> type, List<ItemStack> store,
//                                         List<ItemStack> vault) {
//        for (int i = 0; i < type.size(); i++) {
//            ItemStack inventory = type.get(i);
//            // Copy of item WITH Eternal enchantment
//            if (!inventory.isEmpty() && enchant(inventory, ModEnchantments.ETERNAL.get()) > 0) { store.set(i, inventory.copy()); }
//            else { vault.add(inventory.copy()); } // Copy of item WITHOUT Eternal enchantment
//            type.set(i, ItemStack.EMPTY); // Added on typePreserve or vaultItems removes stack on Inventory, Armor and Offhand slots
//        }
//    }

    // CUSTOM METHOD - Restored Vault item
//    public static void setRestoredVault(NonNullList<ItemStack> type, List<ItemStack> restored) {
//        if (restored != null) { // Player receives items after death
//            // Added all items on Player inventory
//            for (int i = 0; i < restored.size(); i++) { if (!restored.get(i).isEmpty()) { type.set(i, restored.get(i)); } }
//        }
//    }

    // CUSTOM METHOD - Unlock enchantment item toss
//    public static void unlockItemToss(NonNullList<ItemStack> type, int index, ItemStack safeCopy) {
//        if (type.get(index).isEmpty() && !safeCopy.isEmpty()) {
//            type.set(index, safeCopy.copy());
//            safeCopy.setCount(0); // Empties after moving
//        }
//    }

    // CUSTOM METHOD - Unlock enchantment on key press
//    public static void unlockOnKeyPress(ItemStack main, UnlockEnchantmentAction action, int index) {
//        if (!main.isEmpty() && main.hasTag() && main.getTag() != null) { // MAIN HAND, ARMOR and OFFHAND
//            boolean locked = main.getTag().getBoolean("Locked");
//            network(new UnlockNetworkMessage(!locked, action, index));
//        }
//    }

    // CUSTOM METHOD - Mccourse Fishing Rod - Random item
//    public static void mccourseFishingRodDrops(RandomSource random, float chance,
//                                               int min, int max, List<ItemStack> drops, Item item) {
//        if (random.nextFloat() < chance) {
//            int fishAmount = min + random.nextInt(max);
//            drops.add(new ItemStack(item, fishAmount)); // Guaranteed drop
//        }
//    }

    // CUSTOM METHOD - Grouped Enchanted book
    public static void createGroupedEnchantedBook(Map<Holder<Enchantment>, Integer> enchant,
                                                  Level level, BlockPos pos) {
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        enchant.forEach((ench, integer) -> {
            if (integer > 0) { book.enchant(ench, integer); } });
        dropEnchanted(level, pos, book);
    }

    // CUSTOM METHOD - Enchanted item
    public static void createEnchantedItem(Item item,
                                           Map<Holder<Enchantment>, Integer> enchantment,
                                           Level level, BlockPos pos) {
        ItemStack stack = new ItemStack(item);
        enchantment.forEach((ench, integer) -> {
            if (integer > 0) { stack.enchant(ench, integer); }});
        dropEnchanted(level, pos, stack);
    }

    // CUSTOM METHOD - Grouped Enchantment
    public static void groupedEnch(Map<Holder<Enchantment>, Integer> enchantments,
                                   Level level, BlockPos pos) {
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        enchantments.forEach((ench, lvl) -> {
            if (lvl > 0) { book.enchant(ench, lvl); }
        });
        dropEnchanted(level, pos, book); // Drop grouped enchanted book WITH enchantment
    }

    // CUSTOM METHOD - INDIVIDUAL enchanted book
    public static void individualEnch(Map<Holder<Enchantment>, Integer> enchantments,
                                      Level level, BlockPos pos) {
        enchantments.forEach((enc, lvl) -> {
            if (lvl > 0) {
                ItemStack book = EnchantmentHelper.createBook(new EnchantmentInstance(enc, lvl));
                dropEnchanted(level, pos, book);
            }
        });
    }

    // CUSTOM METHOD - Player has enchantment on slots
    public static int hasEnchant(Holder<Enchantment> enchantment, Player player) {
        return EnchantmentHelper.getEnchantmentLevel(enchantment, player);
    }


    // CUSTOM METHOD - Consume Infinite item
    public static void consumeInfinite(Player player, ItemStack usedStack) {
        if (!player.getAbilities().instabuild) {
            usedStack.shrink(1); // Consume Infinite item
            player.containerMenu.broadcastChanges(); // Update the interface
        }
    }
}