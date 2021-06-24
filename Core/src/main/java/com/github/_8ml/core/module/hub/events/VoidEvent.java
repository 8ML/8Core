package com.github._8ml.core.module.hub.events;
/*
Created by @8ML (https://github.com/8ML) on June 24 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.module.hub.HubModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidEvent implements Listener {

    public VoidEvent(Core plugin) {

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onVoidEnter(PlayerMoveEvent e) {
        if (e.getPlayer().getLocation().getY() < 0) {

            e.getPlayer().teleport(ServerConfig.spawnPoint);

        }
    }

}
