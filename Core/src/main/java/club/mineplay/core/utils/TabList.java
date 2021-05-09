package club.mineplay.core.utils;
/*
Created by Sander on 5/8/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.level.Level;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        try {
            Field a = packet.getClass().getDeclaredField("header");
            a.setAccessible(true);
            Field b = packet.getClass().getDeclaredField("footer");
            b.setAccessible(true);

            for (Player p : Bukkit.getOnlinePlayers()) {

                MPlayer player = MPlayer.getMPlayer(p.getName());

                Object h = new ChatComponentText(getWithPlaceholders(player, header));
                Object f = new ChatComponentText(getWithPlaceholders(player, footer));

                a.set(packet, h);
                b.set(packet, f);

                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void updateTabList() {

        if (!this.tabListSet) return;

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {

            Field a = packet.getClass().getDeclaredField("header");
            a.setAccessible(true);
            Field b = packet.getClass().getDeclaredField("footer");

            for (Player p : Bukkit.getOnlinePlayers()) {

                MPlayer player = MPlayer.getMPlayer(p.getName());

                Object h = new ChatComponentText(getWithPlaceholders(player, this.header));
                Object f = new ChatComponentText(getWithPlaceholders(player, this.footer));

                a.set(packet, h);
                b.set(packet, f);

                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String getWithPlaceholders(MPlayer p, String str) {
        return ChatColor.translateAlternateColorCodes('&', str.replaceAll("%playerRank%", p.getRankEnum().getRank().getFullPrefix())
                .replaceAll("%playerCoins%", String.valueOf(p.getCoins()))
                .replaceAll("%playerXP%", String.valueOf(p.getXP()))
                .replaceAll("%playerLevel%", String.valueOf(Level.getLevelFromXP(p.getXP(), false)))
                .replaceAll("%onlineServer%", String.valueOf(Bukkit.getOnlinePlayers().size())
                        .replaceAll("%onlineBungee%", String.valueOf(pluginMessenger.getBungeeCount()))));
    }

    private int timer = 0;

    @EventHandler
    public void onUpdate() {
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
