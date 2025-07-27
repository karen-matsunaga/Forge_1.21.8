package net.karen.mccoursemod.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.*;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import static net.karen.mccoursemod.util.ChatUtil.*;

@Mixin(value = Enchantment.class)
public abstract class EnchantmentMixin {
    // Enchantment tooltip
    @Inject(method = "getFullname", at = @At(value = "TAIL"), cancellable = true)
    private static void getFullname(Holder<Enchantment> holder, int level, CallbackInfoReturnable<Component> cir) {
        Enchantment enchantment = holder.value(); // Enchantment name
        MutableComponent enchantmentComponent = enchantment.description().copy(); // Old enchantment tooltip
        int maxLevel = enchantment.getMaxLevel(); // Enchantment max level
        ChatFormatting color = getEnchantmentColor(holder); // Enchantment color by category
        String icon = icon(holder); // Enchantment icon by category
        // Apply new enchantment tooltip
        boolean isCurse = holder.is(EnchantmentTags.CURSE);
        ComponentUtils.mergeStyles(enchantmentComponent, Style.EMPTY.withColor(isCurse ? red : color)
                                                                    .applyFormat(isCurse ? italic : bold));
        if (level != 1 || maxLevel != 1) { // Enchantment level equals 1+
            MutableComponent line = enchantmentComponent.append(CommonComponents.SPACE)
                                                         .append(Component.literal(level + " / " + maxLevel)) // Level
                                                         .append(CommonComponents.SPACE).append(Component.literal(icon))
                                                         .append(CommonComponents.NEW_LINE).append(CommonComponents.EMPTY)
                                                         .append(Component.literal("Enchantment description")
                                                         .withColor(0xFFFFFF));
            // Return new enchantment tooltip
            cir.setReturnValue(line);
        }
    }

    // Max Enchantment Level
    @Inject(at = @At("HEAD"), method = "getMaxLevel", cancellable = true)
    private void getMaxLevel(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(255); // Enchantment max level -> Ex: Fortune 255
    }
}