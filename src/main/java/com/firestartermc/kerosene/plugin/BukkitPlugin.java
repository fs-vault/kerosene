package com.firestartermc.kerosene.plugin;

import com.firestartermc.kerosene.Kerosene;
import com.firestartermc.kerosene.command.BrigadierExecutor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BukkitPlugin extends JavaPlugin {

    private static final Kerosene KEROSENE = JavaPlugin.getPlugin(Kerosene.class);

    @NotNull
    public Kerosene getKerosene() {
        return KEROSENE;
    }

    public void registerCommand(@NotNull String name, @NotNull CommandExecutor executor) {
        var command = getCommand(name);

        if (command == null) {
            getLogger().warning("Command by name '" + name + "' is not registered.");
            return;
        }

        command.setExecutor(executor);

        if (executor instanceof BrigadierExecutor brigadier) {
            var commodore = getKerosene().getCommodore();
            brigadier.getCompletions().forEach(commodore::register);
        }
    }
}
