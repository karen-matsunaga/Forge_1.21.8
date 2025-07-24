package net.karen.mccoursemod.item;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.item.custom.HammerItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MccourseMod.MOD_ID);

    public static final RegistryObject<Item> ALEXANDRITE = ITEMS.register("alexandrite",
            () -> new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                       ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "alexandrite")
                                                       ))));

    public static final RegistryObject<Item> ALEXANDRITE_HAMMER = ITEMS.register("alexandrite_hammer",
            () -> new HammerItem(ModToolMaterials.ALEXANDRITE, 1F, -3.3F,
                  new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                              ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "alexandrite_hammer")))
                                       .repairable(ItemTags.NETHERITE_TOOL_MATERIALS)));

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

    // CUSTOM METHOD - Registry all items on MccourseMod file
    public static void register(BusGroup busGroup) { ITEMS.register(busGroup); }
}