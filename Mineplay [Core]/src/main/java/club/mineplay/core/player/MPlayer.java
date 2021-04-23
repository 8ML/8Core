package club.mineplay.core.player;
/*
Created by Sander on 4/23/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.hierarchy.Ranks;
import org.bukkit.entity.Player;
import club.mineplay.core.storage.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MPlayer {

    private Player player;
    private String UUID;

    private final SQL sql = Main.instance.sql;

    public MPlayer(Player player) {

        this.player = player;
        this.UUID = club.mineplay.core.player.UUID.getRawUUID(player);

        try {

            PreparedStatement checkStmt = sql.preparedStatement("SELECT * FROM users WHERE uuid=?");
            checkStmt.setString(1, club.mineplay.core.player.UUID.getRawUUID(player));
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                PreparedStatement createUser = sql.preparedStatement("INSERT INTO users (uuid, playerName, rank, xp, coins) VALUES (?,?,?,?,?)");
                createUser.setString(1, club.mineplay.core.player.UUID.getRawUUID(player));
                createUser.setString(2, player.getName());
                createUser.setString(3, Ranks.DEFAULT.toString());
                createUser.setInt(4, 0);
                createUser.setInt(5, 100);
                try {
                    createUser.execute();
                } finally {
                    sql.getConnection().close();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Ranks getRank() {
        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM users WHERE uuid=?");
            st.setString(1, this.UUID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Ranks rank = Ranks.valueOf(rs.getString("rank"));
                sql.getConnection().close();
                return rank;
            }

            sql.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Ranks.DEFAULT;
    }

}
