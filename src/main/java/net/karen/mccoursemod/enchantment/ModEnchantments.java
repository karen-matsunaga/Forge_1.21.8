package net.karen.mccoursemod.enchantment;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.enchantment.custom.LightningStrikerEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public class ModEnchantments {
    // Registry all custom enchantments -> enchantment name on JSON file
    public static final ResourceKey<Enchantment> LIGHTNING_STRIKER = createTag("lightning_striker");

    // CUSTOM METHOD - Registry all custom enchantments (JSON file)
    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var items = context.lookup(Registries.ITEM);

        // Lightning Striker - Sword tool
        register(context, LIGHTNING_STRIKER,
                 Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                                         items.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 5, 10,
                                                               Enchantment.dynamicCost(5, 8),
                                                               Enchantment.dynamicCost(25, 8),
                                                              2, EquipmentSlotGroup.MAINHAND))
                            .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                                        EnchantmentTarget.VICTIM, new LightningStrikerEnchantmentEffect(
                                        LevelBasedValue.perLevel(0.5F, 0.15F))));
    }

    // CUSTOM METHOD - Registry all custom enchantments -> DATA GEN
    private static void register(BootstrapContext<Enchantment> registry,
                                 ResourceKey<Enchantment> key,
                                 Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }

    // CUSTOM METHOD - Registry all custom enchantment resource keys
    private static ResourceKey<Enchantment> createTag(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, name));
    }
}