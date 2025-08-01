package net.karen.mccoursemod.mixin;

import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.component.ModDataComponentTypes;
import net.karen.mccoursemod.item.custom.MultiplierItem;
import net.karen.mccoursemod.util.ChatUtil;
import net.karen.mccoursemod.util.Util;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.*;
import static net.karen.mccoursemod.util.ChatUtil.*;

@Mixin(value = ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "getTooltipLines", at = @At("RETURN"), cancellable = true)
    private void getTooltipLines(Item.TooltipContext context, Player player,
                                 TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir) {
        ItemStack stack = (ItemStack) (Object) this; // Get all blocks, items, etc.
        List<Component> tooltip = new ArrayList<>(cir.getReturnValue()); // Old tooltip
        if (tooltip.size() > 1 || stack.has(ModDataComponentTypes.MULTIPLIER.get())) { // Jump one line
            tooltip.add(CommonComponents.EMPTY);
        }
        if (stack.is(ModBlocks.MAGIC.get().asItem())) { // Item checked is Magic block
            Component original = tooltip.getFirst(), // Original tooltip line 0
                      colored = original.copy().withStyle(style -> style.withColor(0x00ff00));
            tooltip.set(0, colored); // Change only the name (first line of the tooltip) -> Color not appears on screen
            tooltip.add(standardTranslatable("tooltip.mccoursemod.magic_block.tooltip")); // Added more information about block
        }

        // Multiplier, Magnet, Rainbow, Auto Smelt and More Ores effects
        if (stack.has(ModDataComponentTypes.MULTIPLIER.get())) {
            tooltip.add(ChatUtil.componentLiteral("Multiplier x" + MultiplierItem.getMultiplierValue(stack) + "!", yellow));
        }
        if (stack.has(ModDataComponentTypes.MAGNET.get())) {
            tooltip.add(ChatUtil.componentLiteral("Magnet!", darkGray));
        }
        if (stack.has(ModDataComponentTypes.RAINBOW.get())) {
            tooltip.add(ChatUtil.componentLiteral("Rainbow!", purple));
        }
        if (stack.has(ModDataComponentTypes.AUTO_SMELT.get())) {
            tooltip.add(ChatUtil.componentLiteral("Auto Smelt!", gold));
        }
        if (stack.has(ModDataComponentTypes.MORE_ORES.get())) {
            tooltip.add(ChatUtil.componentLiteral("More Ores!", darkAqua));
        }
        cir.setReturnValue(tooltip); // New tooltip
    }

    // Lapis Lazuli consumption is blocked
    @Inject(method = "shrink", at = @At("HEAD"), cancellable = true)
    private void preventLapisShrink(int decrement, CallbackInfo ci) {
        ItemStack self = (ItemStack) (Object) this;
        if (self.is(Items.LAPIS_LAZULI) && Util.IGNORE_LAPIS) { ci.cancel(); }
    }
}