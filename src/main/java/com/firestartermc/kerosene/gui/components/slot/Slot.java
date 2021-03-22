package com.firestartermc.kerosene.gui.components.slot;

import com.firestartermc.kerosene.gui.GuiDefaults;
import com.firestartermc.kerosene.gui.GuiPosition;
import com.firestartermc.kerosene.gui.base.Drawable;
import com.firestartermc.kerosene.gui.base.DrawingContext;
import com.firestartermc.kerosene.gui.base.Interactable;
import com.firestartermc.kerosene.gui.base.OnGuiClose;
import com.firestartermc.kerosene.item.ItemBuilder;
import com.firestartermc.kerosene.util.PlayerUtils;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Slot implements Drawable, Interactable, OnGuiClose {

    private static final ItemStack EMPTY = ItemBuilder.of(Material.AIR).build();

    private final GuiPosition position;
    private ItemStack itemStack;
    private boolean returnOnClose;

    public Slot(GuiPosition position) {
        this.position = position;
        this.returnOnClose = false;
    }

    @Override
    public Map<GuiPosition, ItemStack> draw(DrawingContext context) {
        var itemStack = hasItemStack() ? getItemStack() : EMPTY;
        return ImmutableMap.of(position, itemStack);
    }

    @Override
    public void onInteract(InteractEvent event) {
        if (!event.getPosition().equals(position)) return;

        var player = event.getPlayer();
        var cursorItem = player.getItemOnCursor();
        var cursorHasItem = cursorItem.getType() != Material.AIR;

        if (!hasItemStack() && !cursorHasItem) return;

        if (!(canAccept(cursorItem) || cursorItem.getType() == Material.AIR)) {
            onCannotAccept(player, cursorItem);
            return;
        }

        if (hasItemStack() && cursorHasItem) {
            player.setItemOnCursor(itemStack);
            itemStack = cursorItem;
        } else if (!hasItemStack() && cursorHasItem) {
            itemStack = cursorItem;
            player.setItemOnCursor(EMPTY);
        } else {
            player.setItemOnCursor(itemStack);
            itemStack = null;
        }

        event.getGui().update();
    }

    public boolean hasItemStack() {
        return itemStack != null;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public boolean isReturnedOnClose() {
        return returnOnClose;
    }

    public void setReturnOnClose(boolean returnOnClose) {
        this.returnOnClose = returnOnClose;
    }

    public boolean canAccept(ItemStack stack) {
        return true;
    }

    public void onCannotAccept(Player player, ItemStack stack) {
        GuiDefaults.playErrorSound(player);
    }

    @Override
    public void onClose(Player player) {
        if (returnOnClose && hasItemStack()) {
            PlayerUtils.giveOrDropItem(player, itemStack);
        }
    }
}
