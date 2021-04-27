package club.mineplay.core.player;
/*
Created by Sander on 4/23/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.currency.Coin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import club.mineplay.core.storage.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MPlayer {

    private static final Map<String, MPlayer> playerMap = new HashMap<>();

    private String player;
    private String UUID;
    private boolean isOffline;

    private final SQL sql = Main.instance.sql;

    public MPlayer(Player player) {

        this.player = player.getName();
        this.UUID = player.getUniqueId().toString();
        this.isOffline = false;

        try {

            PreparedStatement checkStmt = sql.preparedStatement("SELECT * FROM users WHERE uuid=?");
            checkStmt.setString(1, this.getUUID());
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                PreparedStatement createUser = sql.preparedStatement("INSERT INTO users (`uuid`, `playerName`, `rank`, `xp`, `coins`) VALUES (?,?,?,?,?)");
                createUser.setString(1, this.getUUID());
                createUser.setString(2, player.getName());
                createUser.setString(3, Ranks.DEFAULT.toString());
                createUser.setInt(4, 0);
                createUser.setInt(5, 0);
                try {
                    createUser.execute();
                } finally {
                    sql.getConnection().close();
                    Coin.addCoins(this, 50, false);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MPlayer(String player) {

        this.player = player;
        this.isOffline = true;

        try {

            PreparedStatement checkStmt = sql.preparedStatement("SELECT * FROM users WHERE `playerName`=?");
            checkStmt.setString(1, player);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                this.UUID = rs.getString("uuid");
                this.sql.getConnection().close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MPlayer(MPlayer player) {
        this.UUID = player.getUUID();
        this.player = player.player;
        this.isOffline = player.isOffline;
    }

    public Ranks getRankEnum() {
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

    public int getXP() {
        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM users WHERE uuid=?");
            st.setString(1, this.UUID);
            ResultSet rs = st.executeQuery();
            if (rs.next())  {

                int xp = rs.getInt("xp");
                sql.getConnection().close();
                return xp;

            }

            sql.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getCoins() {
        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM users WHERE uuid=?");
            st.setString(1, this.UUID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                int coins = rs.getInt("coins");
                sql.getConnection().close();
                return coins;

            }

            sql.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void setRank(Ranks ranks) {
        try {

            PreparedStatement st = sql.preparedStatement("UPDATE users SET `rank`=? WHERE `uuid`=?");
            st.setString(1, ranks.toString());
            st.setString(2, this.UUID);

            try {
                st.executeUpdate();
            } finally {
                sql.getConnection().close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {

        if (Bukkit.getServer().getOfflinePlayer(this.player).isOnline()) {
            return Bukkit.getPlayer(this.player);
        }

        return null;
    }

    public String getPlayerStr() {
        return this.player;
    }

    public boolean isOffline() {
        return this.isOffline;
    }

    public String getUUID() {
        return this.UUID;
    }

    public boolean isPermissible(Ranks rankEnum) {
        if (this.getRankEnum().getRank().hasPermissionLevel(rankEnum.getRank())) return true;
        else { getPlayer().sendMessage(MessageColor.COLOR_ERROR + "You are not allowed to do this!"); return false;}
    }

    public static void registerMPlayer(Player player) {
        if (playerMap.containsKey(player)) return;
        playerMap.put(player.getName(), new MPlayer(player));
    }

    public static MPlayer getMPlayer(String player) {
        if (!playerMap.containsKey(player)) return new MPlayer(player);
        else return new MPlayer(playerMap.get(player));
    }

    public static boolean exists(String player) {
        try {

            SQL sql = Main.instance.sql;

            PreparedStatement st = sql.preparedStatement("SELECT * FROM users WHERE uuid=?");
            st.setString(1, Bukkit.getOfflinePlayer(player).getUniqueId().toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                sql.getConnection().close();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
