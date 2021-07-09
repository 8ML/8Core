package com.github._8ml.core.module.buildserver.events;
/*
Created by @8ML (https://github.com/8ML) on June 27 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BuildEvents implements Listener {

    public BuildEvents(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onConnect(PlayerLoginEvent e) {

        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        if (!player.isPermissible(Ranks.BUILD_TEAM, false)) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, MessageColor.COLOR_ERROR + "You are not allowed in this server!");
        }

    }

}
