package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.item.ModEquipmentAssets;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import java.util.function.BiConsumer;

public class ModItemModelGenerators extends ItemModelGenerators {
    public ModItemModelGenerators(ItemModelOutput itemModelOutput,
                                  BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    // Registry all custom item models
    @Override
    public void run() {
        // Custom blocks
        blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get());
        blockTexture(ModBlocks.ENCHANT.get());
        blockTexture(ModBlocks.DISENCHANT_INDIVIDUAL.get());
        blockTexture(ModBlocks.DISENCHANT_GROUPED.get());
        blockTexture(ModBlocks.MAGIC.get());

        // Custom items
        this.generateFlatItem(ModItems.ALEXANDRITE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ModItems.LEVEL_CHARGER_GENERIC_PLUS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ModItems.LEVEL_CHARGER_GENERIC_MINUS.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ModItems.LEVEL_CHARGER_SPECIF_MINUS_FORTUNE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ModItems.LEVEL_CHARGER_SPECIF_PLUS_FORTUNE.get(), ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ModItems.GEM_EFFECT_SATURATION.get(), ModelTemplates.FLAT_ITEM);

        // Custom tools
        this.generateFlatItem(ModItems.ALEXANDRITE_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(ModItems.ALEXANDRITE_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(ModItems.ALEXANDRITE_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(ModItems.ALEXANDRITE_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(ModItems.ALEXANDRITE_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(ModItems.ALEXANDRITE_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        // Custom armors
        this.generateTrimmableItem(ModItems.ALEXANDRITE_HELMET.get(), ModEquipmentAssets.ALEXANDRITE,
                                   TRIM_PREFIX_HELMET, false);

        this.generateTrimmableItem(ModItems.ALEXANDRITE_CHESTPLATE.get(), ModEquipmentAssets.ALEXANDRITE,
                                   TRIM_PREFIX_CHESTPLATE, false);

        this.generateTrimmableItem(ModItems.ALEXANDRITE_LEGGINGS.get(), ModEquipmentAssets.ALEXANDRITE,
                                   TRIM_PREFIX_LEGGINGS, false);

        this.generateTrimmableItem(ModItems.ALEXANDRITE_BOOTS.get(), ModEquipmentAssets.ALEXANDRITE,
                                   TRIM_PREFIX_BOOTS, false);
    }

    // CUSTOM METHOD - Block texture
    protected void blockTexture(Block block) {
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(block)));
    }
}