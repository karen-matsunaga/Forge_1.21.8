package net.karen.mccoursemod.mixin;

import net.karen.mccoursemod.block.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.ArrayList;
import java.util.List;

@Mixin(value = ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "getTooltipLines", at = @At("RETURN"), cancellable = true)
    private void getTooltipLines(Item.TooltipContext context, Player player,
                                 TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir) {
        ItemStack stack = (ItemStack) (Object) this; // Get all blocks, items, etc.
        if (stack.is(ModBlocks.MAGIC.get().asItem())) { // Item checked is Magic block
            List<Component> tooltip = new ArrayList<>(cir.getReturnValue()); // Old tooltip
            tooltip.add(Component.translatable("tooltip.mccoursemod.magic_block.tooltip")); // Added more information about block
            cir.setReturnValue(tooltip); // New tooltip
        }
    }
}