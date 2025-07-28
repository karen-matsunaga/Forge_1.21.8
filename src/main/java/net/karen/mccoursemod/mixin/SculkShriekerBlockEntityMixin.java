package net.karen.mccoursemod.mixin;

import net.karen.mccoursemod.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Optional;

@Mixin(SculkShriekerBlockEntity.class)
public class SculkShriekerBlockEntityMixin {
    @Inject(method = "tryShriek", at = @At("HEAD"), cancellable = true)
    private void onTryShriek(ServerLevel level, ServerPlayer player, CallbackInfo cir) {
        Optional<Holder<MobEffect>> nothingEffect = ModEffects.NOTHING_EFFECT.getHolder();
        if (player != null && nothingEffect.isPresent() && player.hasEffect(nothingEffect.get())) {
            cir.cancel(); // Cancels Sculk Shrieker block activation
        }
    }
}