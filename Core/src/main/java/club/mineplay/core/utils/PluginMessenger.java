package club.mineplay.core.utils;
/*
Created by Sander on 5/6/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import com.google.common.collect.Iterables;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class PluginMessenger implements PluginMessageListener {

    private Main plugin;

    public PluginMessenger(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
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

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

        if (!s.equalsIgnoreCase("BungeeCord")) return;
        try {

            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            DataInputStream in = new DataInputStream(stream);

            String[] result = in.readUTF().split(" ");
            if (result[0].equals("PROXY_JOIN")) {
                String playerName = result[1];
                MPlayer pl = MPlayer.getMPlayer(playerName);
                if (pl.isPermissible(Ranks.BUILD_TEAM)) {
                    StaffMSG.sendStaffMessage(ChatColor.YELLOW + "joined.", pl);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
