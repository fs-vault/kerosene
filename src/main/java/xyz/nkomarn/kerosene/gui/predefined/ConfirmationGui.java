package xyz.nkomarn.kerosene.gui.predefined;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.util.message.Message;
import xyz.nkomarn.kerosene.gui.Gui;
import xyz.nkomarn.kerosene.gui.GuiDefaults;
import xyz.nkomarn.kerosene.gui.base.Interactable;
import xyz.nkomarn.kerosene.gui.components.buttons.ButtonComponent;
import xyz.nkomarn.kerosene.gui.components.cosmetic.FillComponent;
import xyz.nkomarn.kerosene.gui.components.item.ItemComponent;
import xyz.nkomarn.kerosene.util.item.ItemBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A pre-defined confirmation menu.
 */
public class ConfirmationGui extends Gui {

    /**
     * Create a new {@link ConfirmationGui}
     * @param details The details to how on the information item.
     * @param confirmCallback The callback on confirmation.
     * @param cancelCallback The callback on canceled.
     */
    public ConfirmationGui(String details, Interactable confirmCallback, Interactable cancelCallback) {
        this(null, details, confirmCallback, cancelCallback);
    }

    /**
     * Create a new {@link ConfirmationGui}
     * @param parent The parent Gui
     * @param details The details to how on the information item.
     * @param confirmCallback The callback on confirmation.
     * @param cancelCallback The callback on canceled.
     */
    public ConfirmationGui(Gui parent, String details, Interactable confirmCallback, Interactable cancelCallback) {
        this(parent, Message.splitString(details, 25), confirmCallback, cancelCallback);
    }
    /**
     * Create a new {@link ConfirmationGui}
     * @param detailLines The details to how on the information item.
     * @param confirmCallback The callback on confirmation.
     * @param cancelCallback The callback on canceled.
     */
    public ConfirmationGui(List<String> detailLines, Interactable confirmCallback, Interactable cancelCallback) {
        this(null, detailLines, confirmCallback, cancelCallback);
    }

    /**
     * Create a new {@link ConfirmationGui}
     * @param parent The parent Gui
     * @param detailLines The details to how on the information item.
     * @param confirmCallback The callback on confirmation.
     * @param cancelCallback The callback on canceled.
     */
    public ConfirmationGui(Gui parent, List<String> detailLines, Interactable confirmCallback, Interactable cancelCallback) {
        super(parent, "Please Confirm", 3);

        addElement(new FillComponent(Material.GRAY_STAINED_GLASS_PANE));

        addElement(new ItemComponent(4, 1, getDetailsItem(detailLines)));
        addElement(new ButtonComponent(2, 1, getConfirmItem(), confirmCallback));
        addElement(new ButtonComponent(6, 1, getCancelItem(), cancelCallback));
    }

    /**
     * Gets the details item.
     * @param detailsLines The details dialog lines.
     * @return Details item.
     */
    protected ItemStack getDetailsItem(List<String> detailsLines) {
        return new ItemBuilder(Material.BOOK)
                .name(detailsLines.get(0))
                .addLore(detailsLines.subList(1, detailsLines.size())
                        .stream()
                        .map(l -> "&f" + l)
                        .collect(Collectors.toList())
                )
                .build();
    }

    /**
     * Gets the confirm item.
     * @return The confirm item.
     */
    protected ItemStack getConfirmItem() {
        return GuiDefaults.CONFIRM_ITEM;
    }

    /**
     * Get the cancel item.
     * @return The cancel item.
     */
    protected ItemStack getCancelItem() {
        return GuiDefaults.CANCEL_ITEM;
    }

}
