package xyz.nkomarn.kerosene.gui.predefined;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.util.message.Message;
import xyz.nkomarn.kerosene.gui.Gui;
import xyz.nkomarn.kerosene.gui.GuiDefaults;
import xyz.nkomarn.kerosene.gui.base.Interactable;
import xyz.nkomarn.kerosene.gui.components.buttons.ButtonComponent;
import xyz.nkomarn.kerosene.gui.components.cosmetic.FillComponent;
import xyz.nkomarn.kerosene.gui.components.item.ItemComponent;
import xyz.nkomarn.kerosene.util.item.ItemBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A pre-defined confirmation menu.
 */
public class ConfirmationGui extends Gui {

    private ConfirmationGui(Gui parent, ItemStack item, List<String> detailLines, Interactable confirmCallback, Interactable cancelCallback) {
        super(parent, "Please Confirm", item == null ? 3 : 5);

        addElement(new FillComponent(Material.GRAY_STAINED_GLASS_PANE));

        int offset = 0;
        if (item != null) {
            offset = 2;
            addElement(new ItemComponent(4, 1, item));
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private ItemStack itemStack;
        private List<String> detailLines;
        private Interactable confirmCallback;
        private Interactable cancelCallback;
        private Gui parent;

        private Builder() {
            this.cancelCallback = event -> { // default
                GuiDefaults.playSelectSound(event.getPlayer());
                event.getGui().navigateToParent();
                event.getGui().close();
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
            this.detailLines = Message.splitString(details, 25).stream()
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
