package net.karen.mccoursemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static net.karen.mccoursemod.util.ChatUtil.*;
import static net.karen.mccoursemod.util.Util.*;

public class DisenchantBlock extends Block {
    private final int type;

    public DisenchantBlock(Properties properties, int type) {
        super(properties);
        this.type = type;
    }

    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        if (!level.isClientSide() && entity instanceof ItemEntity itemEntity) {
            ItemStack item = itemEntity.getItem(); // Get real item
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(item); // Get all enchantments of the item
            Map<Holder<Enchantment>, Integer> getEnchantment = new HashMap<>();
            for (Map.Entry<Holder<Enchantment>, Integer> entry : enchantments.entrySet()) {
                Holder<Enchantment> ench = entry.getKey();
                int lvl = entry.getValue();
                getEnchantment.put(ench, lvl);
            }
            switch (type) {
                case 1 -> individualEnchantedBook(level, pos, item, itemEntity, getEnchantment); // Decrement more enchantment level
                case 2 -> groupedEnchantedBook(level, pos, item, itemEntity, getEnchantment); // Original enchantment level
            }
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public @NotNull MutableComponent getName() {
        return super.getName().copy().withStyle((type == 1) ? darkAqua : purple);
    }

    // CUSTOM METHOD - TYPE 1
    private static void individualEnchantedBook(Level level, BlockPos pos, ItemStack item,
                                                ItemEntity itemEntity, Map<Holder<Enchantment>, Integer> enchant) {
        if (enchant.isEmpty()) { return; }
        if (isBook(item)) { // Is enchanted book divided on INDIVIDUAL books
            List<ItemStack> separatedBooks = extractEnchantments(item);
            separatedBooks.forEach(book -> dropEnchanted(level, pos, book));
        }
        else { // Is tool, armor, etc. an enchanted book with all enchantments and base item
            groupedEnch(enchant, level, pos);
            enchant(level, pos, item);
        }
        itemEntity.discard(); // Removed item original
    }


    // CUSTOM METHOD - TYPE 2
    private static void groupedEnchantedBook(Level level, BlockPos pos, ItemStack item,
                                             ItemEntity itemEntity, Map<Holder<Enchantment>, Integer> enchant) {
        // Skip if item has no enchantments - Only process if it's not a previously split book (to avoid infinite loop)
        if (enchant.isEmpty() || (isBook(item) && enchant.size() == 1)) { return; }
        if (!isBook(item)) { // 1. Drop enchanted books with the enchantments
            groupedBooks(enchant, true, level, pos); // It's a TOOL/ARMOR/etc. (Grouped books)
            enchant(level, pos, item);
        }
        // 2. Split each enchantment into INDIVIDUAL books -> ENCHANTED BOOK
        else { groupedBooks(enchant, false, level, pos); }
        itemEntity.discard(); // Remove the original item (to avoid reprocessing)
    }

    // CUSTOM METHOD - Extract enchantments on separate enchanted books (Divided per level)
    private static List<ItemStack> extractEnchantments(ItemStack enchantedBook) {
        List<ItemStack> result = new ArrayList<>();
        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(enchantedBook);
        for (Map.Entry<Holder<Enchantment>, Integer> entry : enchantments.entrySet()) {
            Holder<Enchantment> ench = entry.getKey();
            int lvl = entry.getValue();
            // Enchantment level 10 or below (INDIVIDUAL books with enchantment level 1)
            if (lvl <= 10) {
                for (int i = 0; i < lvl; i++) {
                    result.add(EnchantmentHelper.createBook(new EnchantmentInstance(ench, 1)));
                }
            }
            else { // Enchantment level 11 or above (INDIVIDUAL books with enchantment level 10 + remaining enchantment level)
                int tens = lvl / 10, remainder = lvl % 10;
                for (int i = 0; i < tens; i++) result.add(EnchantmentHelper.createBook(new EnchantmentInstance(ench, 10)));
                if (remainder > 0) { result.add(EnchantmentHelper.createBook(new EnchantmentInstance(ench, remainder))); }
            }
        }
        return result;
    }

    // CUSTOM METHOD - GROUPED -> [Item -> Group Book] | NOT GROUPED -> [Group Book -> Single Book]
    private static void groupedBooks(Map<Holder<Enchantment>, Integer> item, boolean grouped,
                                     Level level, BlockPos pos) {
        if (grouped) { groupedEnch(item, level, pos); } // GROUPED book -> Two or more enchantments
        if (!grouped) { individualEnch(item, level, pos); } // INDIVIDUAL book -> One enchantment
    }

    // CUSTOM METHOD - Item dropped is an ENCHANTED (tool, armor, etc.) or an ENCHANTED BOOK
    private static boolean isBook(ItemStack item) { return item.is(Items.ENCHANTED_BOOK); }

    // CUSTOM METHOD - Enchanted item transform on Base item
    public static void enchant(Level level, BlockPos pos, ItemStack item) {
        // Remove enchantments of original item - Drop the base item WITHOUT enchantments
        ItemStack baseItem = new ItemStack(item.getItem());
        dropEnchanted(level, pos, baseItem); // Drop base item WITHOUT enchantment
    }
}