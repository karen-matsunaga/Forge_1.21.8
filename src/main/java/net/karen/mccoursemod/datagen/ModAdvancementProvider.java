package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends ForgeAdvancementProvider {
    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     * @param existingFileHelper a helper used to find whether a file exists
     */

    public ModAdvancementProvider(PackOutput output,
                                  CompletableFuture<HolderLookup.Provider> registries,
                                  ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new MccourseModAdvancements()));
    }

    public static class MccourseModAdvancements implements AdvancementGenerator {
        @Override public void generate(@NotNull HolderLookup.Provider registries,
                                       @NotNull Consumer<AdvancementHolder> consumer,
                                       @NotNull ExistingFileHelper existingFileHelper) {
            // CUSTOM Tools
            AdvancementHolder rootAdvancement = Advancement.Builder.advancement()
                    .display(ModItems.ALEXANDRITE.get(), Component.translatable("advancement.mccoursemod.root.title"),
                                                         Component.translatable("advancement.mccoursemod.root.description"),
                             ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "textures/item/alexandrite.png"),
                             AdvancementType.TASK, true, true, false)
                    .addCriterion("has_alexandrite", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "root"));

            // CUSTOM VANILLA TOOLS -> Sword, Pickaxe, Shovel, Axe, Hoe, etc.
            AdvancementHolder alexandriteVanillaTools = Advancement.Builder.advancement()
                    .parent(rootAdvancement)
                    .display(ModItems.ALEXANDRITE_PICKAXE.get(),
                             Component.translatable("advancement.mccoursemod.alexandritevanillatools.title"),
                             Component.translatable("advancement.mccoursemod.alexandritevanillatools.description"),
                             null,
                             AdvancementType.TASK, true, true, false)
                    .addCriterion("has_alexandrite_pickaxe",
                                  InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE_PICKAXE.get()))
                    .addCriterion("has_alexandrite_axe",
                                  InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE_AXE.get()))
                    .addCriterion("has_alexandrite_sword",
                                  InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE_SWORD.get()))
                    .addCriterion("has_alexandrite_shovel",
                                  InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE_SHOVEL.get()))
                    .addCriterion("has_alexandrite_hoe",
                                  InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE_HOE.get()))
                    .requirements(AdvancementRequirements.Strategy.AND)
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "alexandrite_vanilla_tools"));

            // CUSTOM TOOLS -> Hammer, Paxel, etc.
            AdvancementHolder alexandriteCustomTools = Advancement.Builder.advancement()
                    .parent(alexandriteVanillaTools)
                    .display(ModItems.ALEXANDRITE_HAMMER.get(),
                             Component.translatable("advancement.mccoursemod.alexandritecustomtools.title"),
                             Component.translatable("advancement.mccoursemod.alexandritecustomtools.description"),
                             null,
                             AdvancementType.TASK, true, true, false)
                    .addCriterion("has_alexandrite_hammer",
                                  InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE_HAMMER.get()))
                    .requirements(AdvancementRequirements.Strategy.AND)
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "alexandrite_custom_tools"));

            // CUSTOM ARMORS -> Helmet, Chestplate, Leggings, Boots or Elytra
            AdvancementHolder alexandriteCustomArmors = Advancement.Builder.advancement()
                    .parent(rootAdvancement)
                    .display(ModItems.ALEXANDRITE_CHESTPLATE.get(),
                             Component.translatable("advancement.mccoursemod.alexandritecustomarmors.title"),
                             Component.translatable("advancement.mccoursemod.alexandritecustomarmors.description"),
                             null,
                             AdvancementType.TASK, true, true, false)
                    .addCriterion("has_alexandrite_helmet",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE_HELMET.get()))
                    .addCriterion("has_alexandrite_chestplate",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE_CHESTPLATE.get()))
                    .addCriterion("has_alexandrite_leggings",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE_LEGGINGS.get()))
                    .addCriterion("has_alexandrite_boots",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE_BOOTS.get()))
                    .requirements(AdvancementRequirements.Strategy.AND)
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(MccourseMod.MOD_ID, "alexandrite_custom_armors"));
        }
    }
}