package net.karen.mccoursemod.enchantment.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import org.jetbrains.annotations.NotNull;

public record AutoSmeltEnchantmentEffect(LevelBasedValue value) implements EnchantmentValueEffect {
    public static final MapCodec<AutoSmeltEnchantmentEffect> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                      instance.group(LevelBasedValue.CODEC.fieldOf("value") // Value name parameter type
                              .forGetter(AutoSmeltEnchantmentEffect::value)) // Value Parameter value
                              .apply(instance, AutoSmeltEnchantmentEffect::new));

    @Override
    public float process(int level, @NotNull RandomSource source, float value) { return (float) level; }

    @Override
    public @NotNull MapCodec<? extends EnchantmentValueEffect> codec() { return CODEC; }
}