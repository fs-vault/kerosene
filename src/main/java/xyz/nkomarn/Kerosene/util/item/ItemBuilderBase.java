package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class ItemBuilderBase<T extends  ItemBuilderBase<T>> {
    public final ItemStack item;

    public ItemBuilderBase(Material material) {
        this(material, 1);
    }

    public ItemBuilderBase(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public ItemBuilderBase(ItemStack item) {
        this.item = item;
    }

    public T material(Material material) {
        this.item.setType(material);
        return (T) this;
    }

    public T amount(int amount) {
        this.item.setAmount(amount);
        return (T) this;
    }

    public T name(String displayName) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        this.item.setItemMeta(meta);
        return (T) this;
    }

    public T lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    public T lore(List<String> lore) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(translateLore(lore));
        this.item.setItemMeta(meta);
        return (T) this;
    }

    public T damage(int damage) {
        Damageable meta = (Damageable) this.item.getItemMeta();
        meta.setDamage(damage);
        this.item.setItemMeta((ItemMeta) meta);
        return (T) this;
    }

    public T enchant(Enchantment enchantment, int level) {
        this.item.addEnchantment(enchantment, level);
        return (T) this;
    }

    public T enchantUnsafe(Enchantment enchantment, int level) {
        this.item.addUnsafeEnchantment(enchantment, level);
        return (T) this;
    }

    public T addItemFlags(ItemFlag... flags) {
        this.item.addItemFlags(flags);
        return (T) this;
    }

    public <Y, Z> T persistData(NamespacedKey key, PersistentDataType<Y, Z> type, Z value) {
        ItemMeta meta = this.item.getItemMeta();
        meta.getPersistentDataContainer().set(key, type, value);
        this.item.setItemMeta(meta);
        return (T) this;
    }

    public T attributeModifier(Attribute attribute, String name, Double level, AttributeModifier.Operation operation) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addAttributeModifier(attribute, new AttributeModifier(name, level, operation));
        this.item.setItemMeta(meta);
        return (T) this;
    }

    public T attributeModifier(Attribute attribute, UUID uuid, String name, Double level, AttributeModifier.Operation operation) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addAttributeModifier(attribute, new AttributeModifier(uuid, name, level, operation));
        this.item.setItemMeta(meta);
        return (T) this;
    }

    public T modelData(Integer integer) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setCustomModelData(integer);
        this.item.setItemMeta(meta);
        return (T) this;
    }

    public T unbreakable() {
        ItemMeta meta = this.item.getItemMeta();
        meta.setUnbreakable(true);
        this.item.setItemMeta(meta);
        return (T) this;
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
