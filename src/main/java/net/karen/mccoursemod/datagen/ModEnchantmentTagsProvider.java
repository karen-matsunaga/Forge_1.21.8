package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.enchantment.ModEnchantments;
import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagsProvider extends EnchantmentTagsProvider {
    public ModEnchantmentTagsProvider(PackOutput output,
                                      CompletableFuture<HolderLookup.Provider> lookupProvider,
                                      @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MccourseMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(Tags.Enchantments.WEAPON_DAMAGE_ENHANCEMENTS).addOptional(ModEnchantments.LIGHTNING_STRIKER);
        this.tag(EnchantmentTags.DAMAGE_EXCLUSIVE).addOptional(ModEnchantments.LIGHTNING_STRIKER);
        this.tag(EnchantmentTags.IN_ENCHANTING_TABLE).addOptional(ModEnchantments.LIGHTNING_STRIKER); // Enchant table
        this.tag(ModTags.Enchantments.LIGHTNING_STRIKER_TAG).addOptional(ModEnchantments.LIGHTNING_STRIKER);
        this.tag(EnchantmentTags.NON_TREASURE).addOptional(ModEnchantments.LIGHTNING_STRIKER); // Not treasure enchantment
        this.tag(EnchantmentTags.TRADEABLE).addOptional(ModEnchantments.LIGHTNING_STRIKER); // Villager Trade
        this.tag(ModTags.Enchantments.MESSAGE_TAG).addOptional(ModEnchantments.MESSAGE);
    }
}