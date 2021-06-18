package com.github._8ml.core.events;
/*
Created by @8ML (https://github.com/8ML) on 4/25/2021
*/

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandEvent implements Listener {

    public CommandEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().toLowerCase().startsWith("/say")
                || e.getMessage().toLowerCase().startsWith("/me")
                || e.getMessage().toLowerCase().startsWith("/minecraft:say")
                || e.getMessage().toLowerCase().startsWith("/minecraft:me")) {
            e.setCancelled(true);
        }
    }

}
