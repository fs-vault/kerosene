package com.firestartermc.kerosene.gui.base;

import com.firestartermc.kerosene.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import com.firestartermc.kerosene.gui.GuiPosition;

/**
 * A Interactable is a {@link GuiElement} which can handle click events.
 */
@FunctionalInterface
public interface Interactable extends GuiElement {

    /**
     * Handle the interaction
     * @param event The event that contains details about the interaction.
     */
     void onInteract(InteractEvent event);

    /**
     * Interaction details
     */
    class InteractEvent {
         private final Gui gui;
         private final GuiPosition position;
         private final Player player;
         private final ClickType clickType;
         private final InventoryAction action;
         private final InventoryType.SlotType slotType;
         private boolean canceled;

         public InteractEvent(Gui gui, GuiPosition position, Player player, ClickType clickType, InventoryAction action, InventoryType.SlotType slotType) {
             this.gui = gui;
             this.position = position;
             this.player = player;
             this.clickType = clickType;
             this.action = action;
             this.slotType = slotType;
             this.canceled = true;
         }

         /**
          * Gets the gui the event was triggered on.
          * @return The gui the event was triggered on.
          */
         public Gui getGui() {
             return gui;
         }

         /**
          * Gets the position the interaction was enacted.
          * @return The {@link GuiPosition}
          */
         public GuiPosition getPosition() {
             return position;
         }

         /**
          * Gets the player who triggered the interaction.
          * @return The {@link Player}
          */
         public Player getPlayer() {
             return player;
         }

         /**
          * Gets the type of click of the interaction.
          * @return The {@link ClickType}
          */
         public ClickType getClickType() {
             return clickType;
         }

         /**
          * Gets the {@link InventoryAction} of the interaction.
          * @return The {link}
          */
         public InventoryAction getAction() {
             return action;
         }

         /**
          * Gets the type of the slot the interaction originated from.
          * @return The {@link org.bukkit.event.inventory.InventoryType.SlotType}
          */
         public InventoryType.SlotType getSlotType() {
             return slotType;
         }

         /**
          * Change of the interaction should canceled. (Default is true)
          * @param canceled True to cancel the interaction.
          */
         public void setCanceled(boolean canceled) {
             this.canceled = canceled;
         }

         /**
          * Check whether the interaction is canceled.
          * @return True if canceled.
          */
         public boolean isCanceled() {
             return canceled;
         }
     }

}
