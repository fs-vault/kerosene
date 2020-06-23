package xyz.nkomarn.Kerosene.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.nkomarn.Kerosene.util.MessageUtil;
import xyz.nkomarn.Kerosene.util.item.ItemBuilder;

import java.util.List;

public class ConfirmationMenu extends Menu {

    public ConfirmationMenu(Player player, String question, MenuButton.GuiButtonCallback yes, MenuButton.GuiButtonCallback no) {
        this(player, MessageUtil.splitString(question, 25), yes, no);
    }

    public ConfirmationMenu(Player player, List<String> lines, MenuButton.GuiButtonCallback yes, MenuButton.GuiButtonCallback no) {
        super(player, "Please Confirm", 27);

        fill(Material.GRAY_STAINED_GLASS_PANE);

        ItemBuilder builder = new ItemBuilder(Material.BOOK)
                .name("&f" + lines.get(0));
        for (int i = 1; i < lines.size(); i++) builder.addLore("&f" + lines.get(i));
        getInventory().setItem(13, builder.build());

        addButton(new MenuButton(this, new ItemBuilder(Material.LIME_BANNER)
                .name("&a&lConfirm")
                .build(), 11, yes));

        addButton(new MenuButton(this, new ItemBuilder(Material.RED_BANNER)
                .name("&c&lCancel")
                .build(), 15, no));

        open();
    }

}
