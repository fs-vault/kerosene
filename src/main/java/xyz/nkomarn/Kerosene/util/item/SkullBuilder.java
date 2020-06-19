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

    public SkullBuilder() {
        super(Material.PLAYER_HEAD);
    }

    public SkullBuilder player(OfflinePlayer player) {
        SkullMeta meta = (SkullMeta) this.item.getItemMeta();
        meta.setOwningPlayer(player);
        this.item.setItemMeta(meta);
        return this;
    }

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
