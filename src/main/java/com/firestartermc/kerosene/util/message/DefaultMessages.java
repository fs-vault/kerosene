package com.firestartermc.kerosene.util.message;

import org.bukkit.ChatColor;

public class DefaultMessages {

    private DefaultMessages() {
    }

    public static final String ERROR_PREFIX = ChatColor.translateAlternateColorCodes('&', "&c&lError: &7");
    public static final String NO_PERMISSION = ERROR_PREFIX + "Insufficient permissions";
    public static final String FAILED_TO_LOAD_DATA = ChatColor.RED + "Failed to read user data. Notify an admin.";
    public static final String NON_PLAYER = ChatColor.RED + "This function can only be used by a player.";
}
