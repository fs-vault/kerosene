package xyz.nkomarn.Kerosene.gui.components.cosmetic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.Kerosene.gui.Gui;
import xyz.nkomarn.Kerosene.gui.base.Drawable;
import xyz.nkomarn.Kerosene.gui.GuiPosition;
import xyz.nkomarn.Kerosene.util.item.ItemUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Fill an entire region with a specific item.
 */
public class FillComponent implements Drawable {

    private ItemStack fillItem;
    private int x, y;
    private int width, height;

    public FillComponent(Material fillMaterial) {
        this(ItemUtils.makeFillItem(fillMaterial));
    }

    public FillComponent(ItemStack fillItem) {
        this(0, 0, 9, -1, fillItem);
    }

    public FillComponent(int x, int y, int width, int height, Material fillMaterial) {
        this(x, y, width, height, ItemUtils.makeFillItem(fillMaterial));
    }

    public FillComponent(int x, int y, int width, int height, ItemStack fillItem) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setFillItem(fillItem);
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

    public void setFillItem(ItemStack fillItem) {
        this.fillItem = fillItem;
    }
}
