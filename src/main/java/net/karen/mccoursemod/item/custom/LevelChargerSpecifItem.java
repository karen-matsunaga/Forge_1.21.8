package net.karen.mccoursemod.item.custom;

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

public class LevelChargerSpecifItem extends Item {
    private final int amount;
    private final ResourceKey<Enchantment> enchantment;

    public LevelChargerSpecifItem(Properties properties, int amount,
                                  ResourceKey<Enchantment> enchantment) {
        super(properties);
        this.amount = amount;
        this.enchantment = enchantment;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        InteractionHand otherHand = (hand == mainHand) ? offhand : mainHand; // Player's MAIN HAND and OFFHAND
        // changerStack -> Level Charger item - OFFHAND || targetStack -> Armor, Tool, Enchanted book, etc. - TARGET MAIN HAND
        ItemStack changerStack = player.getItemInHand(hand), targetStack = player.getItemInHand(otherHand);
        if (!(changerStack.getItem() instanceof LevelChargerSpecifItem self)) { return InteractionResult.FAIL; }
        if (!player.level().isClientSide() && changerStack.is(ModTags.Items.LEVEL_CHARGER_SPECIF)) {
            // Get all enchantments and enchantment levels
            ItemEnchantments allEnch = EnchantmentHelper.getEnchantmentsForCrafting(targetStack);
            Holder<Enchantment> specifEnch = self.enchantment.getOrThrow(level); // SPECIF enchantment
            int amount = self.amount; // Amount of enchantment level
            if (specifEnch != null) {
                if (allEnch.isEmpty() || targetStack.is(ModTags.Items.LEVEL_CHARGER_SPECIF)) {
                    player(player, "The item has no enchantments!", darkRed);
                    return InteractionResult.FAIL;
                }
                if (amount < 0 && changerStack.is(ModTags.Items.LEVEL_CHARGER_SPECIF)) { // Check enchantment levels
                    // Has specif enchantment and all enchantments are with min level is 1
                    boolean hasSpecifEnchant = allEnch.entrySet().stream().filter(e -> e.getKey().equals(specifEnch))
                            .allMatch(e -> e.getIntValue() <= 1);
                    if (hasSpecifEnchant) { // All enchantment are with min level is 1
                        player(player, "All enchantments are already at level 1!", aqua);
                        return InteractionResult.FAIL;
                    }
                }
                // Specif enchantment types
                if (changerStack.is(ModTags.Items.LEVEL_CHARGER_SPECIF) && !allEnch.keySet().contains(specifEnch)) {
                    player(player, "This item doesn't have the required enchantment!", gray);
                    return InteractionResult.FAIL;
                }
                // Create new map with increased levels and store original enchantment and level
                Map<Holder<Enchantment>, Integer> upgraded = new HashMap<>();
                allEnch.entrySet().forEach((enc) -> {
                    // Store new enchantment level of all enchants or Store new specif enchantment level
                    if (enc.getKey().equals(specifEnch)) { upgraded.put(enc.getKey(), Math.max(1, enc.getIntValue() + amount)); }
                });
                // Apply the updated enchantments to the original item
                ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(allEnch);
                upgraded.forEach(enchantments::set);
                EnchantmentHelper.setEnchantments(targetStack, enchantments.toImmutable()); // New enchantment level
                itemHurt(player, changerStack, specifEnch.getRegisteredName()); // Message on screen
            }
            changerStack.shrink(1); // Consumes Level Charger
            return InteractionResult.SUCCESS;
        }
        else { return InteractionResult.FAIL; }
    }

    // DEFAULT METHOD - Added TOOLTIP on all Level Charger
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay tooltipDisplay,
                                @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        String name = stack.getItem().getDescriptionId().replace("item.mccoursemod.", ""),
               newName = itemLines(splitWord(name)), general = amount + " level.",
               plus = newName + " increase +" + general, minus = newName + " decrease " + general;
        boolean value = (amount == 1);
        if (stack.is(ModTags.Items.LEVEL_CHARGER_SPECIF)) { tooltipLine(consumer, value ? (plus) : (minus), value ? green : red); }
        super.appendHoverText(stack, context, tooltipDisplay, consumer, flag);
    }

    // DEFAULT METHOD - Added NAME on all Level Charger -> Translatable en_us.json
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component baseName = super.getName(stack);
        if (stack.is(ModTags.Items.LEVEL_CHARGER_SPECIF)) { return baseName.copy().withStyle((amount == 1) ? green : red); }
        return baseName;
    }

    // CUSTOM METHOD - Message when consumed Level Charger (Plus / Minus) items
    private void itemHurt(Player player, ItemStack chargerStack, String ench) {
        boolean value = (amount == 1);
        if (chargerStack.is(ModTags.Items.LEVEL_CHARGER_SPECIF)) {
            String message = amount + " " + itemLines(ench.replace("minecraft:", "")) + " level!";
            player(player, value ? ("Increased +" + message) : ("Decreased " + message), value ? green : red);
        }
    }
}