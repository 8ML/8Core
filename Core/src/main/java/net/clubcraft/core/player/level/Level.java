package net.clubcraft.core.player.level;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import net.clubcraft.core.Core;
import net.clubcraft.core.config.MessageColor;
import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.player.currency.Coin;
import net.clubcraft.core.storage.SQL;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Level {

    private static final double multiplier = 14;

    private static final SQL sql = Core.instance.sql;

    public static void addXP(MPlayer player, int xp) {
        try {

            int currentXP = player.getXP();
            int newXP = currentXP + xp;

            PreparedStatement st = sql.preparedStatement("UPDATE users SET xp=? WHERE uuid=?");
            st.setInt(1, newXP);
            st.setString(2, player.getUUID());
            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);
                player.update();
            }

            if (getLevelFromXP(currentXP, false) < getLevelFromXP(newXP, false)) {
                levelUP(player);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetXP(MPlayer player) {
        try {

            PreparedStatement st = sql.preparedStatement("UPDATE users SET xp=? WHERE uuid=?");
            st.setInt(1, 0);
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

    public static void removeXP(MPlayer player, int xp) {
        try {

            int currentXP = player.getXP();
            int newXP = currentXP + xp;

            PreparedStatement st = sql.preparedStatement("UPDATE users SET xp=? WHERE uuid=?");
            st.setInt(1, newXP);
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
