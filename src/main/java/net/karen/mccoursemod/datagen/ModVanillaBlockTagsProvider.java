package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VanillaBlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;

public class ModVanillaBlockTagsProvider extends VanillaBlockTagsProvider {
    public ModVanillaBlockTagsProvider(PackOutput output,
                                       CompletableFuture<HolderLookup.Provider> lookupProvider,
                                       ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MccourseMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ENCHANT.get(), ModBlocks.DISENCHANT_INDIVIDUAL.get(),
                                                      ModBlocks.DISENCHANT_GROUPED.get(), ModBlocks.ALEXANDRITE_BLOCK.get(),
                                                      ModBlocks.MAGIC.get());
        // CUSTOM Tier Tools - Alexandrite as Netherite tier
        tag(ModTags.Blocks.NEEDS_ALEXANDRITE_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_ALEXANDRITE_TOOL);

        // Ores
        this.tag(ModTags.Blocks.ALL_ORES).addTag(Tags.Blocks.ORES).addTag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE);

        // Rainbow
        this.tag(ModTags.Blocks.RAINBOW_DROPS).addTag(Tags.Blocks.STORAGE_BLOCKS_COAL)
                                              .addTag(Tags.Blocks.STORAGE_BLOCKS_COPPER)
                                              .addTag(Tags.Blocks.STORAGE_BLOCKS_DIAMOND)
                                              .addTag(Tags.Blocks.STORAGE_BLOCKS_EMERALD)
                                              .addTag(Tags.Blocks.STORAGE_BLOCKS_GOLD)
                                              .addTag(Tags.Blocks.STORAGE_BLOCKS_IRON)
                                              .addTag(Tags.Blocks.STORAGE_BLOCKS_LAPIS)
                                              .addTag(Tags.Blocks.STORAGE_BLOCKS_NETHERITE)
                                              .addTag(Tags.Blocks.STORAGE_BLOCKS_REDSTONE);

        this.tag(ModTags.Blocks.RAINBOW_ORES).add(Blocks.COAL_ORE).add(Blocks.DEEPSLATE_COAL_ORE)
                                             .add(Blocks.COPPER_ORE).add(Blocks.DEEPSLATE_COPPER_ORE)
                                             .add(Blocks.DIAMOND_ORE).add(Blocks.DEEPSLATE_DIAMOND_ORE)
                                             .add(Blocks.EMERALD_ORE).add(Blocks.DEEPSLATE_EMERALD_ORE)
                                             .add(Blocks.GOLD_ORE).add(Blocks.DEEPSLATE_GOLD_ORE)
                                             .add(Blocks.IRON_ORE).add(Blocks.DEEPSLATE_IRON_ORE)
                                             .add(Blocks.LAPIS_ORE).add(Blocks.DEEPSLATE_LAPIS_ORE)
                                             .add(Blocks.REDSTONE_ORE).add(Blocks.DEEPSLATE_REDSTONE_ORE)
                                             .add(Blocks.ANCIENT_DEBRIS);

        this.tag(ModTags.Blocks.RAINBOW_BLOCKS).add(Blocks.COAL_BLOCK)
                                               .add(Blocks.COPPER_BLOCK)
                                               .add(Blocks.DIAMOND_BLOCK)
                                               .add(Blocks.EMERALD_BLOCK)
                                               .add(Blocks.GOLD_BLOCK)
                                               .add(Blocks.IRON_BLOCK)
                                               .add(Blocks.LAPIS_BLOCK)
                                               .add(Blocks.NETHERITE_BLOCK)
                                               .add(Blocks.REDSTONE_BLOCK);

        // More Ores random drop effect
        this.tag(ModTags.Blocks.MORE_ORES_ALL_DROPS).add(Blocks.COAL_ORE)
                                                    .add(Blocks.COPPER_ORE)
                                                    .add(Blocks.IRON_ORE)
                                                    .add(Blocks.LAPIS_ORE)
                                                    .add(Blocks.REDSTONE_ORE)
                                                    .add(Blocks.GOLD_ORE)
                                                    .add(Blocks.DIAMOND_ORE)
                                                    .add(Blocks.EMERALD_ORE)
                                                    .add(Blocks.ANCIENT_DEBRIS)
                                                    .add(Blocks.NETHER_GOLD_ORE)
                                                    .add(Blocks.NETHER_QUARTZ_ORE);

        // More Ores break block effect
        this.tag(ModTags.Blocks.MORE_ORES_BREAK_BLOCK).add(Blocks.STONE);
    }
}