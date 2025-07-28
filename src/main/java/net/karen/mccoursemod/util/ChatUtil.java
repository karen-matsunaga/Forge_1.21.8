package net.karen.mccoursemod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

public class ChatUtil { // GENERAL METHODS
    public static ChatFormatting blue = ChatFormatting.BLUE, darkBlue = ChatFormatting.DARK_BLUE,
            aqua = ChatFormatting.AQUA, darkAqua = ChatFormatting.DARK_AQUA, purple = ChatFormatting.LIGHT_PURPLE,
            darkPurple = ChatFormatting.DARK_PURPLE, green = ChatFormatting.GREEN, darkGreen = ChatFormatting.DARK_GREEN,
            gray = ChatFormatting.GRAY, darkGray = ChatFormatting.DARK_GRAY, yellow = ChatFormatting.YELLOW,
            gold = ChatFormatting.GOLD, red = ChatFormatting.RED, darkRed = ChatFormatting.DARK_RED,
            black = ChatFormatting.BLACK, white = ChatFormatting.WHITE,
            bold = ChatFormatting.BOLD, italic = ChatFormatting.ITALIC;

    // CUSTOM METHOD - COMPONENT LITERAL without color
    public static Component standardLiteral(String message) {
        return Component.literal(message);
    }

    // CUSTOM METHOD - COMPONENT TRANSLATABLE without color
    public static Component standardTranslatable(String message) {
        return Component.translatable(message);
    }

    // CUSTOM METHOD - COMPONENT LITERAL with one color
    public static Component componentLiteral(String message, ChatFormatting color) {
        return Component.literal(message).withStyle(color);
    }

    // CUSTOM METHOD - COMPONENT LITERAL with one color
    public static Component componentTranslatable(String message, ChatFormatting color) {
        return Component.translatable(message).withStyle(color);
    }

    // CUSTOM METHOD - COMPONENT LITERAL with BOLD format
    public static Component componentLiteralStyle(String message, ChatFormatting color) {
        return Component.literal(message).setStyle(Style.EMPTY.applyFormats(color, ChatFormatting.BOLD));
    }

    // CUSTOM METHOD - Message appears on screen with COMPONENT LITERAL without color (BOOL)
    public static void playerBool(Player player, String message) {
        player.displayClientMessage(standardLiteral(message), true);
    }

    // CUSTOM METHOD - Message appears on screen without BOLD format
    public static void player(Player player, String message, ChatFormatting color) {
        player.displayClientMessage(componentLiteral(message, color), true);
    }

    public static void playerRGB(Player player, Component message) {
        player.displayClientMessage(message, true);
    }

    // CUSTOM METHOD - Message appears on screen with BOLD format
    public static void playerStyle(Player player, String message, ChatFormatting color) {
        player.displayClientMessage(componentLiteralStyle(message, color), true);
    }

    // CUSTOM METHOD - Message appears on screen with STAGE change
    public static void playerStyleBool(Player player, boolean bool, boolean bool2,
                                       String message, String message2, ChatFormatting color, ChatFormatting color2) {
        if (bool) { player.displayClientMessage(componentLiteral(message, color), true); }
        if (bool2) { player.displayClientMessage(componentLiteral(message2, color2), true); }
    }

    // CUSTOM METHOD - Using RGB colors and BOLD format
    public static void glow(Player player, boolean test, String message, String message1) {
        player.displayClientMessage(componentLiteralStyle("Glowing " + (test ? message : message1),
                (test ? green : red)), true);
    }

    // CUSTOM METHOD - Using RGB colors and BOLD format
    public static MutableComponent description(String tooltip, ChatFormatting color,
                                               List<Boolean> curse) {
        boolean curseFalse = curse.get(0), curseTrue = curse.get(1);
        return Component.translatable(tooltip).withStyle(Style.EMPTY.withColor(color).withBold(curseFalse).withItalic(curseTrue));
    }

