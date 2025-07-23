package net.karen.mccoursemod.util;

import net.karen.mccoursemod.MccourseMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModTags {
    // CUSTOM CLASS - Registry all custom block tags
//    public static class Blocks {
//        // Created tool tags
//        public static final TagKey<Block> NEEDS_ALEXANDRITE_TOOL =
//                createTag("needs_alexandrite_tool");
//
//        public static final TagKey<Block> INCORRECT_FOR_ALEXANDRITE_TOOL =
//                createTag("incorrect_for_alexandrite_tool");
//
//        // CUSTOM METHOD - Registry all custom block tags
//        private static TagKey<Block> createTag(String name) {
//            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, name));
//        }
//    }

    // CUSTOM CLASS - Registry all custom item tags
//    public static class Items {
//        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");
//
//        // Created Fly effect item tag
//        public static final TagKey<Item> HELMET_FLY = createTag("helmet_fly");
//        public static final TagKey<Item> CHESTPLATE_FLY = createTag("chestplate_fly");
//        public static final TagKey<Item> LEGGINGS_FLY = createTag("leggings_fly");
//        public static final TagKey<Item> BOOTS_FLY = createTag("boots_fly");
//
//        // Level Charger items
//        public static final TagKey<Item> LEVEL_CHARGER_GENERAL = createTag("level_charger_general");
//
//        // CUSTOM METHOD - Registry all custom item tags
//        private static TagKey<Item> createTag(String name) {
//            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, name));
//        }
//    }

    // CUSTOM CLASS - Registry all custom enchantment tags
    public static class Enchantments {
        public static final TagKey<Enchantment> LIGHTNING_STRIKER_TAG = createTag("lightning_striker_tag");

        // CUSTOM METHOD - Registry all custom enchantment tags
        private static TagKey<Enchantment> createTag(String name) {
            return EnchantmentTags.create(ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, name));
        }
    }
}