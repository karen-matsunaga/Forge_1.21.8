package net.karen.mccoursemod.particle;

import net.karen.mccoursemod.MccourseMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MccourseMod.MOD_ID);

    // Registry all custom particles
    public static final RegistryObject<SimpleParticleType> ALEXANDRITE_PARTICLES =
            PARTICLE_TYPES.register("alexandrite_particles", () -> new SimpleParticleType(true));

    // CUSTOM METHOD - Registry all custom particles types on event
    public static void register(BusGroup busGroup) { PARTICLE_TYPES.register(busGroup); }
}