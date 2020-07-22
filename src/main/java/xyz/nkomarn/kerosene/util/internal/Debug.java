package xyz.nkomarn.kerosene.util.internal;

import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.function.Supplier;

public final class Debug {

    public static final String PREFIX = ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Debug: " + ChatColor.GRAY;
    public static final String HEADER = "&7>>>>> &5&lDebug: &e%s";
    public static final String FOOTER = "&7<<<<<";

    private static final Set<String> CATEGORIES = new HashSet<>(ImmutableList.of("all", "none"));
    private static final HashMap<String, Set<CommandSender>> LISTENERS = new HashMap<>();

    private Debug() {
    }

    /**
     * Register a new category.
     * Note: this name is also used for command completion.
     *
     * @param category The category to add.
     */
    public static void registerCategory(String category) {
        CATEGORIES.add(category.toLowerCase());
    }

    /**
     * Unregister a category.
     * @param category The category to unregister.
     */
    public static void unregisterCategory(String category) {
        CATEGORIES.remove(category.toLowerCase());
        LISTENERS.remove(category.toLowerCase());
    }

    public static void enableDebug(String category, CommandSender sender) {
        if(category.equalsIgnoreCase("all")) {
            enableAll(sender);
            return;
        } else if(category.equalsIgnoreCase("none")) {
            disableAll(sender);
            return;
        }

        Set<CommandSender> optionListeners = LISTENERS.getOrDefault(category.toLowerCase(), new HashSet<>());
        optionListeners.add(sender);
        LISTENERS.put(category.toLowerCase(), optionListeners);
    }

    public static void enableAll(CommandSender sender) {
        for (String option : CATEGORIES) {
            if (!option.equalsIgnoreCase("all") && !option.equalsIgnoreCase("none"))
                enableDebug(option, sender);
        }
    }

    public static void disableDebug(String category, CommandSender sender) {
        if (category.equalsIgnoreCase("all")) {
            disableAll(sender);
            return;
        } else if (category.equalsIgnoreCase("none")) {
            enableAll(sender);
            return;
        }

        Set<CommandSender> optionListeners = LISTENERS.getOrDefault(category.toLowerCase(), new HashSet<>());
        optionListeners.remove(sender);

        if (optionListeners.isEmpty()) {
            LISTENERS.remove(category.toLowerCase());
        } else {
            LISTENERS.put(category.toLowerCase(), optionListeners);
        }
    }

    public static void disableAll(CommandSender sender) {
        for (String option : CATEGORIES) {
            if (!option.equalsIgnoreCase("all") && !option.equalsIgnoreCase("none"))
                disableDebug(option, sender);
        }
    }

    /**
     * Send a message.
     * Note: the message is only send to {@link CommandSender}s that are listening on the category.
     *
     * @param category The category to send the message to.
     * @param messageSupplier The message supplier.
     */
    public static void send(String category, Supplier<String> messageSupplier) {
        if (!LISTENERS.containsKey(category.toLowerCase())) {
            return;
        }

        Set<CommandSender> listeners = LISTENERS.get(category.toLowerCase());
        if(listeners.isEmpty()) {
            return;
        }

        final String finalMessage = ChatColor.translateAlternateColorCodes('&', String.format("%s(&e%s&7) %s", PREFIX, category.toLowerCase(),  messageSupplier.get()));
        listeners.forEach(sender -> {
            sender.sendMessage(finalMessage);
        });
    }

    /**
     * Send a message to a specific CommandSender.
     * Note: the message is only send if the {@link CommandSender}s is listening on the category.
     *
     * @param category The category to send the message to.
     * @param sender The sender to send the message to.
     * @param messageSupplier The message supplier.
     */
    public static void send(String category, CommandSender sender, Supplier<String> messageSupplier) {
        if (!LISTENERS.containsKey(category.toLowerCase())) {
            return;
        }

        if (!LISTENERS.get(category.toLowerCase()).contains(sender)) {
            return;
        }

        final String finalMessage = ChatColor.translateAlternateColorCodes('&', String.format("%s(&e%s&7) %s", PREFIX, category.toLowerCase(),  messageSupplier.get()));
        sender.sendMessage(finalMessage);
    }

    /**
     * Send a message.
     * Note: the message is only send to {@link CommandSender}s that are listening on the category.
     *
     * @param category The category to send the message to.
     * @param linesSupplier The message supplier.
     */
    public static void sendLines(String category, Supplier<List<String>> linesSupplier) {
        if (!LISTENERS.containsKey(category.toLowerCase())) {
            return;
        }

        Set<CommandSender> listeners = LISTENERS.get(category.toLowerCase());
        if(listeners.isEmpty()) {
            return;
        }

        List<String> full = new ArrayList<>();
        full.add(String.format(HEADER, category.toLowerCase()));
        full.addAll(linesSupplier.get());
        full.add(FOOTER);

        final String finalMessage = ChatColor.translateAlternateColorCodes('&', String.join("\n", full));
        listeners.forEach(sender -> {
            sender.sendMessage(finalMessage);
        });
    }

    /**
     * Send a message to a specific CommandSender.
     * Note: the message is only send if the {@link CommandSender} is listening on the category.
     *
     * @param category The category to send the message to.
     * @param sender The sender to send the message to.
     * @param linesSupplier The message supplier.
     */
    public static void sendLines(String category, CommandSender sender, Supplier<List<String>> linesSupplier) {
        if (!LISTENERS.containsKey(category.toLowerCase())) {
            return;
        }

        if (!LISTENERS.get(category.toLowerCase()).contains(sender)) {
            return;
        }

        List<String> full = new ArrayList<>();
        full.add(String.format(HEADER, category.toLowerCase()));
        full.addAll(linesSupplier.get());
        full.add(FOOTER);
        String finalMessage = ChatColor.translateAlternateColorCodes('&', String.join("\n", full));
        sender.sendMessage(finalMessage);
    }

    /**
     * Get all categories.
     * @return All cateogries.
     */
    public static Set<String> getCategories() {
        return CATEGORIES;
    }

    /**
     * Check if a value if a valid category
     * @param value The value to check.
     * @return True if the value is a valid category.
     */
    public static boolean isCategory(String value) {
        return CATEGORIES.contains(value);
    }

    public static Optional<Boolean> toggleDebug(String category, CommandSender sender) {
        if (!isCategory(category)) {
            return Optional.empty();
        }

        if (!LISTENERS.containsKey(category)) {
            enableDebug(category, sender);
            return Optional.of(true);
        }

        if (LISTENERS.get(category).contains(sender)) {
            disableDebug(category, sender);
            return Optional.of(false);
        } else {
            enableDebug(category, sender);
            return Optional.of(true);
        }
    }
}
