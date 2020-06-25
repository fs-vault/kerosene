package xyz.nkomarn.Kerosene.gui.components.cosmetic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.Kerosene.gui.GuiPosition;
import xyz.nkomarn.Kerosene.gui.components.cosmetic.base.BorderBase;
import xyz.nkomarn.Kerosene.util.item.ItemUtils;

/**
 * A border with alternating items.
 */
public class BorderAlternatingComponent extends BorderBase {

    private ItemStack fillItem1;
    private ItemStack fillItem2;

    public BorderAlternatingComponent(Material fillMaterial1, Material fillMaterial2) {
        this(0, 0, 9, -1, fillMaterial1, fillMaterial2);
    }

    public BorderAlternatingComponent(int x, int y, int width, int height, Material fillMaterial1, Material fillMaterial2) {
        this(x, y, width, height, ItemUtils.makeFillItem(fillMaterial1), ItemUtils.makeFillItem(fillMaterial2));
    }

    public BorderAlternatingComponent(ItemStack fillItem1, ItemStack fillItem2) {
        this(0, 0, 9, -1, fillItem1, fillItem2);
    }

    public BorderAlternatingComponent(int x, int y, int width, int height, ItemStack fillItem1, ItemStack fillItem2) {
        super(x, y, width, height);
        setFillItem1(fillItem1);
        setFillItem2(fillItem2);
    }

    @Override
    public ItemStack getItemForPosition(GuiPosition position) {
        return (position.toSlot() % 2 == 0) ? this.fillItem1 : this.fillItem2;
    }

    public ItemStack getFillItem1() {
        return fillItem1;
    }

    public void setFillItem1(ItemStack fillItem1) {
        this.fillItem1 = fillItem1;
    }

    public ItemStack getFillItem2() {
        return fillItem2;
    }

    public void setFillItem2(ItemStack fillItem2) {
        this.fillItem2 = fillItem2;
    }
}
