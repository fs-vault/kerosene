package com.firestartermc.kerosene.util;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

/**
 * Utility class for dealing with the Minecraft advancements system and custom advancements in datapacks.
 */
public final class Advancement {

    private Advancement() {
    }

    /**
     * Checks whether a player has completed a specific advancement
     *
     * @param player          The player to check advancement status for
     * @param advancementName The advancement name in the "firestarter" namespace
     * @return The status of advancement completion as a boolean
     */
    private static boolean isAdvancementComplete(Player player, String advancementName) {
        NamespacedKey advancementKey = new NamespacedKey("firestarter", advancementName);
        org.bukkit.advancement.Advancement advancement = Bukkit.getAdvancement(advancementKey);
        if (advancement == null) {
            return true;
        }

        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        return progress.isDone();
    }

    /**
     * Grants a custom advancement to a player if they haven't achieved it yet
     *
     * @param player          The player to grant the advancement to
     * @param advancementName The advancement name in the "firestarter" namespace
     */
    public static void grantAdvancement(Player player, String advancementName) {
        if (!isAdvancementComplete(player, advancementName)) {
            NamespacedKey advancementKey = new NamespacedKey("firestarter", advancementName);
            AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(advancementKey));
            progress.getRemainingCriteria().forEach(progress::awardCriteria);
        }
    }
}
