package net.karen.mccoursemod.event;

import net.karen.mccoursemod.MccourseMod;
import net.karen.mccoursemod.item.custom.HammerItem;
import net.karen.mccoursemod.potion.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Mod.EventBusSubscriber(modid = MccourseMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    // CUSTOM EVENT - Hammer Item
    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) { return; }
            for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }
                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    // CUSTOM EVENT - Registry all custom potion recipes
    @SubscribeEvent
    public static void onBrewingRecipeRegister(BrewingRecipeRegisterEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        Optional<Holder<Potion>> flyPotion = ModPotions.FLY_POTION.getHolder();
        Optional<Holder<Potion>> flyPotionTwo = ModPotions.FLY_II_POTION.getHolder();
        Optional<Holder<Potion>> nothingPotion = ModPotions.NOTHING_POTION.getHolder();
        Optional<Holder<Potion>> hastePotion = ModPotions.HASTE_POTION.getHolder();

        // FLY POTION
        flyPotion.ifPresent(fly -> builder.addMix(Potions.AWKWARD, Items.EMERALD, fly));

        if (flyPotionTwo.isPresent() && flyPotion.isPresent()) {
            flyPotionTwo.ifPresent(flyTwo -> builder.addMix(flyPotion.get(), Blocks.EMERALD_BLOCK.asItem(), flyTwo));
        }

        // NOTHING POTION
        nothingPotion.ifPresent(nothing -> builder.addMix(Potions.AWKWARD, Items.GLOWSTONE, nothing));

        // HASTE POTION
        hastePotion.ifPresent(haste -> builder.addMix(Potions.AWKWARD, Items.CARROT, haste));
    }
}