package net.karen.mccoursemod.item;

import net.karen.mccoursemod.MccourseMod;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAsset;
import java.util.function.BiConsumer;

public class ModEquipmentAssets {
    private static final ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID =
            ResourceKey.createRegistryKey(ResourceLocation.withDefaultNamespace("equipment_asset"));

    public static final ResourceKey<EquipmentAsset> ALEXANDRITE = id("alexandrite");

    // CUSTOM METHOD - Register all custom equipment assets -> Resource Key
    private static ResourceKey<EquipmentAsset> id(String name) {
        return ResourceKey.create(ROOT_ID, ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, name));
    }

    // CUSTOM METHOD - Register all custom equipment assets
    public static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer) {
        registerAssetWithLayers(consumer, ModEquipmentAssets.ALEXANDRITE, "alexandrite");
    }

    // CUSTOM METHOD - Horse body
    private static void registerAssetWithLayers(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer,
                                                ResourceKey<EquipmentAsset> asset, String name) {
        consumer.accept(asset, EquipmentClientInfo.builder()
                .addHumanoidLayers(ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, name))
                .addLayers(EquipmentClientInfo.LayerType.HORSE_BODY,
                           new EquipmentClientInfo.Layer(ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, name)))
                .build());
    }
}