/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.bungee;
/*
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import com.github._8ml.core.bungee.events.JoinEvent;
import com.github._8ml.core.bungee.events.QuitEvent;
import com.github._8ml.core.bungee.storage.SQL;
import com.github._8ml.core.bungee.storage.file.ConfigFile;
import com.google.common.collect.Iterables;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.util.Arrays;

public class Main extends Plugin implements Listener {

    public static Main instance;

    public SQL sql;
    public Configuration config;

    private void initSQL() {
        this.sql = new SQL(config.getString("mysql.database"),
                config.getString("mysql.username"),
                config.getString("mysql.host"),
                config.getString("mysql.password"),
                config.getInt("mysql.port"));
        if (this.sql.testConnection()) {
            this.getLogger().info("[SQL] Connection Established!");
        } else this.getLogger().severe("[SQL] Connection could not be established!");
    }

    private void registerEvents() {
        new JoinEvent(this);
        new QuitEvent(this);
    }

    @Override
    public void onEnable() {

        instance = this;

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(
                    ConfigFile.loadResource(this, "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

            String[] m = in.readUTF().split(" ");
            if (m.length == 2) {
                if (m[0].equals("PROXY_REQUEST")) {
                    if (m[1].equals("PLAYER_SERVER")) {

                        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                        DataOutputStream out = new DataOutputStream(stream2);

                        if (Iterables.getFirst(getProxy().getPlayers(), null) == null) return;

                        ProxiedPlayer first = Iterables.getFirst(getProxy().getPlayers(), null);

                        assert first != null;

                        StringBuilder playerBuilder = new StringBuilder(first.getName());
                        StringBuilder serverBuilder = new StringBuilder(first.getServer().getInfo().getName());

                        for (ProxiedPlayer p : getProxy().getPlayers()) {

                            if (p.equals(first)) continue;

                            playerBuilder.append(",").append(p.getName());
                            serverBuilder.append(",").append(p.getServer().getInfo().getName());

                        }

                        out.writeUTF("PROXY_RESPONSE " + playerBuilder.toString() + " " + serverBuilder.toString());

                        for (ServerInfo server : getProxy().getServers().values()) {
                            server.sendData("BungeeCord", stream2.toByteArray());
                        }

                    }
                }
            } else if (m.length >= 3) {
                if (m[0].equals("MESSAGE_CHANNEL")) {

                    String[] hoverMsgA = m[1].split(",");
                    StringBuilder hoverMsg = new StringBuilder(hoverMsgA[0]);
                    for (String s : hoverMsgA) {
                        if (s.equals(hoverMsgA[0])) continue;
                        hoverMsg.append(" ").append(s);
                    }

                    this.getLogger().info("DEBUG: " + Arrays.toString(m));
                    String[] players = m[2].split(",");
                    StringBuilder msg = new StringBuilder(m[3]);
                    for (int i = 4; i < m.length; i++) {
                        msg.append(" ").append(m[i]);
                    }

                    ProxiedPlayer player = getProxy().getPlayer(players[0]);

                    TextComponent component = new TextComponent(msg.toString());

                    if (player != null) {
                        if (player.isConnected()) {
                            if (player.getServer() != null) {
                                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverMsg.toString()
                                        .replaceAll("%server%", player.getServer().getInfo().getName()))));
                            }
                        }
                    }

                    for (ProxiedPlayer p : getProxy().getPlayers()) {
                        if (Arrays.asList(players).contains(p.getName())) {
                            p.sendMessage(component);
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
