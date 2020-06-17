package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class intended to allow fast building of ItemStacks.
 */
public class ItemBuilder {
    public final ItemStack item;

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public static ItemBuilder of(ItemStack item) {
        return new ItemBuilder(item);
    }

    public ItemBuilder material(Material material) {
        this.item.setType(material);
        return this;
    }

    public ItemBuilder name(String displayName) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(translateLore(lore));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder color(int r, int g, int b) {
        return color(Color.fromRGB(r, g, b));
    }

    public ItemBuilder color(Color color) {
        ItemMeta meta = this.item.getItemMeta();

        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) meta).setColor(color);
        } else if (meta instanceof PotionMeta) {
            ((PotionMeta) meta).setColor(color);
        }

        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder damage(int damage) {
        Damageable meta = (Damageable) this.item.getItemMeta();
        meta.setDamage(damage);
        this.item.setItemMeta((ItemMeta) meta);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.item.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder enchantUnsafe(Enchantment enchantment, int level) {
        this.item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStack build() {
        return this.item;
    }

    private List<String> translateLore(List<String> lore) {
        return lore.stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList());
    }
}
