package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VanillaBlockTagsProvider;
import net.minecraft.tags.BlockTags;
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
    }
}