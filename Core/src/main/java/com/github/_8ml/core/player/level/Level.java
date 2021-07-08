package com.github._8ml.core.player.level;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.currency.Coin;
import com.github._8ml.core.storage.SQL;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Level {

    private static final double multiplier = 14;
    private static final SQL sql = Core.instance.sql;

    public static void addXP(MPlayer player, int xp) {

        int currentXP = player.getXP();
        int newXP = currentXP + xp;

        setXP(player, newXP);

        if (getLevelFromXP(newXP, false) > getLevelFromXP(currentXP, false)) {
            levelUP(player);
        }
    }

    public static void addXP(MPlayer player, int xp, boolean msg) {
        addXP(player, xp);
        if (msg) {
            if (!player.isOffline()) {
                player.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "+" + xp + " xp!");
                player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            }
        }
    }

    public static void resetXP(MPlayer player) {
        setXP(player, 0);
    }

    public static void removeXP(MPlayer player, int xp) {
        setXP(player, player.getXP() - xp);
    }

    public static void setXP(MPlayer player, int xp) {

        try {

            PreparedStatement st = sql.preparedStatement("UPDATE users SET xp=? WHERE uuid=?");
            st.setInt(1, xp);
            st.setString(2, player.getUUID());

            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);
                player.update();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void addLevel(MPlayer player, double level) {

        double tLevel = getLevelFromXP(player.getXP(), true) + level;
        int tXP = getXPFromLevel(tLevel);
        resetXP(player);
        addXP(player, tXP);

    }

    public static void removeLevel(MPlayer player, double level) {

        double tLevel = getLevelFromXP(player.getXP(), true) - level;
        int tXP = getXPFromLevel(tLevel);
        resetXP(player);
        setXP(player, tXP);

    }

    public static void setLevel(MPlayer player, double level) {

        setXP(player, getXPFromLevel(level));

    }

    public static double getLevelFromXP(int xp, boolean w) {
        return !w ? (int) Math.floor(Math.sqrt(xp) / multiplier) : Math.sqrt(xp) / multiplier;
    }

    //REQUIRES THE WHOLE LEVEL! AND NOT THE ROUNDED ONE
    public static int getXPFromLevel(double level) {
        return (int) ((level * multiplier) * (level * multiplier));
    }

    private static void levelUP(MPlayer player) {
        Coin.addCoins(player, 20, false);

        player.getPlayer().sendMessage(MessageColor.COLOR_SUCCESS.toString() + ChatColor.BOLD
                + "\nLEVEL UP!\n" + MessageColor.COLOR_MAIN +"You are now level "
                + ChatColor.AQUA
                + ((int) getLevelFromXP(player.getXP(), false)) + MessageColor.COLOR_MAIN + "!"
                +"\n\n" + ChatColor.GOLD
                + "+20 coins" +
                "\n" +
                " ");

        if (!player.isOffline()) {
            player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }
    }

}
