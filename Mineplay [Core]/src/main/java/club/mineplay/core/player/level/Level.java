package club.mineplay.core.player.level;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.currency.Coin;
import club.mineplay.core.storage.SQL;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Level {

    private static final double multiplier = 14;

    private static final SQL sql = Main.instance.sql;

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
                sql.getConnection().close();
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
                sql.getConnection().close();
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
                sql.getConnection().close();
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
                sql.getConnection().close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static double getLevelFromXP(int xp, boolean w) {
        if (!w) return (int) Math.floor(Math.sqrt(xp) / multiplier);
        else return Math.sqrt(xp) / multiplier;
    }

    //REQUIRES THE WHOLE LEVEL! AND NOT THE ROUNDED ONE
    public static int getXPFromLevel(double level) {
        return (int) ((level * multiplier) * (level * multiplier));
    }

    private static void levelUP(MPlayer player) {
        Coin.addCoins(player, 20, false);

        player.getPlayer().sendMessage(MessageColor.COLOR_SUCCESS.toString() + ChatColor.BOLD
                + "\nLEVEL UP!\n" + MessageColor.COLOR_MAIN +"You are now level "
                + MessageColor.COLOR_HIGHLIGHT
                + ((int) getLevelFromXP(player.getXP(), false))
                +"\n\n" + ChatColor.GOLD
                + "+20 coins\n");

        if (!player.isOffline()) {
            player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }
    }

}
