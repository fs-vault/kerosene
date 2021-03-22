package com.firestartermc.kerosene.gui.paged;

import com.firestartermc.kerosene.gui.Gui;
import com.firestartermc.kerosene.gui.components.buttons.ButtonComponent;
import com.firestartermc.kerosene.gui.components.cosmetic.FillComponent;
import com.firestartermc.kerosene.gui.components.item.ItemComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import com.firestartermc.kerosene.gui.GuiDefaults;

import java.util.List;

/**
 * A base implementation for pages item Gui's
 */
public abstract class PagedItemGuiBase extends Gui {

    private final int itemsPerPage;
    private int page;

    /**
     * Create a new paged Gui.
     * Created a default with 5 rows.
     *
     * @param title The title of the gui.
     * @param page The page to open.
     */
    public PagedItemGuiBase(String title, int page) {
        this(null, title, 5, page);
    }

    /**
     * Create a new paged Gui.
     *
     * @param title The title of the gui.
     * @param rows The amount of rows
     * @param page The page to open.
     */
    public PagedItemGuiBase(String title, int rows, int page) {
        this(null, title, rows, page);
    }

    /**
     * Create a new paged Gui.
     * Created a default with 5 rows.
     *
     * @param parent The parent of this gui.
     * @param title The title of the gui.
     * @param page The page to open.
     */
    public PagedItemGuiBase(Gui parent, String title, int page) {
        this(parent, title, 5, page);
    }

    /**
     * Create a new paged Gui.
     *
     * @param parent The parent of this gui.
     * @param title The title of the gui.
     * @param rows The amount of rows
     * @param page The page to open.
     */
    public PagedItemGuiBase(Gui parent, String title, int rows, int page) {
        super(parent, String.format("%s (Page %s)", title, page), rows);
        this.page = page;
        if (rows < 2) {
            throw new IllegalArgumentException("A paged gui should contains at least 2 rows!");
        }

        itemsPerPage = (rows - 1) * 9;

        addElement(new FillComponent(0, rows - 1, 9, 1, Material.WHITE_STAINED_GLASS_PANE));

        addElement(new ButtonComponent(3, rows - 1, getPreviousItem(), this::handlePrevious));
        addElement(new ButtonComponent(5, rows - 1, getNextItem(), this::handleNext));

        getPageContent(page, this.itemsPerPage).forEach(this::addElement);
    }

    private void handlePrevious(InteractEvent event) {
        if (this.page <= 1) {
            return;
        }

        Gui newPage = getPageGui(this.page - 1);
        newPage.open(getViewers());
    }

    private void handleNext(InteractEvent event) {
        if (this.page >= getMaxPage(this.itemsPerPage)) {
            return;
        }

        Gui newPage = getPageGui(this.page + 1);
        newPage.open(getViewers());
    }

    /**
     * Gets the ItemComponents to add to a specific page.
     * @param page The page to get the components for
     * @param itemsPerPage The amount of items that fit on one page.
     * @return List containing the components to add to the gui.
     */
    public abstract List<ItemComponent> getPageContent(int page, int itemsPerPage);

    /**
     * Get the maximum page
     * @param amountPerPage  The amount of item that fit on one page.
     * @return The maximum amount of Components on a page.
     */
    public abstract int getMaxPage(int amountPerPage);

    /**
     * Create the Gui for another page.
     * @param page The page to get the Gui for.
     * @return The gui with the other page.
     */
    public abstract Gui getPageGui(int page);

    /**
     * Gets the {@link ItemStack} for the previous button.
     * @return The previous button {@link ItemStack}
     */
    protected ItemStack getPreviousItem() {
        return GuiDefaults.PREVIOUS_ITEM;
    }

    /**
     * Gets the {@link ItemStack} for the next button.
     * @return The next button {@link ItemStack}
     */
    protected ItemStack getNextItem() {
        return GuiDefaults.NEXT_ITEM;
    }

}
