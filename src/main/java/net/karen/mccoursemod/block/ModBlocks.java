package net.karen.mccoursemod.block;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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
            () -> new MagicEnchantBlock(BlockBehaviour.Properties.of().strength(5.0F, 3600000.0F)
                                                                      .requiresCorrectToolForDrops()
                                                                      .setId(ResourceKey.create(Registries.BLOCK,
                                                                             ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                                                                "enchant")))));

    public static final RegistryObject<Block> ALEXANDRITE_BLOCK = registerBlock("alexandrite_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5F, 5F)
                                                          .requiresCorrectToolForDrops()
                                                          .setId(ResourceKey.create(Registries.BLOCK,
                                                                 ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID,
                                                                                                 "alexandrite_block")))));

    // Register all custom blocks
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // Register all custom blocks as block items
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                                                               ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, name)))));
    }

    // CUSTOM METHOD - Registry all custom blocks on event bus
    public static void register(BusGroup busGroup) { BLOCKS.register(busGroup); }
}