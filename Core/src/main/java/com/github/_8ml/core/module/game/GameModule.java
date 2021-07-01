package com.github._8ml.core.module.game;
/*
Created by @8ML (https://github.com/8ML) on 5/24/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.cmd.CommandCenter;
import com.github._8ml.core.module.Module;
import com.github._8ml.core.module.game.exceptions.GameNotFoundException;
import com.github._8ml.core.module.game.games.GameRegistry;
import com.github._8ml.core.module.game.manager.map.Maps;
import com.github._8ml.core.storage.file.PluginFile;

public class GameModule extends Module {

    public static GameModule instance;

    public PluginFile gameConfig;

    public GameModule() {
        super("Game");
    }

    private void registerCommands() {
    }

    @Override
    protected void onEnable() {
        instance = this;

        registerCommands();

        Maps.checkForMaps();

        gameConfig = new PluginFile(Core.instance, "game", "config.yml", "gameconfig.yml");

        try {
            GameRegistry.setGame(gameConfig.getString("gameType"));
        } catch (GameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void reloadConfigs() {

    }
}
