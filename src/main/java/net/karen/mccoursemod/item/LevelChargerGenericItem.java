package net.karen.mccoursemod.item;

import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
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

public class LevelChargerGenericItem extends Item {
    private final int amount;

    public LevelChargerGenericItem(Properties properties, int amount) {
        super(properties);
        this.amount = amount;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        InteractionHand otherHand = (hand == mainHand) ? offhand : mainHand; // Player's MAIN HAND and OFFHAND
        ItemStack changerStack = player.getItemInHand(hand), targetStack = player.getItemInHand(otherHand);
        if (!(changerStack.getItem() instanceof LevelChargerGenericItem self)) { return InteractionResult.FAIL; }
        if (!player.level().isClientSide() && changerStack.is(ModTags.Items.LEVEL_CHARGER_GENERIC)) {
            // Get all enchantments and enchantment levels
            ItemEnchantments allEnch = EnchantmentHelper.getEnchantmentsForCrafting(targetStack);
            int amount = self.amount; // Amount of enchantment level
            if (allEnch.isEmpty() || targetStack.is(ModTags.Items.LEVEL_CHARGER_GENERIC)) {
                player(player, "The item has no enchantments!", darkRed);
                return InteractionResult.FAIL;
            }
            if (amount < 0 && changerStack.is(ModItems.LEVEL_CHARGER_GENERIC_MINUS.get())) { // Check enchantment levels
                boolean allMin = allEnch.entrySet().stream().allMatch(e -> e.getIntValue() <= 1);
                if (allMin) { // All enchantment are with min level is 1
                    player(player, "All enchantments are already at level 1!", aqua);
                    return InteractionResult.FAIL;
                }
            }
            // Create new map with increased levels and store original enchantment and level
            Map<Holder<Enchantment>, Integer> upgraded = new HashMap<>();
            allEnch.entrySet().forEach((enc) -> upgraded.put(enc.getKey(), Math.max(1, enc.getIntValue() + amount)));
            // Apply the updated enchantments to the original item
            ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(allEnch);
            upgraded.forEach(enchantments::set);
            EnchantmentHelper.setEnchantments(targetStack, enchantments.toImmutable()); // New enchantment level
            itemHurt(player, changerStack); // Message on screen
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
               pos = newName + " increase +" + general, neg = newName + " decrease " + general;;
        boolean value = (amount == 1);
        if (stack.is(ModTags.Items.LEVEL_CHARGER_GENERIC)) { tooltipLine(consumer, value ? (pos) : (neg), value ? green : red); }
        super.appendHoverText(stack, context, tooltipDisplay, consumer, flag);
    }

    // DEFAULT METHOD - Added NAME on all Level Charger -> Translatable en_us.json
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component baseName = super.getName(stack);
        if (stack.is(ModTags.Items.LEVEL_CHARGER_GENERIC)) { return baseName.copy().withStyle((amount == 1) ? green : red); }
        return baseName;
    }

    // CUSTOM METHOD - Message when consumed Level Charger (Plus / Minus) items
    private void itemHurt(Player player, ItemStack chargerStack) {
        String screen = amount + " level(s)!";
        boolean value = (amount == 1);
        if (chargerStack.is(ModTags.Items.LEVEL_CHARGER_GENERIC)) {
            player(player, value ? ("Increased +" + screen) : ("Decreased " + screen), value ? green : red);
        }
    }
}