package net.karen.mccoursemod.item;

import com.google.common.collect.Maps;
import net.karen.mccoursemod.util.ModTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import java.util.Map;

public class ModArmorMaterials {
    public static final ArmorMaterial ALEXANDRITE =
            new ArmorMaterial(50, makeDefense(3, 6, 8, 3, 11),
                     20, SoundEvents.ARMOR_EQUIP_NETHERITE, 0.0F, 0.0F,
                    ModTags.Items.REPAIRS_ALEXANDRITE_ARMOR, ModEquipmentAssets.ALEXANDRITE);

    // CUSTOM METHOD - Armor Defense
    private static Map<ArmorType, Integer> makeDefense(int boots, int leggings,
                                                       int chestplate, int helmet, int body) {
        return Maps.newEnumMap(Map.of(ArmorType.BOOTS, boots, ArmorType.LEGGINGS, leggings,
                                      ArmorType.CHESTPLATE, chestplate, ArmorType.HELMET, helmet, ArmorType.BODY, body));
    }
}