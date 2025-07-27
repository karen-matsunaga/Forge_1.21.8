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
        tag(ModTags.Items.ALEXANDRITE_TOOLS).add(ModItems.ALEXANDRITE_SWORD.get(), ModItems.ALEXANDRITE_PICKAXE.get(),
                                                 ModItems.ALEXANDRITE_SHOVEL.get(), ModItems.ALEXANDRITE_AXE.get(),
                                                 ModItems.ALEXANDRITE_HOE.get(), ModItems.ALEXANDRITE_HAMMER.get());
        tag(ItemTags.AXES).add(ModItems.ALEXANDRITE_AXE.get());
        tag(ItemTags.PICKAXES).add(ModItems.ALEXANDRITE_PICKAXE.get(), ModItems.ALEXANDRITE_HAMMER.get());
        tag(ItemTags.SWORDS).add(ModItems.ALEXANDRITE_SWORD.get());
        tag(ItemTags.SHOVELS).add(ModItems.ALEXANDRITE_SHOVEL.get());
        tag(ItemTags.HOES).add(ModItems.ALEXANDRITE_HOE.get());

        // CUSTOM Tools ingredients repair
        tag(ModTags.Items.ALEXANDRITE_TOOL_MATERIALS).add(ModItems.ALEXANDRITE.get()); // Tools
        tag(ModTags.Items.REPAIRS_ALEXANDRITE_ARMOR).add(ModItems.ALEXANDRITE.get()); // Armors

        // CUSTOM Armors -> Trimmable armor + Enchanted Armor
        tag(ModTags.Items.ALEXANDRITE_ARMOR).add(ModItems.ALEXANDRITE_HELMET.get(), ModItems.ALEXANDRITE_CHESTPLATE.get(),
                                                 ModItems.ALEXANDRITE_LEGGINGS.get(), ModItems.ALEXANDRITE_BOOTS.get());
        tag(ItemTags.TRIMMABLE_ARMOR).addTag(ModTags.Items.ALEXANDRITE_ARMOR);
        tag(ItemTags.FOOT_ARMOR).add(ModItems.ALEXANDRITE_BOOTS.get());
        tag(ItemTags.LEG_ARMOR).add(ModItems.ALEXANDRITE_LEGGINGS.get());
        tag(ItemTags.CHEST_ARMOR).add(ModItems.ALEXANDRITE_CHESTPLATE.get());
        tag(ItemTags.HEAD_ARMOR).add(ModItems.ALEXANDRITE_HELMET.get());

        // Level Charger
        tag(ModTags.Items.LEVEL_CHARGER_GENERIC).add(ModItems.LEVEL_CHARGER_GENERIC_PLUS.get(),
                                                     ModItems.LEVEL_CHARGER_GENERIC_MINUS.get());

        tag(ModTags.Items.LEVEL_CHARGER_SPECIF).add(ModItems.LEVEL_CHARGER_SPECIF_PLUS_FORTUNE.get(),
                                                    ModItems.LEVEL_CHARGER_SPECIF_MINUS_FORTUNE.get());

        tag(ModTags.Items.LEVEL_CHARGER_ITEMS).addTag(ModTags.Items.LEVEL_CHARGER_GENERIC)
                                              .addTag(ModTags.Items.LEVEL_CHARGER_SPECIF);

        // Tooltip color
        tag(ModTags.Items.LEVEL_CHARGER_GREEN).add(ModItems.LEVEL_CHARGER_GENERIC_PLUS.get(),
                                                   ModItems.LEVEL_CHARGER_SPECIF_PLUS_FORTUNE.get());

        tag(ModTags.Items.LEVEL_CHARGER_RED).add(ModItems.LEVEL_CHARGER_GENERIC_MINUS.get(),
                                                 ModItems.LEVEL_CHARGER_SPECIF_MINUS_FORTUNE.get());

        // Enchantment
        tag(ModTags.Items.LEVEL_CHARGER_ENCHANTABLE).addTag(ModTags.Items.LEVEL_CHARGER_GENERIC);

        // Magic block items
        tag(ModTags.Items.MAGIC_BLOCK).add(ModItems.ALEXANDRITE.get());
    }
}