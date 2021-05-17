package net.clubcraft.hub;/*
Created by @8ML (https://github.com/8ML) on 5/8/2021
*/

import net.clubcraft.core.Core;
import net.clubcraft.core.storage.file.PluginFile;
import net.clubcraft.hub.events.JoinEvent;
import net.clubcraft.hub.scoreboard.Scoreboard;
import org.bukkit.plugin.java.JavaPlugin;

public class Hub extends JavaPlugin {

    public static Hub instance;

    public PluginFile tabConf;

    private void registerFiles() {
        tabConf = new PluginFile(this, "tablist.yml", "tablist.yml");
        tabConf.save();
    }

    private void registerEvents() {
        new JoinEvent(this);
    }

    @Override
    public void onEnable() {
        instance = this;

        Core.instance.mapExtractor.addMap("Base");

        registerFiles();
        registerEvents();

        Core.instance.tabList.setTabList(tabConf.getString("header"), tabConf.getString("footer"));
        Scoreboard.init();
    }

    @Override
    public void onDisable() {

    }
}
