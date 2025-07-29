package net.karen.mccoursemod.item.custom;

import net.karen.mccoursemod.component.ModDataComponentTypes;
import net.karen.mccoursemod.item.ModItems;
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
    private final int value; // Multiplier value x10 etc.

    public MultiplierItem(Properties properties, int value) {
        super(properties);
        this.value = value;
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack offHand = player.getItemInHand(hand), mainHand = player.getMainHandItem();
        if (!level.isClientSide() && !mainHand.isEmpty() && mainHand != offHand) {
            String split = splitWord("Multiplier"), upper = upperString(split);
            int targetValue = value;
            Integer currentValue = getMultiplierValue(mainHand);
            if (currentValue != null && currentValue == targetValue) {
                player(player, "This item is already " + upper + " tag and is " + value + "!", yellow);
                return InteractionResult.FAIL;
            }
            setMultiplierValue(mainHand, targetValue);
            player(player, "Multiplier: x" + targetValue + "!", green);
            player(player, "Added " + upper + " tag!", green);
            consumeInfinite(player, offHand);
            return InteractionResult.SUCCESS;
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
        String message = "Click on item to your tools or armors and multiplier ",
                item = this.toString().replace("_", " ");
        if (stack.is(ModItems.MULTIPLIER.get())) {
            tooltipLine(consumer, message + itemLines(item) + " items!", purple);
        }
        super.appendHoverText(stack, context, display, consumer, flag);
    }

    // CUSTOM METHOD - Get Multiplier value
    public static Integer getMultiplierValue(ItemStack stack) {
        return stack.get(ModDataComponentTypes.MULTIPLIER.get());
    }

    // CUSTOM METHOD - Set Multiplier value
    private void setMultiplierValue(ItemStack stack, int value) {
        stack.set(ModDataComponentTypes.MULTIPLIER.get(), value);
    }
}