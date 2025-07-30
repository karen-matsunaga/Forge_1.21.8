package net.karen.mccoursemod.item.custom;

import net.karen.mccoursemod.component.ModDataComponentTypes;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;
import static net.karen.mccoursemod.util.ChatUtil.*;
import static net.karen.mccoursemod.util.Util.consumeInfinite;

public class MagnetItem extends Item {
    private static final DataComponentType<Boolean> dataName = ModDataComponentTypes.ITEM_STAGE.get();
    private static final int[] COLORS = { 0xff5555, 0xffaa00, 0xffff55, 0x55ff55, 0x55ffff, 0x5555ff, 0xff55ff };

    public MagnetItem(Properties properties) { super(properties); }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) { return tooltipLineTranslatableRGB(COLORS, stack); }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay display,
                                @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        // Tooltip message
        if (stack.is(ModItems.MAGNET.get())) { tooltipLineLiteralRGB(consumer, COLORS, stack, " added Magnetic effect!"); }
        super.appendHoverText(stack, context, display, consumer, flag);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player,
                                          @NotNull InteractionHand hand) {
        ItemStack offHand = player.getItemInHand(hand);
        ItemStack mainHand = player.getMainHandItem(); // Gem Effect item has on MAIN HAND slot
        if (!player.level().isClientSide() && !mainHand.isEmpty() && mainHand != offHand) {
            if (!isGemSpecialEffectActive(mainHand)) {
                mainHand.set(dataName, true);
                consumeInfinite(player, offHand);
                player(player, "Added Magnet effect!", green); // Success message
                return InteractionResult.SUCCESS;
            }
            if (isGemSpecialEffectActive(mainHand)) {
                player(player, "Founded Magnet effect!", darkRed); // Fail message
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.PASS;
    }

    // CUSTOM METHOD - Active Data Component boolean stage -> Get boolean stage True or False
    public static boolean isGemSpecialEffectActive(ItemStack stack) {
        Boolean value = stack.get(dataName);
        return value != null && value;
    }
}