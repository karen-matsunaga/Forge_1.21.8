package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.enchantment.ModEnchantments;
import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantments;
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
        // Tooltip Order
        this.tag(EnchantmentTags.TOOLTIP_ORDER).addOptional(ModEnchantments.LIGHTNING_STRIKER)
                                               .addOptional(ModEnchantments.MESSAGE);

        // Not treasure enchantment
        this.tag(EnchantmentTags.NON_TREASURE).addOptional(ModEnchantments.LIGHTNING_STRIKER);

        // Villager Trade
        this.tag(EnchantmentTags.TRADEABLE).addOptional(ModEnchantments.LIGHTNING_STRIKER);

        // Enchant table
        this.tag(EnchantmentTags.IN_ENCHANTING_TABLE).addOptional(ModEnchantments.LIGHTNING_STRIKER);

        // Lightning Striker tag
        this.tag(ModTags.Enchantments.LIGHTNING_STRIKER_TAG).addOptional(ModEnchantments.LIGHTNING_STRIKER);

        // Message tag
        this.tag(ModTags.Enchantments.MESSAGE_TAG).addOptional(ModEnchantments.MESSAGE);

        // Pickaxe enchantments
        this.tag(ModTags.Enchantments.MINING_ENCHANTMENTS).add(Enchantments.EFFICIENCY)
                                                          .addTag(EnchantmentTags.MINING_EXCLUSIVE);

        // All tools enchantments
        this.tag(ModTags.Enchantments.DURABILITY_ENCHANTMENTS).add(Enchantments.MENDING)
                                                              .add(Enchantments.UNBREAKING);

        // Helmet enchantments
        this.tag(ModTags.Enchantments.HELMET_ENCHANTMENTS).add(Enchantments.RESPIRATION)
                                                          .add(Enchantments.AQUA_AFFINITY);

        // Chestplate enchantments
        this.tag(ModTags.Enchantments.CHESTPLATE_ENCHANTMENTS).add(Enchantments.THORNS);

        // Leggings enchantments
        this.tag(ModTags.Enchantments.LEGGINGS_ENCHANTMENTS).add(Enchantments.SWIFT_SNEAK);

        // Boots enchantments
        this.tag(ModTags.Enchantments.BOOTS_ENCHANTMENTS).addTag(EnchantmentTags.BOOTS_EXCLUSIVE)
                                                         .add(Enchantments.SOUL_SPEED)
                                                         .add(Enchantments.FEATHER_FALLING);

        // Sword enchantments
        this.tag(ModTags.Enchantments.SWORD_ENCHANTMENTS).addTag(ModTags.Enchantments.LIGHTNING_STRIKER_TAG)
                                                         .add(Enchantments.SHARPNESS)
                                                         .add(Enchantments.SMITE)
                                                         .add(Enchantments.BANE_OF_ARTHROPODS)
                                                         .add(Enchantments.SWEEPING_EDGE)
                                                         .add(Enchantments.KNOCKBACK)
                                                         .add(Enchantments.FIRE_ASPECT)
                                                         .add(Enchantments.LOOTING);

        // Bow enchantments
        this.tag(ModTags.Enchantments.BOW_ENCHANTMENTS).add(Enchantments.POWER)
                                                       .add(Enchantments.PUNCH)
                                                       .add(Enchantments.FLAME)
                                                       .add(Enchantments.INFINITY);

        // Crossbow enchantments
        this.tag(ModTags.Enchantments.CROSSBOW_ENCHANTMENTS).addTag(EnchantmentTags.CROSSBOW_EXCLUSIVE)
                                                            .add(Enchantments.QUICK_CHARGE);

        // Trident enchantments
        this.tag(ModTags.Enchantments.TRIDENT_ENCHANTMENTS).addTag(EnchantmentTags.RIPTIDE_EXCLUSIVE)
                                                           .add(Enchantments.IMPALING)
                                                           .add(Enchantments.RIPTIDE);

        // Fishing Rod enchantments
        this.tag(ModTags.Enchantments.FISHING_ENCHANTMENTS).add(Enchantments.LUCK_OF_THE_SEA)
                                                           .add(Enchantments.LURE);
        // Mace enchantments
        this.tag(ModTags.Enchantments.MACE_ENCHANTMENTS).add(Enchantments.BREACH)
                                                        .add(Enchantments.DENSITY)
                                                        .add(Enchantments.WIND_BURST);
    }
}