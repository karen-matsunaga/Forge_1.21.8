package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.datagen.block.ModBlockFamilies;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/* Credits by frankint - https://github.com/frankint/Forge-1.21.4-datagen/blob/main/LICENSE */
public class ModRecipeProvider extends RecipeProvider implements DataProvider {
    protected RecipeOutput output;
    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        this.nineBlockStorageRecipes(RecipeCategory.MISC, Items.ANVIL, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ENCHANT.get());
    }

    @Override
    protected void generateForEnabledBlockFamilies(@NotNull FeatureFlagSet featureFlagSet) {
        ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe)
                                         .forEach(family -> this.generateRecipes(family, featureFlagSet));
    }

    protected void oreSmelting(@NotNull List<ItemLike> itemLikes, @NotNull RecipeCategory category,
                               @NotNull ItemLike result, float experience, int cookingTime, @NotNull String group) {
        this.oreCooking(RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, itemLikes, category, result,
                        experience, cookingTime, group, "_from_smelting");
    }

    protected void oreBlasting(@NotNull List<ItemLike> itemLikes, @NotNull RecipeCategory category,
                               @NotNull ItemLike result, float experience, int cookingTime, @NotNull String group) {
        this.oreCooking(RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, itemLikes, category, result,
                        experience, cookingTime, group, "_from_blasting");
    }

    private <T extends AbstractCookingRecipe> void oreCooking(RecipeSerializer<T> serializer,
                                                              AbstractCookingRecipe.Factory<T> recipeFactory,
                                                              List<ItemLike> itemLikes, RecipeCategory category,
                                                              ItemLike result, float experience, int cookingTime,
                                                              String group, String suffix) {
        for (ItemLike itemlike : itemLikes) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, result, experience, cookingTime,
                                               serializer, recipeFactory)
                                      .group(group)
                                      .unlockedBy(getHasName(itemlike), this.has(itemlike))
                                      .save(this.output, MccourseMod.MOD_ID + ":" + getItemName(result) +
                                            suffix + "_" + getItemName(itemlike));
        }
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        return null;
    }

    @Override
    public @NotNull String getName() {
        return "";
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registries,
                                                               @NotNull RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public @NotNull String getName() {
            return "Mccourse Recipes";
        }
    }
}