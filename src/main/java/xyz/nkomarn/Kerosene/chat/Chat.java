package xyz.nkomarn.Kerosene.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.nkomarn.Kerosene.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {
    private static final Pattern HEX_PATTERN = Pattern.compile("#([A-Fa-f0-9]){6}");

    public Chat() {
    }

    /**
     * Sends a chat message to the specified player with translated color and hex codes.
     *
     * @param player  The player to send the message to.
     * @param message The message to translate and send.
     */
    public static void send(Player player, String message) {
        player.spigot().sendMessage(TextComponent.fromLegacyText(parseColors(message)));
    }

    /**
     * Sends a chat message to all online players with translated color and hex codes.
     *
     * @param message The message to translate and send.
     */
    public static void sendAll(String message) {
        BaseComponent[] messageComponent = TextComponent.fromLegacyText(parseColors(message));
        Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(messageComponent));
    }

    /**
     * Translates the hex and color codes in a message.
     *
     * @param message The message to translate.
     * @return A color-replaced message.
     * @see <a href="https://github.com/Esophose/PlayerParticles/blob/6844d147ddf0175a4d848370e4d389c4497e5594/src/main/java/dev/esophose/playerparticles/manager/LocaleManager.java#L257">Source.</a>
     */
    private static String parseColors(String message) {
        String parsed = message;
        Matcher matcher = HEX_PATTERN.matcher(parsed);
        while (matcher.find()) {
            ChatColor hexColor = ChatColor.of(matcher.group());
            String before = parsed.substring(0, matcher.start());
            String after = parsed.substring(matcher.end());
            parsed = before + hexColor + after;
            matcher = HEX_PATTERN.matcher(parsed);
        }
        return ChatColor.translateAlternateColorCodes('&', parsed);
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
