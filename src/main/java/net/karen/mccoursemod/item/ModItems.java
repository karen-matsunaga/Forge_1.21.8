package net.karen.mccoursemod.item;

import net.karen.mccoursemod.MccourseMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MccourseMod.MOD_ID);

    // CUSTOM METHOD - Registry all custom items
    public static RegistryObject<Item> item(String name, Supplier<Item> supplier) {
        return ITEMS.register(name, supplier);
    }

    // CUSTOM METHOD - Registry all items on MccourseMod file
    public static void register(BusGroup busGroup) { ITEMS.register(busGroup); }
}
