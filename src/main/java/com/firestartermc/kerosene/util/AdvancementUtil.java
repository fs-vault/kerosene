package com.firestartermc.kerosene.util;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

/**
 * Utility class for dealing with the Minecraft advancements system and custom advancements in datapacks.
 */
public final class AdvancementUtil {

    private AdvancementUtil() {
    }

    public static boolean isComplete(Player player, String name) {
        return isComplete(player, "firestarter", name);
    }

    public static boolean isComplete(Player player, String namespace, String name) {
        var key = new NamespacedKey(namespace, name);
        var advancement = Bukkit.getAdvancement(key);

        if (advancement == null) {
            return false;
        }

        return player.getAdvancementProgress(advancement).isDone();
    }

    public static void grant(Player player, String name) {
        grant(player, "firestarter", name);
    }

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
        progress.getRemainingCriteria().forEach(progress::awardCriteria);
    }
}
