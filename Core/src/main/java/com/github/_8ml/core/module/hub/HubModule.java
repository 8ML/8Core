package com.github._8ml.core.module.hub;
/*
Created by @8ML (https://github.com/8ML) on 5/24/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.cmd.CommandCenter;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.module.Module;
import com.github._8ml.core.module.hub.commands.CosmeticCMD;
import com.github._8ml.core.module.hub.cosmetic.CosmeticManager;
import com.github._8ml.core.module.hub.events.InteractionEvent;
import com.github._8ml.core.module.hub.scoreboard.Scoreboard;
import com.github._8ml.core.storage.file.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import com.github._8ml.core.module.hub.events.JoinEvent;

public class HubModule extends Module {

    public static HubModule instance;

    private final String worldName = "Base";
    private final double spawnX = 0.554;
    private final double spawnY = 93.0;
    private final double spawnZ = -3.301;

    private PluginFile tabYML;

    public CosmeticManager cosmeticManager;

    public HubModule() {
        super("Hub");
    }

    private void registerCommands() {
        CommandCenter.registerCommand(new CosmeticCMD(), mainInstance, this);
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

        this.cosmeticManager = new CosmeticManager();
    }

    @Override
    protected void onDisable() {

    }

}
