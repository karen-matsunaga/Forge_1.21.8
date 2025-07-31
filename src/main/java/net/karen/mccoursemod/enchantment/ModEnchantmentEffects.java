package net.karen.mccoursemod.enchantment;

import com.mojang.serialization.MapCodec;
import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.enchantment.custom.AutoSmeltEnchantmentEffect;
import net.karen.mccoursemod.enchantment.custom.LightningStrikerEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, MccourseMod.MOD_ID);

    public static final DeferredRegister<MapCodec<? extends EnchantmentValueEffect>> VALUE_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_VALUE_EFFECT_TYPE, MccourseMod.MOD_ID);

    // Registry all custom enchantments
    // Lightning Striker - Sword tool
    public static final RegistryObject<MapCodec<? extends EnchantmentEntityEffect>> LIGHTNING_STRIKER =
            ENTITY_ENCHANTMENT_EFFECTS.register("lightning_striker", () -> LightningStrikerEnchantmentEffect.CODEC);

    public static final RegistryObject<MapCodec<? extends EnchantmentValueEffect>> AUTO_SMELT =
            VALUE_ENCHANTMENT_EFFECTS.register("auto_smelt", () -> AutoSmeltEnchantmentEffect.CODEC);

    // CUSTOM METHOD - Registry all custom enchantments on event
    public static void register(BusGroup busGroup) {
        VALUE_ENCHANTMENT_EFFECTS.register(busGroup);
        ENTITY_ENCHANTMENT_EFFECTS.register(busGroup);
    }
}