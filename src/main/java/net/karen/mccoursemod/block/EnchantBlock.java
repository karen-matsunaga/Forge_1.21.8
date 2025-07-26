package net.karen.mccoursemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class EnchantBlock extends Block {
    public EnchantBlock(Properties properties) { super(properties); }

    @Override
    public void stepOn(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        if (!level.isClientSide() && entity instanceof ItemEntity) {
            List<ItemEntity> enchantedBooks = findItems(level, pos, book -> // Book dropped on ground
                                                        book.getItem().getItem() == Items.ENCHANTED_BOOK),
                             enchantedItems = findItems(level, pos, item -> // Item dropped on ground
                                                        item.getItem().isEnchantable() || item.getItem().isEnchanted());
            if (enchantedBooks.size() > 1) { combineBooks(level, pos, enchantedBooks); } // Combine enchanted books
            else if (enchantedBooks.size() == 1 && !enchantedItems.isEmpty()) {
               applyBookToItem(level, pos, enchantedBooks.getFirst(), enchantedItems.getFirst()); // Combine book and item
            }
            else if (enchantedItems.size() > 1) { combineItems(level, pos, enchantedItems); } // Combine items
        }
        super.stepOn(level, pos, state, entity);
    }

    // CUSTOM METHOD - Enchanted book or items on Magic Book area
    private static List<ItemEntity> findItems(Level level, BlockPos pos,
                                              Predicate<ItemEntity> filter) {
        return level.getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(0.5), filter);
    }

    // CUSTOM METHOD - Grouped enchantments on enchanted book
    private static void groupEnchant(List<ItemEntity> list, Map<Holder<Enchantment>, Integer> combine,
                                     Item drop, Level level, BlockPos pos) {
        list.forEach(item -> {
            ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(item.getItem()); // Get item enchantment
            enchants.entrySet().forEach((ench) -> { // Added and sum enchantment level
                if (ench.getIntValue() > 0) { combine.merge(ench.getKey(), ench.getIntValue(), Integer::sum); }
            });
            item.discard(); // Remove enchanted book or item
        });
        ItemStack newValue = new ItemStack(drop); // Enchanted book or item with new enchantment
        ItemEnchantments newDropItem = ItemEnchantments.EMPTY;
        ItemEnchantments.Mutable newDrop = new ItemEnchantments.Mutable(newDropItem);
        combine.forEach((ench, lvl) -> { if (lvl > 0) { newDrop.set(ench, lvl); } });
        EnchantmentHelper.setEnchantments(newValue, newDrop.toImmutable()); // Set item enchantments
        dropItem(level, pos, newValue); // Drop item on ground
    }

    // CUSTOM METHOD - COMBINE vanilla or custom enchantments on enchanted book
    private static void combineBooks(Level level, BlockPos pos, List<ItemEntity> books) {
        Map<Holder<Enchantment>, Integer> combined = new HashMap<>();
        groupEnchant(books, combined, Items.ENCHANTED_BOOK, level, pos);
        playSound(level, pos);
    }

    // CUSTOM METHOD - Transfer enchanted book with enchantments to vanilla or custom item
    private static void applyBookToItem(Level level, BlockPos pos,
                                        ItemEntity bookEntity, ItemEntity toolEntity) {
        ItemStack bookStack = bookEntity.getItem(), toolStack = toolEntity.getItem();
        ItemEnchantments bookEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(bookStack), // Enchanted book
                toolEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(toolStack); // Base item
        Map<Holder<Enchantment>, Integer> sumLevel = new HashMap<>();
        // Added enchantment level -> BOOK
        bookEnchantments.entrySet().forEach((ench) -> {
            if (ench.getIntValue() > 0) { sumLevel.put(ench.getKey(), ench.getIntValue()); }
        });
        // Added and sum enchantment level -> ITEM
        toolEnchantments.entrySet().forEach((ench) -> {
            if (ench.getIntValue() > 0) { sumLevel.merge(ench.getKey(), ench.getIntValue(), Integer::sum); }
        });
        // Store all new enchantment levels
        ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(toolEnchantments);
        sumLevel.forEach(enchantments::set); // Stored new enchantment level
        EnchantmentHelper.setEnchantments(toolStack, enchantments.toImmutable());
        // If you can't deliver, drop it in the world
        dropItem(level, pos, toolStack);
        toolEntity.discard(); // Remove old item
        bookStack.shrink(1); // Remove old book
        if (bookStack.isEmpty()) { bookEntity.discard(); }
        else { bookEntity.setItem(bookStack); }
        playSound(level, pos);
    }

    // CUSTOM METHOD - COMBINE vanilla or custom enchanted items
    private static void combineItems(Level level, BlockPos pos, List<ItemEntity> items) {
        Item firstType = items.getFirst().getItem().getItem();
        boolean allSame = items.stream().allMatch(e -> e.getItem().getItem() == firstType);
        if (!allSame) { return; }
        Map<Holder<Enchantment>, Integer> combined = new HashMap<>();
        groupEnchant(items, combined, firstType, level, pos); // Drop tool WITH all sum enchantments
        for (int i = 0; i < items.size() - 1; i++) { // Drop tools WITHOUT enchantment
            ItemStack clean = new ItemStack(firstType);
            dropItem(level, pos, clean);
        }
        playSound(level, pos);
    }

    // CUSTOM METHOD - Enchanted Book and Item quantities list
    private static void dropItem(Level level, BlockPos pos, ItemStack stack) {
        ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack);
        item.setDeltaMovement(Vec3.ZERO);
        level.addFreshEntity(item);
    }

    // CUSTOM METHOD - Sound when player uses Magic Book block
    private static void playSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}