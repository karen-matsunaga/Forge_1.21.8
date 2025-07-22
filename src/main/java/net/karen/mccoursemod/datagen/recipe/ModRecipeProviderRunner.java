package net.karen.mccoursemod.datagen.recipe;

import net.karen.mccoursemod.datagen.ModRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;

/* Credits by frankint - https://github.com/frankint/Forge-1.21.4-datagen/blob/main/LICENSE */
public class ModRecipeProviderRunner extends ModRecipeProvider.Runner {
    public ModRecipeProviderRunner(PackOutput packOutput,
                                   CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registries,
                                                           @NotNull RecipeOutput output) {
        // Return an instance of your actual recipe provider that implements buildRecipes.
        // The ModRecipeProvider must extend RecipeProvider.
        return new ModRecipeProvider(registries, output);
    }
}