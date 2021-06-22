package com.github._8ml.core.module.hub.scoreboard;
/*
Created by @8ML (https://github.com/8ML) on 5/24/2021
*/

import com.github._8ml.core.config.MessageColor;
import org.bukkit.ChatColor;
import com.github._8ml.core.Core;

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
                        ChatColor.GRAY + "www.dev-8.com",
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
                        ChatColor.DARK_GRAY + "" + ChatColor.BOLD + '\u00BB' + ChatColor.RESET + MessageColor.COLOR_MAIN + " %onlineBungee%",
                        " ",
                        " ",
                        ChatColor.DARK_GRAY + "" + ChatColor.BOLD + '\u00BB' + ChatColor.RESET + ChatColor.GOLD + " %playerCoins%",
                        " ",
                        " ",
                        ChatColor.DARK_GRAY + "" + ChatColor.BOLD + '\u00BB' + ChatColor.RESET + " %playerRankWithColor%",
                        " ",
                        ""});
    }

}
