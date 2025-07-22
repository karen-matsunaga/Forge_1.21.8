package net.karen.mccoursemod.item;

import net.karen.mccoursemod.MccourseMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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

    // CUSTOM METHOD - Registry all items on MccourseMod file
    public static void register(BusGroup busGroup) { ITEMS.register(busGroup); }
}