package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MccourseMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        // CUSTOM Block models and Item models
        generator.addProvider(event.includeClient(), new ModModelProvider(packOutput));

        // CUSTOM Loot Tables
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                              List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new,
                                                                             LootContextParamSets.BLOCK)), lookupProvider));

        // CUSTOM Recipes
        generator.addProvider(event.includeServer(), new ModRecipeProvider.ModRecipeProviderRunner(packOutput, lookupProvider));

        // CUSTOM Block tags
        generator.addProvider(event.includeServer(), new ModVanillaBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));

        // CUSTOM Item tags
//        generator.addProvider(event.includeServer(), new ModVanillaItemTagsProvider(packOutput, lookupProvider, existingFileHelper));

        // CUSTOM Enchantment tags
//        generator.addProvider(event.includeServer(), new ModEnchantmentTagsProvider(packOutput, lookupProvider, existingFileHelper));

        // CUSTOM Languages
        generator.addProvider(event.includeClient(), new ModLanguageProvider(packOutput, "en_us"));
    }
}