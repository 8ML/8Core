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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaffMSG {

    private static final PluginMessenger messenger = Main.instance.pluginMessenger;

    public static void sendStaffMessage(String msg) {

        List<String> staffList = new ArrayList<>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            MPlayer staff = MPlayer.getMPlayer(p.getName());
            if (staff.isPermissible(Ranks.BUILD_TEAM)) {
                staffList.add(staff.getPlayerStr());
            }
        }

        messenger.sendPluginMessage("STAFF_CHANNEL", staffList, ChatColor.AQUA + "[STAFF] " + ChatColor.RESET + msg);
    }
    public static void sendStaffMessage(String msg, MPlayer pl) {

        List<String> staffList = new ArrayList<>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            MPlayer staff = MPlayer.getMPlayer(p.getName());
            if (staff.isPermissible(Ranks.BUILD_TEAM)) {
                staffList.add(staff.getPlayerStr());
            }
        }

        messenger.sendPluginMessage("STAFF_CHANNEL", staffList, ChatColor.AQUA + "[STAFF] "
                + ChatColor.AQUA + ChatColor.stripColor(pl.getRankEnum().getRank().getFullPrefixWithSpace())
                + ChatColor.WHITE + pl.getPlayerStr() + " " + ChatColor.RESET + msg);

    }


}
