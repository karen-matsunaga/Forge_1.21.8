package net.karen.mccoursemod.item;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.List;

public record ModToolMaterials(TagKey<Block> incorrectBlocksForDrops, int durability, float speed,
                               float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems) {
    public static final ToolMaterial WOOD = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
                    59, 2.0F, 0.0F, 15, ItemTags.WOODEN_TOOL_MATERIALS);

    public static final ModToolMaterials STONE =
            new ModToolMaterials(BlockTags.INCORRECT_FOR_STONE_TOOL,
                    131, 4.0F, 1.0F, 5, ItemTags.STONE_TOOL_MATERIALS);

    public static final ModToolMaterials IRON =
            new ModToolMaterials(BlockTags.INCORRECT_FOR_IRON_TOOL,
                    250, 6.0F, 2.0F, 14, ItemTags.IRON_TOOL_MATERIALS);

    public static final ModToolMaterials DIAMOND =
            new ModToolMaterials(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
                    1561, 8.0F, 3.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);

    public static final ModToolMaterials GOLD =
            new ModToolMaterials(BlockTags.INCORRECT_FOR_GOLD_TOOL,
                    32, 12.0F, 0.0F, 22, ItemTags.GOLD_TOOL_MATERIALS);

    public static final ModToolMaterials NETHERITE =
            new ModToolMaterials(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
                    2031, 9.0F, 4.0F, 15, ItemTags.NETHERITE_TOOL_MATERIALS);

    public static final ModToolMaterials ALEXANDRITE =
            new ModToolMaterials(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
                    3000, 15.0F, 5.0F, 20, ItemTags.NETHERITE_TOOL_MATERIALS);

    // CUSTOM METHOD - Custom Common properties
    private Item.Properties applyCommonProperties(Item.Properties properties) {
        return properties.durability(this.durability).repairable(this.repairItems).enchantable(this.enchantmentValue);
    }

    // CUSTOM METHOD - Custom Tool properties
    public Item.Properties applyToolProperties(Item.Properties properties, TagKey<Block> mineableBlocks,
                                               float attackDamage, float attackSpeed, float disableBlockingForSeconfs) {
        HolderGetter<Block> holdergetter = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return this.applyCommonProperties(properties).component(DataComponents.TOOL,
                    new Tool(List.of(Tool.Rule.deniesDrops(holdergetter.getOrThrow(this.incorrectBlocksForDrops)),
                                     Tool.Rule.minesAndDrops(holdergetter.getOrThrow(mineableBlocks), this.speed)),
                                1.0F, 1, true))
                .attributes(this.createToolAttributes(attackDamage, attackSpeed))
                .component(DataComponents.WEAPON, new Weapon(2, disableBlockingForSeconfs));
    }

    // CUSTOM METHOD - Custom Tool attributes
    private ItemAttributeModifiers createToolAttributes(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            attackDamage + this.attackDamageBonus, AttributeModifier.Operation.ADD_VALUE),
                                                    EquipmentSlotGroup.MAINHAND)
                                               .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                                    attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                               .build();
    }

    // CUSTOM METHOD - Custom Sword properties
    public Item.Properties applySwordProperties(Item.Properties pProperties, float pAttackDamage, float pAttackSpeed) {
        HolderGetter<Block> holdergetter = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return this.applyCommonProperties(pProperties).component(DataComponents.TOOL,
                    new Tool(List.of(Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
                                     Tool.Rule.overrideSpeed(holdergetter.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                                     Tool.Rule.overrideSpeed(holdergetter.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)),
                            1.0F, 2, false))
                .attributes(this.createSwordAttributes(pAttackDamage, pAttackSpeed))
                .component(DataComponents.WEAPON, new Weapon(1));
    }

    // CUSTOM METHOD - Custom Sword attributes
    private ItemAttributeModifiers createSwordAttributes(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID,
                                            attackDamage + this.attackDamageBonus, AttributeModifier.Operation.ADD_VALUE),
                                                    EquipmentSlotGroup.MAINHAND)
                                               .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID,
                                                    attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                               .build();
    }
}