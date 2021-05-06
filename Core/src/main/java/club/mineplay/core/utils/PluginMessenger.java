package club.mineplay.core.utils;
/*
Created by Sander on 5/6/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;

@SuppressWarnings("UnstableApiUsage")
public class PluginMessenger implements PluginMessageListener {

    public PluginMessenger(Main plugin) {
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
    }

    public void sendPluginMessage(String message) {

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ONLINE");
        out.writeUTF("StaffChannel");

        ByteArrayOutputStream msgBytes = new ByteArrayOutputStream();
        DataOutputStream msgOut = new DataOutputStream(msgBytes);

        try {
            msgOut.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.write(msgBytes.toByteArray());
        out.writeShort(msgBytes.toByteArray().length);

    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!s.equalsIgnoreCase("BungeeCord")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();
        if (subChannel.equals("StaffChannel")) {

            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgIn = new DataInputStream(new ByteArrayInputStream(msgBytes));
            try {
                String msg = msgIn.readUTF();

                for (Player p : Bukkit.getOnlinePlayers()) {

                    MPlayer pl = MPlayer.getMPlayer(p.getName());

                    if (pl.isPermissible(Ranks.BUILD_TEAM)) {

                        p.sendMessage(msg);

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
