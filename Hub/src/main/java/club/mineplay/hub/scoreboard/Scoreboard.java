package club.mineplay.hub.scoreboard;
/*
Created by @8ML (https://github.com/8ML) on 5/15/2021
*/

import club.mineplay.core.Core;
import org.bukkit.ChatColor;

public class Scoreboard {

    public static void init() {

        String[] frames = new String[]{ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",

                ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY",

                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",

                ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY",

                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLAY",
        };

        Core.instance.scoreBoard.setScoreboard(frames,
                new String[]{
                        "www.mineplay.club",
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
