package xyz.nkomarn.Kerosene.util;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Claim;

import java.util.Collection;

/**
 * Utility class for managing and checking for
 * GriefPrevention claims.
 */
public class ClaimUtil {
    private static final GriefPrevention INSTANCE = GriefPrevention.instance;

    /**
     * Checks whether a player has access to the current claim in a location.
     * @param player The player for which to check access.
     * @param location A location in the world which may have claims.
     * @return Whether the player has access to any of the claims in the location.
     */
    public static boolean doesLocationHaveForeignClaims(OfflinePlayer player, Location location) {
        Collection<Claim> claims = INSTANCE.dataStore.getClaims(location.getChunk().getX(), location.getChunk().getZ());

        if (claims.size() > 0) {
            for (Claim claim : claims) {
                if (!claim.getOwnerName().equals(player.getName())) {
                    if (!claim.managers.contains(player.getUniqueId().toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
