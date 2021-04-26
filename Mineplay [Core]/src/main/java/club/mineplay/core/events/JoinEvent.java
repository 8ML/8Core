package club.mineplay.core.events;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.currency.Coin;
import club.mineplay.core.storage.SQL;
import club.mineplay.core.utils.BookUtil;
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

        if (!MPlayer.exists(e.getPlayer().getName())) {
            BookUtil.displayHelpBook(e.getPlayer());

            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r\n" +
                    "                &fWelcome to the &6Mineplay Network&f!" +
                    "\n" +
                    "       &fJoin our &aDiscord &ffor news, updates, announcements," +
                    "\n" +
                    "                            and much more!" +
                    "\n\n" +
                    "                      &d&ndiscord.io/mineplayclub" +
                    "\n" +
                    "\n\n" +
                    "&7You received &e+50 Coins &7for joining the first time!"));
        }

        if (!proxyCheck(e.getPlayer())) {
            e.getPlayer().kickPlayer(MessageColor.COLOR_ERROR + "Could not establish connection!\n\n" +
                    "You have to connect using" + ChatColor.YELLOW + " mineplay.club");
        }

        MPlayer.registerMPlayer(e.getPlayer());
        e.setJoinMessage("");


    }

    public boolean proxyCheck(Player player) {

        try {
            PreparedStatement st = sql.preparedStatement("SELECT * FROM proxy WHERE proxyPlayer=?");
            st.setString(1, player.getName());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                PreparedStatement del = sql.preparedStatement("DELETE FROM proxy WHERE proxyPlayer=?");
                del.setString(1, player.getName());
                del.execute();

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
