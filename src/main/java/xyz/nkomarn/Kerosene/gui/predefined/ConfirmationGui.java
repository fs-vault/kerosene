package xyz.nkomarn.Kerosene.gui.predefined;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.Kerosene.gui.Gui;
import xyz.nkomarn.Kerosene.gui.base.Interactable;
import xyz.nkomarn.Kerosene.gui.components.buttons.ButtonComponent;
import xyz.nkomarn.Kerosene.gui.components.cosmetic.FillComponent;
import xyz.nkomarn.Kerosene.gui.components.item.ItemComponent;
import xyz.nkomarn.Kerosene.util.MessageUtil;
import xyz.nkomarn.Kerosene.util.item.ItemBuilder;

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
        this(parent, MessageUtil.splitString(details, 25), confirmCallback, cancelCallback);
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

        addElement(new ItemComponent(4, 1, buildDetailsItem(detailLines)));
        addElement(new ButtonComponent(2, 1, getConfirmItem(), confirmCallback));
        addElement(new ButtonComponent(6, 1, getCancelItem(), cancelCallback));
    }

    private ItemStack buildDetailsItem(List<String> detailsLines) {
        return new ItemBuilder(Material.BOOK)
                .name(detailsLines.get(0))
                .addLore(detailsLines.subList(1, detailsLines.size())
                        .stream()
                        .map(l -> "&f" + l)
                        .collect(Collectors.toList())
                )
                .build();
    }

    // region static helpers

    private static ItemStack confirmItem, cancelItem;

    private static ItemStack getConfirmItem() {
        if(confirmItem == null) {
            confirmItem = new ItemBuilder(Material.LIME_BANNER)
                    .name("&a&lConfirm")
                    .build();
        }

        return confirmItem;
    }

    private static ItemStack getCancelItem() {
        if (cancelItem == null) {
            cancelItem = new ItemBuilder(Material.RED_BANNER)
                    .name("&c&lCancel")
                    .build();
        }

        return cancelItem;
    }

    // endregion static helpers
}
