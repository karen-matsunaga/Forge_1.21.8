package net.karen.mccoursemod.potion;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, MccourseMod.MOD_ID);

    // Registry all potions
    // Fly's potion register
    public static final RegistryObject<Potion> FLY_POTION = POTIONS.register("fly_potion",
            () -> new Potion("fly_potion",
                  new MobEffectInstance(ModEffects.FLY_EFFECT.getHolder().get(), 2000, 0)));

    public static final RegistryObject<Potion> FLY_II_POTION = POTIONS.register("fly_ii_potion",
            () -> new Potion("fly_potion",
                  new MobEffectInstance(ModEffects.FLY_EFFECT.getHolder().get(), 4000, 1)));

    // Haste's potion register
    public static final RegistryObject<Potion> HASTE_POTION = POTIONS.register("haste_potion",
            () -> new Potion("haste_potion",
                  new MobEffectInstance(MobEffects.HASTE, 2000, 0)));

    // Nothing's potion register
    public static final RegistryObject<Potion> NOTHING_POTION = POTIONS.register("nothing_potion",
            () -> new Potion("nothing_potion",
                  new MobEffectInstance(ModEffects.NOTHING_EFFECT.getHolder().get(), 2000, 0)));

    public static void register(BusGroup busGroup) { POTIONS.register(busGroup); } // Registry all potions on Forge
}