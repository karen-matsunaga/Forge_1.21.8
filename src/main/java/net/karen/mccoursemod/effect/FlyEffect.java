package net.karen.mccoursemod.effect;

import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import static net.karen.mccoursemod.util.Util.slot;

public class FlyEffect extends MobEffect {
    protected FlyEffect(MobEffectCategory category, int color) { super(category, color); }

    @Override
    public boolean applyEffectTick(@NotNull ServerLevel level, @NotNull LivingEntity entity, int amplifier) { // Fly effect APPLIED
        if (entity instanceof Player player && !player.level().isClientSide()) {
            Abilities abilities = player.getAbilities();
            Optional<Holder<MobEffect>> flyEffect = ModEffects.FLY_EFFECT.getHolder();
            boolean hasItem = (slot(player, EquipmentSlot.HEAD, ModTags.Items.HELMET_FLY) &&
                    slot(player, EquipmentSlot.CHEST, ModTags.Items.CHESTPLATE_FLY) && // Player used FULL ARMOR or FLY EFFECT
                    slot(player, EquipmentSlot.LEGS, ModTags.Items.LEGGINGS_FLY) &&
                    slot(player, EquipmentSlot.FEET, ModTags.Items.BOOTS_FLY)) ||
                    (flyEffect.isPresent() && player.hasEffect(flyEffect.get()));
            if (hasItem) { // Player has FULL ARMOR or FLY EFFECT
                if (!abilities.mayfly) {
                    abilities.mayfly = true;
                    player.getAbilities().setFlyingSpeed(0.05f + (0.02f * amplifier));
                    player.onUpdateAbilities();
                }
            }
            if (!hasItem) { // Player hasn't FULL ARMOR or FLY EFFECT
                if (abilities.mayfly && !player.isCreative()) {
                    abilities.mayfly = false;
                    abilities.flying = false;
                    player.getAbilities().setFlyingSpeed(0.05F);
                    player.onUpdateAbilities();
                }
            }
        }
        return super.applyEffectTick(level, entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) { return true; }
}