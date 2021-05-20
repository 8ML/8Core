package xyz.dev_8.game;
/*
Created by @8ML (https://github.com/8ML) on 5/20/2021
*/

import org.bukkit.plugin.java.JavaPlugin;
import xyz.dev_8.core.Core;
import xyz.dev_8.core.world.MapExtract;

public class GameMain extends JavaPlugin {

    public static GameMain instance;

    public void registerCommands() {}

    public void registerEvents() {}

    @Override
    public void onEnable() {
        instance = this;

        registerCommands();
        registerEvents();

        //Core.instance.mapExtractor.addMap("WaitingLobby");
    }

    @Override
    public void onDisable() {
    }
}
