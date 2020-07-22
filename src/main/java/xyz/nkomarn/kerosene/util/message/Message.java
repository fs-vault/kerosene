package xyz.nkomarn.kerosene.util.message;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.nkomarn.kerosene.util.internal.Reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    public Message() {
    }

    public static void sendActionbar(@NotNull Player player, @NotNull BaseComponent[] message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
    }

    public static void broadcastActionbar(@NotNull BaseComponent[] message) {
        Bukkit.getOnlinePlayers().forEach(player -> sendActionbar(player, message));
    }

    public static void sendChat(@NotNull Player player, @NotNull BaseComponent[] message) {
        player.spigot().sendMessage(message);
    }

    public static void broadcastChat(@NotNull BaseComponent[] message) {
        Bukkit.getOnlinePlayers().forEach(player -> sendChat(player, message));
    }

    /**
     * Convert an ItemStack to JSON for use in TextComponents.
     *
     * @param itemStack ItemStack the convert
     * @return json Serialized JSON.
     * @see <a href="https://www.spigotmc.org/threads/tut-item-tooltips-with-the-chatcomponent-api.65964/">Source.</a>
     */
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
     * Split text on at specific size.
     *
     * @param text     Text to split.
     * @param lineSize Size to split by.
     * @return Array of the split parts.
     */
    public static List<String> splitString(String text, int lineSize) {
        List<String> res = new ArrayList<>();

        Pattern p = Pattern.compile("\\b.{1," + (lineSize - 1) + "}\\b\\W?");
        Matcher m = p.matcher(text);

        while (m.find()) {
            System.out.println(m.group().trim());   // Debug
            res.add(m.group());
        }
        return res;
    }
}
