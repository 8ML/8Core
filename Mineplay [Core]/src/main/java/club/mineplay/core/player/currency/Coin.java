package club.mineplay.core.player.currency;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.storage.SQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Coin {

    private final static SQL sql = Main.instance.sql;

    public static void addCoins(MPlayer player, int coins) {

        try {

            int currentCoins = player.getCoins();
            int newCoins = currentCoins + coins;

            PreparedStatement st = sql.preparedStatement("UPDATE users SET coins=? WHERE uuid=?");
            st.setInt(1, newCoins);
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

    public static void removeCoins(MPlayer player, int coins) {
        try {

            int currentCoins = player.getCoins();
            int newCoins = currentCoins - coins;

            PreparedStatement st = sql.preparedStatement("UPDATE users SET coins=? WHERE uuid=?");
            st.setInt(1, newCoins);
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

    public static void resetCoins(MPlayer player) {
        try {

            PreparedStatement st = sql.preparedStatement("UPDATE users SET coins=? WHERE uuid=?");
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

}
