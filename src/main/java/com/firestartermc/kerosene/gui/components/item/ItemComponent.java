package com.firestartermc.kerosene.gui.components.item;

import com.google.common.collect.ImmutableMap;
import org.bukkit.inventory.ItemStack;
import com.firestartermc.kerosene.gui.Gui;
import com.firestartermc.kerosene.gui.base.Drawable;
import com.firestartermc.kerosene.gui.GuiPosition;

import java.util.Map;

/**
 * Display a bsic item in a Gui.
 * Without any further functionality.
 */
public class ItemComponent implements Drawable {

    private GuiPosition position;
    private ItemStack item;

    /**
     * Create a new item component.
     * @param x The x index.
     * @param y The y index.
     * @param item The {@link ItemStack} to use.
     */
    public ItemComponent(int x, int y, ItemStack item) {
        this(new GuiPosition(x, y), item);
    }

    /**
     * Create a new item component.
     * @param position The position
     * @param item The {@link ItemStack} to use.
     */
    public ItemComponent(GuiPosition position, ItemStack item) {
        this.position = position;
        this.item = item;
    }

    @Override
    public Map<GuiPosition, ItemStack> draw(Gui gui) {
        if (item == null) return ImmutableMap.of();
        return ImmutableMap.of(position, item);
    }

    /**
     * Get the position of the item component.
     * @return The position.
     */
    public GuiPosition getPosition() {
        return position;
    }

    /**
     * Set the position of the item component.
     * Note: this requires a Gui update.
     *
     * @param position The new position.
     */
    public void setPosition(GuiPosition position) {
        this.position = position;
    }

    /**
     * Get the item of the item component.
     * @return The item.
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * Sets the item of the item component.
     * Note: this requires a gui update.
     *
     * @param item The new item.
     */
    public void setItem(ItemStack item) {
        this.item = item;
    }
}
