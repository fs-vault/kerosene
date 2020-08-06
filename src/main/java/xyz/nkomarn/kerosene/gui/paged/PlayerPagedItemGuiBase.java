package xyz.nkomarn.kerosene.gui.paged;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.gui.Gui;
import xyz.nkomarn.kerosene.gui.GuiDefaults;
import xyz.nkomarn.kerosene.gui.GuiPosition;
import xyz.nkomarn.kerosene.gui.PlayerGui;
import xyz.nkomarn.kerosene.gui.base.DrawingContext;
import xyz.nkomarn.kerosene.gui.base.Interactable;
import xyz.nkomarn.kerosene.gui.components.buttons.ButtonComponent;
import xyz.nkomarn.kerosene.gui.components.cosmetic.FillComponent;
import xyz.nkomarn.kerosene.gui.components.item.ItemComponent;

import java.util.List;
import java.util.Map;

/**
 * A base implementation for pages item PlayerGui's
 */
public abstract class PlayerPagedItemGuiBase extends PlayerGui {

    private final int itemsPerPage;
    private int page;

    private List<ItemComponent> pageContent;

    /**
     * Create a new paged Gui.
     * Created a default with 5 rows.
     *
     * @param title The title of the gui.
     * @param page The page to open.
     */
    public PlayerPagedItemGuiBase(Player player, String title, int page) {
        this(player, null, title, 5, page);
    }

    /**
     * Create a new paged Gui.
     *
     * @param title The title of the gui.
     * @param rows The amount of rows
     * @param page The page to open.
     */
    public PlayerPagedItemGuiBase(Player player, String title, int rows, int page) {
        this(player, null, title, rows, page);
    }

    /**
     * Create a new paged Gui.
     * Created a default with 5 rows.
     *
     * @param parent The parent of this gui.
     * @param title The title of the gui.
     * @param page The page to open.
     */
    public PlayerPagedItemGuiBase(Player player, Gui parent, String title, int page) {
        this(player, parent, title, 5, page);
    }

    /**
     * Create a new paged Gui.
     *
     * @param parent The parent of this gui.
     * @param title The title of the gui.
     * @param rows The amount of rows
     * @param page The page to open.
     */
    public PlayerPagedItemGuiBase(Player player, Gui parent, String title, int rows, int page) {
        super(player, parent, String.format("%s (Page %s)", title, page), rows);
        this.page = page;
        if (rows < 2) {
            throw new IllegalArgumentException("A paged gui should contains at least 2 rows!");
        }

        itemsPerPage = (rows - 1) * 9;

        addElement(new FillComponent(0, rows - 1, 9, 1, Material.WHITE_STAINED_GLASS_PANE));

        addElement(new ButtonComponent(3, rows - 1, getPreviousItem(), this::handlePrevious));
        addElement(new ButtonComponent(5, rows - 1, getNextItem(), this::handleNext));

        this.pageContent = getPageContent(page, this.itemsPerPage);
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

    public int getPage() {
        return page;
    }

    @Override
    public void update() {
        update(false);
    }

    public void update(boolean items) {
        if (items) {
            this.pageContent = getPageContent(page, this.itemsPerPage);
            System.out.println(this.pageContent.size()); // TODO remove debug line
        }
        super.update();
    }

    @Override
    protected void render(Player player) {
        super.render(player);

        this.pageContent.forEach(component -> {
            DrawingContext context = new DrawingContext(this, player);
            Map<GuiPosition, ItemStack> items = component.draw(context);
            if (items == null) return;

            items.forEach((vector2i, stack) -> {
                getInventory().setItem(vector2i.getX() + 9 * vector2i.getY(), stack);
            });
        });
    }

    @Override
    public void onInteract(InteractEvent event) {
        super.onInteract(event);

        this.pageContent.forEach(component -> {
            if (component instanceof Interactable) {
                ((Interactable) component).onInteract(event);
            }
        });
    }
}