    // CUSTOM METHOD - Text appears on TOOLTIP item
    public static void tooltipLine(Consumer<Component> tooltip, String message, ChatFormatting color) {
        tooltip.accept(componentLiteral(message, color));
    }

    // CUSTOM METHOD - Text appears on TOOLTIP item (Translatable version)
    public static void tooltipLineTranslatable(List<Component> tooltip, String message) {
        tooltip.add(standardTranslatable(message));
    }

    // CUSTOM METHOD - Text appears on TOOLTIP item (Translatable version) with RGB color
    public static MutableComponent tooltipLineTranslatableRGB(int[] COLORS, ItemStack stack) {
        int shift = (int) (System.currentTimeMillis() / 200L % COLORS.length); // Calculates color shift based on time
        MutableComponent minerText = Component.literal(""); // Animated text for "Miner Bow" with RGB wave effect
        String text = itemLine(stack.getItem().getDescriptionId(), "item.mccoursemod.", "", "_", " "),
                 on = itemLines(text);
        for (int i = 0; i < on.length(); i++) {
            int colorIndex = (i - shift + COLORS.length) % COLORS.length; // Adjust to move colors from left to right
            minerText.append(Component.translatable(String.valueOf(on.charAt(i)))
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(COLORS[colorIndex]))));
        }
        return minerText;
    }

    // CUSTOM METHOD - Text appears on TOOLTIP item with BOLD format
    public static void tooltipLineBold(List<Component> tooltip, String message, ChatFormatting color) {
        tooltip.add(componentLiteralStyle(message, color));
    }

    // CUSTOM METHOD - Text appears on TOOLTIP item with STAGE change
    public static void tooltipLines(List<Component> tooltip, ItemStack item,
                                    String message, ChatFormatting color) {
        tooltip.add(Component.translatable(item.getItem().getDescriptionId()).withStyle(color)
               .append(componentLiteral(message, color)));
    }

    // CUSTOM METHOD - Tooltip Line Literal with RGB colors
    public static void tooltipLineLiteralRGB(Consumer<Component> tooltip,
                                             int[] COLORS, ItemStack stack, String message) {
        int shift = (int) (System.currentTimeMillis() / 200L % COLORS.length); // Calculates color shift based on time
        MutableComponent minerText = Component.literal(""); // Animated text for "Miner Bow" with RGB wave effect
        String text = itemLine(stack.getItem().getDescriptionId(), "item.mccoursemod.", "", "_", " "),
                on = itemLines(text) + message;
        for (int i = 0; i < on.length(); i++) {
            int colorIndex = (i - shift + COLORS.length) % COLORS.length; // Adjust to move colors from left to right
            minerText.append(Component.literal(String.valueOf(on.charAt(i)))
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(COLORS[colorIndex]))));
        }
        tooltip.accept(minerText);
    }

    // CUSTOM METHOD - Message Literal on screen with RGB colors
    public static void messageLiteralRGB(Player player, int[] COLORS, ItemStack stack, String message) {
        int shift = (int) (System.currentTimeMillis() / 200L % COLORS.length); // Calculates color shift based on time
        MutableComponent minerText = Component.literal(""); // Animated text for "Miner Bow" with RGB wave effect
        String text = itemLine(stack.getItem().getDescriptionId(), "item.mccoursemod.", "", "_", " "),
                on = itemLines(text) + message;
        for (int i = 0; i < on.length(); i++) {
            int colorIndex = (i - shift + COLORS.length) % COLORS.length; // Adjust to move colors from left to right
            minerText.append(Component.literal(String.valueOf(on.charAt(i)))
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(COLORS[colorIndex]))));
        }
        playerRGB(player, minerText);
    }

    // CUSTOM METHOD - UNIQUE message
    public static void normalMessage(Player player, String message, ChatFormatting color) {
        player(player, message, color);
    }

    // CUSTOM METHOD - Icon message TOOLTIP
