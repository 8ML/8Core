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


/**
 * This class manages the level of players
 */
public class Level {


    /**
     * This multiplier is used when calculating levels and xp
     *
     * The higher this number is, the more xp you need to achieve levels
     */
    private static final double MULTIPLIER = 14;


    private static final SQL sql = Core.instance.sql;


    /**
     * This will add the specified xp to the specified player
     *
     * @param player The player to add xp to
     * @param xp     The xp to add
     */
    public static void addXP(MPlayer player, int xp) {

        int currentXP = player.getXP();
        int newXP = currentXP + xp;

        setXP(player, newXP);

        if (getLevelFromXP(newXP, false) > getLevelFromXP(currentXP, false)) {
            levelUP(player);
        }
    }


    /**
     * This will add the specified xp to the specified player
     *
     * @param player The player to add xp to
     * @param xp     The xp to add
     * @param msg    Set this to true if you want to send a message with sound to the player
     */
    public static void addXP(MPlayer player, int xp, boolean msg) {
        addXP(player, xp);
        if (msg) {
            if (!player.isOffline()) {
                player.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "+" + xp + " xp!");
                player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            }
        }
    }


    /**
     * This will reset the specified player's xp
     *
     * @param player The player to reset xp
     */
    public static void resetXP(MPlayer player) {
        setXP(player, 0);
    }


    /**
     * This will remove the specified xp from the specified player
     *
     * @param player The player to remove from
     * @param xp     The xp to remove
     */
    public static void removeXP(MPlayer player, int xp) {
        setXP(player, player.getXP() - xp);
    }


    /**
     * This will set the xp of the specified player to the specified xp
     *
     * @param player The player to set xp
     * @param xp     The xp to set
     */
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


    /**
     * This will add the specified level to the specified player
     *
     * @param player The player to add levels to
     * @param level  The level to add
     */
    public static void addLevel(MPlayer player, double level) {

        double tLevel = getLevelFromXP(player.getXP(), true) + level;
        int tXP = getXPFromLevel(tLevel);
        resetXP(player);
        addXP(player, tXP);

    }


    /**
     * This will remove the specified level from the specified player
     *
     * @param player The player to remove from
     * @param level  The level to remove
     */
    public static void removeLevel(MPlayer player, double level) {

        double tLevel = getLevelFromXP(player.getXP(), true) - level;
        int tXP = getXPFromLevel(tLevel);
        resetXP(player);
        setXP(player, tXP);

    }


    /**
     * This will set the specified players level to the specified level
     *
     * @param player The player to set level
     * @param level  The level to set
     */
    public static void setLevel(MPlayer player, double level) {

        setXP(player, getXPFromLevel(level));

    }


    /**
     * This will get the level from the specified xp
     * using the algorithm below and the const multiplier specified
     * in the top of this class
     *
     * @param xp The xp to get level from
     * @param w  Set this to true if you want a rounded number or not
     * @return The level from the xp
     */
    public static double getLevelFromXP(int xp, boolean w) {
        return !w ? (int) Math.floor(Math.sqrt(xp) / MULTIPLIER) : Math.sqrt(xp) / MULTIPLIER;
    }


    /**
     * This will get the xp from the specified level
     * using the algorithm below and the const multiplier specified
     * in the top of this class
     * <p>
     * REQUIRES THE NON ROUNDED LEVEL!
     *
     * @param level The level to get xp from
     * @return The xp from the level
     */
    public static int getXPFromLevel(double level) {
        return (int) ((level * MULTIPLIER) * (level * MULTIPLIER));
    }


    /**
     * This will display a message with a sound telling the player
     * they have levelled up. This is called when you add xp to the player
     * and the xp added, makes the player level up.
     *
     * @param player The player to level up
     */
    private static void levelUP(MPlayer player) {
        Coin.addCoins(player, 20, false);

        player.getPlayer().sendMessage(MessageColor.COLOR_SUCCESS.toString() + ChatColor.BOLD
                + "\nLEVEL UP!\n" + MessageColor.COLOR_MAIN + "You are now level "
                + ChatColor.AQUA
                + ((int) getLevelFromXP(player.getXP(), false)) + MessageColor.COLOR_MAIN + "!"
                + "\n\n" + ChatColor.GOLD
                + "+20 coins" +
                "\n" +
                " ");

        if (!player.isOffline()) {
            player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }
    }

}
