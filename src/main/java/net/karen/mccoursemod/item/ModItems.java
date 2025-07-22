package net.karen.mccoursemod.item;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.block.MagicEnchantBlock;
import net.karen.mccoursemod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MccourseMod.MOD_ID);

//    public static final ResourceKey<?> ENCHANT_ITEM_ID = ResourceKey.create(ForgeRegistries.Keys.ITEMS,
//            ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "enchant_block"));
//
//    public static final RegistryObject<Item> ENCHANT_ITEM = ITEMS.register("enchant_block", () ->
//                    new BlockItem(ModBlocks.ENCHANT.get(), new Item.Properties()
//                            .setId(ResourceKey.create(ForgeRegistries.Keys.ITEMS, ENCHANT_ITEM_ID.location()))));

    // CUSTOM METHOD - Registry all items on MccourseMod file
    public static void register(BusGroup busGroup) { ITEMS.register(busGroup); }
}
