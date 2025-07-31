package net.karen.mccoursemod.item.custom;

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

public class GemSpecialEffectItem extends Item {
    private final DataComponentType<Boolean> dataName;
    private static final int[] COLORS = { 0xff5555, 0xffaa00, 0xffff55, 0x55ff55, 0x55ffff, 0x5555ff, 0xff55ff };
    private final String item = this.descriptionId.replace("item.mccoursemod.", "");

    public GemSpecialEffectItem(Properties properties, DataComponentType<Boolean> dataName) {
        super(properties);
        this.dataName = dataName;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) { return tooltipLineTranslatableRGB(COLORS, stack); }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay display,
                                @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        tooltipLineLiteralRGB(consumer, COLORS, stack, " added " + itemLines(item) + " effect!"); // Tooltip message
        super.appendHoverText(stack, context, display, consumer, flag);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player,
                                          @NotNull InteractionHand hand) {
        // Gem Special Effect item has on OFFHAND slot
        ItemStack offHand = player.getItemInHand(hand), mainHand = player.getMainHandItem();
        if (!player.level().isClientSide() && !mainHand.isEmpty() && mainHand != offHand) {
            if (!isGemSpecialEffectActive(mainHand)) {
                mainHand.set(dataName, true);
                consumeInfinite(player, offHand);
                player(player, "Added " + itemLines(item) + " effect!", green); // Success message
                return InteractionResult.SUCCESS;
            }
            if (isGemSpecialEffectActive(mainHand)) {
                player(player, "Founded " + itemLines(item) + " effect!", darkRed); // Fail message
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.PASS;
    }

    // CUSTOM METHOD - Active Data Component boolean stage -> Get boolean stage True or False
    public boolean isGemSpecialEffectActive(ItemStack stack) {
        Boolean value = stack.get(dataName);
        return value != null && value;
    }
}