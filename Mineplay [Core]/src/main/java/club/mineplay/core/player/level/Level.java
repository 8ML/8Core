package club.mineplay.core.player.level;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.currency.Coin;
import club.mineplay.core.storage.SQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Level {

    private static final double multiplier = 14;

    private static final SQL sql = Main.instance.sql;

    public static void addXP(MPlayer player, int xp) {
        try {

            int currentXP = player.getXP();
            int newXP = currentXP + xp;

            if (getLevelFrom(currentXP) < getLevelFrom(newXP)) {
                levelUP(player);
            }

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

    public static int getLevelFrom(int xp) {
        return (int) Math.floor(Math.sqrt(xp) / multiplier);
    }

    private static void levelUP(MPlayer player) {
        Coin.addCoins(player, 20, false);

        player.getPlayer().sendMessage(MessageColor.COLOR_SUCCESS
                + "\nLEVEL UP!\n" + MessageColor.COLOR_MAIN +"You are now level "
                + MessageColor.COLOR_HIGHLIGHT
                + getLevelFrom(player.getXP())
                +"\n\n" + MessageColor.COLOR_HIGHLIGHT
                + "+20 Coins\n");
    }

}
