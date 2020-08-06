package xyz.nkomarn.kerosene.gui.components.buttons;

import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.gui.GuiDefaults;
import xyz.nkomarn.kerosene.gui.GuiPosition;
import xyz.nkomarn.kerosene.gui.components.buttons.base.ButtonBase;
import xyz.nkomarn.kerosene.util.item.ItemBuilder;

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
        this(x, y, (String) null);
    }

    /**
     * Create a back button.
     * @param x The horizontal position.
     * @param y The vertical position.
     * @param lore The lore.
     */
    public BackButtonComponent(int x, int y, String... lore) {
        this(new GuiPosition(x, y), lore);
    }

    /**
     * Create a back button.
     * @param position The position of the button.
     */
    public BackButtonComponent(GuiPosition position) {
        this(position, (String) null);
    }

    /**
     * Create a back button.
     * @param position The position of the button.
     * @param lore The lore.
     */
    public BackButtonComponent(GuiPosition position, String... lore) {
        this(position, (ItemStack) null);
        this.setItem(getBackItem(lore));
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

    protected ItemStack getBackItem(String[] lore) {
        if (lore != null) {
            return ItemBuilder.of(GuiDefaults.BACK_ITEM)
                    .addLore(lore)
                    .build();
        }
        return GuiDefaults.BACK_ITEM;
    }
}
