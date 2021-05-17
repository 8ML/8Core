package net.clubcraft.hub.scoreboard;
/*
Created by @8ML (https://github.com/8ML) on 5/15/2021
*/

import net.clubcraft.core.Core;
import org.bukkit.ChatColor;

public class Scoreboard {

    public static void init() {

        String[] frames = new String[]{ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",

                ChatColor.WHITE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.WHITE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.WHITE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.WHITE + "" + ChatColor.BOLD + "CLUBCRAFT",

                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",

                ChatColor.WHITE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.WHITE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.WHITE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.WHITE + "" + ChatColor.BOLD + "CLUBCRAFT",

                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "CLUBCRAFT",
        };

        Core.instance.scoreBoard.setScoreboard(frames,
                new String[]{
                        ChatColor.LIGHT_PURPLE + "www.clubcraft.net",
                        ChatColor.RESET + "",
                        "Rank: ",
                        ChatColor.GREEN + "",
                        "in the lobby!",
                        "by clicking an " + ChatColor.GRAY + "NPC",
                        "Start playing games",
                        ChatColor.GOLD + ""},
                new String[]{
                        "",
                        " ",
                        ChatColor.GRAY + "%playerRankWithColor%",
                        " ",
                        " ",
                        " ",
                        " ",
                        " "});
    }

}
