package com.firestartermc.kerosene.gui;

import com.google.common.collect.ImmutableList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.gui.base.Interactable;
import com.firestartermc.kerosene.util.internal.Debug;

public class GuiListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Gui)) {
            return;
        }

        Gui gui = (Gui) holder;
        Player player = (Player) event.getWhoClicked();

        int slot = event.getRawSlot();
        GuiPosition position = new GuiPosition(slot % 9, slot / 9);

        Debug.sendLines(Kerosene.DEBUG_CATEGORY_GUI_INTERACT, event.getWhoClicked(), () -> ImmutableList.of(
                "&7Gui: '&e" + gui.getTitle() + "'",
                String.format("&7Slot: &e%s &7Position: &e(%s, %s) &7Type: &e%s", slot, position.getX(), position.getY(), event.getSlotType().name()),
                "&7Click: &e" + event.getClick().name(),
                "&7Action: &e" + event.getAction().name()
        ));

        Interactable.InteractEvent interactEvent = new Interactable.InteractEvent(gui, position, player, event.getClick(), event.getAction(), event.getSlotType());
        gui.onInteract(interactEvent);
        event.setCancelled(interactEvent.isCanceled());
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Gui)) {
            return;
        }

        Gui gui = (Gui) holder;
        gui.removeViewer((Player) event.getPlayer());
        gui.onClose((Player) event.getPlayer());
    }

}
