package club.mineplay.core.utils;
/*
Created by Sander on 5/6/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.events.event.UpdateEvent;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class PluginMessenger implements PluginMessageListener, Listener {

    private final Core plugin;

    private int bungeeOnline = 0;

    public PluginMessenger(Core plugin) {
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void sendPluginMessage(String channel, List<String> objects, String message) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);

        boolean obj = false;

        if (!objects.isEmpty()) obj = true;

        try {
            if (obj) {
                StringBuilder b = new StringBuilder(objects.get(0));
                for (String o : objects) {
                    if (o.equals(objects.get(0))) continue;
                    b.append(",").append(o);
                }
                String objectsArray = b.toString();
                out.writeUTF(channel + " " + objectsArray + " " + message);
            } else {
                out.writeUTF(channel + " " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(Iterables.getFirst(Bukkit.getOnlinePlayers(), null))
                .sendPluginMessage(plugin, "BungeeCord", stream.toByteArray());

    }

    private void requestBungeeCount() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);

        try {
            out.writeUTF("PlayerCount");
            out.writeUTF("ALL");

            Objects.requireNonNull(Iterables.getFirst(Bukkit.getOnlinePlayers(), null))
                    .sendPluginMessage(this.plugin, "BungeeCord", stream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBungeeCount() {
        return this.bungeeOnline;
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

        if (!s.equalsIgnoreCase("BungeeCord")) return;
        try {

            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();

            if (subChannel.equals("OnlineCount")) {
                this.bungeeOnline = in.readInt();
            } else {

                ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
                DataInputStream i = new DataInputStream(stream);

                String[] result = i.readUTF().split(" ");
                if (result[0].equals("PROXY_JOIN")) {
                    String playerName = result[1];
                    MPlayer pl = MPlayer.getMPlayer(playerName);
                    if (pl.isPermissible(Ranks.BUILD_TEAM)) {
                        StaffMSG.sendStaffMessage(ChatColor.YELLOW + "joined.", pl);
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        requestBungeeCount();
    }
}
