package com.firestartermc.kerosene.util;

import com.firestartermc.kerosene.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.annotation.concurrent.ThreadSafe;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

    @NotNull
    public static String serializeItemStack(@NotNull ItemStack itemStack) throws IOException {
        var outputStream = new ByteArrayOutputStream();
        var dataStream = new BukkitObjectOutputStream(outputStream);
        dataStream.writeObject(itemStack);
        dataStream.close();
        return Base64Coder.encodeLines(outputStream.toByteArray());
    }

    @NotNull
    public static String serializeItemStacks(@NotNull ItemStack[] itemStacks) throws IOException {
        var outputStream = new ByteArrayOutputStream();
        var dataStream = new BukkitObjectOutputStream(outputStream);
        for (var item : itemStacks) {
            dataStream.writeObject(item);
        }
        dataStream.close();
        return Base64Coder.encodeLines(outputStream.toByteArray());
    }

    @NotNull
    public static ItemStack deserializeItemStack(@NotNull String serialized) throws IOException, ClassNotFoundException {
        var inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(serialized));
        var dataStream = new BukkitObjectInputStream(inputStream);
        return (ItemStack) dataStream.readObject();
    }

    @NotNull
    public static ItemStack[] deserializeItemStacks(@NotNull String serialized) throws IOException, ClassNotFoundException {
        var inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(serialized));
        var dataStream = new BukkitObjectInputStream(inputStream);
        var items = new ItemStack[dataStream.readInt()];
        for (var i = 0; i < items.length; i++) {
            items[i] = (ItemStack) dataStream.readObject();
        }
        dataStream.close();
        return items;
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
