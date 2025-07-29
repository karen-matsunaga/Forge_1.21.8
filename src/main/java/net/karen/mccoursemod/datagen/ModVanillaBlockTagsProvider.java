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
                .addTag(Tags.Blocks.STORAGE_BLOCKS_COPPER).addTag(Tags.Blocks.STORAGE_BLOCKS_DIAMOND)
                .addTag(Tags.Blocks.STORAGE_BLOCKS_EMERALD).addTag(Tags.Blocks.STORAGE_BLOCKS_GOLD)
                .addTag(Tags.Blocks.STORAGE_BLOCKS_IRON).addTag(Tags.Blocks.STORAGE_BLOCKS_LAPIS)
                .addTag(Tags.Blocks.STORAGE_BLOCKS_NETHERITE).addTag(Tags.Blocks.STORAGE_BLOCKS_REDSTONE);

        // More Ores's tags -> More Ores I, II, III, IV, V, Max level
        this.tag(ModTags.Blocks.MORE_ORES_ONE_DROPS).add(Blocks.COAL_ORE, Blocks.COPPER_ORE);
        this.tag(ModTags.Blocks.MORE_ORES_TWO_DROPS).add(Blocks.IRON_ORE, Blocks.LAPIS_ORE);
        this.tag(ModTags.Blocks.MORE_ORES_THREE_DROPS).add(Blocks.REDSTONE_ORE, Blocks.GOLD_ORE);
        this.tag(ModTags.Blocks.MORE_ORES_FOUR_DROPS).add(Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE);
        this.tag(ModTags.Blocks.MORE_ORES_FIVE_DROPS).add(Blocks.ANCIENT_DEBRIS, Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE);
        this.tag(ModTags.Blocks.MORE_ORES_ALL_DROPS).addTag(ModTags.Blocks.MORE_ORES_ONE_DROPS)
                                                    .addTag(ModTags.Blocks.MORE_ORES_TWO_DROPS)
                                                    .addTag(ModTags.Blocks.MORE_ORES_THREE_DROPS)
                                                    .addTag(ModTags.Blocks.MORE_ORES_FOUR_DROPS)
                                                    .addTag(ModTags.Blocks.MORE_ORES_FIVE_DROPS);
    }
}