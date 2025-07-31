package net.karen.mccoursemod.item;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.component.ModDataComponentTypes;
import net.karen.mccoursemod.item.custom.*;
import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
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
                                                       ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                       "alexandrite")))));

    // Custom tools
    // CUSTOM Sword
    public static final RegistryObject<Item> ALEXANDRITE_SWORD = ITEMS.register("alexandrite_sword",
            () -> new Item(new Item.Properties().sword(ModToolMaterials.ALEXANDRITE, 3, -2.4f)
                                                .fireResistant()
                                                .setId(ResourceKey.create(Registries.ITEM,
                                                       ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                       "alexandrite_sword")))
                                                .repairable(ModTags.Items.ALEXANDRITE_TOOL_MATERIALS)));

    // CUSTOM Pickaxe
    public static final RegistryObject<Item> ALEXANDRITE_PICKAXE = ITEMS.register("alexandrite_pickaxe",
            () -> new Item(new Item.Properties().pickaxe(ModToolMaterials.ALEXANDRITE, 1, -2.8f)
                                                .fireResistant()
                                                .setId(ResourceKey.create(Registries.ITEM,
                                                       ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                       "alexandrite_pickaxe")))
                                                .repairable(ModTags.Items.ALEXANDRITE_TOOL_MATERIALS)));

    // CUSTOM Shovel
    public static final RegistryObject<Item> ALEXANDRITE_SHOVEL = ITEMS.register("alexandrite_shovel",
            () -> new ShovelItem(ModToolMaterials.ALEXANDRITE, 1.5f, -3.0f,
                  new Item.Properties().fireResistant()
                                       .setId(ResourceKey.create(Registries.ITEM,
                                              ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                              "alexandrite_shovel")))
                                       .repairable(ModTags.Items.ALEXANDRITE_TOOL_MATERIALS)));

    // CUSTOM Axe
    public static final RegistryObject<Item> ALEXANDRITE_AXE = ITEMS.register("alexandrite_axe",
            () -> new AxeItem(ModToolMaterials.ALEXANDRITE,6, -3.2f,
                  new Item.Properties().fireResistant()
                                       .setId(ResourceKey.create(Registries.ITEM,
                                              ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                              "alexandrite_axe")))
                                       .repairable(ModTags.Items.ALEXANDRITE_TOOL_MATERIALS)));

    // CUSTOM Hoe
    public static final RegistryObject<Item> ALEXANDRITE_HOE = ITEMS.register("alexandrite_hoe",
            () -> new HoeItem(ModToolMaterials.ALEXANDRITE, 0, -3.0f,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                "alexandrite_hoe")))));

    public static final RegistryObject<Item> ALEXANDRITE_HAMMER = ITEMS.register("alexandrite_hammer",
            () -> new HammerItem(ModToolMaterials.ALEXANDRITE, 1F, -3.3F,
                  new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                              ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                              "alexandrite_hammer")))
                                       .repairable(ModTags.Items.ALEXANDRITE_TOOL_MATERIALS)));

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
    public static final RegistryObject<Item> LEVEL_CHARGER_GENERIC_PLUS =
            ITEMS.register("level_charger_generic_plus",
            () -> new LevelChargerGenericItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                          ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                          "level_charger_generic_plus")))
                                                                   .fireResistant(), 1));

    public static final RegistryObject<Item> LEVEL_CHARGER_GENERIC_MINUS =
            ITEMS.register("level_charger_generic_minus",
            () -> new LevelChargerGenericItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                          ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                          "level_charger_generic_minus")))
                                                                   .fireResistant(), -1));

    public static final RegistryObject<Item> LEVEL_CHARGER_SPECIF_PLUS_FORTUNE =
            ITEMS.register("level_charger_specif_plus_fortune",
            () -> new LevelChargerSpecifItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                         ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                         "level_charger_specif_plus_fortune")))
                                                                  .fireResistant(), 1, Enchantments.FORTUNE));

    public static final RegistryObject<Item> LEVEL_CHARGER_SPECIF_MINUS_FORTUNE =
            ITEMS.register("level_charger_specif_minus_fortune",
            () -> new LevelChargerSpecifItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                         ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                         "level_charger_specif_minus_fortune")))
                                                                  .fireResistant(), -1, Enchantments.FORTUNE));

    // Gem Effect
    public static final RegistryObject<Item> GEM_EFFECT_SATURATION =
            ITEMS.register("gem_effect_saturation",
            () -> new GemEffectItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                "gem_effect_saturation"))),
                                                                MobEffects.SATURATION, -1, 0));

    // Multiplier item
    public static final RegistryObject<Item> MULTIPLIER =
            ITEMS.register("multiplier",
            () -> new MultiplierItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                 ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                 "multiplier"))), 10));

    // Magnet item
    public static final RegistryObject<Item> MAGNET =
            ITEMS.register("magnet",
            () -> new MultiplierItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                 ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                 "magnet"))), 1));

    // Rainbow item
    public static final RegistryObject<Item> RAINBOW =
            ITEMS.register("rainbow",
            () -> new MultiplierItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                 ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                 "rainbow"))), 1));

    // Auto smelt item
    public static final RegistryObject<Item> AUTO_SMELT =
            ITEMS.register("auto_smelt",
            () -> new MultiplierItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                 ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                 "auto_smelt"))), 1));

    // More Ores item
    public static final RegistryObject<Item> MORE_ORES =
            ITEMS.register("more_ores",
            () -> new MultiplierItem(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                                 ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                 "more_ores"))), 1));

    // CUSTOM METHOD - Registry all items on MccourseMod file
    public static void register(BusGroup busGroup) { ITEMS.register(busGroup); }
}