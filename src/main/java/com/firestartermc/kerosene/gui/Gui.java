package com.firestartermc.kerosene.gui;

import com.firestartermc.kerosene.gui.base.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import com.firestartermc.kerosene.Kerosene;

import java.util.*;

public class Gui implements InventoryHolder, Interactable {

    private final Inventory inventory;
    private final Set<GuiElement> guiElements = new LinkedHashSet<>();
    private final Set<Drawable> drawableElements = new LinkedHashSet<>();
    private final Set<Interactable> interactableElements = new LinkedHashSet<>();
    private final Set<OnGuiClose> closeListenerElements = new LinkedHashSet<>();
    private final Set<Player> viewers = new HashSet<>();
    private final String title;
    private final int rows;
    private final Gui parent;
    private boolean overridable;

    /**
     * Create a new Gui.
     * @param title The title of the gui.
     * @param rows The amount of rows in the gui.
     */
    public Gui(String title, int rows) {
        this(null, title, rows);
    }

    /**
     * Create a new Gui.
     * @param parent the parent {@link Gui} of the Gui.
     * @param title The title of the gui.
     * @param rows The amount of rows in the gui.
     */
    public Gui(Gui parent, String title, int rows) {
        if (rows < 1) {
            throw new IllegalArgumentException("A gui should contains at least 1 row!");
        }

        this.parent = parent;
        this.title = title;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(this, rows * 9, title);
        this.overridable = true;
    }

    /**
     * Add a new element to the Gui.
     * @param element The element to add.
     */
    public void addElement(GuiElement element) {
        guiElements.add(element);

        if (element instanceof Drawable drawable) {
            drawableElements.add(drawable);
        }

        if (element instanceof Interactable interactable) {
            interactableElements.add(interactable);
        }

        if (element instanceof OnGuiClose closeListener) {
            closeListenerElements.add(closeListener);
        }
    }

    /**
     * Remove an element from the Gui.
     * @param element The element to remove.
     */
    public void removeElement(GuiElement element) {
        guiElements.remove(element);

        if (element instanceof Drawable) {
            drawableElements.remove(element);
        }

        if(element instanceof Interactable) {
            interactableElements.remove(element);
        }
    }

    /**
     * Close the Gui for all viewers
     */
    public void close() {
        this.viewers.forEach(Player::closeInventory);
    }

    /**
     * Open the Gui
     * @param players The {@link Player}(s) to open the Gui for.
     */
    public void open(Set<Player> players) {
        Bukkit.getScheduler().runTask(Kerosene.getKerosene(), () -> {
            players.forEach(player -> {
                if (player.getOpenInventory().getTopInventory().getHolder() instanceof Gui gui) {
                    if (!gui.isOverridable()) {
                        return;
                    }
                }

                this.onOpen(player);
                this.render(player);
                player.openInventory(this.inventory);
                this.viewers.add(player);
            });
        });
    }

    /**
     * Open the Gui
     * @param players the {@link Player}(s) to open the Gui for.
     */
    public void open(Player... players) {
        open(new HashSet<>(Arrays.asList(players)));
    }

    @Override
    public void onInteract(InteractEvent event) {
        for (Interactable interactable : interactableElements) {
            interactable.onInteract(event);
        }
    }

    /**
     * Trigger an update for the current Gui.
     */
    public void update() {
        this.viewers.forEach(player -> {
            this.render(player);
            player.updateInventory();
        });
    }

    /**
     * Navigate to the parent Gui.
     * If there is no parent Gui, nothing will happen.
     */
    public void navigateToParent() {
        if (!hasParent()) {
            return;
        }

        this.parent.open(this.getViewers());
    }

    protected void render(Player player) {
        getInventory().clear();

        for (Drawable drawable : this.drawableElements) {
            DrawingContext context = new DrawingContext(this, player);
            Map<GuiPosition, ItemStack> items = drawable.draw(context);
            if (items == null) return;

            items.forEach((vector2i, stack) -> {
                this.inventory.setItem(vector2i.getX() + 9 * vector2i.getY(), stack);
            });
        }

    }

    void removeViewer(Player player) {
        closeListenerElements.forEach(element -> element.onClose(player));
        this.onClose(player);
        this.viewers.remove(player);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Get the players that are currently viewing the Gui.
     * @return The player that are viewing the Gui.
     */
    public Set<Player> getViewers() {
        return viewers;
    }

    /**
     * Gets the title of the Gui.
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the amount of rows in the Gui.
     * @return The amount of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the parent Gui.
     * @return The parent Gui.
     */
    public Gui getParent() {
        return parent;
    }

    /**
     * Check if the current Gui has a parent Gui.
     * @return True if there is a parent gui.
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Check if the current Gui is overridable by other Gui's.
     * @return True if the Gui can be overriden.
     */
    public boolean isOverridable() {
        return overridable;
    }

    /**
     * Change whether the Gui can be overriden.
     * @param overridable The value.
     */
    public void setOverridable(boolean overridable) {
        this.overridable = overridable;
    }

    public void onOpen(Player player) {
    }

    public void onClose(Player player) {
    }

    /**
     * Close all open Gui's.
     */
    public static void closeAll() {
        // Close all open Gui's to prevent players from taking any items out.
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory().getTopInventory().getHolder() instanceof Gui)
                .forEach(player -> ((Gui) player.getOpenInventory().getTopInventory().getHolder()).close());
    }
}
