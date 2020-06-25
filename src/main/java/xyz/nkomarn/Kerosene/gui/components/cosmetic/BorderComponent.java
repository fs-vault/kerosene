package xyz.nkomarn.Kerosene.gui.components.cosmetic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.Kerosene.gui.GuiPosition;
import xyz.nkomarn.Kerosene.gui.components.cosmetic.base.BorderBase;
import xyz.nkomarn.Kerosene.util.item.ItemUtils;

/**
 * A solid border
 */
public class BorderComponent extends BorderBase {

    private ItemStack fillItem1;

    public BorderComponent(Material fillMaterial1) {
        this(0, 0, 9, -1, fillMaterial1);
    }

    public BorderComponent(int x, int y, int width, int height, Material fillMaterial1) {
        this(x, y, width, height, ItemUtils.makeFillItem(fillMaterial1));
    }

    public BorderComponent(ItemStack fillItem1) {
        this(0, 0, 9, -1, fillItem1);
    }

    public BorderComponent(int x, int y, int width, int height, ItemStack fillItem1) {
        super(x, y, width, height);
        setFillItem1(fillItem1);
    }

    @Override
    public ItemStack getItemForPosition(GuiPosition position) {
        return this.fillItem1;
    }

    public ItemStack getFillItem1() {
        return fillItem1;
    }

    public void setFillItem1(ItemStack fillItem1) {
        this.fillItem1 = fillItem1;
    }

}
