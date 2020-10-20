package com.firestartermc.kerosene.gui.components.cosmetic;

import com.firestartermc.kerosene.gui.base.DrawingContext;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import com.firestartermc.kerosene.gui.Gui;
import com.firestartermc.kerosene.gui.GuiDefaults;
import com.firestartermc.kerosene.gui.base.Drawable;
import com.firestartermc.kerosene.gui.GuiPosition;
import com.firestartermc.kerosene.item.ItemUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Fill an entire region with a specific item.
 */
public class FillComponent implements Drawable {

    private ItemStack fillItem;
    private int x, y;
    private int width, height;

    /**
     * Fill the entire {@link Gui} with the {@link GuiDefaults#FILL_ITEM}.
     */
    public FillComponent() {
        this(GuiDefaults.FILL_ITEM);
    }

    /**
     * Fill the entire {@link Gui} with a fill item of a specific {@link Material}.
     * @param fillMaterial The material to make the fill item out of.
     */
    public FillComponent(Material fillMaterial) {
        this(ItemUtils.makeFillItem(fillMaterial));
    }

    /**
     * Fill the entire {@link Gui} with a specific fill item.
     * @param fillItem The fill item.
     */
    public FillComponent(ItemStack fillItem) {
        this(0, 0, 9, -1, fillItem);
    }

    /**
     * Fill a specific region of the {@link Gui} with a specific material.
     * @param x The horizontal start position.
     * @param y The vertical start position.
     * @param width The width of the region to fill.
     * @param height The height of the region to fill.
     * @param fillMaterial The material to use as a fill item.
     */
    public FillComponent(int x, int y, int width, int height, Material fillMaterial) {
        this(x, y, width, height, ItemUtils.makeFillItem(fillMaterial));
    }

    /**
     * Fill a specific region of the {@link Gui} with a specific fill item.
     * @param x The horizontal start position.
     * @param y The vertical start position.
     * @param width The width of the region to fill.
     * @param height The height of the region to fill.
     * @param fillItem The item to use as a fill item.
     */
    public FillComponent(int x, int y, int width, int height, ItemStack fillItem) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setFillItem(fillItem);
    }

    @Override
    public Map<GuiPosition, ItemStack> draw(DrawingContext context) {
        Map<GuiPosition, ItemStack> result = new HashMap<>();
        int rows = context.getGui().getRows();

        if (this.y > rows - 1) {
            return null; // starts outside gui (vertically) -> don't render
        }

        if (this.height == -1) {
            setHeight(rows);
        }

        for (int xx = this.x; xx < Math.min(9, this.x + this.width); xx++) {
            for (int yy = this.y; yy < Math.min(rows, this.y + this.height); yy++){
                result.put(new GuiPosition(xx, yy), this.fillItem);
            }
        }

        return result;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x < 0) {
            x = 0;
        } else if (x > 8) {
            x = 8;
        }
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (x < 0) {
            x = 0;
        }
        // maximum y is handled in draw(Gui)
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width < 1) {
            width = 1;
        }
        // maximum width is handled in draw(Gui)
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height < 1 && height != -1) {
            height = 1;
        }
        // maximum height is handled in draw(Gui)
        this.height = height;
    }

    public ItemStack getFillItem() {
        return fillItem;
    }

    /**
     * Change the fill item.
     *
     * Note: If the {@link Gui} is already displayed to any players, {@link Gui#update()} should be called to apply the changes.
     *
     * @param fillItem The new fill item.
     */
    public void setFillItem(ItemStack fillItem) {
        this.fillItem = fillItem;
    }
}
