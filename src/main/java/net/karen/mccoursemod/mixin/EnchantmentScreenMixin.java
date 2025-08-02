package net.karen.mccoursemod.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static net.karen.mccoursemod.util.ChatUtil.*;

@Mixin(EnchantmentScreen.class)
public abstract class EnchantmentScreenMixin extends AbstractContainerScreen<EnchantmentMenu> {
    public EnchantmentScreenMixin(EnchantmentMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    // DEFAULT METHOD - Render enchantment name
    @Inject(method = "render", at = @At("TAIL"))
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo cir) {
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
                            List<Component> list = new ArrayList<>();
                            Component test = Component.translatable("container.enchant.clue", optional.isEmpty() ? "" :
                                                                    Enchantment.getFullname(optional.get(), l)).withStyle(white);
                            list.add(test);
                            if (optional.isEmpty()) {
                                list.add(Component.literal(""));
                                Component test2 = Component.translatable("forge.container.enchant.limitedEnchantability")
                                                           .withStyle(red);
                                list.add(test2);
                            }
                            else if (!flag) {
                                list.add(CommonComponents.EMPTY);
                                if (this.minecraft.player.experienceLevel < k) {
                                    list.add(Component.translatable("container.enchant.level.requirement",
                                                                    this.menu.costs[j]).withStyle(red));
                                }
                                else {
                                    Component line;
                                    if (i1 == 1) {
                                        line = Component.translatable("container.enchant.lapis.one")
                                                        .withStyle(i >= i1 ? green : red);
                                    }
                                    else {
                                        line = Component.translatable("container.enchant.lapis.many", i1)
                                                        .withStyle(i >= i1 ? green : red);
                                    }
                                    list.add(line);
                                    Component line2;
                                    if (i1 == 1) {
                                        line2 = Component.translatable("container.enchant.level.one").withStyle(darkGreen);
                                    }
                                    else {
                                        line2 = Component.translatable("container.enchant.level.many", i1).withStyle(darkGreen);
                                    }
                                    list.add(line2);
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