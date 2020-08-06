package xyz.nkomarn.kerosene.util.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.kerosene.Kerosene;

public final class PlayerUtil {

    private PlayerUtil() {
    }

    /**
     * Attempt to give an item to a player, or drop it at the players feet.
     *
     * @param player The player to attempt to give the item.
     * @param itemStack The item to give.
     */
    public static void giveOrDropItem(Player player, ItemStack itemStack) {
        Bukkit.getScheduler().runTask(Kerosene.getKerosene(), task -> {
            player.getInventory().addItem(itemStack).forEach(((integer, overflowStack) -> {
                player.getWorld().dropItemNaturally(player.getLocation(), overflowStack);
            }));
        });
    }

}
