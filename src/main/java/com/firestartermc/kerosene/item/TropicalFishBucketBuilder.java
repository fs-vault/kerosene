package com.firestartermc.kerosene.item;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

/**
 * Utility class intended to allow fast building of TropicalFishBucket.
 */
public class TropicalFishBucketBuilder extends ItemBuilderBase<TropicalFishBucketBuilder> {

    /**
     * Single TropicalFishBucket
     */
    public TropicalFishBucketBuilder() {
        this(1);
    }

    /**
     * Multiple TropicalFishBucket
     */
    public TropicalFishBucketBuilder(int amount) {
        super(Material.TROPICAL_FISH_BUCKET, amount);
    }

    /**
     * Set the color of the body of the fish.
     * @param color The color to use
     * @return This instance
     */
    public TropicalFishBucketBuilder bodyColor(DyeColor color) {
        TropicalFishBucketMeta meta = (TropicalFishBucketMeta) this.item.getItemMeta();
        meta.setBodyColor(color);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the color of the pattern of the fish.
     * @param color The color to use
     * @return This instance
     */
    public TropicalFishBucketBuilder patternColor(DyeColor color) {
        TropicalFishBucketMeta meta = (TropicalFishBucketMeta) this.item.getItemMeta();
        meta.setPatternColor(color);
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Set the pattern of the fish.
     * @param pattern The pattern to use
     * @return This instance
     */
    public TropicalFishBucketBuilder pattern(TropicalFish.Pattern pattern) {
        TropicalFishBucketMeta meta = (TropicalFishBucketMeta) this.item.getItemMeta();
        meta.setPattern(pattern);
        this.item.setItemMeta(meta);
        return this;
    }
}
