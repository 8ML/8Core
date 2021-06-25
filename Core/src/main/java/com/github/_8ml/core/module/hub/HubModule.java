package com.github._8ml.core.module.hub;
/*
Created by @8ML (https://github.com/8ML) on 5/24/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.cmd.CommandCenter;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.module.Module;
import com.github._8ml.core.module.hub.commands.CosmeticCMD;
import com.github._8ml.core.module.hub.commands.admin.NewsCMD;
import com.github._8ml.core.module.hub.cosmetic.CosmeticManager;
import com.github._8ml.core.module.hub.events.*;
import com.github._8ml.core.module.hub.item.JoinItems;
import com.github._8ml.core.module.hub.news.News;
import com.github._8ml.core.module.hub.scoreboard.Scoreboard;
import com.github._8ml.core.storage.file.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class HubModule extends Module {

    public static HubModule instance;

    private final static String WORLD_NAME = "Base";

    private PluginFile confYML;
    private PluginFile tabYML;

    public CosmeticManager cosmeticManager;
    public News newsManager;

    public HubModule() {
        super("Hub");
    }

    private void registerCommands() {
        CommandCenter.registerCommand(new CosmeticCMD(), mainInstance, this);
        CommandCenter.registerCommand(new NewsCMD(), mainInstance, this);
    }

    private void registerEvents() {
        new JoinEvent(mainInstance);
        new QuitEvent(mainInstance);
        new InteractionEvent(mainInstance);
        new UpdaterEvent(mainInstance);
        new VoidEvent(mainInstance);

        new JoinItems();
    }

    @Override
    protected void onEnable() {

        instance = this;

        Core.instance.mapExtractor.addMap(WORLD_NAME);

        tabYML = new PluginFile(mainInstance, "hub", "tablist.yml", "tablist.yml");
        confYML = new PluginFile(mainInstance, "hub", "config.yml", "hubconfig.yml");

        registerCommands();
        registerEvents();

        Core.instance.tabList.setTabList(tabYML.getString("header"), tabYML.getString("footer"));
        Scoreboard.init();

        //Spawn point
        setSpawnPoint();

        this.cosmeticManager = new CosmeticManager();
        this.newsManager = new News();
    }

    private void setSpawnPoint() {
        double x = confYML.getDouble("spawnpoint.x");
        double y = confYML.getDouble("spawnpoint.y");
        double z = confYML.getDouble("spawnpoint.z");
        float yaw = (float) confYML.getDouble("spawnpoint.yaw");
        float pitch = (float) confYML.getDouble("spawnpoint.pitch");

        ServerConfig.SPAWN_POINT.setValue(new Location(Bukkit.getWorld(WORLD_NAME), x, y, z, yaw, pitch));
    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void reloadConfigs() {
        setSpawnPoint();
    }
}
