package xyz.nkomarn.kerosene.gui.components.cosmetic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.gui.GuiDefaults;
import xyz.nkomarn.kerosene.gui.GuiPosition;
import xyz.nkomarn.kerosene.gui.components.cosmetic.base.BorderBase;
import xyz.nkomarn.kerosene.util.item.ItemUtils;

/**
 * A solid border
 */
public class BorderComponent extends BorderBase {

    private ItemStack fillItem;

    /**
     * Create a border on the entire {@link xyz.nkomarn.kerosene.gui.Gui} with the {@link GuiDefaults#FILL_ITEM}
     */
    public BorderComponent() {
        this(0, 0, 9, -1, GuiDefaults.FILL_ITEM);
    }

    /**
     * Create a border on the entire {@link xyz.nkomarn.kerosene.gui.Gui} with a fill item of a specific {@link Material}
     * @param fillMaterial Material to create.
     */
    public BorderComponent(Material fillMaterial) {
        this(0, 0, 9, -1, fillMaterial);
    }

    /**
     * Create a border on the entire {@link xyz.nkomarn.kerosene.gui.Gui} with a fill item of a specific {@link ItemStack}
     * @param fillItem the fill item to use.
     */
    public BorderComponent(ItemStack fillItem) {
        this(0, 0, 9, -1, fillItem);
    }

    /**
     * Create a border around a specific region of the {@link xyz.nkomarn.kerosene.gui.Gui} with {@link GuiDefaults#FILL_ITEM}
     */
    public BorderComponent(int x, int y, int width, int height) {
        this(x, y, width, height, GuiDefaults.FILL_ITEM);
    }

    /**
     * Create a border around a specific region of the {@link xyz.nkomarn.kerosene.gui.Gui} with a specific {@link Material}
     */
    public BorderComponent(int x, int y, int width, int height, Material fillMaterial) {
        this(x, y, width, height, ItemUtils.makeFillItem(fillMaterial));
    }

    /**
     * Create a border around a specific region of the {@link xyz.nkomarn.kerosene.gui.Gui} with a specific {@link ItemStack}
     * @param x
     * @param y
     * @param width
     * @param height
     * @param fillItem
     */
    public BorderComponent(int x, int y, int width, int height, ItemStack fillItem) {
        super(x, y, width, height);
        setFillItem(fillItem);
    }

    @Override
    public ItemStack getItemForPosition(GuiPosition position) {
        return this.fillItem;
    }

    public ItemStack getFillItem() {
        return fillItem;
    }

    /**
     * Change the fill item.
     *
     * Note: If the {@link xyz.nkomarn.kerosene.gui.Gui} is already displayed to any players, {@link xyz.nkomarn.kerosene.gui.Gui#update()} should be called to apply the changes.
     *
     * @param fillItem The new fill item.
     */
    public void setFillItem(ItemStack fillItem) {
        this.fillItem = fillItem;
    }

}
