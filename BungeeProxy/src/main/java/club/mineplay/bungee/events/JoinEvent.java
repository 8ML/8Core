package club.mineplay.bungee.events;
/*
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import club.mineplay.bungee.Main;
import club.mineplay.bungee.storage.SQL;
import net.md_5.bungee.api.ServerConnectRequest;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JoinEvent implements Listener {

    public JoinEvent(Plugin plugin) {
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    private final SQL sql = Main.instance.sql;

    @EventHandler
    public void onServerConnect(ServerConnectEvent e) {

        System.out.println(e.getPlayer().getName() + " Connected!");

        try {

            PreparedStatement st = sql.preparedStatement("INSERT INTO proxy (proxyPlayer) VALUES(?)");
            st.setString(1, e.getPlayer().getName());
            try {
                st.execute();
            } finally {
                sql.getConnection().close();
            }

            if (e.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(stream);

                try {
                    out.writeUTF("PROXY_JOIN " + e.getTarget().getName() + " " + e.getPlayer().getName());
                    for (ServerInfo server : Main.instance.getProxy().getServers().values()) {
                        if (server.getPlayers().isEmpty()) continue;
                        server.sendData("BungeeCord", stream.toByteArray());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
