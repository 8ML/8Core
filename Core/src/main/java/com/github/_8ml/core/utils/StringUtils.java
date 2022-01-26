/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 5/15/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.level.Level;
import com.github._8ml.core.Core;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Map;

public class StringUtils {


    /**
     * @param player Player to get the placeholders from
     * @param str    String to replace placeholders in
     * @return String with the placeholders replaced with player info and server info
     */
    public static String getWithPlaceholders(MPlayer player, String str) {
        return ChatColor.translateAlternateColorCodes('&', str
                .replaceAll("%networkName%", ServerConfig.SERVER_NAME.toString())
                .replaceAll("%networkDomain%", ServerConfig.SERVER_DOMAIN.toString())
                .replaceAll("%networkStoreDomain%", ServerConfig.SERVER_STORE_DOMAIN.toString())
                .replaceAll("%networkAppealDomain%", ServerConfig.SERVER_APPEAL_DOMAIN.toString())
                .replaceAll("%discordLink%", ServerConfig.SERVER_DISCORD_LINK.toString())
                .replaceAll("%player%", player.getPlayerStr())
                .replaceAll("%onlineServer%", String.valueOf(Core.onlinePlayers.size()))
                .replaceAll("%onlineBungee%", String.valueOf(Core.instance.pluginMessenger.getBungeeCount()))
                .replaceAll("%playerRank%", player.getRankEnum().getRank().isDefaultRank() ? "Default" : player.getRankEnum().getRank().getLabel())
                .replaceAll("%playerRankWithColor%", player.getRankEnum().getRank().isDefaultRank() ? MessageColor.COLOR_MAIN + "Default"
                        : player.getRankEnum().getRank().getNameColor() + player.getRankEnum().getRank().getLabel())
                .replaceAll("%playerCoins%", String.valueOf(player.getCoins()))
                .replaceAll("%playerLevel%", String.valueOf((int) Level.getLevelFromXP(player.getXP(), false)))
                .replaceAll("%playerXP%", String.valueOf(player.getXP()))
                .replaceAll(":nl:", "\n"));
    }


    /**
     *
     * @param player Player to get the placeholders from
     * @param str String to replace placeholders in
     * @param extraPlaceholders Extra placeholders as list of Map<String, String> first String = placeholder, second = replacementValue
     * @return String with the placeholders replaced with player info, server info and extra placeholders
     */
    public static String getWithPlaceholders(MPlayer player, String str, List<Map<String, String>> extraPlaceholders) {
        String withPlaceholders = getWithPlaceholders(player, str);
        for (Map<String, String> placeholders : extraPlaceholders) {
            for (String key : placeholders.keySet()) {
                withPlaceholders = withPlaceholders.replaceAll(key, placeholders.get(key));
            }
        }
        return withPlaceholders;
    }


    /**
     * @param str String to format
     * @return Correctly capitalized text
     */
    public static String formatCapitalization(String str) {
        String strLower = str.toLowerCase();
        String firstChar = String.valueOf(strLower.charAt(0));
        str = strLower.replaceFirst(firstChar, firstChar.toUpperCase());
        return str;
    }


    /**
     * @param str         String to replace in
     * @param regex       Array of objects to be replaced
     * @param replacement Replacement
     * @return String with all objects replaced
     */
    public static String replaceMultiple(String str, String[] regex, String replacement) {
        String finalStr = str;
        for (String reg : regex) {
            if (finalStr.equalsIgnoreCase(replacement)) continue;
            finalStr = finalStr.replaceAll(reg, replacement);
        }
        return finalStr;
    }

}
