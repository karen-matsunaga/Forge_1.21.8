package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/* Credits by frankint - https://github.com/frankint/Forge-1.21.4-datagen/blob/main/LICENSE */
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registries,
                                                               @NotNull RecipeOutput output) {
            // Return an instance of your actual recipe provider that implements buildRecipes.
            // The ModRecipeProvider must extend RecipeProvider.
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public @NotNull String getName() { return "Mccourse Recipes"; }
    }

    @Override
    protected void buildRecipes() {
        // CUSTOM block
        this.nineBlockStorageRecipes(RecipeCategory.MISC, Items.ANVIL, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ENCHANT.get());
        this.nineBlockStorageRecipes(RecipeCategory.MISC, ModItems.ALEXANDRITE.get(),
                                     RecipeCategory.BUILDING_BLOCKS, ModBlocks.ALEXANDRITE_BLOCK.get());
        this.nineBlockStorageRecipes(RecipeCategory.MISC, Items.NETHER_STAR, RecipeCategory.BUILDING_BLOCKS, Blocks.BONE_BLOCK);
        // CUSTOM tools
        this.allTools(List.of(ModItems.ALEXANDRITE_SWORD.get(), ModItems.ALEXANDRITE_PICKAXE.get(),
                              ModItems.ALEXANDRITE_SHOVEL.get(), ModItems.ALEXANDRITE_AXE.get(),
                              ModItems.ALEXANDRITE_HOE.get(), ModItems.ALEXANDRITE.get()));

        this.hammerTool(List.of(ModItems.ALEXANDRITE_HAMMER.get(), ModBlocks.ALEXANDRITE_BLOCK.get()));
        // CUSTOM armors
        this.fullArmor(List.of(ModItems.ALEXANDRITE_HELMET.get(), ModItems.ALEXANDRITE_CHESTPLATE.get(),
                               ModItems.ALEXANDRITE_LEGGINGS.get(), ModItems.ALEXANDRITE_BOOTS.get(),
                               ModItems.ALEXANDRITE.get()));
    }

    // CUSTOM METHOD - Block Families (Stairs, Button, Door, etc.)
    @Override
    protected void generateForEnabledBlockFamilies(@NotNull FeatureFlagSet featureFlagSet) {
        ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe)
                                         .forEach(family -> this.generateRecipes(family, featureFlagSet));
    }

    // CUSTOM METHOD - Smelting
    protected void oreSmelting(@NotNull List<ItemLike> itemLikes, @NotNull RecipeCategory category,
                               @NotNull ItemLike result, float experience, int cookingTime, @NotNull String group) {
        this.oreCooking(RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, itemLikes, category, result,
                        experience, cookingTime, group, "_from_smelting");
    }

    // CUSTOM METHOD - Blasting
    protected void oreBlasting(@NotNull List<ItemLike> itemLikes, @NotNull RecipeCategory category,
                               @NotNull ItemLike result, float experience, int cookingTime, @NotNull String group) {
        this.oreCooking(RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, itemLikes, category, result,
                        experience, cookingTime, group, "_from_blasting");
    }

    // CUSTOM METHOD - Smelting and Blasting
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

    // CUSTOM Tools
    // CUSTOM METHOD - All tools
    protected void allTools(List<ItemLike> itemLikes) {
        // 0 index -> Result (SWORD) || 1 index -> Result (PICKAXE) || 2 index -> Result (SHOVEL)
        // 3 index -> Result (AXE)   || 4 index -> Result (HOE)     || 5 index -> Result (ITEM)
        swordTool(List.of(itemLikes.getFirst(), itemLikes.get(5)));
        pickaxeTool(List.of(itemLikes.get(1), itemLikes.get(5)));
        shovelTool(List.of(itemLikes.get(2), itemLikes.get(5)));
        axeTool(List.of(itemLikes.get(3), itemLikes.get(5)));
        hoeTool(List.of(itemLikes.get(4), itemLikes.get(5)));
    }

    // CUSTOM METHOD - Hammer Method
    protected void hammerTool(List<ItemLike> itemLikes) {
        // 0 index -> Result (HAMMER) || 1 index -> Ingredient (ITEM)
        this.shaped(RecipeCategory.TOOLS, itemLikes.getFirst())
            .pattern("WWW")
            .pattern(" S ")
            .pattern(" S ")
            .define('W', itemLikes.get(1)) // Ingredient
            .define('S', Items.STICK)
            .unlockedBy(getHasName(itemLikes.get(1)), this.has(itemLikes.get(1)))
            .save(this.output);
    }

    // CUSTOM METHOD - Sword tool
    protected void swordTool(List<ItemLike> itemLikes) {
        // 0 index -> Result (SWORD) || 1 index -> Ingredient (ITEM)
        this.shaped(RecipeCategory.COMBAT, itemLikes.getFirst())
            .pattern("X")
            .pattern("X")
            .pattern("#")
            .define('X', itemLikes.get(1)) // Ingredient
            .define('#', Items.STICK)
            .unlockedBy(getHasName(itemLikes.get(1)), this.has(itemLikes.get(1)))
            .save(this.output);
    }

    // CUSTOM METHOD - Pickaxe tool
    protected void pickaxeTool(List<ItemLike> itemLikes) {
        // 0 index -> Result (PICKAXE) || 1 index -> Ingredient (ITEM)
        this.shaped(RecipeCategory.TOOLS, itemLikes.getFirst())
            .pattern("XXX")
            .pattern(" # ")
            .pattern(" # ")
            .define('X', itemLikes.get(1)) // Ingredient
            .define('#', Items.STICK)
            .unlockedBy(getHasName(itemLikes.get(1)), this.has(itemLikes.get(1)))
            .save(this.output);
    }

    // CUSTOM METHOD - Shovel tool
    protected void shovelTool(List<ItemLike> itemLikes) {
        // 0 index -> Result (SHOVEL) || 1 index -> Ingredient (ITEM)
        this.shaped(RecipeCategory.TOOLS, itemLikes.getFirst())
            .pattern("X")
            .pattern("#")
            .pattern("#")
            .define('X', itemLikes.get(1)) // Ingredient
            .define('#', Items.STICK)
            .unlockedBy(getHasName(itemLikes.get(1)), this.has(itemLikes.get(1)))
            .save(this.output);
    }

    // CUSTOM METHOD - Axe tool
    protected void axeTool(List<ItemLike> itemLikes) {
        // 0 index -> Result (AXE) || 1 index -> Ingredient (ITEM)
        this.shaped(RecipeCategory.TOOLS, itemLikes.getFirst())
            .pattern("XX")
            .pattern("X#")
            .pattern(" #")
            .define('X', itemLikes.get(1)) // Ingredient
            .define('#', Items.STICK)
            .unlockedBy(getHasName(itemLikes.get(1)), this.has(itemLikes.get(1)))
            .save(this.output);
    }

    // CUSTOM METHOD - Hoe tool
    protected void hoeTool(List<ItemLike> itemLikes) {
        // 0 index -> Result (HOE) || 1 index -> Ingredient (ITEM)
        this.shaped(RecipeCategory.TOOLS, itemLikes.getFirst())
            .pattern("XX")
            .pattern(" #")
            .pattern(" #")
            .define('X', itemLikes.get(1)) // Ingredient
            .define('#', Items.STICK)
            .unlockedBy(getHasName(itemLikes.get(1)), this.has(itemLikes.get(1)))
            .save(this.output);
    }

    // CUSTOM Armors
    // CUSTOM METHOD - Full armor
    protected void fullArmor(List<ItemLike> itemLikes) {
        // 0 index -> Result (HELMET)   || 1 index -> Result (CHESTPLATE)
        // 2 index -> Result (LEGGINGS) || 3 index -> Result (BOOTS)
        // 4 index -> Ingredient (ITEM)
        this.helmetArmor(List.of(itemLikes.getFirst(), itemLikes.get(4)));
        this.chestplateArmor(List.of(itemLikes.get(1), itemLikes.get(4)));
        this.leggingsArmor(List.of(itemLikes.get(2), itemLikes.get(4)));
        this.bootsArmor(List.of(itemLikes.get(3), itemLikes.get(4)));
    }

    // CUSTOM METHOD - Helmet armor
    protected void helmetArmor(List<ItemLike> itemLikes) {
        // 0 index -> Result (HELMET) || 1 index -> Ingredient (ITEM)
        this.shaped(RecipeCategory.COMBAT, itemLikes.getFirst())
            .pattern("XXX")
            .pattern("X X")
            .define('X', itemLikes.get(1)) // Ingredient
            .unlockedBy(getHasName(itemLikes.get(1)), this.has(itemLikes.get(1)))
            .save(this.output);
    }

    // CUSTOM METHOD - Chestplate armor
    protected void chestplateArmor(List<ItemLike> itemLikes) {
        // 0 index -> Result (CHESTPLATE) || 1 index -> Ingredient (ITEM)
        this.shaped(RecipeCategory.COMBAT, itemLikes.getFirst())
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', itemLikes.get(1)) // Ingredient
                .unlockedBy(getHasName(itemLikes.get(1)), this.has(itemLikes.get(1)))
                .save(this.output);
    }

    // CUSTOM METHOD - Leggings armor
    protected void leggingsArmor(List<ItemLike> itemLikes) {
        // 0 index -> Result (LEGGINGS) || 1 index -> Ingredient (ITEM)
        this.shaped(RecipeCategory.COMBAT, itemLikes.getFirst())
            .pattern("XXX")
            .pattern("X X")
            .pattern("X X")
            .define('X', itemLikes.get(1)) // Ingredient
            .unlockedBy(getHasName(itemLikes.get(1)), this.has(itemLikes.get(1)))
            .save(this.output);
    }

    // CUSTOM METHOD - Boots armor
    protected void bootsArmor(List<ItemLike> itemLikes) {
        // 0 index -> Result (BOOTS) || 1 index -> Ingredient (ITEM)
        this.shaped(RecipeCategory.COMBAT, itemLikes.getFirst())
            .pattern("X X")
            .pattern("X X")
            .define('X', itemLikes.get(1)) // Ingredient
            .unlockedBy(getHasName(itemLikes.get(1)), this.has(itemLikes.get(1)))
            .save(this.output);
    }
}