package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;
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
        ItemInfoCollector iteminfocollector = new ItemInfoCollector(this::getKnownItems);
        BlockStateGeneratorCollector blockstategeneratorcollector = new BlockStateGeneratorCollector(this::getKnownBlocks);
        SimpleModelCollector simplemodelcollector = new SimpleModelCollector();
        this.getBlockModelGenerators(blockstategeneratorcollector, iteminfocollector, simplemodelcollector).run();
        this.getItemModelGenerators(iteminfocollector, simplemodelcollector).run();
        blockstategeneratorcollector.validate();
        iteminfocollector.finalizeAndValidate();
        return CompletableFuture.allOf(blockstategeneratorcollector.save(cachedOutput, this.blockStatePathProvider),
                                       simplemodelcollector.save(cachedOutput, this.modelPathProvider),
                                       iteminfocollector.save(cachedOutput, this.itemInfoPathProvider));
    }
}