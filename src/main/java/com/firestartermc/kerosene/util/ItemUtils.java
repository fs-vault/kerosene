package com.firestartermc.kerosene.util;

import com.firestartermc.kerosene.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Miscellaneous {@link ItemStack} and {@link Material} utility methods.
 * <p>
 * This class provides common methods for ItemStack manipulation. It
 * also provides easy-to-use, thread-safe utilities that can be safely
 * used in the {@link BukkitScheduler} or executors.
 *
 * @author Firestarter Minecraft Servers
 * @see ItemStack
 * @since 4.0
 */
@ThreadSafe
public final class ItemUtils {

    private ItemUtils() {
    }

    /**
     * Returns the vanilla name for a given {@code material}. By default, the Bukkit
     * API does not contain methods to get the vanilla name of a block or item type.
     * Using this returns the name of the provided material in the current locale.
     * <p>
     * For example, inputting a material of {@link Material#GRINDSTONE} will return
     * a friendly name of "Grindstone." This is useful because Bukkit material names
     * often do not match the vanilla names. Additionally, this method will return
     * the item type name with the correct capitalization.
     *
     * @param material the material to get the vanilla name of
     * @since 5.0
     */
    @NotNull
    public static String getFriendlyName(@NotNull Material material) {
        return CraftItemStack.asNMSCopy(new ItemStack(material)).getName().getString();
    }

    /**
     * Returns a generic Gui fill item with a given {@code material}. This will create
     * an {@link ItemStack} with a blank name and all {@link ItemFlag}s applied.
     *
     * @param material the material to use for the fill item
     * @since 4.0
     */
    @NotNull
    public static ItemStack createFillItem(@NotNull Material material) {
        return ItemBuilder.of(material)
                .name(" ")
                .addAllItemFlags()
                .build();
    }
}
