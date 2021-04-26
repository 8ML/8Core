package club.mineplay.core.events;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.currency.Coin;
import club.mineplay.core.storage.SQL;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinEvent implements Listener {

    public JoinEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    SQL sql = Main.instance.sql;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        if (!proxyCheck(e.getPlayer())) {
            e.getPlayer().kickPlayer(MessageColor.COLOR_ERROR + "Could not establish connection!\n\n" +
                    "You have to connect using" + ChatColor.YELLOW + " mineplay.club");
        }

        MPlayer.registerMPlayer(e.getPlayer());
        e.setJoinMessage("");

        if (!e.getPlayer().hasPlayedBefore()) {
            Coin.addCoins(MPlayer.getMPlayer(e.getPlayer()), 50);
        }

    }

    public boolean proxyCheck(Player player) {

        try {
            PreparedStatement st = sql.preparedStatement("SELECT * FROM proxy WHERE playerName=?");
            st.setString(1, player.getName());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                PreparedStatement del = sql.preparedStatement("DELETE FROM proxy WHERE playerName=?");
                st.setString(1, player.getName());
                st.execute();

                sql.getConnection().close();
                return true;
            }

            sql.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

}
