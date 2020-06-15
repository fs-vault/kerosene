package xyz.nkomarn.Kerosene.util;

import me.ryanhamshire.GriefPrevention.DataStore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Utility class for managing and checking for
 * GriefPrevention claims.
 */
public class ClaimUtil {
    private static final DataStore DATA = GriefPrevention.instance.dataStore;

    /**
     * Returns the GriefPrevention data store, used for viewing claims.
     * @return An instance of the GriefPrevention data store.
     */
    public static DataStore getDataStore() {
        return DATA;
    }

    /**
     * Checks whether a player has access to the current claim in a location.
     * @param player The player for which to check access.
     * @param location A location in the world which may have claims.
     * @return Whether the player has access to any of the claims in the location.
     */
    public static boolean doesLocationHaveForeignClaims(OfflinePlayer player, Location location) {
        Collection<Claim> claims = DATA.getClaims(location.getChunk().getX(), location.getChunk().getZ());

        if (claims.size() > 0) {
            for (Claim claim : claims) {
                if (!claim.ownerID.equals(player.getUniqueId())) {
                    if (player.isOnline()) {
                        if (claim.allowBuild((Player) player, Material.AIR) != null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
