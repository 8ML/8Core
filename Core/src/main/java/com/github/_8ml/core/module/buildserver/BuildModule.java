package com.github._8ml.core.module.buildserver;
/*
Created by @8ML (https://github.com/8ML) on June 27 2021
*/

import com.github._8ml.core.cmd.CommandCenter;
import com.github._8ml.core.module.Module;
import com.github._8ml.core.module.buildserver.commands.MapCMD;
import com.github._8ml.core.module.buildserver.events.BuildEvents;

public class BuildModule extends Module {
    public BuildModule() {
        super("Build");
    }

    @Override
    public void reloadConfigs() {

    }

    @Override
    protected void onEnable() {
        //Command
        CommandCenter.registerCommand(new MapCMD(), mainInstance, this);

        //Event
        new BuildEvents(mainInstance);

        //Fetch Maps
        MapCreator.fetchMaps();
    }

    @Override
    protected void onDisable() {

    }
}
