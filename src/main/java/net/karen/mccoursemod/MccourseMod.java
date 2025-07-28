package net.karen.mccoursemod;

import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.enchantment.ModEnchantmentEffects;
import net.karen.mccoursemod.item.ModCreativeModeTabs;
import net.karen.mccoursemod.item.ModItems;
import net.karen.mccoursemod.particle.AlexandriteParticles;
import net.karen.mccoursemod.particle.ModParticles;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        // Register the Deferred Register to the mod event bus so enchantments get registered
        ModEnchantmentEffects.register(modBusGroup);
        // Register the Deferred Register to the mod event bus so particles get registered
        ModParticles.register(modBusGroup);

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

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        // CUSTOM EVENT - Register all custom entity renderers, custom menu screen, etc.
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
        // CUSTOM EVENT - Register all custom particles
        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            // Register all custom particles
            event.registerSpriteSet(ModParticles.ALEXANDRITE_PARTICLES.get(), AlexandriteParticles.Provider::new);
        }
        // CUSTOM EVENT - Register all custom block entity renderers
        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {}
    }
}