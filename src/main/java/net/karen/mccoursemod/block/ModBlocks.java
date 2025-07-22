package net.karen.mccoursemod.block;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MccourseMod.MOD_ID);

    // CUSTOM Enchant block
    public static final RegistryObject<Block> ENCHANT = registerBlock("enchant",
            () -> new MagicEnchantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)
                    .strength(5F)
                    .explosionResistance(3600000.0F)));

    // CUSTOM METHOD - Registry all custom blocks
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // CUSTOM METHOD - Registry all custom blocks as item
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // CUSTOM METHOD - Registry all custom blocks on event bus
    public static void register(BusGroup busGroup) { BLOCKS.register(busGroup); }
}