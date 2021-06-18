package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 5/6/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.ProxyJoinEvent;
import com.github._8ml.core.events.event.UpdateEvent;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.*;

@SuppressWarnings("UnstableApiUsage")
public class PluginMessenger implements PluginMessageListener, Listener {

    private final Core plugin;

    private int bungeeOnline = 0;
    private List<String> bungeePlayers = new ArrayList<>();
    private final HashMap<String, String> playerServer = new HashMap<>();

    public PluginMessenger(Core plugin) {
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     *
     * @param channel Sub channel which the message is sent to
     * @param hoverMessage Message to display when hovering over the message
     * @param objects Object list to be sent with the message (Usually the player names of who is receiving it)
     * @param message The message
     */

    public void sendPluginMessage(String channel, String hoverMessage, List<String> objects, String message) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);

        boolean obj = !objects.isEmpty();

        try {

            String[] hoverStr = hoverMessage.split(" ");
            StringBuilder hoverBuilder = new StringBuilder(hoverStr[0]);
            for (String h : hoverStr) {
                if (h.equals(hoverStr[0])) continue;
                hoverBuilder.append(",").append(h);
            }

            if (obj) {
                StringBuilder b = new StringBuilder(objects.get(0));
                for (String o : objects) {
                    if (o.equals(objects.get(0))) continue;
                    b.append(",").append(o);
                }
                String objectsArray = b.toString();
                out.writeUTF(channel + " " + hoverBuilder + " " + objectsArray + " " + message);
            } else {
                out.writeUTF(channel + " " + hoverBuilder + " " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(Iterables.getFirst(Core.onlinePlayers, null))
                .sendPluginMessage(plugin, "BungeeCord", stream.toByteArray());

    }

    public void sendToServer(Player player, String serverName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(serverName);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    private void requestBungeeCount() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("PlayerCount");
        out.writeUTF("ALL");

        if (Core.onlinePlayers.isEmpty()) return;

        Objects.requireNonNull(Iterables.getFirst(Core.onlinePlayers, null))
                .sendPluginMessage(this.plugin, "BungeeCord", out.toByteArray());
    }

    public int getBungeeCount() {
        return this.bungeeOnline;
    }

    public List<String> getBungeePlayers() {
        return this.bungeePlayers;
    }

    public String getServer(String player) {
        return playerServer.get(player);
    }

    private void requestBungeePlayers() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("PlayerList");
        out.writeUTF("ALL");

        if (Core.onlinePlayers.isEmpty()) return;

        Objects.requireNonNull(Iterables.getFirst(Core.onlinePlayers, null))
                .sendPluginMessage(this.plugin, "BungeeCord", out.toByteArray());

    }

    private void requestServers() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("PROXY_REQUEST PLAYER_SERVER");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Core.onlinePlayers.isEmpty()) return;

        Objects.requireNonNull(Iterables.getFirst(Core.onlinePlayers, null))
                .sendPluginMessage(this.plugin, "BungeeCord", stream.toByteArray());
    }


    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

        if (!s.equalsIgnoreCase("BungeeCord")) return;
        try {

            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();

            if (subChannel.equals("PlayerCount")) {
                in.readUTF();
                this.bungeeOnline = in.readInt();
            } else if (subChannel.equals("PlayerList")) {
                in.readUTF();
                this.bungeePlayers = Arrays.asList(in.readUTF().split(", "));
            } else {

                ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
                DataInputStream i = new DataInputStream(stream);

                String[] result = i.readUTF().split(" ");
                switch (result[0]) {
                    case "PROXY_JOIN":
                        requestBungeePlayers();
                        requestBungeeCount();

                        if (!result[1].equalsIgnoreCase(Core.instance.serverName)) return;

                        String playerName = result[2];

                        if (MPlayer.exists(playerName)) {
                            MPlayer pl = MPlayer.getMPlayer(playerName);
                            if (pl.isPermissible(Ranks.BUILD_TEAM, false)) {
                                StaffMSG.sendStaffMessage(ChatColor.YELLOW + "joined.", pl);
                            }
                            plugin.getServer().getPluginManager().callEvent(new ProxyJoinEvent(pl.getPlayer()));
                        }

                        break;
                    case "PROXY_QUIT":
                        requestBungeePlayers();
                        requestBungeeCount();

                        String playerName2 = result[1];
                        MPlayer pl2 = MPlayer.getMPlayer(playerName2);

                        if (pl2.isPermissible(Ranks.BUILD_TEAM, false)) {
                            StaffMSG.sendStaffMessage(ChatColor.YELLOW + "quit.", pl2);
                        }
                        break;
                    case "PROXY_RESPONSE":
                        requestBungeeCount();
                        requestBungeePlayers();

                        String[] players = result[1].split(",");
                        String[] servers = result[2].split(",");

                        for (String p : players) {
                            String server = Arrays.asList(servers)
                                    .get(Arrays.asList(players).indexOf(p));
                            playerServer.put(p, server);
                        }
                        break;

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getType().equals(UpdateEvent.UpdateType.SECONDS)) {
            requestBungeeCount();
            requestBungeePlayers();
            requestServers();
        }
    }
}
