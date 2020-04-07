package xyz.nkomarn.Kerosene.util;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

/**
 * Utility class for dealing with the Minecraft advancements
 * system and custom advancements in datapacks.
 */
public class AdvancementUtil {
    /**
     * Checks whether a player has completed a specific advancement
     * @param player The player to check advancement status for
     * @param advancementName The advancement name in the "firestarter" namespace
     * @return The status of advancement completion as a boolean
     */
    private static boolean isAdvancementComplete(final Player player, final String advancementName) {
        final NamespacedKey advancementKey = new NamespacedKey("firestarter", advancementName);
        final Advancement advancement = Bukkit.getAdvancement(advancementKey);
        if (advancement == null) return true;
        final AdvancementProgress progress = player.getAdvancementProgress(advancement);
        return progress.isDone();
    }

    /**
     * Grants a custom advancement to a player if they haven't achieved it yet
     * @param player The player to grant the advancement to
     * @param advancementName The advancement name in the "firestarter" namespace
     */
    public static void grantAdvancement(final Player player, final String advancementName) {
        if (isAdvancementComplete(player, advancementName)) return;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(
                "advancement grant %s only firestarter:%s", player.getName(), advancementName));
    }
}
