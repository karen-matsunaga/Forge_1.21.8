package net.karen.mccoursemod.item;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.enchantment.ModEnchantments;
import net.karen.mccoursemod.item.custom.HammerItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MccourseMod.MOD_ID);

    // Custom items
    public static final RegistryObject<Item> ALEXANDRITE = ITEMS.register("alexandrite",
            () -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                       ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "alexandrite")
                                                       ))));

    // Custom tools
    public static final RegistryObject<Item> ALEXANDRITE_HAMMER = ITEMS.register("alexandrite_hammer",
            () -> new HammerItem(ModToolMaterials.ALEXANDRITE, 1F, -3.3F,
                  new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                              ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "alexandrite_hammer")))
                                       .repairable(ItemTags.NETHERITE_TOOL_MATERIALS)));

    // Custom armors
    public static final RegistryObject<Item> ALEXANDRITE_HELMET = ITEMS.register("alexandrite_helmet",
            () -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                       ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                                        "alexandrite_helmet")))
                                                .humanoidArmor(ModArmorMaterials.ALEXANDRITE, ArmorType.HELMET)));

    public static final RegistryObject<Item> ALEXANDRITE_CHESTPLATE = ITEMS.register("alexandrite_chestplate",
            () -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                       ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                       "alexandrite_chestplate")))
                                                .humanoidArmor(ModArmorMaterials.ALEXANDRITE, ArmorType.CHESTPLATE)));

    public static final RegistryObject<Item> ALEXANDRITE_LEGGINGS = ITEMS.register("alexandrite_leggings",
            () -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                       ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                 "alexandrite_leggings")))
                                                .humanoidArmor(ModArmorMaterials.ALEXANDRITE, ArmorType.LEGGINGS)));

    public static final RegistryObject<Item> ALEXANDRITE_BOOTS = ITEMS.register("alexandrite_boots",
            () -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                       ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                    "alexandrite_boots")))
                                                .humanoidArmor(ModArmorMaterials.ALEXANDRITE, ArmorType.BOOTS)));

    // Level Charger
    public static final RegistryObject<Item> LEVEL_CHARGER_PLUS = ITEMS.register("level_charger_plus",
            () -> new LevelChargerItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                   ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                   "level_charger_plus")))
                                                            .fireResistant(), 1, ModEnchantments.MESSAGE));

    public static final RegistryObject<Item> LEVEL_CHARGER_MINUS = ITEMS.register("level_charger_minus",
            () -> new LevelChargerItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                   ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                   "level_charger_minus")))
                                                            .fireResistant(), -1, ModEnchantments.MESSAGE));

    public static final RegistryObject<Item> LEVEL_CHARGER_PLUS_FORTUNE =
            ITEMS.register("level_charger_plus_fortune",
            () -> new LevelChargerItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                   ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                            "level_charger_plus_fortune")))
                                                            .fireResistant(), 1, Enchantments.FORTUNE));

    public static final RegistryObject<Item> LEVEL_CHARGER_MINUS_FORTUNE =
            ITEMS.register("level_charger_minus_fortune",
            () -> new LevelChargerItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                   ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                   "level_charger_minus_fortune")))
                                                            .fireResistant(), -1, Enchantments.FORTUNE));


    // CUSTOM METHOD - Registry all items on MccourseMod file
    public static void register(BusGroup busGroup) { ITEMS.register(busGroup); }
}