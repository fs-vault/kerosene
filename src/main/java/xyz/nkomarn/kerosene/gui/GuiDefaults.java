package xyz.nkomarn.kerosene.gui;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.util.item.ItemBuilder;
import xyz.nkomarn.kerosene.util.item.ItemUtils;

/**
 * Default gui settings that are used across different GUI's.
 */
public final class GuiDefaults {

    /**
     * The default fill material.
     */
    public static final Material FILL_MATERIAL = Material.WHITE_STAINED_GLASS_PANE;

    /**
     * The default fill item.
     */
    public static final ItemStack FILL_ITEM = ItemUtils.makeFillItem(FILL_MATERIAL);

    /**
     * The default back item.
     */
    public static final ItemStack BACK_ITEM = new ItemBuilder(Material.PAPER).name("&b&lBack").build();

    /**
     * The default previous item.
     */
    public static final ItemStack PREVIOUS_ITEM = new ItemBuilder(Material.SPRUCE_BUTTON).name("&f&lPrevious").build();

    /**
     * The default next item.
     */
    public static final ItemStack NEXT_ITEM = new ItemBuilder(Material.SPRUCE_BUTTON).name("&f&lNext").build();

    /**
     * The default confirm item.
     */
    public static final ItemStack CONFIRM_ITEM = new ItemBuilder(Material.LIME_BANNER).name("&a&lConfirm").build();

    /**
     * The default cancel item.
     */
    public static final ItemStack CANCEL_ITEM = new ItemBuilder(Material.RED_BANNER).name("&c&lCancel").build();

    /**
     * Plat the default selection sound for a player.
     * @param player The player to play the sound for.
     */
    public static void playSelectSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.6f, 0.6f);
    }

    /**
     * Play the default bad selection sound for a player.
     * @param player The player to play the sound for.
     */
    public static void playBadSelectSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.6f, 0.6f);
    }

    /**
     * Play the default error sound for a player.
     * @param player The player to play the sound for.
     */
    public static void playErrorSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 0.6f, 0.6f);
    }

    private GuiDefaults() { }
}
