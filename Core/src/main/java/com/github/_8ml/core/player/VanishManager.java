/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.player;
/*
Created by @8ML (https://github.com/8ML) on June 26 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.utils.ActionBar;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VanishManager implements Listener {

    private static final Set<Player> vanishedPlayers = new HashSet<>();

    public VanishManager(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void hidePlayer(Player player) {
        ActionBar.sendActionBar(player, ChatColor.GREEN + "You are vanished!");
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
        vanishedPlayers.remove(player);
        ActionBar.sendActionBar(player, "");
        player.setAllowFlight(false);
        player.setFlying(false);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        if (player.isVanished()) {

            hidePlayer(e.getPlayer());
            ActionBar.sendActionBar(e.getPlayer(), ChatColor.GREEN + "You are vanished!");

        } else {

            for (Player hidden : vanishedPlayers) {
                if (hidden.getName().equalsIgnoreCase(e.getPlayer().getName())) continue;
                player.getPlayer().hidePlayer(Core.instance, hidden);
            }

        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {

        if (e.getType().equals(UpdateEvent.UpdateType.SECONDS)) {
            for (Player player : vanishedPlayers) {
                ActionBar.sendActionBar(player, ChatColor.GREEN + "You are vanished!");
            }
        }

    }

}
