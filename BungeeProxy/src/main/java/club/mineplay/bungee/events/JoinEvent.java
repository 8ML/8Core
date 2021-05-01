package club.mineplay.bungee.events;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.bungee.Main;
import club.mineplay.bungee.storage.SQL;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JoinEvent implements Listener {

    public JoinEvent(Plugin plugin) {
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    private final SQL sql = Main.instance.sql;

    @EventHandler
    public void onConnect(ServerConnectEvent e) {

        System.out.println(e.getPlayer().getName() + " Connected!");

        try {

            PreparedStatement st = sql.preparedStatement("INSERT INTO proxy (proxyPlayer) VALUES(?)");
            st.setString(1, e.getPlayer().getName());
            try {
                st.execute();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
