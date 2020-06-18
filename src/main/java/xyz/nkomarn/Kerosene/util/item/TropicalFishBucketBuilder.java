package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

public class TropicalFishBucketBuilder extends ItemBuilderBase<TropicalFishBucketBuilder> {
    public TropicalFishBucketBuilder() {
        this(1);
    }

    public TropicalFishBucketBuilder(int amount) {
        super(Material.TROPICAL_FISH_BUCKET, amount);
    }

    public TropicalFishBucketBuilder bodyColor(DyeColor color) {
        TropicalFishBucketMeta meta = (TropicalFishBucketMeta) this.item.getItemMeta();
        meta.setBodyColor(color);
        this.item.setItemMeta(meta);
        return this;
    }

    public TropicalFishBucketBuilder patternColor(DyeColor color) {
        TropicalFishBucketMeta meta = (TropicalFishBucketMeta) this.item.getItemMeta();
        meta.setPatternColor(color);
        this.item.setItemMeta(meta);
        return this;
    }

    public TropicalFishBucketBuilder pattern(TropicalFish.Pattern pattern) {
        TropicalFishBucketMeta meta = (TropicalFishBucketMeta) this.item.getItemMeta();
        meta.setPattern(pattern);
        this.item.setItemMeta(meta);
        return this;
    }
}
