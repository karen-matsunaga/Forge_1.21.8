package net.karen.mccoursemod.item;

import net.karen.mccoursemod.util.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.List;

public record ModToolMaterials(TagKey<Block> incorrectBlocksForDrops, int durability, float speed,
                               float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems) {
    public static final ModToolMaterials ALEXANDRITE =
            new ModToolMaterials(ModTags.Blocks.INCORRECT_FOR_ALEXANDRITE_TOOL, 3000, 15.0F,
                    5.0F, 20, ModTags.Items.ALEXANDRITE_TOOL_MATERIALS);

    // CUSTOM METHOD - Custom Common properties
    private Item.Properties applyCommonProperties(Item.Properties properties) {
        return properties.durability(this.durability).repairable(this.repairItems).enchantable(this.enchantmentValue);
    }

    // CUSTOM METHOD - Custom Tool properties
    public Item.Properties applyToolProperties(Item.Properties properties, TagKey<Block> mineableBlocks,
                                               float attackDamage, float attackSpeed, float disableBlockingForSeconds) {
        HolderGetter<Block> holdergetter = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return this.applyCommonProperties(properties).component(DataComponents.TOOL,
                    new Tool(List.of(Tool.Rule.deniesDrops(holdergetter.getOrThrow(this.incorrectBlocksForDrops)),
                                     Tool.Rule.minesAndDrops(holdergetter.getOrThrow(mineableBlocks), this.speed)),
                                1.0F, 1, true))
                .attributes(this.createToolAttributes(attackDamage, attackSpeed))
                .component(DataComponents.WEAPON, new Weapon(2, disableBlockingForSeconds));
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