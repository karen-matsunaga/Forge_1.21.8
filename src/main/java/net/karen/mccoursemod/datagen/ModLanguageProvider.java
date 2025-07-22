package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, MccourseMod.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        // CUSTOM Blocks
        addBlock(ModBlocks.ENCHANT, "Enchant Block");
        addBlock(ModBlocks.ALEXANDRITE_BLOCK, "Alexandrite Block");
        // CUSTOM Items
        addItem(ModItems.ALEXANDRITE, "Alexandrite Gem");
        add("item.mccoursemod.enchant", "Enchant Block");
        add("item.mccoursemod.alexandrite_block", "Alexandrite Block");
        // CUSTOM Creative Mode Tabs
        add("creativetab.mccoursemod.mccourse_blocks", "Mccourse Blocks");
        add("creativetab.mccoursemod.mccourse_items", "Mccourse Items");
    }
}