package net.karen.mccoursemod.mixin;

import net.karen.mccoursemod.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Optional;

@Mixin(VibrationSystem.Listener.class)
public class VibrationListenerMixin {
    @Inject(method = "handleGameEvent", at = @At("HEAD"), cancellable = true)
    private void mccourse$handleGameEvent(ServerLevel level, Holder<GameEvent> gameEvent,
                                          GameEvent.Context context, Vec3 pPos, CallbackInfoReturnable<Boolean> cir) {
        if (context.sourceEntity() instanceof Player player) {
            Optional<Holder<MobEffect>> nothingEffect = ModEffects.NOTHING_EFFECT.getHolder();
            if (nothingEffect.isPresent() && player.hasEffect(nothingEffect.get())) {
                cir.setReturnValue(true); // Cancel vibration of Sculk Sensor block
            }
        }
    }
}