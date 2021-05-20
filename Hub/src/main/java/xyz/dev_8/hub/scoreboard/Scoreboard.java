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
                        ChatColor.GRAY + "www.dev-8.xyz",
                        ChatColor.RESET + "" + ChatColor.BOLD,
                        ChatColor.RESET + "",
                        ChatColor.AQUA + "Players",
                        ChatColor.BLACK + "" + ChatColor.RESET,
                        ChatColor.BLACK + "" + ChatColor.GRAY,
                        ChatColor.AQUA + "Coins",
                        ChatColor.GRAY + "" + ChatColor.GOLD,
                        ChatColor.BLACK.toString(),
                        ChatColor.AQUA + "Rank",
                        ChatColor.GOLD + ""},
                new String[]{
                        "",
                        " ",
                        ChatColor.DARK_GRAY + "" + ChatColor.BOLD + String.valueOf('\u00BB') + ChatColor.RESET + ChatColor.GRAY + " %onlineBungee%",
                        " ",
                        " ",
                        ChatColor.DARK_GRAY + "" + ChatColor.BOLD + String.valueOf('\u00BB') + ChatColor.RESET + ChatColor.GOLD + " %playerCoins%",
                        " ",
                        " ",
                        ChatColor.DARK_GRAY + "" + ChatColor.BOLD + String.valueOf('\u00BB') + ChatColor.RESET + " %playerRankWithColor%",
                        " ",
                        ""});
    }

}
