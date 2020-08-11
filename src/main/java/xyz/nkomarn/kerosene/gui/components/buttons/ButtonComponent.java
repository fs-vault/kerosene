package xyz.nkomarn.kerosene.gui.components.buttons;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.gui.GuiDefaults;
import xyz.nkomarn.kerosene.gui.base.Interactable;
import xyz.nkomarn.kerosene.gui.GuiPosition;
import xyz.nkomarn.kerosene.gui.components.buttons.base.ButtonBase;

/**
 * A button
 */
public class ButtonComponent extends ButtonBase {

    private final Interactable callback;

    /**
     * Create a new button.
     * @param x The x index.
     * @param y The y index.
     * @param item The item.
     * @param callback The interaction callback.
     */
    public ButtonComponent(int x, int y, ItemStack item, Interactable callback) {
        this(new GuiPosition(x, y), item, callback);
    }

    /**
     * Create a new button.
     * @param position The position.
     * @param item The item.
     * @param callback The ineraction callback.
     */
    public ButtonComponent(GuiPosition position, ItemStack item, Interactable callback) {
        super(position, item);
        this.callback = callback;
    }

    @Override
    public void onInteract(InteractEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.CONTAINER && event.getPosition().equals(this.getPosition())) {
            GuiDefaults.playSelectSound(event.getPlayer());
            this.callback.onInteract(event);
        }
    }
}
