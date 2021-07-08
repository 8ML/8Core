package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 5/8/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.UpdateEvent;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;

public class TabList implements Listener {

    private static final PluginMessenger pluginMessenger = Core.instance.pluginMessenger;

    private String header;
    private String footer;

    private boolean tabListSet = false;

    public TabList(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void setTabList(String header, String footer) {

        this.footer = footer;
        this.header = header;
        this.tabListSet = true;


        try {

            for (Player p : Core.onlinePlayers) {

                PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();



                Field a = packet.getClass().getDeclaredField("header");
                a.setAccessible(true);
                Field b = packet.getClass().getDeclaredField("footer");
                b.setAccessible(true);

                MPlayer player = MPlayer.getMPlayer(p.getName());

                Object h = new ChatComponentText(StringUtils.getWithPlaceholders(player, String.join("\n", header)));
                Object f = new ChatComponentText(StringUtils.getWithPlaceholders(player, String.join("\n", footer)));

                a.set(packet, h);
                b.set(packet, f);

                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void removeTabList(Player player) {

        try {

            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

            Field a = packet.getClass().getDeclaredField("header");
            a.setAccessible(true);
            Field b = packet.getClass().getDeclaredField("footer");
            b.setAccessible(true);

            ChatComponentText header = new ChatComponentText("");
            ChatComponentText footer = new ChatComponentText("");

            a.set(packet, header);
            b.set(packet, footer);

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void updateTabList() {

        if (!this.tabListSet) return;

        try {


            for (Player p : Core.onlinePlayers) {

                PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

                Field a = packet.getClass().getDeclaredField("header");
                a.setAccessible(true);
                Field b = packet.getClass().getDeclaredField("footer");

                MPlayer player = MPlayer.getMPlayer(p.getName());

                Object h = new ChatComponentText(StringUtils.getWithPlaceholders(player, String.join("\n", this.header)));
                Object f = new ChatComponentText(StringUtils.getWithPlaceholders(player, String.join("\n", this.footer)));

                a.set(packet, h);
                b.set(packet, f);

                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isTabListSet() {
        return this.tabListSet;
    }

    private int timer = 0;

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        timer++;
        if (timer >= 5) {
            timer = 0;
            updateTabList();
        }
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }
}
