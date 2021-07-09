package com.github._8ml.core.player;
/*
Created by @8ML (https://github.com/8ML) on June 26 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.utils.ActionBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashSet;
import java.util.Set;

public class VanishManager implements Listener {

    private static final Set<Player> vanishedPlayers = new HashSet<>();

    public VanishManager(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void hidePlayer(Player player) {
        vanishedPlayers.add(player);
        for (Player pl : Core.onlinePlayers) {
            if (pl.getName().equalsIgnoreCase(player.getName())) continue;
            pl.hidePlayer(Core.instance, player);
        }
        player.setAllowFlight(true);
        player.setFlying(true);
    }

    public static void unHidePlayer(Player player) {
        for (Player pl : Core.onlinePlayers) {
            if (pl.getName().equalsIgnoreCase(player.getName())) continue;
            pl.showPlayer(Core.instance, player);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        if (player.isVanished()) {

            hidePlayer(e.getPlayer());

        } else {

            for (Player hidden : vanishedPlayers) {
                player.getPlayer().hidePlayer(Core.instance, hidden);
            }

        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {

        if (e.getType().equals(UpdateEvent.UpdateType.SECONDS)) {
            for (Player player : vanishedPlayers) {
                ActionBar.sendActionBar(player, MessageColor.COLOR_SUCCESS + "You are vanished!");
            }
        }

    }

}
