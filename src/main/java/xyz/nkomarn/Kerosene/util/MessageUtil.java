package xyz.nkomarn.Kerosene.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MessageUtil {

    /**
     * Private constructor preventing the instantiation of this static class.
     */
    private MessageUtil() {
    }

    /**
     * Split text on a specific size.
     * @param text Text to split.
     * @param lineSize Size to split by.
     * @return Array of the split parts.
     */
    public static List<String> splitString(String text, int lineSize) {
        List<String> res = new ArrayList<>();

        Pattern p = Pattern.compile("\\b.{1," + (lineSize-1) + "}\\b\\W?");
        Matcher m = p.matcher(text);

        while(m.find()) {
            res.add(m.group());
        }
        return res;
    }

    /**
     * Convert an ItemStack to send via BungeeCord TextComponent
     * @param itemStack ItemStack the convert
     * @return json
     *
     * source: https://www.spigotmc.org/threads/tut-item-tooltips-with-the-chatcomponent-api.65964/
     */
    public static String convertItemStackToJson(ItemStack itemStack) {
        // ItemStack methods to get a net.minecraft.server.ItemStack object for serialization
        Class<?> craftItemStackClazz = ReflectionUtil.getOBCClass("inventory.CraftItemStack");
        Method asNMSCopyMethod = ReflectionUtil.getMethod(craftItemStackClazz, "asNMSCopy", ItemStack.class);

        // NMS Method to serialize a net.minecraft.server.ItemStack to a valid Json string
        Class<?> nmsItemStackClazz = ReflectionUtil.getNMSClass("ItemStack");
        Class<?> nbtTagCompoundClazz = ReflectionUtil.getNMSClass("NBTTagCompound");
        Method saveNmsItemStackMethod = ReflectionUtil.getMethod(nmsItemStackClazz, "save", nbtTagCompoundClazz);

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
}
