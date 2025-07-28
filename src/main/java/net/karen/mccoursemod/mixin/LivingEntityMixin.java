package net.karen.mccoursemod.mixin;

import net.karen.mccoursemod.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "canAttack*", at = @At("HEAD"), cancellable = true)
    private void livingPlayerAttack(LivingEntity living, CallbackInfoReturnable<Boolean> cir) {
        if (living instanceof Player player) { // Monsters etc. not attack Player
            Optional<Holder<MobEffect>> nothingEffect = ModEffects.NOTHING_EFFECT.getHolder();
            if (nothingEffect.isPresent() && player.hasEffect(nothingEffect.get())) {
                cir.setReturnValue(false);
            }
        }
    }
}