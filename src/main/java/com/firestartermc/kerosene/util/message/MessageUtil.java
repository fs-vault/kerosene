package com.firestartermc.kerosene.util.message;

import org.bukkit.Bukkit;;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import com.firestartermc.kerosene.util.internal.Reflection;

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class MessageUtil {

    private MessageUtil() {
    }

    /**
     * Convert an ItemStack to JSON for use in TextComponents.
     *
     * @param itemStack ItemStack the convert
     * @return json Serialized JSON.
     * @see <a href="https://www.spigotmc.org/threads/tut-item-tooltips-with-the-chatcomponent-api.65964/">Source.</a>
     */
    @NotNull
    public static String getJsonFromItemStack(ItemStack itemStack) {
        // ItemStack methods to get a net.minecraft.server.ItemStack object for serialization
        Class<?> craftItemStackClazz = Reflection.getOBCClass("inventory.CraftItemStack");
        Method asNMSCopyMethod = Reflection.getMethod(craftItemStackClazz, "asNMSCopy", ItemStack.class);

        // NMS Method to serialize a net.minecraft.server.ItemStack to a valid Json string
        Class<?> nmsItemStackClazz = Reflection.getNMSClass("ItemStack");
        Class<?> nbtTagCompoundClazz = Reflection.getNMSClass("NBTTagCompound");
        Method saveNmsItemStackMethod = Reflection.getMethod(nmsItemStackClazz, "save", nbtTagCompoundClazz);

        Object nmsNbtTagCompoundObj; // This will just be an empty NBTTagCompound instance to invoke the saveNms method
        Object nmsItemStackObj; // This is the net.minecraft.server.ItemStack object received from the asNMSCopy method
        Object itemAsJsonObject; // This is the net.minecraft.server.ItemStack after being put through saveNmsItem method

        try {
            nmsNbtTagCompoundObj = nbtTagCompoundClazz.newInstance();
            nmsItemStackObj = asNMSCopyMethod.invoke(null, itemStack);
            itemAsJsonObject = saveNmsItemStackMethod.invoke(nmsItemStackObj, nmsNbtTagCompoundObj);
        } catch (Throwable t) {
            Bukkit.getLogger().log(Level.WARNING, "failed to serialize itemstack to nms item", t);
            return "null";
        }

        // Return a string representation of the serialized object
        return itemAsJsonObject.toString();
    }

    /**
     * Splits a given String at a given length, effectively
     * splitting it into multiple, separate lines.
     *
     * @param text     Text to split.
     * @param lineSize Maximum line size.
     * @return Array of the split parts.
     */
    public static List<String> splitString(String text, int lineSize) {
        var matcher = Pattern.compile("\\b.{1," + (lineSize - 1) + "}\\b\\W?").matcher(text);
        return matcher.results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }
}
