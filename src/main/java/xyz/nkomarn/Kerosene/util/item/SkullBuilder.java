package xyz.nkomarn.Kerosene.util.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Utility class intended to allow fast building of Skulls.
 */
public class SkullBuilder extends ItemBuilderBase<SkullBuilder> {

    /**
     * Single Player Skull
     */
    public SkullBuilder() {
        this(1);
    }

    /**
     * Multiple Player Skulls
     */
    public SkullBuilder(int amount) {
        super(Material.PLAYER_HEAD, amount);
    }

    /**
     * Change the skull to the skull of a specific player.
     * @param player The player to set the skill for
     * @return This instance
     */
    public SkullBuilder player(OfflinePlayer player) {
        SkullMeta meta = (SkullMeta) this.item.getItemMeta();
        meta.setOwningPlayer(player);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set a custom texture on the skull.
     * @param texture Base64 encoded texture data
     * @return This instance
     */
    public SkullBuilder texture(String texture) {
        SkullMeta meta = (SkullMeta) this.item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));

        try {
            Field field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        this.item.setItemMeta(meta);
        return this;
    }
}