//    public static void image(List<Either<FormattedText, TooltipComponent>> element,
//                             String path, int width, int height, String text, Boolean bool) {
//        if (bool) {
//            element.add(Either.right(new ImageTooltipComponent(new ResourceLocation(MccourseMod.MOD_ID, path),
//                    width, height, Component.literal(text))));
//        }
//    }

    // CUSTOM METHOD - INVALID message with BOLD format
    public static void invalidMessage(Player player, String invalid) {
        playerStyle(player, invalid, darkRed);
    }

    // CUSTOM METHOD - TRADE message with BOLD format
    public static void tradeMessage(Player player, String trade) {
        player(player, trade, red);
    }

    // CUSTOM METHOD - [X, Y, Z] Coordinates
    public static Component literal(double x, double y, double z) {
        return Component.literal("X: ").append(componentLiteral(String.format("%.3f", x), aqua))
                .append(standardLiteral("  Y: ")).append(componentLiteral(String.format("%.5f", y), purple))
                .append(standardLiteral("  Z: ")).append(componentLiteral(String.format("%.3f", z), gold));
    }

    // CUSTOM METHOD - Light numbers
    public static Component numbers(int totalLight, int skyLight, int blockLight) {
        return Component.literal("Light:").append(customStyle(" " + totalLight, totalLight))
                .append(standardLiteral("  Sky:")).append(customStyle(" " + skyLight, skyLight))
                .append(standardLiteral("  Block:")).append(customStyle(" " + blockLight, blockLight));
    }

    // CUSTOM METHOD - Light colors numbers
    public static Component customStyle(String number, int light) {
        return Component.literal(String.valueOf(number)).setStyle(Style.EMPTY.withColor(light > 6 ? 0x32FC76 : 0xFF1818));
    }

    // CUSTOM METHOD - Vault item display
    public static Component itemChatMessage(Player player, BlockPos pos, ChatFormatting color) {
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        return Component.literal(player.getGameProfile().getName() +
                                 " died at [X: " + x + ", Y: " + y + ", Z: " + z + "] " +
                                 LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .withStyle(Style.EMPTY.withColor(color).withItalic(false));
    }

    // CUSTOM METHOD - Interaction Result Holder FAIL messages
    public static boolean fail(Player player, String message, ChatFormatting color) {
        player(player, message, color);
        return false;
    }

    // CUSTOM METHOD - Renamed string on TOOLTIP -> Ex: Fortune etc. (Only one word) -> Capitalize First Letters
    public static String itemLine(String var, String old, String value,
                                  String old2, String value2) {
        String name = var.replace(old, value).replace(old2, value2), firstIndex = name.substring(0, 1).toUpperCase();
        return firstIndex + name.substring(1);
    }

    // CUSTOM METHOD - Renamed string on TOOLTIP -> Ex: Mccourse Generator etc. (Two+ words) -> Capitalize First Letters
    public static String itemLines(String input) {
        String[] words = input.split(" "); // Separated words
        StringJoiner capitalized = new StringJoiner(" "); // Spaced words
        for (String word : words) { // For each word
            if (!word.isEmpty()) { capitalized.add(word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase()); }
        }
        return capitalized.toString(); // Joined words with capitalize words
    }

    // CUSTOM METHOD - SPLIT STRING
    public static String splitWord(String word) {
        return word.replace("_", " ");
    }

    // CUSTOM METHOD - SPLIT UPPER STRING -> Example: LuckyBomb -> Lucky Bomb
    public static String upperString(String upper) {
        return upper.replaceAll("([a-z])([A-Z])", "$1 $2");
    }

    // CUSTOM METHOD - Enchantment color
    public static ChatFormatting getEnchantmentColor(Holder<Enchantment> holder) {
        // FULL ARMOR
        if (holder.is(EnchantmentTags.ARMOR_EXCLUSIVE)) { return gold; }
        // HELMET ARMOR
        if (holder.is(ModTags.Enchantments.HELMET_ENCHANTMENTS)) { return darkAqua; }
        // CHESTPLATE ARMOR
        if (holder.is(ModTags.Enchantments.CHESTPLATE_ENCHANTMENTS)) { return purple; }
        // LEGGINGS ARMOR
        if (holder.is(ModTags.Enchantments.LEGGINGS_ENCHANTMENTS)) { return darkGray; }
        // BOOTS ARMOR
        if (holder.is(ModTags.Enchantments.BOOTS_ENCHANTMENTS)) { return blue; }
        // FISHING ROD
        if (holder.is(ModTags.Enchantments.FISHING_ENCHANTMENTS)) { return yellow; }
        // PICKAXE, AXE, SHOVEL ETC.
        if (holder.is(ModTags.Enchantments.MINING_ENCHANTMENTS)) { return darkPurple; }
        // UNBREAKING, MENDING
        if (holder.is(ModTags.Enchantments.DURABILITY_ENCHANTMENTS)) { return darkBlue; }
        // TRIDENT
        if (holder.is(ModTags.Enchantments.TRIDENT_ENCHANTMENTS)) { return aqua; }
        // SWORD
        if (holder.is(ModTags.Enchantments.SWORD_ENCHANTMENTS)) { return darkRed; }
        // BOW
        if (holder.is(ModTags.Enchantments.BOW_ENCHANTMENTS)) { return green; }
        // CROSSBOW
        if (holder.is(ModTags.Enchantments.CROSSBOW_ENCHANTMENTS)) { return darkGreen; }
        // MACE
        if (holder.is(ModTags.Enchantments.MACE_ENCHANTMENTS)) { return white; }
        return gray;
    }

    // CUSTOM METHOD - Enchantment Icon compatibility
    public static String icon(Holder<Enchantment> holder) {
        String armor = "§6⭐", pick = "§5⛏", hammer = "§3🔨", shield = "§1🛡",
                bow = "§a\uD83C\uDFF9", sword = "§4\uD83D\uDDE1", trident = "§b\uD83D\uDD31",
                fish = "§e\uD83C\uDFA3", axe = "§5\uD83E\uDE93";
        // CURSE
        if (holder.is(EnchantmentTags.CURSE)) { return "§c🔥"; }
        // ARMOR
        if (holder.is(EnchantmentTags.ARMOR_EXCLUSIVE)) { return armor; }
        // HELMET, CHESTPLATE, LEGGINGS, BOOTS ARMORS AND MACE
        if (holder.is(ModTags.Enchantments.HELMET_ENCHANTMENTS) || holder.is(ModTags.Enchantments.CHESTPLATE_ENCHANTMENTS) ||
            holder.is(ModTags.Enchantments.LEGGINGS_ENCHANTMENTS) || holder.is(ModTags.Enchantments.BOOTS_ENCHANTMENTS) ||
            holder.is(ModTags.Enchantments.MACE_ENCHANTMENTS)) {
            return "";
        }
        // FISHING ROD
        if (holder.is(ModTags.Enchantments.FISHING_ENCHANTMENTS)) { return fish; }
        // PICKAXE
        if (holder.is(ModTags.Enchantments.MINING_ENCHANTMENTS)) { return pick + " " + axe; }
        // GENERAL UNBREAKING, MENDING
        if (holder.is(ModTags.Enchantments.DURABILITY_ENCHANTMENTS)) {
            return axe + " " + fish + " " + pick + " " + armor + " " + sword + " " + bow + " " + trident + " " +
                   hammer + " " + shield;
        }
        // TRIDENT
        if (holder.is(ModTags.Enchantments.TRIDENT_ENCHANTMENTS)) { return trident; }
        // SWORD
        if (holder.is(ModTags.Enchantments.SWORD_ENCHANTMENTS)) { return sword; }
        // BOW or CROSSBOW
        if (holder.is(ModTags.Enchantments.BOW_ENCHANTMENTS) || holder.is(ModTags.Enchantments.CROSSBOW_ENCHANTMENTS)) {
            return bow;
        }
        return "ICON";
    }
}