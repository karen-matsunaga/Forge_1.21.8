package net.karen.mccoursemod.item.custom;

import net.karen.mccoursemod.component.ModDataComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;
import static net.karen.mccoursemod.util.ChatUtil.*;

public class GemEffectItem extends Item {
    private final Holder<MobEffect> effect;
    private final int duration, amplifier;
    private static final int[] COLORS = { 0xff5555, 0xffaa00, 0xffff55, 0x55ff55, 0x55ffff, 0x5555ff, 0xff55ff };

    public GemEffectItem(Properties properties, Holder<MobEffect> effect, int duration, int amplifier) {
        super(properties);
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) { return tooltipLineTranslatableRGB(COLORS, stack); }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay display,
                                @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        tooltipLineLiteralRGB(consumer, COLORS, stack, isGemEffectActive(stack) ? " Activated" : " Disable"); // Tooltip message
        super.appendHoverText(stack, context, display, consumer, flag);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) { return isGemEffectActive(stack); }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player,
                                          @NotNull InteractionHand hand) {
        ItemStack mainHand = player.getItemInHand(hand); // Gem Effect item has on MAIN HAND slot
        if (!level.isClientSide() && !mainHand.isEmpty() && mainHand.getItem() instanceof GemEffectItem) {
            toggleGemEffectStage(mainHand);
            boolean active = isGemEffectActive(mainHand);  // Gem Effect Stage
            messageLiteralRGB(player, COLORS, mainHand, active ? " Activated" : " Disable"); // Screen message
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level,
                              @NotNull Entity entity, @Nullable EquipmentSlot pSlot) {
        MobEffectInstance effect = new MobEffectInstance(this.effect, this.duration, this.amplifier,
                true, true, true);
        Player player = (Player) entity;
        if (isGemEffectActive(stack)) { player.addEffect(effect); } // Added effect
        if (!isGemEffectActive(stack)) { player.removeEffect(effect.getEffect()); } // Removed effect
        super.inventoryTick(stack, level, entity, pSlot);
    }

    // CUSTOM METHOD - Active Data Component boolean stage -> Get boolean stage True or False
    private boolean isGemEffectActive(ItemStack stack) {
        Boolean current = stack.get(ModDataComponentTypes.GEM_EFFECT.get());
        return current != null && current;
    }

    // CUSTOM METHOD - Toggle Data Component boolean stage (True -> False | False -> True)
    private void toggleGemEffectStage(ItemStack stack) {
        boolean current = isGemEffectActive(stack);
        stack.set(ModDataComponentTypes.GEM_EFFECT.get(), !current);
    }
}