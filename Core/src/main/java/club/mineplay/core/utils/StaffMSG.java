package club.mineplay.core.utils;
/*
Created by Sander on 5/6/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StaffMSG {

    private static final PluginMessenger messenger = Main.instance.pluginMessenger;

    public static void sendStaffMessage(String msg) {

        messenger.sendPluginMessage(ChatColor.AQUA + "[STAFF] " + ChatColor.RESET + msg);
        for (Player p : Bukkit.getOnlinePlayers()) {
            MPlayer staff = MPlayer.getMPlayer(p.getName());
            if (staff.isPermissible(Ranks.BUILD_TEAM)) {
                p.sendMessage(ChatColor.AQUA + "[STAFF] " + ChatColor.RESET + msg);
            }
        }
    }
    public static void sendStaffMessage(String msg, MPlayer pl) {

        messenger.sendPluginMessage(ChatColor.AQUA + "[STAFF] "
                + pl.getRankEnum().getRank().getFullPrefixWithSpace()
                + ChatColor.WHITE + pl.getPlayerStr() + " " + ChatColor.RESET + msg);
        for (Player p : Bukkit.getOnlinePlayers()) {
            MPlayer staff = MPlayer.getMPlayer(p.getName());
            if (staff.isPermissible(Ranks.BUILD_TEAM)) {
                p.sendMessage(ChatColor.AQUA + "[STAFF] "
                        + pl.getRankEnum().getRank().getFullPrefixWithSpace()
                        + ChatColor.WHITE + pl.getPlayerStr() + " " + ChatColor.RESET + msg);
            }
        }

    }


}
