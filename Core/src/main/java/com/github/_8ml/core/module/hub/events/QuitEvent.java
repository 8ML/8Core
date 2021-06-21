package com.github._8ml.core.module.hub.events;
/*
Created by @8ML (https://github.com/8ML) on June 21 2021
*/

import com.github._8ml.core.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    public QuitEvent(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        e.getPlayer().getInventory().clear();

    }

}
