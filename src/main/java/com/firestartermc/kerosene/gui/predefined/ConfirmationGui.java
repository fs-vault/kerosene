package com.firestartermc.kerosene.gui.predefined;

import com.firestartermc.kerosene.util.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.firestartermc.kerosene.gui.Gui;
import com.firestartermc.kerosene.gui.GuiDefaults;
import com.firestartermc.kerosene.gui.base.Interactable;
import com.firestartermc.kerosene.gui.components.buttons.ButtonComponent;
import com.firestartermc.kerosene.gui.components.cosmetic.FillComponent;
import com.firestartermc.kerosene.gui.components.item.ItemComponent;
import com.firestartermc.kerosene.item.ItemBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
        this(parent, MessageUtils.splitString(details, 25), confirmCallback, cancelCallback);
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
        this(parent, null, detailLines, confirmCallback, cancelCallback);
    }

    private ConfirmationGui(Gui parent, ItemStack showcaseItem, List<String> detailLines, Interactable confirmCallback, Interactable cancelCallback) {
        super(parent, "Please Confirm", showcaseItem == null ? 3 : 5);

        addElement(new FillComponent(Material.GRAY_STAINED_GLASS_PANE));

        int offset = 0;
        if (showcaseItem != null) {
            offset = 2;
            addElement(new ItemComponent(4, 1, showcaseItem));
        }

        addElement(new ItemComponent(4, 1 + offset, getDetailsItem(detailLines)));
        addElement(new ButtonComponent(2, 1 + offset, getConfirmItem(), confirmCallback));
        addElement(new ButtonComponent(6, 1 + offset, getCancelItem(), cancelCallback));
    }

    /**
     * Gets the details item.
     * @param detailsLines The details dialog lines.
     * @return Details item.
     */
    protected ItemStack getDetailsItem(List<String> detailsLines) {
        return new ItemBuilder(Material.BOOK)
                .name(detailsLines.get(0))
                .addLore(detailsLines.subList(1, detailsLines.size()))
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

    /**
     * Get a builder for creating a {@link ConfirmationGui}.
     *
     * @return A Builder for creating a {@link ConfirmationGui}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for creating a {@link ConfirmationGui}.
     */
    public static final class Builder {

        private ItemStack itemStack;
        private List<String> detailLines;
        private Interactable confirmCallback;
        private Interactable cancelCallback;
        private Gui parent;

        private Builder() {
            this.cancelCallback = event -> { // default
                GuiDefaults.playSelectSound(event.getPlayer());
                event.getGui().close(); // TODO Check if required (I this has to be here to make sure that the gui closes for all viewers)
                event.getGui().navigateToParent();
            };
        }

        public Builder parent(Gui parent) {
            this.parent = parent;
            return this;
        }

        public Builder item(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        public Builder details(String details) {
            this.detailLines = MessageUtils.splitString(details, 25).stream()
                    .map(l -> "&f" + l)
                    .collect(Collectors.toList());
            return this;
        }

        public Builder details(String... detailLines) {
            this.detailLines = Arrays.asList(detailLines);
            return this;
        }

        public Builder details(List<String> detailLines) {
            this.detailLines = detailLines;
            return this;
        }

        public Builder confirm(Interactable event) {
            confirmCallback = event;
            return this;
        }

        public Builder cancel(Interactable event) {
            cancelCallback = event;
            return this;
        }

        public ConfirmationGui build() {
            return new ConfirmationGui(parent, itemStack, detailLines, confirmCallback, cancelCallback);
        }

        public void open(Player... players) {
            build().open(players);
        }

        public void open(Set<Player> players) {
            build().open(players);
        }

    }

}
