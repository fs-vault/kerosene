package xyz.nkomarn.Kerosene;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nkomarn.Kerosene.commands.KeroseneCommand;
import xyz.nkomarn.Kerosene.data.LocalStorage;
import xyz.nkomarn.Kerosene.data.PlayerData;
import xyz.nkomarn.Kerosene.data.Redis;
import xyz.nkomarn.Kerosene.gui.Gui;
import xyz.nkomarn.Kerosene.gui.GuiListener;
import xyz.nkomarn.Kerosene.gui.GuiPosition;
import xyz.nkomarn.Kerosene.gui.components.buttons.ToggleButtonComponent;
import xyz.nkomarn.Kerosene.gui.components.cosmetic.BorderComponent;
import xyz.nkomarn.Kerosene.listener.PlayerQuitListener;
import xyz.nkomarn.Kerosene.menu.MenuHandler;
import xyz.nkomarn.Kerosene.util.DebugUtil;
import xyz.nkomarn.Kerosene.util.EconomyUtil;

import java.util.ArrayList;
import java.util.List;

public class Kerosene extends JavaPlugin {

    public static final String DEBUG_CATEGORY_GUI_INTERACT = "gui:interact";

    private static Kerosene kerosene;
    private static Essentials essentials;

    @Override
    public void onEnable() {
        kerosene = this;
        essentials = (Essentials) Bukkit.getPluginManager().getPlugin("essentials");

        if (!PlayerData.connect(getConfig().getString("database.url"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password"))) {
            getLogger().severe("Failed to connect to database.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (!Redis.connect(getConfig().getString("redis.host"),
                getConfig().getInt("redis.port"),
                getConfig().getString("redis.password"),
                getConfig().getInt("redis.pool-size"),
                getConfig().getInt("redis.timeout"))) {
            getLogger().severe("Failed to connect to Redis.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (!LocalStorage.initialize()) {
            getLogger().severe("Failed to initialize local storage.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            LocalStorage.createTables();
        }

        if (!EconomyUtil.initializeEconomy()) {
            getLogger().severe("Failed to initialize the economy.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        DebugUtil.registerCategory(DEBUG_CATEGORY_GUI_INTERACT);

        Bukkit.getPluginManager().registerEvents(new MenuHandler(), this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);

        getCommand("kerosene").setExecutor(new KeroseneCommand());
        getCommand("test").setExecutor(this); // TODO remove
    }

    @Override
    public void onDisable() {
        MenuHandler.closeAll();
        PlayerData.close();
        Redis.close();
    }

    private static boolean state = true; // TODO remove
    private static boolean state2 = true; // TODO remove

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { // TODO remove
        Gui gui = new Gui("Test", 4);
        gui.addElement(new BorderComponent());

        List<String> details = new ArrayList<>();
        details.add("&7Makes every armor stand");
        details.add("&7you place have arms.");

        List<String> details2 = new ArrayList<>();
        details2.add("&7Enabled your PvP and allows");
        details2.add("&7you to fight with other players.");

        gui.addElement(new ToggleButtonComponent(new GuiPosition(1, 1), "Toggle: Armor Stand Arms", details, Material.ARMOR_STAND, Material.ARMOR_STAND, () -> state, event -> {
            state = event.getNewState();
        }));

        gui.addElement(new ToggleButtonComponent(new GuiPosition(2, 1), "PvP", details2, Material.IRON_AXE, Material.SHIELD, () -> state2, event -> {
            state2 = event.getNewState();
        }));

        gui.open((Player) sender);

        return super.onCommand(sender, command, label, args);
    }

    /**
     * Fetches an instance of the Kerosene plugin.
     * @return Kerosene plugin instance.
     */
    public static Kerosene getKerosene() {
        return kerosene;
    }

    /**
     * Fetches an instance of the Essentials plugin.
     * @return Essentials plugin instance.
     */
    public static Essentials getEssentials() {
        return essentials;
    }
}
