package com.firestartermc.kerosene.util;

import net.md_5.bungee.api.ChatColor;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Standard action response messages and colors.
 * <p>
 * This class provides static messages that can be used as standard responses
 * for common actions or results. It also provides common strings for error
 * handling and user notifications, preventing duplicate or differing messages
 * throughout the codebase.
 *
 * @author Firestarter Minecraft Servers
 * @since 5.0
 */
@ThreadSafe
public class Constants {

    public static final ChatColor OFF_WHITE = ChatColor.of("#dedede");

    public static final String ERROR_PREFIX = MessageUtils.formatColors("&c&lERROR: ", false) + OFF_WHITE;
    public static final String NO_PERMISSION = ERROR_PREFIX + "Insufficient permissions";
    public static final String FAILED_TO_LOAD_DATA = "Failed to read user data. Notify an admin.";
    public static final String NON_PLAYER = "This function can only be used by a player.";
}
