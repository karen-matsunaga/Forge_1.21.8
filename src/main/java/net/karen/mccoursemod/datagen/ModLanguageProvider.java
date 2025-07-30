package net.karen.mccoursemod.datagen;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.block.ModBlocks;
import net.karen.mccoursemod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, MccourseMod.MOD_ID, locale);
    }

    String item = "item.mccoursemod.";

    @Override
    protected void addTranslations() {
        // CUSTOM Blocks
        addBlock(ModBlocks.ENCHANT, "Enchant Block");
        addBlock(ModBlocks.DISENCHANT_INDIVIDUAL, "Disenchant Individual");
        addBlock(ModBlocks.DISENCHANT_GROUPED, "Disenchant Grouped");
        addBlock(ModBlocks.ALEXANDRITE_BLOCK, "Alexandrite Block");
        addBlock(ModBlocks.MAGIC, "Magic Block");

        // CUSTOM Items
        addItem(ModItems.ALEXANDRITE, "Alexandrite Gem");
        addItem(ModItems.LEVEL_CHARGER_GENERIC_PLUS, "Level Charger Generic Plus");
        addItem(ModItems.LEVEL_CHARGER_GENERIC_MINUS, "Level Charger Generic Minus");
        addItem(ModItems.LEVEL_CHARGER_SPECIF_MINUS_FORTUNE, "Level Charger Specif Minus Fortune");
        addItem(ModItems.LEVEL_CHARGER_SPECIF_PLUS_FORTUNE, "Level Charger Specif Plus Fortune");
        addItem(ModItems.GEM_EFFECT_SATURATION, "Gem Effect Saturation");
        addItem(ModItems.MULTIPLIER, "Multiplier");
        addItem(ModItems.MAGNET, "Magnet");

        // CUSTOM Tools
        addItem(ModItems.ALEXANDRITE_HAMMER, "Alexandrite Hammer");
        addItem(ModItems.ALEXANDRITE_SWORD, "Alexandrite Sword");
        addItem(ModItems.ALEXANDRITE_PICKAXE, "Alexandrite Pickaxe");
        addItem(ModItems.ALEXANDRITE_SHOVEL, "Alexandrite Shovel");
        addItem(ModItems.ALEXANDRITE_AXE, "Alexandrite Axe");
        addItem(ModItems.ALEXANDRITE_HOE, "Alexandrite Hoe");

        // CUSTOM Armors
        addItem(ModItems.ALEXANDRITE_HELMET, "Alexandrite Helmet");
        addItem(ModItems.ALEXANDRITE_CHESTPLATE, "Alexandrite Chestplate");
        addItem(ModItems.ALEXANDRITE_LEGGINGS, "Alexandrite Leggings");
        addItem(ModItems.ALEXANDRITE_BOOTS, "Alexandrite Boots");

        // CUSTOM block item
        add(item + "enchant", "Enchant Block");
        add(item + "alexandrite_block", "Alexandrite Block");
        add(item + "disenchant_individual", "Disenchant Individual Block");
        add(item + "disenchant_grouped", "Disenchant Grouped Block");
        add(item + "magic", "Magic Block");

        // CUSTOM Enchantment
        add("enchantment.mccoursemod.lightning_striker", "Lightning Striker");
        add("enchantment.mccoursemod.message", "Message");

        // MOD
        add("enchantment.mccoursemod.lightning_striker.desc", "When applied on sword when player hits on entities appears lightning, but player receive damage if attacked.");
        add("enchantment.mccoursemod.message.desc", "When applied on item appears a custom message on chat.");

        // MINING
        add("enchantment.minecraft.efficiency.desc", "When applied on axe, pickaxe, shovel or hoe increases mining speed.");
        add("enchantment.minecraft.silk_touch.desc", "When applied on axe, pickaxe, shovel or hoe receives the broken block.");
        add("enchantment.minecraft.fortune.desc", "When applied on axe, pickaxe, shovel or hoe receives bonus loot drops of ores.");

        // DURABILITY
        add("enchantment.minecraft.mending.desc", "When applied on tools or armor regenerates tools, equipments, etc. only player received XP.");
        add("enchantment.minecraft.unbreaking.desc", "When applied on tools or armor increases durability of tools, equipments, etc.");

        // ARMOR EXCLUSIVE
        add("enchantment.minecraft.protection.desc", "When applied on armor add bonus damage reduction.");
        add("enchantment.minecraft.fire_protection.desc", "When applied on armor add bonus damage reduction of fire.");
        add("enchantment.minecraft.blast_protection.desc", "When applied on armor add bonus damage reduction of explosions and fireworks.");
        add("enchantment.minecraft.projectile_protection.desc", "When applied on armor add bonus damage reduction of projectiles.");

        // CHESTPLATE
        add("enchantment.minecraft.thorns.desc", "When applied on armor if entities attacked player receives damage.");

        // HELMET
        add("enchantment.minecraft.respiration.desc", "When applied on helmet armor extending breathing time underwater.");
        add("enchantment.minecraft.aqua_affinity.desc", "When applied on helmet armor increases underwater mining speed.");

        // LEGGINGS
        add("enchantment.minecraft.swift_sneak.desc", "When applied on leggings armor walk more quickly while sneaking.");

        // BOOTS
        add("enchantment.minecraft.feather_falling.desc", "When applied on boots armor reduces fall damage the player takes, but it does not affect falling speed.");
        add("enchantment.minecraft.depth_strider.desc", "When applied on boots armor increases underwater movement speed.");
        add("enchantment.minecraft.frost_walker.desc", "When applied on boots armor creates frosted ice blocks when walking over water, and causes the wearer to be immune to damage from certain blocks such as campfires and magma blocks when stepped on, but not working with lava.");
        add("enchantment.minecraft.soul_speed.desc", "When applied on boots armor walk more quickly on soul sand and soul soil blocks.");

        // SWORD
        add("enchantment.minecraft.sharpness.desc", "When applied on sword or axe increases melee damage attack on entities.");
        add("enchantment.minecraft.smite.desc", "When applied on sword or axe increases damage dealt to undead mobs also Skeleton, Zombie, Wither, Phantom, Zoglin, etc.");
        add("enchantment.minecraft.bane_of_arthropods.desc", "When applied on sword or axe increases damage to arthropod mobs also Spiders, Bees, Silverfish, Endermites, etc.");
        add("enchantment.minecraft.knockback.desc", "When applied on sword or axe increases knockback distance of entities.");
        add("enchantment.minecraft.fire_aspect.desc", "When applied on sword or axe an entity received fire attack when hit.");
        add("enchantment.minecraft.looting.desc", "When applied on sword or axe increases amount of drop loot of entities.");
        // SWEEPING EDGE ENCHANTMENT
        add("enchantment.minecraft.sweeping.desc", "When applied on sword or axe increases sweep attack damage on entities.");
        add("enchantment.minecraft.sweeping_edge.desc", "When applied on sword or axe increases sweep attack damage on entities.");

        // BOW
        add("enchantment.minecraft.power.desc", "When applied on bow increases arrow damage.");
        add("enchantment.minecraft.punch.desc", "When applied on bow increases an arrow's knockback, but it not affect damage dealt of arrows.");
        add("enchantment.minecraft.flame.desc", "When applied on bow shoots flaming arrows.");
        add("enchantment.minecraft.infinity.desc", "When applied on bow one arrow is needed to used the enchantment that prevents regular arrows from being consumed when slot.");

        // FISHING ROD
        add("enchantment.minecraft.luck_of_the_sea.desc", "When applied on fishing rod increases luck while fishing to received enchantments, armors, tools, etc.");
        add("enchantment.minecraft.lure.desc", "When applied on fishing rod decreases the wait time for a bite on the hook.");

        // TRIDENT
        add("enchantment.minecraft.loyalty.desc", "When applied on trident causing it to return to the owner once thrown.");
        add("enchantment.minecraft.impaling.desc", "When applied on trident deal extra damage on each hit against aquatic mobs also axolotls, dolphins, guardians, squid, turtles, all variants of fish, etc. Except drowned is an undead mob.");
        add("enchantment.minecraft.riptide.desc", "When applied on trident hurls the user in the direction the user is facing, but only when they are wet.");
        add("enchantment.minecraft.channeling.desc", "When applied on trident produces lightning when thrown at a mob or lightning rod while a thunderstorm is occurring.");

        // CROSSBOW
        add("enchantment.minecraft.quick_charge.desc", "When applied on crossbow quickly reloading a crossbow.");
        add("enchantment.minecraft.multishot.desc", "When applied on crossbow shoot three arrows or firework rockets at the cost of one.");
        add("enchantment.minecraft.piercing.desc", "When applied on crossbow causes arrows to pierce through entities.");

        // CURSE
        add("enchantment.minecraft.binding_curse.desc", "When applied on armor the player not remove the item of inventory.");
        add("enchantment.minecraft.vanishing_curse.desc", "When applied on tool or armor if player killed the item disappears of inventory.");

        // MACE
        add("enchantment.minecraft.breach.desc", "When applied on mace ignores 15% of armor damage reduction per level.");
        add("enchantment.minecraft.density.desc", "When applied on mace increases 0.5 additional damage per level for each block fallen with tool.");
        add("enchantment.minecraft.wind_burst.desc", "When applied on mace the player into the air seven blocks per level after performing a smash attack.");

        // CUSTOM Creative Mode Tabs
        add("creativetab.mccoursemod.mccourse_blocks", "Mccourse Blocks");
        add("creativetab.mccoursemod.mccourse_items", "Mccourse Items");

        // CUSTOM Advancements
        // ROOT
        add("advancement.mccoursemod.root.title", "Mccourse Mod");
        add("advancement.mccoursemod.root.description", "Collect all exclusive ores, armors, tools, etc. on your journey!");

        // ALEXANDRITE VANILLA TOOLS
        add("advancement.mccoursemod.alexandritevanillatools.title", "Alexandrite Vanilla Tools");
        add("advancement.mccoursemod.alexandritevanillatools.description", "Collect all exclusive Alexandrite vanilla tools!");

        // ALEXANDRITE CUSTOM TOOLS
        add("advancement.mccoursemod.alexandritecustomtools.title", "Alexandrite Custom Tools");
        add("advancement.mccoursemod.alexandritecustomtools.description", "Collect all exclusive Alexandrite custom tools!");

        // ALEXANDRITE CUSTOM ARMORS
        add("advancement.mccoursemod.alexandritecustomarmors.title", "Alexandrite Custom Armors");
        add("advancement.mccoursemod.alexandritecustomarmors.description", "Collect all exclusive Alexandrite custom armors!");

        // CUSTOM TOOLTIP
        add("tooltip.mccoursemod.magic_block.tooltip", "This Block is quite §9MAGICAL§r");

        // CUSTOM Effects and Potions
        // FLY effect + potion
        add("effect.mccoursemod.fly", "Fly");
        add("item.minecraft.potion.effect.fly_potion", "Fly Potion");
        add("item.minecraft.splash_potion.effect.fly_potion", "Fly Splash Potion");
        add("item.minecraft.lingering_potion.effect.fly_potion", "Fly Lingering Potion");
        add("item.minecraft.tipped_arrow.effect.fly_potion", "Arrow of Fly");

        // NOTHING effect + potion
        add("effect.mccoursemod.nothing", "Nothing");
        add("item.minecraft.potion.effect.nothing_potion", "Nothing Potion");
        add("item.minecraft.splash_potion.effect.nothing_potion", "Nothing Splash Potion");
        add("item.minecraft.lingering_potion.effect.nothing_potion", "Nothing Lingering Potion");
        add("item.minecraft.tipped_arrow.effect.nothing_potion", "Arrow of Nothing");

        // HASTE potion
        add("item.minecraft.potion.effect.haste_potion", "Haste Potion");
        add("item.minecraft.splash_potion.effect.haste_potion", "Haste Splash Potion");
        add("item.minecraft.lingering_potion.effect.haste_potion", "Haste Lingering Potion");
        add("item.minecraft.tipped_arrow.effect.haste_potion", "Arrow of Haste");
    }
}