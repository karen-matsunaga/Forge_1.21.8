package net.karen.mccoursemod.event;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.component.ModDataComponentTypes;
import net.karen.mccoursemod.item.custom.HammerItem;
import net.karen.mccoursemod.item.custom.MultiplierItem;
import net.karen.mccoursemod.potion.ModPotions;
import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.*;
import static net.karen.mccoursemod.item.custom.MultiplierItem.getEffectMultiplier;
import static net.karen.mccoursemod.util.Util.*;

@Mod.EventBusSubscriber(modid = MccourseMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    // CUSTOM EVENT - Hammer Item
    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) { return; }
            for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }
                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    // CUSTOM EVENT - Registry all custom potion recipes
    @SubscribeEvent
    public static void onBrewingRecipeRegister(BrewingRecipeRegisterEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        Optional<Holder<Potion>> flyPotion = ModPotions.FLY_POTION.getHolder();
        Optional<Holder<Potion>> flyPotionTwo = ModPotions.FLY_II_POTION.getHolder();
        Optional<Holder<Potion>> nothingPotion = ModPotions.NOTHING_POTION.getHolder();
        Optional<Holder<Potion>> hastePotion = ModPotions.HASTE_POTION.getHolder();
        // FLY I POTION
        flyPotion.ifPresent(fly -> builder.addMix(Potions.AWKWARD, Items.EMERALD, fly));
        // FLY II POTION
        if (flyPotionTwo.isPresent() && flyPotion.isPresent()) {
            flyPotionTwo.ifPresent(flyTwo -> builder.addMix(flyPotion.get(), Blocks.EMERALD_BLOCK.asItem(), flyTwo));
        }
        // NOTHING POTION
        nothingPotion.ifPresent(nothing -> builder.addMix(Potions.AWKWARD, Items.GLOWSTONE, nothing));
        // HASTE POTION
        hastePotion.ifPresent(haste -> builder.addMix(Potions.AWKWARD, Items.CARROT, haste));
    }

    // CUSTOM EVENT -> AUTO SMELT, MAGNET, MORE ORES, MULTIPLIER and RAINBOW custom effects
    @SubscribeEvent
    public static void onBlockBreakWithCustomEnchantments(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        LevelAccessor world = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        ItemStack tool = player.getMainHandItem();
        Level level = (Level) event.getLevel();
        if (tool.isEmpty()) { return; } // * PROBLEMS *
        int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FORTUNE.getOrThrow(level), tool);
        boolean hasRainbow = MultiplierItem.getMultiplierBool(tool, ModDataComponentTypes.RAINBOW.get()),
                hasMoreOres = MultiplierItem.getMultiplierBool(tool, ModDataComponentTypes.MORE_ORES.get()),
                hasAutoSmelt = MultiplierItem.getMultiplierBool(tool, ModDataComponentTypes.AUTO_SMELT.get()),
                hasMagnet = MultiplierItem.getMultiplierBool(tool, ModDataComponentTypes.MAGNET.get());
        if (!level.isClientSide() && world instanceof ServerLevel serverLevel) {
            boolean cancelVanillaDrop = false; // Adapt the drop according to the enchantment being true
            List<ItemStack> finalDrops = new ArrayList<>(); // Items caused by enchantments are stored in the list
            int oresFortune = serverLevel.random.nextInt(fortune + 1),
                hasFortune = (fortune > 0) ? (1 + oresFortune) : 1;
            if (hasRainbow) { // * RAINBOW EFFECT *
                Map<Block, TagKey<Block>> rainbowMap = Map.ofEntries(Map.entry(Blocks.COAL_BLOCK, Tags.Blocks.ORES_COAL),
                Map.entry(Blocks.COPPER_BLOCK, Tags.Blocks.ORES_COPPER), Map.entry(Blocks.DIAMOND_BLOCK, Tags.Blocks.ORES_DIAMOND),
                Map.entry(Blocks.EMERALD_BLOCK, Tags.Blocks.ORES_EMERALD), Map.entry(Blocks.GOLD_BLOCK, Tags.Blocks.ORES_GOLD),
                Map.entry(Blocks.IRON_BLOCK, Tags.Blocks.ORES_IRON), Map.entry(Blocks.LAPIS_BLOCK, Tags.Blocks.ORES_LAPIS),
                Map.entry(Blocks.REDSTONE_BLOCK, Tags.Blocks.ORES_REDSTONE),
                Map.entry(Blocks.NETHERITE_BLOCK, Tags.Blocks.ORES_NETHERITE_SCRAP));
                for (Map.Entry<Block, TagKey<Block>> entry : rainbowMap.entrySet()) {
                    if (state.is(entry.getValue())) {
                        block(world, pos, entry.getKey(), event);
                        return;
                    }
                }
                if (state.is(ModTags.Blocks.RAINBOW_DROPS)) {
                    ItemStack rainbowDrop = new ItemStack(state.getBlock());
                    rainbowDrop.setCount((rainbowDrop.getCount() * hasFortune) *
                                         (getEffectMultiplier(tool, ModDataComponentTypes.RAINBOW.get(), 1)));
                    finalDrops.add(rainbowDrop);
                    cancelVanillaDrop = true;
                }
            }
            if (hasMoreOres) { // * MORE ORES EFFECT *
                if (state.is(ModTags.Blocks.MORE_ORES_BREAK_BLOCK)) {
                    Iterable<Holder<Block>> tagBlock = BuiltInRegistries.BLOCK.getTagOrEmpty(ModTags.Blocks.MORE_ORES_ALL_DROPS);
                    tagBlock.forEach((block -> {
                        if (serverLevel.random.nextFloat() < 0.01F) {
                            ItemStack drop = new ItemStack(block.get().asItem()); // Increase ore drop with Multiplier enchantment
                            drop.setCount((drop.getCount() * hasFortune) *
                                          (getEffectMultiplier(tool, ModDataComponentTypes.MORE_ORES.get(), 1)));
                            finalDrops.add(drop); // Break block and ore chance drop
                        }
                    }));
                    // Break block and ore chance drop
                    finalDrops.addAll(Block.getDrops(state, serverLevel, pos, null, player, tool));
                    cancelVanillaDrop = true;
                }
            }
            if (hasAutoSmelt) { // * AUTO SMELT EFFECT *
                SingleRecipeInput singleRecipe = new SingleRecipeInput(new ItemStack(state.getBlock()));
                ServerLevel worldServer = serverLevel.getLevel();
                Optional<RecipeHolder<SmeltingRecipe>> recipe =
                        serverLevel.getServer().getRecipeManager().getRecipeFor(RecipeType.SMELTING, singleRecipe, worldServer);
                recipe.ifPresent(result -> {
                    ItemStack recipeValue = result.value().assemble(singleRecipe, worldServer.registryAccess()),
                                     drop = new ItemStack(recipeValue.getItem().asItem());
                    if (state.is(ModTags.Blocks.AUTO_SMELT_ORES)) {
                        drop.setCount((drop.getCount() * hasFortune) *
                                      (getEffectMultiplier(tool, ModDataComponentTypes.AUTO_SMELT.get(), 1)));
                    }
                    finalDrops.add(drop);
                });
                if (recipe.isEmpty()) { finalDrops.addAll(Block.getDrops(state, serverLevel, pos, null, player, tool)); }
                cancelVanillaDrop = true;
            }
            if (hasMagnet && !state.isAir()) { // * MAGNETIC EFFECT *
                if (finalDrops.isEmpty()) { // FinalDrops empty list added all items on it is
                    finalDrops.addAll(Block.getDrops(state, serverLevel, pos, null, player, tool));
                }
                finalDrops.forEach(drop -> { // FinalDrops list added on Player's inventory
                    if (!player.getInventory().add(drop)) { player.drop(drop, false); }});
                block(serverLevel, pos, Blocks.AIR, event);
                dropXp(state, serverLevel, pos, fortune);
                return;
            }
            if (cancelVanillaDrop) { // FinalDrops list accumulate drop on world
                block(serverLevel, pos, Blocks.AIR, event);
                finalDrops.forEach(drop -> dropItem(serverLevel, pos, drop));
                dropXp(state, serverLevel, pos, fortune);
            }
        }
    }
}