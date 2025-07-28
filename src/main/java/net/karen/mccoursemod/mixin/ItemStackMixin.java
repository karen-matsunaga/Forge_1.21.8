package net.karen.mccoursemod.mixin;

import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static net.karen.mccoursemod.util.ChatUtil.*;

@Mixin(value = ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "getTooltipLines", at = @At("RETURN"), cancellable = true)
    private void getTooltipLines(Item.TooltipContext context, Player player,
                                 TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir) {
        ItemStack stack = (ItemStack) (Object) this; // Get all blocks, items, etc.
        List<Component> tooltip = new ArrayList<>(cir.getReturnValue()); // Old tooltip
        if (stack.is(ModBlocks.MAGIC.get().asItem())) { // Item checked is Magic block
            tooltip.add(Component.translatable("tooltip.mccoursemod.magic_block.tooltip")); // Added more information about block
            cir.setReturnValue(tooltip); // New tooltip
        }

        // Enchantment
        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        if (!stack.isEmpty() && stack.isEnchanted() || stack.getItem() == Items.ENCHANTED_BOOK) {
            if (!enchantments.isEmpty()) {
                for (int i = 0; i < tooltip.size(); i++) {
                    String raw = ChatFormatting.stripFormatting(tooltip.get(i).getString()); // Detected old line
                    for (Map.Entry<Holder<Enchantment>, Integer> entry : enchantments.entrySet()) {
                        Holder<Enchantment> enchantment = entry.getKey(); // Enchantment name
                        int level = entry.getValue(); // Enchantment level
                        String expected = standardTranslatable(enchantment.value().description().copy().toString()).getString();
                        if (raw != null && raw.startsWith(expected)) { // Raw has "expected" line replace old to new tooltip
                            boolean isCurse = enchantment.is(EnchantmentTags.CURSE);
                            ChatFormatting color = getEnchantmentColor(enchantment); // Enchantment color by category
                            // JSON file -> I18n = en_us.json
                            String enchant = enchantment.value().description().copy().toString(),
                                   descriptionValue = enchant + ".desc";
                            if (level > 0 || I18n.exists(descriptionValue)) {
                                // Enchantment Level with Arabic numeral + Enchantment compatibility + Enchantment description
                                MutableComponent desc = description(descriptionValue, isCurse ? red : color, List.of(false, false));
                                // Number line of enchantments and enchantment descriptions
                                tooltip.add(i + 1, desc);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    // Lapis Lazuli consumption is blocked
    @Inject(method = "shrink", at = @At("HEAD"), cancellable = true)
    private void preventLapisShrink(int decrement, CallbackInfo ci) {
        ItemStack self = (ItemStack) (Object) this;
        if (self.is(Items.LAPIS_LAZULI) && Util.IGNORE_LAPIS) { ci.cancel(); }
    }
}