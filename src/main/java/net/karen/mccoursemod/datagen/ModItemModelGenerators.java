package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import java.util.function.BiConsumer;

public class ModItemModelGenerators extends ItemModelGenerators {
    public ModItemModelGenerators(ItemModelOutput itemModelOutput,
                                  BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    // Registry all custom item models
    @Override
    public void run() {
        // Custom items
        this.generateFlatItem(ModItems.ALEXANDRITE.get(), ModelTemplates.FLAT_ITEM);
        // Custom blocks
        this.itemModelOutput.accept(ModBlocks.ALEXANDRITE_BLOCK.get().asItem(),
                                    ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(ModBlocks.ALEXANDRITE_BLOCK.get())));
        this.itemModelOutput.accept(ModBlocks.ENCHANT.get().asItem(),
                                    ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(ModBlocks.ENCHANT.get())));
    }
}