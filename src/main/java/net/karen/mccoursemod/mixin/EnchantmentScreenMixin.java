package net.karen.mccoursemod.mixin;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import java.util.Optional;

@Mixin(EnchantmentScreen.class)
public abstract class EnchantmentScreenMixin extends AbstractContainerScreen<EnchantmentMenu> {
    public EnchantmentScreenMixin(EnchantmentMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        float f;
        if (this.minecraft != null) {
            f = this.minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
            super.render(guiGraphics, mouseX, mouseY, f);
            this.renderTooltip(guiGraphics, mouseX, mouseY);
            boolean flag;
            if (this.minecraft.player != null) {
                flag = this.minecraft.player.hasInfiniteMaterials();
                int i = this.menu.getGoldCount();
                for (int j = 0; j < 3; j++) {
                    int k = this.menu.costs[j];
                    Optional<Holder.Reference<Enchantment>> optional;
                    if (this.minecraft.level != null) {
                        optional = this.minecraft.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                                                                        .get(this.menu.enchantClue[j]);
                        int l = this.menu.levelClue[j];
                        int i1 = j + 1;
                        if (this.isHovering(60, 14 + 19 * j, 108, 17, mouseX, mouseY) && k > 0) {
                            List<Component> list = Lists.newArrayList();
                            list.add(Component.translatable("container.enchant.clue", optional.isEmpty() ? "" :
                                                            Enchantment.getFullname(optional.get(), l)).withStyle(ChatFormatting.WHITE));
                            if (optional.isEmpty()) {
                                list.add(Component.literal(""));
                                list.add(Component.translatable("forge.container.enchant.limitedEnchantability").withStyle(ChatFormatting.RED));
                            } else if (!flag) {
                                list.add(CommonComponents.EMPTY);
                                if (this.minecraft.player.experienceLevel < k) {
                                    list.add(Component.translatable("container.enchant.level.requirement",
                                                                    this.menu.costs[j]).withStyle(ChatFormatting.RED));
                                } else {
                                    MutableComponent mutablecomponent;
                                    if (i1 == 1) { mutablecomponent = Component.translatable("container.enchant.lapis.one"); }
                                    else { mutablecomponent = Component.translatable("container.enchant.lapis.many", i1); }
                                    list.add(mutablecomponent.withStyle(i >= i1 ? ChatFormatting.GREEN : ChatFormatting.RED));
                                    MutableComponent mutablecomponent1;
                                    if (i1 == 1) { mutablecomponent1 = Component.translatable("container.enchant.level.one"); }
                                    else { mutablecomponent1 = Component.translatable("container.enchant.level.many", i1); }
                                    list.add(mutablecomponent1.withStyle(ChatFormatting.DARK_GREEN));
                                }
                            }
                            guiGraphics.setComponentTooltipForNextFrame(this.font, list, mouseX, mouseY);
                            break;
                        }
                    }
                }
            }
        }
    }
}