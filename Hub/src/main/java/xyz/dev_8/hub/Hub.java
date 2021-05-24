package xyz.dev_8.hub;/*
Created by @8ML (https://github.com/8ML) on 5/8/2021
*/

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.permissions.ServerOperator;
import xyz.dev_8.core.Core;
import xyz.dev_8.core.config.ServerConfig;
import xyz.dev_8.core.storage.file.PluginFile;
import xyz.dev_8.hub.events.InteractionEvent;
import xyz.dev_8.hub.events.JoinEvent;
import xyz.dev_8.hub.scoreboard.Scoreboard;
import org.bukkit.plugin.java.JavaPlugin;

public class Hub extends JavaPlugin {

    public static Hub instance;

    private final String worldName = "Base";
    private final double spawnX = 0.554;
    private final double spawnY = 93.0;
    private final double spawnZ = -3.301;

    public PluginFile tabConf;


    private void registerFiles() {
        tabConf = new PluginFile(this, "tablist.yml", "tablist.yml");
        tabConf.save();
    }

    private void registerEvents() {
        new JoinEvent(this);
        new InteractionEvent(this);
    }

    @Override
    public void onEnable() {
        instance = this;

        Core.instance.mapExtractor.addMap(worldName);

        registerFiles();
        registerEvents();

        Core.instance.tabList.setTabList(tabConf.getString("header"), tabConf.getString("footer"));
        Scoreboard.init();

        ServerConfig.spawnPoint = new Location(Bukkit.getWorld(worldName), spawnX, spawnY, spawnZ);
    }

    @Override
    public void onDisable() {

    }
}
