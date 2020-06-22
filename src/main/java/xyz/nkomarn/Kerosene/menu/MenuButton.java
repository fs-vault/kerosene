package xyz.nkomarn.Kerosene.menu;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Represents a button on a Menu Inventory.
 * Code can be bound to the button to execute when it is pressed in its menu.
 */
public class MenuButton {
    private final Menu inventory;
    private ItemStack item;
    private final int slot;
    private final GuiButtonCallback callback;

    /**
     * Creates a new MenuButton and binds it to the provided Inventory.
     *
     * @param inventory The Menu Inventory to bind the button to.
     * @param item      The ItemStack to represent the button in the Menu.
     * @param slot      The slot of the Menu Inventory in which to place the button.
     * @param callback  The callback to code which should be executed on button click in the Menu.
     */
    public MenuButton(Menu inventory, ItemStack item, int slot, GuiButtonCallback callback) {
        this.inventory = inventory;
        this.item = item;
        this.slot = slot;
        this.callback = callback;
    }

    /**
     * Returns the ItemStack which represents the MenuButton in the Menu Inventory.
     *
     * @return The ItemStack that is bound to this MenuButton.
     */
    public ItemStack getItem() {
        return this.item;
    }

    /**
     * Returns the slot of the Menu Inventory that this button is in.
     *
     * @return The Menu Inventory slot of this button.
     */
    public int getSlot() {
        return this.slot;
    }

    /**
     * Returns the callback for this MenuButton.
     *
     * @return The callback with code to run on click.
     */
    public GuiButtonCallback getCallback() {
        return this.callback;
    }

    /**
     * Updates the ItemStack of the MenuButton and then updates the inventory.
     *
     * @param item The new ItemStack for the MenuButton.
     */
    public void setItem(ItemStack item) {
        this.item = item;
        update();
    }

    /**
     * Updates the Material type of the MenuButton and then updates the inventory.
     *
     * @param material The new Material for the MenuButton.
     */
    public void setType(Material material) {
        item.setType(material);
        update();
    }

    /**
     * Updates the ItemMeta of the MenuButton and then updates the inventory.
     *
     * @param meta The new ItemMeta for the GuiButton.
     */
    public void setItemMeta(ItemMeta meta) {
        item.setItemMeta(meta);
        update();
    }

    /**
     * Refreshes the Menu Inventory for the viewing player without reopening.
     */
    public void update() {
        inventory.getInventory().setItem(slot, item);
        inventory.getPlayer().updateInventory();
    }

    /**
     * Represents a callback for MenuButton click in the Gui Inventory.
     */
    @FunctionalInterface
    public interface GuiButtonCallback {
        void handle(MenuButton button, ClickType clickType);
    }
}
