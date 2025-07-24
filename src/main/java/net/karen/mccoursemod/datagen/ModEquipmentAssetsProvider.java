package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.item.ModEquipmentAssets;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModEquipmentAssetsProvider extends EquipmentAssetProvider {
    private final PackOutput.PathProvider pathProvider;

    public ModEquipmentAssetsProvider(PackOutput output) {
        super(output);
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> map = new HashMap<>();
        ModEquipmentAssets.bootstrap((resourceKey, clientInfo) -> {
            if (map.putIfAbsent(resourceKey, clientInfo) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + resourceKey);
            }
        });
        return DataProvider.saveAll(cachedOutput, EquipmentClientInfo.CODEC, this.pathProvider::json, map);
    }

    @Override
    public @NotNull String getName() { return "Mod Equipment Asset Definitions"; }
}