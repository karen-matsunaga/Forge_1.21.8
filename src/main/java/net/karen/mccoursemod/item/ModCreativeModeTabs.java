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

    // Register all custom items
    public static final RegistryObject<CreativeModeTab> MCCOURSE_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("mccourse_items_tab",
                    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ALEXANDRITE.get()))
                            .title(Component.translatable("creativetab.mccoursemod.mccourse_items"))
                            .displayItems((itemDisplayParameters, output) -> {
                                // CUSTOM items
                                output.accept(ModItems.ALEXANDRITE.get());
                                // CUSTOM tools
                                output.accept(ModItems.ALEXANDRITE_HAMMER.get());
                                // CUSTOM armors
                                output.accept(ModItems.ALEXANDRITE_HELMET.get());
                                output.accept(ModItems.ALEXANDRITE_CHESTPLATE.get());
                                output.accept(ModItems.ALEXANDRITE_LEGGINGS.get());
                                output.accept(ModItems.ALEXANDRITE_BOOTS.get());
                                output.accept(ModItems.LEVEL_CHARGER_PLUS.get());
                                output.accept(ModItems.LEVEL_CHARGER_MINUS.get());
                            }).build());

    // Register all custom blocks
    public static final RegistryObject<CreativeModeTab> MCCOURSE_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("mccourse_blocks_tab",
                    () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.ANVIL))
                            .withTabsBefore(MCCOURSE_ITEMS_TAB.getId())
                            .title(Component.translatable("creativetab.mccoursemod.mccourse_blocks"))
                            .displayItems((itemDisplayParameters, output) -> {
                                // CUSTOM blocks
                                output.accept(ModBlocks.ENCHANT.get());
                                output.accept(ModBlocks.ALEXANDRITE_BLOCK.get());
                            }).build());

    // Register all custom items and blocks on bus group event
    public static void register(BusGroup busGroup) { CREATIVE_MODE_TABS.register(busGroup); }
}