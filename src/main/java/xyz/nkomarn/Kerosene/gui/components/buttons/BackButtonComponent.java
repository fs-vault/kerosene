package xyz.nkomarn.Kerosene.gui.components.buttons;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.Kerosene.gui.GuiPosition;
import xyz.nkomarn.Kerosene.gui.components.buttons.base.ButtonBase;
import xyz.nkomarn.Kerosene.util.item.ItemBuilder;

/**
 * Default implementation of the back button.
 */
public class BackButtonComponent extends ButtonBase {

    public BackButtonComponent(int x, int y) {
        this(new GuiPosition(x, y));
    }

    public BackButtonComponent(GuiPosition position) {
        this(position, getBackItem());
    }

    public BackButtonComponent(int x, int y, ItemStack item) {
        this(new GuiPosition(x, y), item);
    }

    public BackButtonComponent(GuiPosition position, ItemStack item) {
        super(position, item);
    }

    @Override
    public void onInteract(InteractEvent event) {
        if (event.getPosition().equals(this.getPosition())) {
            event.getGui().navigateToParent();
        }
    }

    private static ItemStack backItem;
    private static ItemStack getBackItem() {
        if(backItem == null) {
            backItem = new ItemBuilder(Material.PAPER)
                    .name("&b&lBack")
                    .build();
        }
        return backItem;
    }
}
