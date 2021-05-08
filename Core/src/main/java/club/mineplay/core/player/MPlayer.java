package club.mineplay.core.player;
/*
Created by Sander on 4/23/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.hierarchy.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import club.mineplay.core.storage.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("deprecation")
public class MPlayer {

    private static final Map<String, MPlayer> playerMap = new HashMap<>();

    private final String player;
    private final boolean isOffline;

    private Ranks rank;
    private int coins;
    private int xp;
    private long firstJoin;

    private String UUID;

    private final SQL sql = Core.instance.sql;

    public MPlayer(Player player) {

        this.player = player.getName();
        this.UUID = player.getUniqueId().toString();
        this.isOffline = false;

        update();
    }

    public MPlayer(String player) {

        this.player = player;

        try {

            PreparedStatement checkStmt = sql.preparedStatement("SELECT * FROM users WHERE `playerName`=?");
            checkStmt.setString(1, player);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                this.UUID = rs.getString("uuid");
                this.sql.closeConnection(checkStmt);
            }

            this.sql.closeConnection(checkStmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.isOffline = !Bukkit.getOfflinePlayer(java.util.UUID.fromString(getUUID())).isOnline();

        update();

    }

    public MPlayer(MPlayer player) {
        this.UUID = player.getUUID();
        this.player = player.player;
        this.isOffline = player.isOffline;
        this.rank = player.rank;
        this.xp = player.xp;
        this.coins = player.coins;
        update();
    }

    public void update() {
        try {

            PreparedStatement checkStmt = sql.preparedStatement("SELECT * FROM users WHERE uuid=?");
            checkStmt.setString(1, this.getUUID());
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                PreparedStatement createUser = sql.preparedStatement("INSERT INTO users" +
                        " (`uuid`, `playerName`, `rank`, `xp`, `coins`, `firstJoin`) VALUES (?,?,?,?,?,?)");
                createUser.setString(1, this.getUUID());
                createUser.setString(2, player);
                createUser.setString(3, Ranks.DEFAULT.toString());
                createUser.setInt(4, 0);
                createUser.setInt(5, 0);
                createUser.setLong(6, System.currentTimeMillis());
                try {
                    createUser.execute();
                } finally {
                    sql.closeConnection(createUser);
                }
            } else {
                this.rank = Ranks.valueOf(rs.getString("rank"));
                this.xp = rs.getInt("xp");
                this.coins = rs.getInt("coins");
                this.firstJoin = rs.getLong("firstJoin");
            }

            sql.closeConnection(checkStmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Ranks getRankEnum() {
        return this.rank;
    }

    public int getXP() {
        return this.xp;
    }

    public int getCoins() {
        return this.coins;
    }

    public void setRank(Ranks ranks) {
        try {

            PreparedStatement st = sql.preparedStatement("UPDATE users SET `rank`=? WHERE `uuid`=?");
            st.setString(1, ranks.toString());
            st.setString(2, this.UUID);

            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
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

    public String firstJoin() {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(this.firstJoin);
        return format.format(date);
    }

    public boolean isPermissible(Ranks rankEnum) {
        if (this.getRankEnum().getRank().hasPermissionLevel(rankEnum.getRank())) return true;
        else { getPlayer().sendMessage(MessageColor.COLOR_ERROR + "You are not allowed to do this!"); return false;}
    }

    public static void registerMPlayer(String player) {
        if (playerMap.containsKey(player)) playerMap.remove(player, getMPlayer(player));
        playerMap.put(player, new MPlayer(player));
    }

    public static MPlayer getMPlayer(String player) {
        if (!playerMap.containsKey(player)) registerMPlayer(player);
        return playerMap.get(player);
    }

    public static void removeMPlayer(MPlayer player) {
        playerMap.remove(player.getPlayerStr(), player);
    }

    public static boolean exists(String player) {
        try {

            SQL sql = Core.instance.sql;

            PreparedStatement st = sql.preparedStatement("SELECT * FROM users WHERE uuid=?");
            st.setString(1, Bukkit.getOfflinePlayer(player).getUniqueId().toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                sql.closeConnection(st);
                return true;
            }
            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



}
