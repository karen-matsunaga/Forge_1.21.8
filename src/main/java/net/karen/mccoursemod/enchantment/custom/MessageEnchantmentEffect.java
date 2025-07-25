package net.karen.mccoursemod.enchantment.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.karen.mccoursemod.enchantment.ModEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record MessageEnchantmentEffect(LevelBasedValue value) implements EnchantmentEntityEffect {
    public static final MapCodec<MessageEnchantmentEffect> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                      instance.group(LevelBasedValue.CODEC.fieldOf("value")
                              .forGetter(MessageEnchantmentEffect::value))
                              .apply(instance, MessageEnchantmentEffect::new));

    @Override
    public void apply(@NotNull ServerLevel level, int i, @NotNull EnchantedItemInUse item,
                      @NotNull Entity entity, @NotNull Vec3 vec3) {
        if (entity instanceof Player player) {
            ItemEnchantments levels = EnchantmentHelper.getEnchantmentsForCrafting(item.itemStack());
            for (int e = 1; e < i; e++) {
                if (e == levels.getLevel(ModEnchantments.MESSAGE.getOrThrow(level))) {
                    player.displayClientMessage(Component.literal("Level Charger " + e + " level!"), false);
                }
            }
        }
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() { return CODEC; }
}