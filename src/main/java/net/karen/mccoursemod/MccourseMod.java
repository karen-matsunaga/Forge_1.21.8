package net.karen.mccoursemod;

import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.item.ModCreativeModeTabs;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MccourseMod.MOD_ID)
public final class MccourseMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "mccoursemod";

    public MccourseMod(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        // Register the commonSetup method for mod-loading
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        ModBlocks.register(modBusGroup);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.register(modBusGroup);
        // Register the Deferred Register to the mod event bus so tabs get registered
        ModCreativeModeTabs.register(modBusGroup);

        // Register the item to a creative tab
        BuildCreativeModeTabContentsEvent.getBus(modBusGroup).addListener(MccourseMod::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    // Add the example block item to the building blocks tab
    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.ALEXANDRITE);
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.ALEXANDRITE_BLOCK);
        }
    }
}