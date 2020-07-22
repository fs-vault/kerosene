package xyz.nkomarn.kerosene.util.item;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.meta.BannerMeta;

/**
 * Utility class intended to allow fast building of Banners.
 */
public class BannerBuilder extends ItemBuilderBase<BannerBuilder> {

    /**
     * Basic single white banner
     */
    public BannerBuilder() {
        this(1);
    }

    /**
     * Basic white banner
     * @param amount The amount of the stack
     */
    public BannerBuilder(int amount) {
        this(DyeColor.WHITE, amount);
    }

    /**
     * Customer colored banner and amount.
     * @param color The base color to use
     * @param amount The amount of the stack
     */
    public BannerBuilder(DyeColor color, int amount) {
        super(dyeColorToMaterial(color), amount);
    }

    /**
     * Change the base color of the banner.
     * @param color The new base color of the banner
     * @return This instance
     */
    public BannerBuilder baseColor(DyeColor color) {
        this.item.setType(dyeColorToMaterial(color));
        return this;
    }

    /**
     * Add a pattern to the banner.
     * @param color The color to use when drawing
     * @param pattern The pattern to use when drawing
     * @return This instance
     */
    public BannerBuilder pattern(DyeColor color, PatternType pattern) {
        BannerMeta meta = (BannerMeta) this.item.getItemMeta();
        meta.addPattern(new Pattern(color, pattern));
        this.item.setItemMeta(meta);
        return this;
    }

    private static Material dyeColorToMaterial(DyeColor color) {
        switch (color) {
            case WHITE:
                return Material.WHITE_BANNER;
            case ORANGE:
                return Material.ORANGE_BANNER;
            case MAGENTA:
                return Material.MAGENTA_BANNER;
            case LIGHT_BLUE:
                return Material.LIGHT_BLUE_BANNER;
            case YELLOW:
                return Material.YELLOW_BANNER;
            case LIME:
                return Material.LIME_BANNER;
            case PINK:
                return Material.PINK_BANNER;
            case GRAY:
                return Material.GRAY_BANNER;
            case LIGHT_GRAY:
                return Material.LIGHT_GRAY_BANNER;
            case CYAN:
                return Material.CYAN_BANNER;
            case PURPLE:
                return Material.PURPLE_BANNER;
            case BLUE:
                return Material.BLUE_BANNER;
            case BROWN:
                return Material.BROWN_BANNER;
            case GREEN:
                return Material.GREEN_BANNER;
            case RED:
                return Material.RED_BANNER;
            case BLACK:
                return Material.BLACK_BANNER;
        }
        return Material.WHITE_BANNER;
    }
}
