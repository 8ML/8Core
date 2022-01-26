/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.events.common;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.events.event.ProxyJoinEvent;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.achievement.Achievement;
import com.github._8ml.core.player.currency.Coin;
import com.github._8ml.core.player.options.PlayerOptions;
import com.github._8ml.core.player.social.friend.Friend;
import com.github._8ml.core.punishment.PunishInfo;
import com.github._8ml.core.punishment.Punishment;
import com.github._8ml.core.punishment.type.Ban;
import com.github._8ml.core.security.OnlyProxyJoin;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.utils.BookUtil;
import com.github._8ml.core.Core;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinEvent implements Listener {

    public JoinEvent(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private final SQL sql = Core.instance.sql;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        boolean tp = ServerConfig.SPAWN_POINT.getValue() != null;

        if (!e.getPlayer().hasPlayedBefore()) {
            if (tp) e.getPlayer().teleport((Location) ServerConfig.SPAWN_POINT.getValue());
        }
        if (tp) e.getPlayer().teleport((Location) ServerConfig.SPAWN_POINT.getValue());

        Core.onlinePlayers.add(e.getPlayer());

        boolean addCoins = false;

        if (!MPlayer.exists(e.getPlayer().getName())) {
            BookUtil.displayHelpBook(e.getPlayer());

            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r\n" +
                    "&fWelcome to the &6" + ServerConfig.SERVER_NAME + " Network&f!" +
                    "\n\n" +
                    "&fJoin our &adiscord &ffor news and announcements!" +
                    "\n" +
                    "&d" + ServerConfig.SERVER_DISCORD_LINK +
                    "\n\n" +
                    "&7You received &e+50 coins &7for joining the first time!"));

            addCoins = true;
        }

        MPlayer.registerMPlayer(e.getPlayer());
        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        if (addCoins) Coin.addCoins(player, 50, false);

        if (!Core.instance.tabList.isTabListSet()) {
            Core.instance.tabList.removeTabList(e.getPlayer());
        } else {
            Core.instance.tabList.updateTabList();
        }

        Friend.initialize(player);
        PlayerOptions.fetchPreferences(player);
        Achievement.fetchAchievements(player);

        e.setJoinMessage("");

    }

    @EventHandler
    public void onProxyJoin(ProxyJoinEvent e) { }


    @EventHandler
    public void onConnect(PlayerLoginEvent e) {

        if (MPlayer.exists(e.getPlayer().getName())) {
            MPlayer p = MPlayer.getMPlayer(e.getPlayer().getName());
            PunishInfo info = Punishment.getActivePunishment(p, Punishment.PunishType.BAN);
            if (info.isActive()) {

                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Ban.getBan(info).getPunishMessage());

            }
        }

    }

}
