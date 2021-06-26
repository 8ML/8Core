package com.github._8ml.core.module.game;
/*
Created by @8ML (https://github.com/8ML) on 5/24/2021
*/

import com.github._8ml.core.module.Module;
import com.github._8ml.core.module.game.manager.map.Maps;

public class GameModule extends Module {

    public GameModule() {
        super("Game");
    }

    @Override
    protected void onEnable() {
        Maps.checkForMaps();
    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void reloadConfigs() {

    }
}
