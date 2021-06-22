package com.github._8ml.core.module.hub.events;
/*
Created by @8ML (https://github.com/8ML) on June 22 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.UpdateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UpdaterEvent implements Listener {

    public UpdaterEvent(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {



    }

}
