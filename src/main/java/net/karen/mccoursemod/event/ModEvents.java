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
import net.minecraft.util.RandomSource;
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
import net.minecraftforge.registries.ForgeRegistries;
import java.util.*;
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

        // FLY POTION
        flyPotion.ifPresent(fly -> builder.addMix(Potions.AWKWARD, Items.EMERALD, fly));

        if (flyPotionTwo.isPresent() && flyPotion.isPresent()) {
            flyPotionTwo.ifPresent(flyTwo -> builder.addMix(flyPotion.get(), Blocks.EMERALD_BLOCK.asItem(), flyTwo));
        }

        // NOTHING POTION
        nothingPotion.ifPresent(nothing -> builder.addMix(Potions.AWKWARD, Items.GLOWSTONE, nothing));

        // HASTE POTION
        hastePotion.ifPresent(haste -> builder.addMix(Potions.AWKWARD, Items.CARROT, haste));
    }

    // CUSTOM EVENT -> MULTIPLIER CUSTOM TAG (** TOOLTIP DESCRIPTION; MULTIPLIER VALUE **)
    @SubscribeEvent
    public static void onBlockBreakWithCustomEnchantments(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        LevelAccessor world = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        ItemStack tool = player.getMainHandItem();
        Level level = (Level) event.getLevel();
        if (tool.isEmpty() || !tool.has(ModDataComponentTypes.MULTIPLIER.get())) { return; }
        int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FORTUNE.getOrThrow(level), tool),
            multiplier = MultiplierItem.getMultiplierValue(tool);
        var blockTag = ForgeRegistries.BLOCKS.tags();
        if (!level.isClientSide() && world instanceof ServerLevel serverLevel) {
            boolean cancelVanillaDrop = false; // Adapt the drop according to the enchantment being true
            List<ItemStack> finalDrops = new ArrayList<>(); // Items caused by enchantments are stored in the list
            int oresFortune = serverLevel.random.nextInt(fortune + 1);
            if (multiplier > 0) { // * RAINBOW EFFECT *
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
                    if (fortune > 0) { rainbowDrop.setCount(rainbowDrop.getCount() * (1 + oresFortune)); }
                    finalDrops.add(rainbowDrop);
                    cancelVanillaDrop = true;
                }
            }
            if (multiplier > 0) { // * MORE ORES EFFECT *
                if (state.is(ModTags.Blocks.MORE_ORES_BREAK_BLOCK) && RandomSource.create().nextFloat() < 1F) {
                    var tagBlock = BuiltInRegistries.BLOCK.getTagOrEmpty(ModTags.Blocks.MORE_ORES_ALL_DROPS);
                    tagBlock.forEach((block -> {
                        ItemStack drop = new ItemStack(block.get().asItem()); // Increase ore drop with Multiplier enchantment
                        if (fortune > 0) { drop.setCount(drop.getCount() * (1 + oresFortune)); }
                        finalDrops.add(drop); // Break block and ore chance drop
                    }));
                    cancelVanillaDrop = true;
                }
            }
            if (multiplier > 0) { // * AUTO SMELT EFFECT *
                SingleRecipeInput singleRecipe = new SingleRecipeInput(new ItemStack(state.getBlock()));
                ServerLevel worldServer = serverLevel.getLevel();
                Optional<RecipeHolder<SmeltingRecipe>> recipe =
                        serverLevel.getServer().getRecipeManager().getRecipeFor(RecipeType.SMELTING, singleRecipe, worldServer);
                if (recipe.isPresent()) {
                    ItemStack result = recipe.get().value().assemble(singleRecipe, worldServer.registryAccess());
                    int drop = 1;
                    if (state.is(ModTags.Blocks.ALL_ORES) && fortune > 0) { drop += oresFortune; }
                    drop *= multiplier;
                    for (int i = 0; i < drop; i++) { finalDrops.add(result.copy()); }
                }
                cancelVanillaDrop = true;
            }
            if (multiplier > 1 && !finalDrops.isEmpty()) { // * MULTIPLIER EFFECT *
                List<ItemStack> multipliedDrops = new ArrayList<>();
                finalDrops.forEach(drop -> {
                    ItemStack multiplied = drop.copy(); // Copy ORIGINAL drop
                    if (drop.is(ModTags.Items.MULTIPLIER_ORES)) {
                        multiplied.setCount(drop.getCount() * multiplier); // Duplicate drops with Multiplier
                        multipliedDrops.add(multiplied);
                    }
                    else { multipliedDrops.add(multiplied); }});
                finalDrops.clear(); // Remove the non-multiplied originals
                finalDrops.addAll(multipliedDrops); // Adds the multiplied values
            }
            if (multiplier > 0 && !state.isAir()) { // * MAGNETIC EFFECT *
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