package net.karen.mccoursemod.item;

import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import static net.karen.mccoursemod.util.ChatUtil.*;
import static net.karen.mccoursemod.util.Util.mainHand;
import static net.karen.mccoursemod.util.Util.offhand;

public class LevelChargerItem extends Item {
    private final int amount;
    private final ResourceKey<Enchantment> enchantment;

    public LevelChargerItem(Properties properties, int amount,
                            ResourceKey<Enchantment> enchantment) {
        super(properties);
        this.amount = amount;
        this.enchantment = enchantment;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        InteractionHand otherHand = (hand == mainHand) ? offhand : mainHand; // Player's MAIN HAND and OFFHAND
        ItemStack changerStack = player.getItemInHand(hand), // Level Charger item - OFFHAND
                   targetStack = player.getItemInHand(otherHand); // Armor, Tool, Enchanted book, etc. - TARGET MAIN HAND
        if (!(changerStack.getItem() instanceof LevelChargerItem self)) { return InteractionResult.FAIL; }
        if (!player.level().isClientSide() && changerStack.is(ModTags.Items.LEVEL_CHARGER_ITEMS)) {
            // Get all enchantments and enchantment levels
            ItemEnchantments allEnch = EnchantmentHelper.getEnchantmentsForCrafting(targetStack);
            Holder<Enchantment> specifEnch = self.enchantment.getOrThrow(level); // SPECIF enchantment
            int amount = self.amount;
            if (allEnch.isEmpty() || targetStack.is(ModTags.Items.LEVEL_CHARGER_ITEMS)) {
                player(player, "The item has no enchantments!", darkRed);
                return InteractionResult.FAIL;
            }
            // Specif enchantment types
            if (changerStack.is(ModTags.Items.LEVEL_CHARGER_SPECIF) && specifEnch != null && !allEnch.keySet().contains(specifEnch)) {
                player(player, "This item doesn't have the required enchantment!", gray);
                return InteractionResult.FAIL;
            }
            if (amount < 0) { // Check enchantment levels
                boolean allMin; // All enchantment are with min level is 1
                if (changerStack.is(ModItems.LEVEL_CHARGER_MINUS.get())) {
                    allMin = allEnch.entrySet().stream().allMatch(e -> e.getIntValue() <= 1);
                }
                else if (changerStack.is(ModTags.Items.LEVEL_CHARGER_SPECIF) && specifEnch != null) {
                    allMin = allEnch.entrySet().stream().filter(e -> e.getKey().equals(specifEnch))
                                                        .allMatch(e -> e.getIntValue() <= 1);
                }
                else { return InteractionResult.FAIL; }
                if (allMin) {
                    player(player, "All enchantments are already at level 1!", aqua);
                    return InteractionResult.FAIL;
                }
            }
            // Create new map with increased levels and store original enchantment and level
            Map<Holder<Enchantment>, Integer> upgraded = new HashMap<>();
            allEnch.entrySet().forEach((enc) -> {
                // ** CREATE A FAKE ENCHANTMENT TO FUNCTION ** -> Store new enchantment level of all enchants
                // Store new specif enchantment level
                if (changerStack.is(ModTags.Items.LEVEL_CHARGER_GENERAL) && !enc.getKey().equals(specifEnch) ||
                    changerStack.is(ModTags.Items.LEVEL_CHARGER_SPECIF) && enc.getKey().equals(specifEnch)) {
                    upgraded.put(enc.getKey(), Math.max(1, enc.getIntValue() + amount));
                }
            });
            // Apply the updated enchantments to the original item
            ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(allEnch);
            upgraded.forEach(enchantments::set);
            EnchantmentHelper.setEnchantments(targetStack, enchantments.toImmutable()); // New enchantment level
            if (specifEnch != null) { itemHurt(player, changerStack, specifEnch); } // Message on screen
            changerStack.shrink(1); // Consumes Level Charger
            return InteractionResult.SUCCESS;
        }
        else { return InteractionResult.FAIL; }
    }

    // DEFAULT METHOD - Added TOOLTIP on all Level Charger
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> consumer,
                                @NotNull TooltipFlag flag) {
        String name = stack.getItem().getDescriptionId().replace("item.mccoursemod.", ""),
               newName = itemLines(splitWord(name)), general = amount + " level.",
               pos = newName + " increase +" + general, neg = newName + " decrease " + general;
        boolean value = (amount == 1);
        if (stack.is(ModTags.Items.LEVEL_CHARGER_ITEMS)) { tooltipLine(consumer, value ? pos : neg, value ? green : red); }
        super.appendHoverText(stack, context, tooltipDisplay, consumer, flag);
    }

    // DEFAULT METHOD - Added NAME on all Level Charger -> Translatable en_us.json
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component baseName = super.getName(stack);
        if (stack.is(ModTags.Items.LEVEL_CHARGER_GREEN)) { return baseName.copy().withStyle(green); }
        else if (stack.is(ModTags.Items.LEVEL_CHARGER_RED)) { return baseName.copy().withStyle(red); }
        return baseName;
    }

    // CUSTOM METHOD - Message when consumed Level Charger (Plus / Minus) items
    private void itemHurt(Player player, ItemStack chargerStack, Holder<Enchantment> ench) {
        String pos = "Increased +", neg = "Decreased ";
        boolean value = (amount == 1);
        if (chargerStack.is(ModTags.Items.LEVEL_CHARGER_GENERAL)) {
           String screen = amount + " level(s)!", positive = pos + screen, negative = neg + screen;
           player(player, value ? positive : negative, value ? green : red);
        }
        if (chargerStack.is(ModTags.Items.LEVEL_CHARGER_SPECIF)) {
            String i18 = ench.getRegisteredName(),
                   message = amount + " " + itemLines(i18.replace("minecraft:", "")) + " level!";
            player(player, value ? (pos + message) : (neg + message), value ? green : red);
        }
    }
}