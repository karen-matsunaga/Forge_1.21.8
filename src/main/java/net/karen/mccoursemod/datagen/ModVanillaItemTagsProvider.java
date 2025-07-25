package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.item.ModItems;
import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VanillaItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;

public class ModVanillaItemTagsProvider extends VanillaItemTagsProvider {
    public ModVanillaItemTagsProvider(PackOutput output,
                                      CompletableFuture<HolderLookup.Provider> lookupProvider,
                                      ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MccourseMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        // CUSTOM Items
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(ModItems.ALEXANDRITE.get());
        // CUSTOM Tools
        tag(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.ALEXANDRITE_HAMMER.get()).addTag(ModTags.Items.ALEXANDRITE_ARMOR);
        tag(ItemTags.MINING_LOOT_ENCHANTABLE).add(ModItems.ALEXANDRITE_HAMMER.get());
        tag(ItemTags.VANISHING_ENCHANTABLE).add(ModItems.ALEXANDRITE_HAMMER.get()).addTag(ModTags.Items.ALEXANDRITE_ARMOR);
        tag(ItemTags.MINING_ENCHANTABLE).add(ModItems.ALEXANDRITE_HAMMER.get());

        // CUSTOM Tools ingredients repair
        tag(ModTags.Items.ALEXANDRITE_TOOL_MATERIALS).add(ModItems.ALEXANDRITE.get()); // Tools
        tag(ModTags.Items.REPAIRS_ALEXANDRITE_ARMOR).add(ModItems.ALEXANDRITE.get()); // Armors

        // CUSTOM Armors
        tag(ModTags.Items.ALEXANDRITE_ARMOR).add(ModItems.ALEXANDRITE_HELMET.get(), ModItems.ALEXANDRITE_CHESTPLATE.get(),
                                          ModItems.ALEXANDRITE_LEGGINGS.get(), ModItems.ALEXANDRITE_BOOTS.get());

        // Trimmable armor
        tag(ItemTags.TRIMMABLE_ARMOR).addTag(ModTags.Items.ALEXANDRITE_ARMOR);

        // Enchanted armor
        tag(ItemTags.ARMOR_ENCHANTABLE).addTag(ModTags.Items.ALEXANDRITE_ARMOR);
        tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(ModItems.ALEXANDRITE_BOOTS.get());
        tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(ModItems.ALEXANDRITE_LEGGINGS.get());
        tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(ModItems.ALEXANDRITE_CHESTPLATE.get());
        tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(ModItems.ALEXANDRITE_HELMET.get());

        // Armor
        tag(ItemTags.FOOT_ARMOR).add(ModItems.ALEXANDRITE_BOOTS.get());
        tag(ItemTags.LEG_ARMOR).add(ModItems.ALEXANDRITE_LEGGINGS.get());
        tag(ItemTags.CHEST_ARMOR).add(ModItems.ALEXANDRITE_CHESTPLATE.get());
        tag(ItemTags.HEAD_ARMOR).add(ModItems.ALEXANDRITE_HELMET.get());

        // Level Charger
        tag(ModTags.Items.LEVEL_CHARGER_GENERAL).add(ModItems.LEVEL_CHARGER_PLUS.get(), ModItems.LEVEL_CHARGER_MINUS.get());
        tag(ModTags.Items.LEVEL_CHARGER_SPECIF).add(ModItems.LEVEL_CHARGER_PLUS_FORTUNE.get(),
                                                    ModItems.LEVEL_CHARGER_MINUS_FORTUNE.get());

        tag(ModTags.Items.LEVEL_CHARGER_ITEMS).addTag(ModTags.Items.LEVEL_CHARGER_GENERAL)
                                              .addTag(ModTags.Items.LEVEL_CHARGER_SPECIF);

        tag(ModTags.Items.LEVEL_CHARGER_ENCHANTABLE).addTag(ModTags.Items.LEVEL_CHARGER_GENERAL);
        tag(ModTags.Items.LEVEL_CHARGER_GREEN).add(ModItems.LEVEL_CHARGER_PLUS.get(), ModItems.LEVEL_CHARGER_PLUS_FORTUNE.get());
        tag(ModTags.Items.LEVEL_CHARGER_RED).add(ModItems.LEVEL_CHARGER_MINUS.get(), ModItems.LEVEL_CHARGER_MINUS_FORTUNE.get());
    }
}