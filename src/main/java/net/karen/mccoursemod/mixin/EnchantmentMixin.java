package net.karen.mccoursemod.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.List;
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
        ChatFormatting colors = isCurse ? red : color, formats = isCurse ? italic : bold;
        ComponentUtils.mergeStyles(enchantmentComponent, Style.EMPTY.withColor(colors).applyFormat(formats));
        if (level != 1 || maxLevel != 1) { // Enchantment level equals 1+
            MutableComponent line = enchantmentComponent.append(CommonComponents.SPACE)
                                                        .append(standardLiteral(level + " / " + maxLevel)) // Level
                                                        .append(CommonComponents.SPACE).append(standardLiteral(icon));
            Enchantment enchName = holder.value();
            Level LEVEL = Minecraft.getInstance().level;
            if (LEVEL != null) {
                Registry<Enchantment> enchantmentRegistry = LEVEL.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                ResourceLocation enchantKey = enchantmentRegistry.getKey(enchName);
                if (enchantKey != null) {
                    String descriptionKey = "enchantment." + enchantKey.getNamespace() + "." + enchantKey.getPath() + ".desc";
                    if (I18n.exists(descriptionKey)) {
                        MutableComponent line1 = description(I18n.get(descriptionKey), colors, List.of(false, false));
                        Component combined = ComponentUtils.formatList(List.of(line, line1), CommonComponents.NEW_LINE);
                        // Return new enchantment tooltip
                        cir.setReturnValue(combined);
                    }
                }
            }
        }
    }

    // Max Enchantment Level
    @Inject(at = @At("HEAD"), method = "getMaxLevel", cancellable = true)
    private void getMaxLevel(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(255); // Enchantment max level -> Ex: Fortune 255
    }
}