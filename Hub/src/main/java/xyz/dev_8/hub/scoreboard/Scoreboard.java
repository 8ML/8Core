package xyz.dev_8.hub.scoreboard;
/*
Created by @8ML (https://github.com/8ML) on 5/15/2021
*/

import xyz.dev_8.core.Core;
import org.bukkit.ChatColor;

public class Scoreboard {

    public static void init() {

        String[] frames = new String[]{ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",

                ChatColor.WHITE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.WHITE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.WHITE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.WHITE + "" + ChatColor.BOLD + "8CORE",

                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",

                ChatColor.WHITE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.WHITE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.WHITE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.WHITE + "" + ChatColor.BOLD + "8CORE",

                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
                ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "8CORE",
        };

        Core.instance.scoreBoard.setScoreboard(frames,
                new String[]{
                        ChatColor.WHITE + "www.dev-8.xyz",
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
