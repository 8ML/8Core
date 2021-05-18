package net.clubcraft.core.utils;
/*
Created by @8ML (https://github.com/8ML) on 5/15/2021
*/

import net.clubcraft.core.Core;
import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.player.level.Level;
import org.bukkit.ChatColor;

public class StringUtils {

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

    public static String formatCapitalization(String str) {
        String strLower = str.toLowerCase();
        String firstChar = String.valueOf(strLower.charAt(0));
        str = strLower.replaceFirst(firstChar, firstChar.toUpperCase());
        return str;
    }

}
