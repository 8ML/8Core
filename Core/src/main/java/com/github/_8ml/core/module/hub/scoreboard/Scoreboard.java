package com.github._8ml.core.module.hub.scoreboard;
/*
Created by @8ML (https://github.com/8ML) on 5/24/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.utils.ScoreBoard;
import org.bukkit.ChatColor;

public class Scoreboard {

    public static void init() {

        String[] frames = ScoreBoard.animateString(ServerConfig.SERVER_NAME.toString());

        Core.instance.scoreBoard.setScoreboard(frames,
                new String[]{
                        ChatColor.GRAY + ServerConfig.SERVER_DOMAIN.toString(),
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
