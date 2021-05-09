package club.mineplay.bungee.events;
/*
Created by Sander on 5/9/2021
*/

import club.mineplay.bungee.Main;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class QuitEvent implements Listener {

    public QuitEvent(Main plugin) {
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);

        try {
            out.writeUTF("PROXY_QUIT " + e.getPlayer().getName());
            for (ServerInfo server : Main.instance.getProxy().getServers().values()) {
                if (server.getPlayers().isEmpty()) continue;
                server.sendData("BungeeCord", stream.toByteArray());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
