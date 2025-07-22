package net.karen.mccoursemod.item;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs extends CreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MccourseMod.MOD_ID);

//    public static final RegistryObject<CreativeModeTab> ALEXANDRITE_ITEMS_TAB =
//            CREATIVE_MODE_TABS.register("alexandrite_items_tab",
//                    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ALEXANDRITE.get()))
//                            .title(Component.translatable("creativetab.mccoursemod.alexandrite_items"))
//                            .displayItems((itemDisplayParameters, output) -> {
//                                // Custom item
//                                output.accept(ModItems.ALEXANDRITE.get());
//                            }).build());

    public static final RegistryObject<CreativeModeTab> ENCHANT_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("enchant_blocks_tab",
                    () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.ANVIL))
//                            .withTabsBefore(ALEXANDRITE_ITEMS_TAB.getId())
                            .title(Component.translatable("creativetab.mccoursemod.enchant_blocks"))
                            .displayItems((itemDisplayParameters, output) -> {
                                // Custom block
                                output.accept(ModBlocks.ENCHANT.get());
                            }).build());


    public static void register(BusGroup eventBus) { CREATIVE_MODE_TABS.register(eventBus); }
}