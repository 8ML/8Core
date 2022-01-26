/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
