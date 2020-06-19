package xyz.nkomarn.Kerosene.util.item;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class BannerBuilder extends ItemBuilderBase<BannerBuilder> {

    public BannerBuilder() {
        this(1);
    }

    public BannerBuilder(int amount) {
        this(Material.WHITE_BANNER, amount);
    }

    public BannerBuilder(Material material, int amount) {
        super(material, amount);
    }

    public BannerBuilder baseColor(DyeColor color) {
        switch (color) {
            case WHITE:
                this.item.setType(Material.WHITE_BANNER);
                break;
            case ORANGE:
                this.item.setType(Material.ORANGE_BANNER);
                break;
            case MAGENTA:
                this.item.setType(Material.MAGENTA_BANNER);
                break;
            case LIGHT_BLUE:
                this.item.setType(Material.LIGHT_BLUE_BANNER);
                break;
            case YELLOW:
                this.item.setType(Material.YELLOW_BANNER);
                break;
            case LIME:
                this.item.setType(Material.LIME_BANNER);
                break;
            case PINK:
                this.item.setType(Material.PINK_BANNER);
                break;
            case GRAY:
                this.item.setType(Material.GRAY_BANNER);
                break;
            case LIGHT_GRAY:
                this.item.setType(Material.LIGHT_GRAY_BANNER);
                break;
            case CYAN:
                this.item.setType(Material.CYAN_BANNER);
                break;
            case PURPLE:
                this.item.setType(Material.PURPLE_BANNER);
                break;
            case BLUE:
                this.item.setType(Material.BLUE_BANNER);
                break;
            case BROWN:
                this.item.setType(Material.BROWN_BANNER);
                break;
            case GREEN:
                this.item.setType(Material.GREEN_BANNER);
                break;
            case RED:
                this.item.setType(Material.RED_BANNER);
                break;
            case BLACK:
                this.item.setType(Material.BLACK_BANNER);
                break;
        }
        return this;
    }

    public BannerBuilder pattern(DyeColor color, PatternType pattern) {
        BannerMeta meta = (BannerMeta) this.item.getItemMeta();
        meta.addPattern(new Pattern(color, pattern));
        this.item.setItemMeta(meta);
        return this;
    }
}
