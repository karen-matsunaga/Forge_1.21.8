package net.karen.mccoursemod.enchantment.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record LightningStrikerEnchantmentEffect(LevelBasedValue value) implements EnchantmentEntityEffect {
    public static final MapCodec<LightningStrikerEnchantmentEffect> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                      instance.group(LevelBasedValue.CODEC.fieldOf("value") // Value name parameter type
                              .forGetter(LightningStrikerEnchantmentEffect::value)) // Value Parameter value
                              .apply(instance, LightningStrikerEnchantmentEffect::new));

    // DEFAULT METHOD - Registry Lightning Striker function
    @Override
    public void apply(@NotNull ServerLevel level, int enchantmentLevel, @NotNull EnchantedItemInUse use,
                      @NotNull Entity entity, @NotNull Vec3 vec3) {
        if (entity instanceof LivingEntity living) {
            float number = this.value.calculate(enchantmentLevel); // Lightning Striker enchantment level
            BlockPos pos = living.getOnPos(); // Entity position
            for (int i = 0; i < number; i++) { // Number of Lightning Bolt adapted to Lightning Striker enchantment level
                EntityType.LIGHTNING_BOLT.spawn(level, pos, EntitySpawnReason.TRIGGERED);
            }
        }
    }

    // DEFAULT METHOD - Registry custom CODEC
    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() { return CODEC; }
}