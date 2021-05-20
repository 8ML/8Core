package xyz.dev_8.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 5/6/2021
*/

import xyz.dev_8.core.Core;
import xyz.dev_8.core.player.hierarchy.Ranks;
import xyz.dev_8.core.player.MPlayer;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class StaffMSG {

    private static final PluginMessenger messenger = Core.instance.pluginMessenger;

    public static void sendStaffMessage(String msg, String hoverMessage) {

        List<String> staffList = new ArrayList<>();

        for (String p : messenger.getBungeePlayers()) {
            MPlayer staff = MPlayer.getMPlayer(p);
            if (staff.isPermissible(Ranks.BUILD_TEAM, false)) {
                staffList.add(staff.getPlayerStr());
            }
        }

        messenger.sendPluginMessage("MESSAGE_CHANNEL", hoverMessage, staffList, ChatColor.AQUA + "[STAFF] " + ChatColor.RESET + msg);
    }
    public static void sendStaffMessage(String msg, MPlayer pl) {

        List<String> staffList = new ArrayList<>();
        staffList.add(pl.getPlayerStr());
        for (String p : messenger.getBungeePlayers()) {
            MPlayer staff = MPlayer.getMPlayer(p);
            if (staff.isPermissible(Ranks.BUILD_TEAM, false)) {
                if (staff.getPlayerStr().equals(pl.getPlayerStr())) continue;
                staffList.add(staff.getPlayerStr());
            }
        }

        String hover = ChatColor.YELLOW + "Rank: " + ChatColor.AQUA + pl.getRankEnum().getRank().getPrefix()
                + ChatColor.YELLOW + "\nServer: " + ChatColor.AQUA + "%server%";

        messenger.sendPluginMessage("MESSAGE_CHANNEL", hover, staffList, ChatColor.AQUA + "[STAFF] "
                + ChatColor.WHITE + pl.getPlayerStr() + " " + ChatColor.RESET + msg);

    }


}
