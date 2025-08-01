package net.karen.mccoursemod.item.custom;

import net.karen.mccoursemod.util.ModTags;
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

public class MultiplierItem extends Item {
    private final DataComponentType<Integer> dataName;
    private final int value; // Multiplier value x10 etc.
    private static final int[] COLORS = { 0xff5555, 0xffaa00, 0xffff55, 0x55ff55, 0x55ffff, 0x5555ff, 0xff55ff };

    public MultiplierItem(Properties properties, DataComponentType<Integer> dataName, int value) {
        super(properties);
        this.dataName = dataName;
        this.value = value;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack offHand = player.getItemInHand(hand), mainHand = player.getMainHandItem();
        if (!player.level().isClientSide() && !mainHand.isEmpty() && mainHand != offHand) {
            String split = itemLines(splitWord(this.descriptionId.replace("item.mccoursemod.", ""))),
                   upper = upperString(split);
            Integer currentValue = getMultiplierType(mainHand);
            if (currentValue != null && currentValue == value) {
                player(player, "This item is already " + upper + " tag and is " + value + "!", yellow);
                return InteractionResult.FAIL;
            }
            else {
                setMultiplierValue(mainHand);
                player(player, "Added " + upper + " tag!", green);
                consumeInfinite(player, offHand);
                return InteractionResult.SUCCESS;
            }
        }
        player(player, "Hold the tool in your main hand!", red);
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return componentTranslatable(stack.getItem().getDescriptionId(), aqua);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull TooltipDisplay display, @NotNull Consumer<Component> consumer,
                                @NotNull TooltipFlag flag) {
        String message = " click on item to your tools or armors and multiplier items!";
        if (stack.is(ModTags.Items.MULTIPLIER_ITEMS)) { tooltipLineLiteralRGB(consumer, COLORS, stack, message); }
        super.appendHoverText(stack, context, display, consumer, flag);
    }

    // CUSTOM METHOD - Get Multiplier value (Non static)
    public Integer getMultiplierType(ItemStack stack) { return stack.get(dataName); }

    // CUSTOM METHOD - Set Multiplier value (Non static)
    private void setMultiplierValue(ItemStack stack) { stack.set(dataName, value); }

    // CUSTOM METHOD - Get Data Component value (Static)
    public static Integer getMultiplier(ItemStack stack, DataComponentType<Integer> dataName) {
        return stack.get(dataName);
    }

    // CUSTOM METHOD - Get Data Component boolean value (Static)
    public static Boolean getMultiplierBool(ItemStack stack, DataComponentType<Integer> dataName) {
        Integer value = getMultiplier(stack, dataName);
        return value != null && value > 0;
    }

    // CUSTOM METHOD - Get Data Component integer value (Static)
    public static int getEffectMultiplier(ItemStack stack, DataComponentType<Integer> type, int baseValue) {
        boolean hasEffect = MultiplierItem.getMultiplierBool(stack, type);
        Integer multiplier = MultiplierItem.getMultiplier(stack, type);
        return (hasEffect && multiplier != null && multiplier > 0) ? baseValue * multiplier : baseValue;
    }
}