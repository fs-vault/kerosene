package xyz.nkomarn.kerosene.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.nkomarn.kerosene.Kerosene;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Menu Inventory with buttons.
 * Makes it easy to create menus in-game with minimal effort.
 */
public class Menu {
    private final Inventory inventory;
    private final Map<Integer, MenuButton> buttons = new HashMap<>();
    private final Player player;
    private final int size;
    private CloseCallback closeCallback;

    // TODO method to set inventory name on the fly
    // https://www.spigotmc.org/threads/how-to-set-the-title-of-an-open-inventory-itemgui.95572/

    /**
     * Creates a new Menu with a provided Inventory.
     *
     * @param inventory The Inventory to use for this Menu.
     * @param player    The player to open the Menu to when open() is called.
     */
    public Menu(Player player, Inventory inventory) {
        this.inventory = inventory;
        this.player = player;
        this.size = inventory.getSize();
    }

    /**
     * Creates a new Menu Inventory with a provided player, title, and size.
     *
     * @param player The player to open the Menu to when open() is called.
     * @param name   The title of the Menu Inventory.
     * @param size   The size of the Menu Inventory, in slots (must be a multiple of 9).
     */
    public Menu(Player player, String name, int size) {
        this.inventory = Bukkit.createInventory(player, size, name);
        this.player = player;
        this.size = size;
    }

    /**
     * Return the Inventory instance tied to this Menu.
     *
     * @return The Inventory instance of this Menu.
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Returns the player the Menu is being displayed to.
     *
     * @return The player that the Menu is displayed to.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Binds a MenuButton instance to the Menu, effectively creating a clickable action.
     *
     * @param button An instance of the MenuButton to add to the Menu.
     */
    public void addButton(MenuButton button) {
        this.buttons.put(button.getSlot(), button);
    }

    /**
     * Returns an ItemStack for the specified Material. Used when filling the Menu.
     *
     * @param material The Material to make the ItemStack out of.
     * @return An ItemStack with the specified Material and a blank display name.
     */
    private ItemStack getFillItem(Material material) {
        ItemStack fill = new ItemStack(material);
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.setDisplayName(" ");
        fill.setItemMeta(fillMeta);
        return fill;
    }

    /**
     * Fills the whole Inventory with a solid Material.
     *
     * @param material The Material with which to fill the whole Inventory.
     */
    public void fill(Material material) {
        ItemStack fill = getFillItem(material);

        for (int i = 0; i < size; i++) {
            inventory.setItem(i, fill);
        }
    }

    /**
     * Fills the border of the Inventory with a solid Material.
     *
     * @param material The Material with which to fill the border of the Inventory.
     */
    public void fillBorder(Material material) {
        ItemStack fill = getFillItem(material);

        for (int i = 0; i < 9; i++) { // Top
            inventory.setItem(i, fill);
        }

        for (int i = size - 9; i < size; i++) { // Bottom
            inventory.setItem(i, fill);
        }

        for (int i = 0; i < size; i += 9) { // Left
            inventory.setItem(i, fill);
        }

        for (int i = 8; i < size; i += 9) { // Right
            this.inventory.setItem(i, fill);
        }
    }

    /**
     * Fills the border of the Inventory with two alternating Material types.
     *
     * @param materialOne The first Material, which only displays on odd slots.
     * @param materialTwo The second Material, which only displays on even slots.
     */
    public void fillBorderAlternating(Material materialOne, Material materialTwo) {
        ItemStack fillOne = getFillItem(materialOne);
        ItemStack fillTwo = getFillItem(materialTwo);

        for (int i = 0; i < 9; i++) { // Top
            if (i % 2 == 0) inventory.setItem(i, fillOne);
            else inventory.setItem(i, fillTwo);
        }

        for (int i = size - 9; i < size; i++) { // Bottom
            if (i % 2 == 0) inventory.setItem(i, fillOne);
            else inventory.setItem(i, fillTwo);
        }

        for (int i = 0; i < size; i += 9) { // Left
            if (i % 2 == 0) inventory.setItem(i, fillOne);
            else inventory.setItem(i, fillTwo);
        }

        for (int i = 8; i < size; i += 9) { // Right
            if (i % 2 == 0) inventory.setItem(i, fillOne);
            else inventory.setItem(i, fillTwo);
        }
    }

    /**
     * Registers this Menu in the GuiHandler and then opens it synchronously to the player.
     */
    public void open() {
        buttons.forEach((slot, button) -> inventory.setItem(slot, button.getItem()));
        Bukkit.getScheduler().runTask(Kerosene.getKerosene(), () -> {
            player.openInventory(inventory);
            MenuHandler.registerInventory(this);
        });
    }

    /**
     * Closes the Menu Inventory synchronously.
     */
    public void close() {
        Bukkit.getScheduler().runTask(Kerosene.getKerosene(), (Runnable) player::closeInventory);
    }

    /**
     * Set a callback to run when this Menu is closed.
     *
     * @param callback The callback to run on Menu close.
     */
    public void onClose(CloseCallback callback) {
        this.closeCallback = callback;
    }

    /**
     * Run the close callback when this Menu is closed.
     */
    public void handleClose() {
        if (closeCallback != null) {
            closeCallback.handle(this);
        }
    }

    /**
     * Called when a MenuButton is clicked and handles the execution of the button's code.
     *
     * @param event An instance of the InventoryClickEvent.
     */
    public void handleClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(this.inventory)) {
            if (event.getRawSlot() < event.getClickedInventory().getSize()) {
                MenuButton button = this.buttons.get(event.getSlot());
                if (button != null) {
                    if (button.getCallback() != null) {
                        button.getCallback().handle(button, event.getClick());
                        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0.6f, 0.6f);
                    }
                }
            }
        }
    }

    /**
     * Represents a callback ran on Menu close.
     */
    @FunctionalInterface
    public interface CloseCallback {
        void handle(Menu menu);
    }
}
