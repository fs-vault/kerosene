package com.firestartermc.kerosene.util;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Miscellaneous advancement utility methods.
 * <p>
 * This class provides methods for interacting with blocks. It also
 * provides easy-to-use, thread-safe utilities for safety checking
 * block locations, intended to be used for teleportation checks.
 * <p>
 * This class provides methods for interacting with advancements. It
 * also provides easy-to-use, thread-safe utilities for checking
 * advancement completion and granting advancements to specific players.
 * This makes it easy to interact with Minecraft advancements, along
 * with custom advancements registered via datapacks.
 *
 * @author Firestarter Minecraft Servers
 * @since 3.0
 */
@ThreadSafe
public final class AdvancementUtils {

    private AdvancementUtils() {
    }

    /**
     * Returns whether a given advancement key in the "firestarter" namespace has
     * been completed by the given {@code player}. This method is useful for
     * checking advancement completion of Firestarter's custom, datapack-provided
     * advancements.
     *
     * @param player the player for which to check completion
     * @param name   the advancement name key
     * @return status of advancement completion
     * @since 3.0
     */
    public static boolean isComplete(@NotNull Player player, @NotNull String name) {
        return isComplete(player, "firestarter", name);
    }

    /**
     * Returns whether a given advancement key in the given {@code namespace} has
     * been completed by the given {@code player}. This method is useful for
     * checking completion of any registered advancement.
     *
     * @param player    the player for which to check completion
     * @param namespace the advancement namespace
     * @param name      the advancement name key
     * @return status of advancement completion
     * @since 3.0
     */
    public static boolean isComplete(Player player, String namespace, String name) {
        var key = new NamespacedKey(namespace, name);
        var advancement = Bukkit.getAdvancement(key);

        if (advancement == null) {
            return false;
        }

        return player.getAdvancementProgress(advancement).isDone();
    }

    /**
     * Awards all required criteria for a given advancement key in the "firestarter"
     * namespace, which effectively grants an advancement to the given {@code player}.
     * This method is useful for granting one of Firestarter's custom, datapack-provided
     * advancements.
     *
     * @param player the player to grant advancement criteria
     * @param name   the advancement name key
     * @since 3.0
     */
    public static void grant(Player player, String name) {
        grant(player, "firestarter", name);
    }

    /**
     * Awards all required criteria for a given advancement key in the "firestarter"
     * namespace, which effectively grants an advancement to the given {@code player}.
     * This method is useful for granting any currently registered advancement.
     *
     * @param player    the player to grant advancement criteria
     * @param namespace the advancement namespace
     * @param name      the advancement name key
     * @since 3.0
     */
    public static void grant(Player player, String namespace, String name) {
        if (isComplete(player, name)) {
            return;
        }

        var key = new NamespacedKey(namespace, name);
        var advancement = Bukkit.getAdvancement(key);

        if (advancement == null) {
            return;
        }

        var progress = player.getAdvancementProgress(advancement);
        ConcurrentUtils.ensureMain(() -> progress.getRemainingCriteria().forEach(progress::awardCriteria));
    }
}
