package com.github._8ml.core.events.common;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.utils.NameTag;
import com.github._8ml.core.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LeaveEvent implements Listener {

    public LeaveEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        e.setQuitMessage("");

        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        MPlayer.removeMPlayer(player);

        Core.instance.tabList.removeTabList(e.getPlayer());

        NameTag.removeTitle(e.getPlayer());

        Core.onlinePlayers.remove(e.getPlayer());

    }

}
