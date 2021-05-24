package xyz.dev_8.core.module.hub;
/*
Created by @8ML (https://github.com/8ML) on 5/24/2021
*/

import org.bukkit.Bukkit;
import org.bukkit.Location;
import xyz.dev_8.core.Core;
import xyz.dev_8.core.config.ServerConfig;
import xyz.dev_8.core.module.Module;
import xyz.dev_8.core.module.hub.events.InteractionEvent;
import xyz.dev_8.core.module.hub.events.JoinEvent;
import xyz.dev_8.core.module.hub.scoreboard.Scoreboard;
import xyz.dev_8.core.storage.file.PluginFile;

public class HubModule extends Module {

    public static HubModule instance;

    private final String worldName = "Base";
    private final double spawnX = 0.554;
    private final double spawnY = 93.0;
    private final double spawnZ = -3.301;

    private PluginFile tabYML;

    public HubModule() {
        super("Hub");
    }

    private void registerCommands() {

    }

    private void registerEvents() {
        new JoinEvent(mainInstance);
        new InteractionEvent(mainInstance);
    }

    @Override
    protected void onEnable() {

        instance = this;

        Core.instance.mapExtractor.addMap(worldName);

        tabYML = new PluginFile(mainInstance, "tablist.yml", "tablist.yml");

        registerCommands();
        registerEvents();

        Core.instance.tabList.setTabList(tabYML.getString("header"), tabYML.getString("footer"));
        Scoreboard.init();

        ServerConfig.spawnPoint = new Location(Bukkit.getWorld(worldName), spawnX, spawnY, spawnZ);
    }

    @Override
    protected void onDisable() {

    }

}
