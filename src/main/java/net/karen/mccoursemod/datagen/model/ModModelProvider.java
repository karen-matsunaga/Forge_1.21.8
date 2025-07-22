package net.karen.mccoursemod.datagen.model;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.datagen.ModBlockModelGenerators;
import net.karen.mccoursemod.datagen.ModItemModelGenerators;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output);
        this.blockStatePathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.itemInfoPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
        this.modelPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }

    private final PackOutput.PathProvider blockStatePathProvider;
    private final PackOutput.PathProvider itemInfoPathProvider;
    private final PackOutput.PathProvider modelPathProvider;

    @Override
    protected @NotNull Stream<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get);
    }

    @Override
    protected @NotNull Stream<Item> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream().map(RegistryObject::get);
    }

    @Override
    protected @NotNull BlockModelGenerators getBlockModelGenerators(@NotNull BlockStateGeneratorCollector blocks,
                                                                    @NotNull ItemInfoCollector items,
                                                                    @NotNull SimpleModelCollector models) {
        return new ModBlockModelGenerators(blocks, items, models);
    }

    @Override
    protected @NotNull ItemModelGenerators getItemModelGenerators(@NotNull ItemInfoCollector items,
                                                                  @NotNull SimpleModelCollector models) {
        return new ModItemModelGenerators(items, models);
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        ModItemInfoCollector iteminfocollector = new ModItemInfoCollector(this::getKnownItems);
        ModModelProvider.BlockStateGeneratorCollector blockstategeneratorcollector = new BlockStateGeneratorCollector(this::getKnownBlocks);
        ModModelProvider.SimpleModelCollector simplemodelcollector = new SimpleModelCollector();
        this.getBlockModelGenerators(blockstategeneratorcollector, iteminfocollector, simplemodelcollector).run();
        this.getItemModelGenerators(iteminfocollector, simplemodelcollector).run();
        blockstategeneratorcollector.validate();
        iteminfocollector.finalizeAndValidate();
        return CompletableFuture.allOf(blockstategeneratorcollector.save(cachedOutput, this.blockStatePathProvider),
                                       simplemodelcollector.save(cachedOutput, this.modelPathProvider),
                                       iteminfocollector.save(cachedOutput, this.itemInfoPathProvider));
    }

    public static class ModItemInfoCollector extends ItemInfoCollector {
        private final Map<Item, ClientItem> itemInfos = new HashMap<>();
        private final Map<Item, Item> copies = new HashMap<>();
        private final Supplier<Stream<Item>> known;

        public ModItemInfoCollector() {
            this(() -> BuiltInRegistries.ITEM.stream().filter(item ->
                    MccourseMod.MOD_ID.equals(item.builtInRegistryHolder().key().location().getNamespace())));
        }

        public ModItemInfoCollector(Supplier<Stream<Item>> known) {
            this.known = known;
        }

        @Override
        public void accept(@NotNull Item register, @NotNull ItemModel.Unbaked model) {
            this.register(register, new ClientItem(model, ClientItem.Properties.DEFAULT));
        }

        private void register(Item item, ClientItem clientItem) {
            ClientItem clientitem = this.itemInfos.put(item, clientItem);
            if (clientitem != null) {
                throw new IllegalStateException("Duplicate item model definition for " + item);
            }
        }

        @Override
        public void copy(@NotNull Item item, @NotNull Item item1) {
            this.copies.put(item1, item);
        }

        public void generateDefaultBlockModels() {
            ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(item -> {
                if (!this.copies.containsKey(item)) {
                    if (item instanceof BlockItem blockitem && !this.itemInfos.containsKey(blockitem)) {
                        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(blockitem.getBlock());
                        this.accept(blockitem, ItemModelUtils.plainModel(resourcelocation));
                    }
                }
            });
        }
        public void finalizeAndValidate() {
            this.copies.forEach((item, item1) -> {
                ClientItem clientitem = this.itemInfos.get(item1);
                if (clientitem == null) {
                    throw new IllegalStateException("Missing donor: " + item1 + " -> " + item);
                } else {
                    this.register(item, clientitem);
                }
            });
            List<ResourceLocation> list = known.get()
                    .map(item -> item.builtInRegistryHolder())
                    .filter(holder -> !this.itemInfos.containsKey(holder.value()))
                    .map(holder -> holder.key().location())
                    .toList();
            if (!list.isEmpty()) {
                throw new IllegalStateException("Missing item model definitions for: " + list);
            }
        }

        public @NotNull CompletableFuture<?> save(@NotNull CachedOutput cachedOutput,
                                                  @NotNull PackOutput.PathProvider pathProvider) {
            return DataProvider.saveAll(cachedOutput, ClientItem.CODEC,
                                        item -> pathProvider.json(item.builtInRegistryHolder().key().location()),
                                        this.itemInfos);
        }
    }
}
