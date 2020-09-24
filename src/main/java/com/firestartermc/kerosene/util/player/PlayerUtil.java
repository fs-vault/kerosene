package com.firestartermc.kerosene.util.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import com.firestartermc.kerosene.Kerosene;

public final class PlayerUtil {

    private PlayerUtil() {
    }

    /**
     * Attempt to give an item to a player, or drop it at the players feet.
     *
     * @param player The player to attempt to give the item.
     * @param itemStack The item to give.
     */
    public static void giveOrDropItem(Player player, ItemStack itemStack) { // TODO check if main thread before making a task to avoid unecessary scheduler tasks
        Bukkit.getScheduler().runTask(Kerosene.getKerosene(), task -> {
            player.getInventory().addItem(itemStack).forEach(((integer, overflowStack) -> {
                player.getWorld().dropItemNaturally(player.getLocation(), overflowStack);
            }));
        });
    }

    /**
     * Adds velocity to a player, effectively making them get knocked back from their current location.
     * @param player The player to apply the velocity vector to.
     * @param multiplier Velocity multiplier.
     * @param y Additional y-axis velocity.
     */
    public static void knockback(@NotNull Player player, int multiplier, int y) {
        Vector locationVector = player.getLocation().toVector();
        Vector vector = locationVector.normalize().multiply(multiplier).setY(y);

        if (player.isInsideVehicle()) {
            player.getVehicle().setVelocity(vector);
        } else {
            player.setVelocity(vector);
        }
    }

}
