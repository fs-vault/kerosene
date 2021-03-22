package com.firestartermc.kerosene.gui.components.buttons.base;

import org.bukkit.inventory.ItemStack;
import com.firestartermc.kerosene.gui.base.Interactable;
import com.firestartermc.kerosene.gui.GuiPosition;
import com.firestartermc.kerosene.gui.components.item.ItemComponent;

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
