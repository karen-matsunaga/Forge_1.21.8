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
        addItem(ModItems.LEVEL_CHARGER_GENERIC_PLUS, "Level Charger Generic Plus");
        addItem(ModItems.LEVEL_CHARGER_GENERIC_MINUS, "Level Charger Generic Minus");
        addItem(ModItems.LEVEL_CHARGER_SPECIF_MINUS_FORTUNE, "Level Charger Specif Minus Fortune");
        addItem(ModItems.LEVEL_CHARGER_SPECIF_PLUS_FORTUNE, "Level Charger Specif Plus Fortune");
        // CUSTOM Tools
        addItem(ModItems.ALEXANDRITE_HAMMER, "Alexandrite Hammer");
        addItem(ModItems.ALEXANDRITE_SWORD, "Alexandrite Sword");
        addItem(ModItems.ALEXANDRITE_PICKAXE, "Alexandrite Pickaxe");
        addItem(ModItems.ALEXANDRITE_SHOVEL, "Alexandrite Shovel");
        addItem(ModItems.ALEXANDRITE_AXE, "Alexandrite Axe");
        addItem(ModItems.ALEXANDRITE_HOE, "Alexandrite Hoe");
        // CUSTOM Armors
        addItem(ModItems.ALEXANDRITE_HELMET, "Alexandrite Helmet");
        addItem(ModItems.ALEXANDRITE_CHESTPLATE, "Alexandrite Chestplate");
        addItem(ModItems.ALEXANDRITE_LEGGINGS, "Alexandrite Leggings");
        addItem(ModItems.ALEXANDRITE_BOOTS, "Alexandrite Boots");
        // CUSTOM block item
        add("item.mccoursemod.enchant", "Enchant Block");
        add("item.mccoursemod.alexandrite_block", "Alexandrite Block");
        // CUSTOM Enchantment
        add("enchantment.mccoursemod.lightning_striker", "Lightning Striker");
        add("enchantment.mccoursemod.message", "Message");
        // CUSTOM Creative Mode Tabs
        add("creativetab.mccoursemod.mccourse_blocks", "Mccourse Blocks");
        add("creativetab.mccoursemod.mccourse_items", "Mccourse Items");
        // CUSTOM Advancements
        // ROOT
        add("advancement.mccoursemod.root.title", "Mccourse Mod");
        add("advancement.mccoursemod.root.description", "Collect all exclusive ores, armors, tools, etc. on your journey!");
        // ALEXANDRITE VANILLA TOOLS
        add("advancement.mccoursemod.alexandritevanillatools.title", "Alexandrite Vanilla Tools");
        add("advancement.mccoursemod.alexandritevanillatools.description", "Collect all exclusive Alexandrite vanilla tools!");
        // ALEXANDRITE CUSTOM TOOLS
        add("advancement.mccoursemod.alexandritecustomtools.title", "Alexandrite Custom Tools");
        add("advancement.mccoursemod.alexandritecustomtools.description", "Collect all exclusive Alexandrite custom tools!");
        // ALEXANDRITE CUSTOM ARMORS
        add("advancement.mccoursemod.alexandritecustomarmors.title", "Alexandrite Custom Armors");
        add("advancement.mccoursemod.alexandritecustomarmors.description", "Collect all exclusive Alexandrite custom armors!");
    }
}