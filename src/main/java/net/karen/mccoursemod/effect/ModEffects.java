package net.karen.mccoursemod.effect;

import net.karen.mccoursemod.MccourseMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MccourseMod.MOD_ID);

    // Registry all custom effects
    // Fly's effect
    public static final RegistryObject<MobEffect> FLY_EFFECT = MOB_EFFECTS.register("fly",
            () -> new FlyEffect(MobEffectCategory.BENEFICIAL, 0xFFFF00).addAttributeModifier(Attributes.FLYING_SPEED,
                    ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "fly"),
                    1.00f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    // Nothing's effect
    public static final RegistryObject<MobEffect> NOTHING_EFFECT = MOB_EFFECTS.register("nothing",
            () -> new NothingEffect(MobEffectCategory.NEUTRAL, 0x333366));

    // Registry all effects on Forge
    public static void register(BusGroup busGroup) { MOB_EFFECTS.register(busGroup); }
}