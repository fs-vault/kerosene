package xyz.nkomarn.Kerosene.gui.base;

import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.Kerosene.gui.Gui;
import xyz.nkomarn.Kerosene.gui.GuiPosition;

import java.util.Map;

/**
 * A Drawable is an {@link GuiElement} that can draw (display items) in the inventory.
 */
public interface Drawable extends GuiElement {

    /**
     * Gets all the icons and there position on the gui.
     * @param gui The {@link Gui} the icons are drawn for.
     * @return A mapping between position on the {@link Gui} and the {@link ItemStack}
     */
    Map<GuiPosition, ItemStack> draw(Gui gui);
}
