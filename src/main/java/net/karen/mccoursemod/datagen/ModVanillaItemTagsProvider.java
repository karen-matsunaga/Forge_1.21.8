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
        tag(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.ALEXANDRITE_HAMMER.get());
        tag(ItemTags.MINING_LOOT_ENCHANTABLE).add(ModItems.ALEXANDRITE_HAMMER.get());
        tag(ItemTags.VANISHING_ENCHANTABLE).add(ModItems.ALEXANDRITE_HAMMER.get());
        tag(ItemTags.MINING_ENCHANTABLE).add(ModItems.ALEXANDRITE_HAMMER.get());
        // CUSTOM Tools ingredients repair
        tag(ModTags.Items.ALEXANDRITE_TOOL_MATERIALS).add(ModItems.ALEXANDRITE.get());
    }
}