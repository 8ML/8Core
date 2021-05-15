package club.mineplay.hub.scoreboard;
/*
Created by Sander on 5/15/2021
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
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLA" + "" + ChatColor.GOLD + ChatColor.BOLD + "Y",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPL" + "" + ChatColor.GOLD + ChatColor.BOLD + "A" + ChatColor.WHITE + "" + ChatColor.BOLD + "Y",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEP" + "" + ChatColor.GOLD + ChatColor.BOLD + "L" + ChatColor.WHITE + "" + ChatColor.BOLD + "AY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINE" + "" + ChatColor.GOLD + ChatColor.BOLD + "P" + ChatColor.WHITE + "" + ChatColor.BOLD + "LAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MIN" + "" + ChatColor.GOLD + ChatColor.BOLD + "E" + ChatColor.WHITE + "" + ChatColor.BOLD + "PLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MI" + "" + ChatColor.GOLD + ChatColor.BOLD + "N" + ChatColor.WHITE + "" + ChatColor.BOLD + "EPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "M" + "" + ChatColor.GOLD + ChatColor.BOLD + "I" + ChatColor.WHITE + "" + ChatColor.BOLD + "NEPLAY",
                ChatColor.GOLD + "" + ChatColor.BOLD + "M" + "" + ChatColor.WHITE + ChatColor.BOLD + "INEPLAY",
                ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY", ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY", ChatColor.WHITE + "" + ChatColor.BOLD + "MINEPLAY",
                ChatColor.GOLD + "" + ChatColor.BOLD + "M" + "" + ChatColor.WHITE + ChatColor.BOLD + "INEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "M" + "" + ChatColor.GOLD + ChatColor.BOLD + "I" + ChatColor.WHITE + "" + ChatColor.BOLD + "NEPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MI" + "" + ChatColor.GOLD + ChatColor.BOLD + "N" + ChatColor.WHITE + "" + ChatColor.BOLD + "EPLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MIN" + "" + ChatColor.GOLD + ChatColor.BOLD + "E" + ChatColor.WHITE + "" + ChatColor.BOLD + "PLAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINE" + "" + ChatColor.GOLD + ChatColor.BOLD + "P" + ChatColor.WHITE + "" + ChatColor.BOLD + "LAY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEP" + "" + ChatColor.GOLD + ChatColor.BOLD + "L" + ChatColor.WHITE + "" + ChatColor.BOLD + "AY",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPL" + "" + ChatColor.GOLD + ChatColor.BOLD + "A" + ChatColor.WHITE + "" + ChatColor.BOLD + "Y",
                ChatColor.YELLOW + "" + ChatColor.BOLD + "MINEPLA" + "" + ChatColor.GOLD + ChatColor.BOLD + "Y"};

        Core.instance.scoreBoard.setScoreboard(frames,
                new String[]{"www.mineplay.club",
                        ChatColor.RESET + "",
                        "Level: ",
                        "Rank: ",
                        ChatColor.GREEN + "",
                        "in the lobby!",
                        "by clicking an " + ChatColor.GRAY + "NPC",
                        "Start paying games",
                        ChatColor.GOLD + ""},
                new String[]{"",
                        " ",
                        ChatColor.AQUA + "%playerLevel%",
                        ChatColor.GRAY + "%playerRankWithColor%",
                        " ",
                        " ",
                        " ",
                        " ",
                        " "});
    }

}
