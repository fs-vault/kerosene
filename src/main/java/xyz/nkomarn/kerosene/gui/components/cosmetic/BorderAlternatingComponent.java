package xyz.nkomarn.kerosene.gui.components.cosmetic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.gui.Gui;
import xyz.nkomarn.kerosene.gui.GuiPosition;
import xyz.nkomarn.kerosene.gui.components.cosmetic.base.BorderBase;
import xyz.nkomarn.kerosene.util.item.ItemUtils;

/**
 * A border with alternating items.
 */
public class BorderAlternatingComponent extends BorderBase {

    private ItemStack fillItem1;
    private ItemStack fillItem2;

    /**
     * Create an alternating border on the entire {@link xyz.nkomarn.kerosene.gui.Gui} of two specific {@link Material}s.
     * @param fillMaterial1 First material.
     * @param fillMaterial2 Seconds material.
     */
    public BorderAlternatingComponent(Material fillMaterial1, Material fillMaterial2) {
        this(0, 0, 9, -1, fillMaterial1, fillMaterial2);
    }

    /**
     * Create an alternating border on the entire {@link xyz.nkomarn.kerosene.gui.Gui} of two specific {@link ItemStack}s
     * @param fillItem1 First item.
     * @param fillItem2 Second item.
     */
    public BorderAlternatingComponent(ItemStack fillItem1, ItemStack fillItem2) {
        this(0, 0, 9, -1, fillItem1, fillItem2);
    }

    /**
     * Create an alternating border for a specific region of the {@link xyz.nkomarn.kerosene.gui.Gui} of two specific {@link Material}s
     * @param x The horizontal start position.
     * @param y The vertical start position.
     * @param width The width of the region to fill.
     * @param height The height of the region to fill.
     * @param fillMaterial1 The first material to use as a border item.
     * @param fillMaterial2 The second material to use as a border item.
     */
    public BorderAlternatingComponent(int x, int y, int width, int height, Material fillMaterial1, Material fillMaterial2) {
        this(x, y, width, height, ItemUtils.makeFillItem(fillMaterial1), ItemUtils.makeFillItem(fillMaterial2));
    }

    /**
     * Create an alternating border for a specific region of the {@link xyz.nkomarn.kerosene.gui.Gui} of two specific {@link ItemStack}s
     * @param x The horizontal start position.
     * @param y The vertical start position.
     * @param width The width of the region to fill.
     * @param height The height of the region to fill.
     * @param fillItem1 The first item to use as a border item.
     * @param fillItem2 The second item to use as a border item.
     */
    public BorderAlternatingComponent(int x, int y, int width, int height, ItemStack fillItem1, ItemStack fillItem2) {
        super(x, y, width, height);
        setFillItem1(fillItem1);
        setFillItem2(fillItem2);
    }

    @Override
    public ItemStack getItemForPosition(GuiPosition position) {
        return (position.toSlot() % 2 == 0) ? this.fillItem1 : this.fillItem2;
    }

    /**
     * Change the first fill item.
     *
     * Note: If the {@link Gui} is already displayed to any players, {@link Gui#update()} should be called to apply the changes.
     *
     * @param fillItem1 The new fill item.
     */
    public void setFillItem1(ItemStack fillItem1) {
        this.fillItem1 = fillItem1;
    }

    /**
     * Change the second fill item.
     *
     * Note: If the {@link Gui} is already displayed to any players, {@link Gui#update()} should be called to apply the changes.
     *
     * @param fillItem2 The new fill item.
     */
    public void setFillItem2(ItemStack fillItem2) {
        this.fillItem2 = fillItem2;
    }
}
