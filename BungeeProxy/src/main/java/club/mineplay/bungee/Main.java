package club.mineplay.bungee;
/*
Created by Sander on 4/26/2021
*/

import club.mineplay.bungee.events.JoinEvent;
import club.mineplay.bungee.storage.SQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class Main extends Plugin implements Listener {

    public static Main instance;

    public SQL sql;

    private void initSQL() {
        this.sql = new SQL("monke", "monke", "localhost", "monke.4994", 3306);
        if (this.sql.testConnection()) {
            this.getLogger().info("[SQL] Connection Established!");
        } else this.getLogger().severe("[SQL] Connection could not be established!");
    }

    private void registerEvents() {
        new JoinEvent(this);
    }

    @Override
    public void onEnable() {

        instance = this;

        initSQL();
        registerEvents();

        this.getProxy().registerChannel("BungeeCord");
        this.getProxy().getPluginManager().registerListener(this, this);

    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) throws IOException {
        if (e.getTag().equalsIgnoreCase("BungeeCord")) {

            if (!(e.getSender() instanceof Server)) return;

            ByteArrayInputStream stream = new ByteArrayInputStream(e.getData());
            DataInputStream in = new DataInputStream(stream);

            this.getLogger().info("DEBUG: yes");

            String[] m = in.readUTF().split(" ");
            if (m.length == 2) {
                if (m[0].equals("STAFF_CHANNEL")) {
                }
            } else if (m.length >= 3) {
                if (m[0].equals("STAFF_CHANNEL")) {
                    this.getLogger().info("DEBUG: " + Arrays.toString(m));
                    String[] players = m[1].split(",");
                    StringBuilder msg = new StringBuilder(m[2]);
                    for (int i = 3; i < m.length; i++) {
                        msg.append(" ").append(m[i]);
                    }
                    for (ProxiedPlayer p : getProxy().getPlayers()) {
                        if (Arrays.asList(players).contains(p.getName())) {
                            p.sendMessage(sendMessage(msg.toString()));
                        }
                    }
                }
            }


        }
    }

    public BaseComponent[] sendMessage(String message) {
        return new ComponentBuilder(message).create();
    }

    @Override
    public void onDisable() {
    }
}
