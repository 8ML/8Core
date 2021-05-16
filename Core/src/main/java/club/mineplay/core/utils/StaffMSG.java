package club.mineplay.core.utils;
/*
Created by Sander on 5/6/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.player.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class StaffMSG {

    private static final PluginMessenger messenger = Core.instance.pluginMessenger;

    public static void sendStaffMessage(String msg, String hoverMessage) {

        List<String> staffList = new ArrayList<>();

        for (String p : messenger.getBungeePlayers()) {
            MPlayer staff = MPlayer.getMPlayer(p);
            if (staff.isPermissible(Ranks.BUILD_TEAM)) {
                staffList.add(staff.getPlayerStr());
            }
        }

        messenger.sendPluginMessage("STAFF_CHANNEL", hoverMessage, staffList, ChatColor.AQUA + "[STAFF] " + ChatColor.RESET + msg);
    }
    public static void sendStaffMessage(String msg, MPlayer pl) {

        List<String> staffList = new ArrayList<>();
        staffList.add(pl.getPlayerStr());
        for (String p : messenger.getBungeePlayers()) {
            MPlayer staff = MPlayer.getMPlayer(p);
            if (staff.isPermissible(Ranks.BUILD_TEAM)) {
                if (staff.getPlayerStr().equals(pl.getPlayerStr())) continue;
                staffList.add(staff.getPlayerStr());
            }
        }

        String hover = ChatColor.YELLOW + "Rank: " + ChatColor.AQUA + pl.getRankEnum().getRank().getPrefix()
                + ChatColor.YELLOW + "\nServer: " + ChatColor.AQUA + "%server%";

        messenger.sendPluginMessage("STAFF_CHANNEL", hover, staffList, ChatColor.AQUA + "[STAFF] "
                + ChatColor.WHITE + pl.getPlayerStr() + " " + ChatColor.RESET + msg);

    }


}
