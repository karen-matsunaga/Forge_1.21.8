package net.karen.mccoursemod.datagen.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonObject;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ModModelTemplate extends ModelTemplate {
    private final Optional<ResourceLocation> model;
    private final Set<TextureSlot> requiredSlots;
    private final Optional<String> suffix;

    public ModModelTemplate(Optional<ResourceLocation> pModel,
                            Optional<String> pSuffix,
                            TextureSlot... pRequiredSlots) {
        super(pModel, pSuffix, pRequiredSlots);
        this.model = pModel;
        this.suffix = pSuffix;
        this.requiredSlots = ImmutableSet.copyOf(pRequiredSlots);
    }

    public @NotNull ResourceLocation create(@NotNull Block block,
                                            @NotNull TextureMapping textureMapping,
                                            @NotNull BiConsumer<ResourceLocation, ModelInstance> consumer) {
        if (block instanceof DoorBlock || block instanceof TrapDoorBlock) {
            return this.createDoor(ModelLocationUtils.getModelLocation(block, this.suffix.orElse("")),
                                   textureMapping, consumer);
        }
        return this.create(ModelLocationUtils.getModelLocation(block, this.suffix.orElse("")),
                                   textureMapping, consumer);
    }

    public @NotNull ResourceLocation create(@NotNull ResourceLocation location,
                                            @NotNull TextureMapping textureMapping,
                                            @NotNull BiConsumer<ResourceLocation, ModelInstance> consumer) {
        Map<TextureSlot, ResourceLocation> map = this.createMap(textureMapping);
        consumer.accept(location, () -> {
            JsonObject jsonobject = new JsonObject();
            this.model.ifPresent(resourceLocation ->
                                 jsonobject.addProperty("parent", resourceLocation.toString()));
            if (!map.isEmpty()) {
                JsonObject jsonobject1 = new JsonObject();
                map.forEach((textureSlot, resourceLocation) ->
                             jsonobject1.addProperty(textureSlot.getId(), resourceLocation.toString()));
                jsonobject.add("textures", jsonobject1);
            }
            return jsonobject;
        });
        return location;
    }

    public ResourceLocation createDoor(ResourceLocation resourceLocation,
                                       TextureMapping textureMapping,
                                       BiConsumer<ResourceLocation, ModelInstance> consumer) {
        Map<TextureSlot, ResourceLocation> map = this.createMap(textureMapping);
        consumer.accept(resourceLocation, () -> {
            JsonObject jsonobject = new JsonObject();
            this.model.ifPresent(location ->
                                 jsonobject.addProperty("parent", location.toString()));
            if (!map.isEmpty()) {
                JsonObject jsonobject1 = new JsonObject();
                map.forEach((textureSlot, location) ->
                             jsonobject1.addProperty(textureSlot.getId(), location.toString()));
                jsonobject.add("textures", jsonobject1);
            }
            jsonobject.addProperty("render_type", "minecraft:cutout");
            return jsonobject;
        });
        return resourceLocation;
    }

    private Map<TextureSlot, ResourceLocation> createMap(TextureMapping textureMapping) {
        return Streams.concat(this.requiredSlots.stream(),
                              textureMapping.getForced())
                                            .collect(ImmutableMap.toImmutableMap(Function.identity(), textureMapping::get));
    }
}