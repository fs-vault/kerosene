package xyz.nkomarn.Kerosene.gui.components.buttons.base;

import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.Kerosene.gui.base.Interactable;
import xyz.nkomarn.Kerosene.gui.GuiPosition;
import xyz.nkomarn.Kerosene.gui.components.item.ItemComponent;

/**
 * Button base
 */
public abstract class ButtonBase extends ItemComponent implements Interactable {

    public ButtonBase(int x, int y, ItemStack item) {
        super(x, y, item);
    }

    public ButtonBase(GuiPosition position, ItemStack item) {
        super(position, item);
    }

}
