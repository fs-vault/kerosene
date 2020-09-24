package xyz.nkomarn.kerosene.gui.components.buttons;

import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.gui.GuiDefaults;
import xyz.nkomarn.kerosene.gui.GuiPosition;
import xyz.nkomarn.kerosene.gui.components.buttons.base.ButtonBase;

/**
 * Default implementation of the back button.
 */
public class BackButtonComponent extends ButtonBase {

    /**
     * Create a back button.
     * @param x The horizontal position.
     * @param y The vertical position.
     */
    public BackButtonComponent(int x, int y) {
        this(new GuiPosition(x, y));
    }

    /**
     * Create a back button.
     * @param position The position of the button.
     */
    public BackButtonComponent(GuiPosition position) {
        this(position, null);
        this.setItem(getBackItem());
    }

    /**
     * Create a back button with a specific {@link ItemStack}.
     * @param x The horizontal position.
     * @param y The vertical position.
     * @param item The {@link ItemStack}
     */
    public BackButtonComponent(int x, int y, ItemStack item) {
        this(new GuiPosition(x, y), item);
    }

    /**
     * Create a back button with a specific {@link ItemStack}.
     * @param position The position of the button.
     * @param item The {@link ItemStack}
     */
    public BackButtonComponent(GuiPosition position, ItemStack item) {
        super(position, item);
    }

    @Override
    public void onInteract(InteractEvent event) {
        if (event.getPosition().equals(this.getPosition())) {
            GuiDefaults.playSelectSound(event.getPlayer());
            event.getGui().navigateToParent();
        }
    }

    protected ItemStack getBackItem() {
        return GuiDefaults.BACK_ITEM;
    }
}
