package xyz.dev_8.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 5/15/2021
*/

import xyz.dev_8.core.Core;
import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.player.level.Level;
import org.bukkit.ChatColor;

public class StringUtils {

    /**
     *
     * @param player - Player to get the placeholders from
     * @param str - String to replace placeholders in
     * @return - String with the placeholders replaced with player info and server info
     */

    public static String getWithPlaceholders(MPlayer player, String str) {
        return ChatColor.translateAlternateColorCodes('&', str
                .replaceAll("%onlineServer%", String.valueOf(Core.onlinePlayers.size()))
                .replaceAll("%onlineBungee%", String.valueOf(Core.instance.pluginMessenger.getBungeeCount()))
                .replaceAll("%playerRank%", player.getRankEnum().getRank().isDefaultRank() ? "Default" : player.getRankEnum().getRank().getLabel())
                .replaceAll("%playerRankWithColor%", player.getRankEnum().getRank().isDefaultRank() ? ChatColor.GRAY + "Default"
                        : player.getRankEnum().getRank().getNameColor() + player.getRankEnum().getRank().getLabel())
                .replaceAll("%playerCoins%", String.valueOf(player.getCoins()))
                .replaceAll("%playerLevel%", String.valueOf((int) Level.getLevelFromXP(player.getXP(), false)))
                .replaceAll("%playerXP%", String.valueOf(player.getXP()))
                .replaceAll(":nl:", "\n"));
    }

    /**
     *
     * @param str - String to format
     * @return - Correctly capitalized text
     */

    public static String formatCapitalization(String str) {
        String strLower = str.toLowerCase();
        String firstChar = String.valueOf(strLower.charAt(0));
        str = strLower.replaceFirst(firstChar, firstChar.toUpperCase());
        return str;
    }

    /**
     *
     * @param str - String to replace in
     * @param regex - Array of objects to be replaced
     * @param replacement - Replacement
     * @return - String with all objects replaced
     */

    public static String replaceMultiple(String str, String[] regex, String replacement) {
        String finalStr = str;
        for (String reg: regex) {
            finalStr = finalStr.replaceAll(reg, replacement);
        }
        return finalStr;
    }

}
