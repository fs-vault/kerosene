package xyz.nkomarn.kerosene.gui.components.cosmetic.base;

import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.gui.Gui;
import xyz.nkomarn.kerosene.gui.GuiPosition;
import xyz.nkomarn.kerosene.gui.base.Drawable;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic functionality of a border
 */
public abstract class BorderBase implements Drawable {

    private int x, y;
    private int width, height;

    public BorderBase() {
        this(0, 0, 9, -1);
    }

    public BorderBase(int x, int y, int width, int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public Map<GuiPosition, ItemStack> draw(Gui gui) {
        Map<GuiPosition, ItemStack> result = new HashMap<>();
        int rows = gui.getRows();

        if (this.y > rows - 1) {
            return null; // starts outside gui (vertically) -> don't render
        }

        if (this.height == -1) {
            setHeight(rows);
        }

        // top
        for (int xx = this.x; xx < Math.min(9, this.x + this.width); xx++) {
            GuiPosition position = new GuiPosition(xx, this.y);
            result.put(position, getItemForPosition(position));
        }

        // bottom
        for (int xx = this.x; xx < Math.min(9, this.x + this.width); xx++) {
            GuiPosition position = new GuiPosition(xx, Math.min(rows, this.y + this.height) - 1);
            result.put(position, getItemForPosition(position));
        }

        // left
        for (int yy = this.y + 1; yy < Math.min(rows, this.y + this.height) - 1; yy++){
            GuiPosition position = new GuiPosition(this.x, yy);
            result.put(position, getItemForPosition(position));
        }

        // right
        for (int yy = this.y + 1; yy < Math.min(rows, this.y + this.height) - 1; yy++){
            GuiPosition position = new GuiPosition(Math.min(9, this.x + this.width) - 1, yy);
            result.put(position, getItemForPosition(position));
        }

        return result;
    }

    public abstract ItemStack getItemForPosition(GuiPosition position);

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
}
