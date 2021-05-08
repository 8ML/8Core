package club.mineplay.hub;/*
Created by Sander on 5/8/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.storage.file.PluginFile;
import club.mineplay.hub.events.JoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Hub extends JavaPlugin {

    public static Hub instance;

    public PluginFile tabConf;

    private void registerFiles() {
        tabConf = new PluginFile(this, "tablist.yml", "tablist.yml");
        tabConf.options().copyDefaults(true);
        tabConf.save();
    }

    private void registerEvents() {
        new JoinEvent(this);
    }

    @Override
    public void onEnable() {
        instance = this;

        registerFiles();
        registerEvents();

        Core.instance.tabList.setTabList(tabConf.getString("header"), tabConf.getString("footer"));
    }

    @Override
    public void onDisable() {

    }
}
