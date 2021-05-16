package club.mineplay.core.events.common;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.events.event.ProxyJoinEvent;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.currency.Coin;
import club.mineplay.core.player.options.PlayerOptions;
import club.mineplay.core.player.social.friend.Friend;
import club.mineplay.core.punishment.PunishInfo;
import club.mineplay.core.punishment.Punishment;
import club.mineplay.core.punishment.type.Ban;
import club.mineplay.core.storage.SQL;
import club.mineplay.core.utils.BookUtil;
import net.md_5.bungee.api.ChatColor;
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

        Core.onlinePlayers.add(e.getPlayer());

        boolean addCoins = false;

        if (!MPlayer.exists(e.getPlayer().getName())) {
            BookUtil.displayHelpBook(e.getPlayer());

            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r\n" +
                    "&fWelcome to the &6Mineplay Network&f!" +
                    "\n\n" +
                    "&fJoin our &adiscord &ffor news and announcements!" +
                    "\n" +
                    "&d&nmineplay.club/discord" +
                    "\n\n" +
                    "&7You received &e+50 coins &7for joining the first time!"));

            addCoins = true;
        }

        MPlayer.registerMPlayer(e.getPlayer());

        if (addCoins) Coin.addCoins(MPlayer.getMPlayer(e.getPlayer().getName()), 50, false);

        if (!Core.instance.tabList.isTabListSet()) {
            Core.instance.tabList.removeTabList(e.getPlayer());
        } else {
            Core.instance.tabList.updateTabList();
        }

        Friend.initialize(MPlayer.getMPlayer(e.getPlayer().getName()));
        PlayerOptions.fetchPreferences(MPlayer.getMPlayer(e.getPlayer().getName()));

        e.setJoinMessage("");

    }

    @EventHandler
    public void onProxyJoin(ProxyJoinEvent e) { }


    @EventHandler
    public void onConnect(PlayerLoginEvent e) {

        if (!proxyCheck(e.getPlayer())) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, MessageColor.COLOR_ERROR + "Could not establish connection!\n\n" +
                    "You have to connect using" + ChatColor.YELLOW + " mineplay.club" + MessageColor.COLOR_ERROR + "!");
        }

        if (MPlayer.exists(e.getPlayer().getName())) {
            MPlayer p = MPlayer.getMPlayer(e.getPlayer().getName());
            PunishInfo info = Punishment.getActivePunishment(p, Punishment.PunishType.BAN);
            if (info.isActive()) {

                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Ban.getBan(info).getPunishMessage());

            }
        }

    }

    private boolean proxyCheck(Player player) {

        try {
            PreparedStatement st = sql.preparedStatement("SELECT * FROM proxy WHERE proxyPlayer=?");
            st.setString(1, player.getName());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                PreparedStatement del = sql.preparedStatement("DELETE FROM proxy WHERE proxyPlayer=?");
                del.setString(1, player.getName());
                del.execute();

                sql.closeConnection(st);
                sql.closeConnection(del);
                return true;
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }
}
