package net.karen.mccoursemod.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;

public class AlexandriteParticles extends TextureSheetParticle {
    protected AlexandriteParticles(ClientLevel pLevel, double x, double y, double z,
                                   SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(pLevel, x, y, z, xSpeed, ySpeed, zSpeed);
        this.friction = 0.8f;
        this.lifetime = 40;
        this.setSpriteFromAge(spriteSet);
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() { return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT; }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) { this.spriteSet = spriteSet; }

        @Nullable @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level,
                                       double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AlexandriteParticles(level, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
        }
    }
}