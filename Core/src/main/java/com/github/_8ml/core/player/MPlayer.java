package com.github._8ml.core.player;
/*
Created by @8ML (https://github.com/8ML) on 4/23/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.storage.SQL;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class MPlayer {

    static {

        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS users (`id` INT AUTO_INCREMENT PRIMARY KEY NOT NULL" +
                    ", `uuid` VARCHAR(255) NOT NULL" +
                    ", `playerName` VARCHAR(30) NOT NULL" +
                    ", `rank` VARCHAR(30) NOT NULL" +
                    ", `xp` INT NOT NULL" +
                    ", `coins` INT NOT NULL" +
                    ", `firstJoin` BIGINT NOT NULL" +
                    ", `signature` MEDIUMTEXT" +
                    ", `vanished` BIT NOT NULL)");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static final Map<String, MPlayer> playerMap = new HashMap<>();

    private final String player;
    private final boolean isOffline;
    private final boolean exists;

    private Ranks rank;
    private int coins;
    private int xp;
    private long firstJoin;
    private String signature;
    private boolean vanished;

    private String UUID;

    private final SQL sql = Core.instance.sql;

    public MPlayer(Player player) {

        this.player = player.getName();
        this.UUID = player.getUniqueId().toString();
        this.isOffline = false;
        this.exists = true;

        update();
    }

    public MPlayer(String player) {

        this.player = player;

        if (!exists(player)) {
            this.exists = false;
            this.isOffline = true;
        } else {
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

            this.isOffline = !Core.instance.pluginMessenger.getBungeePlayers().contains(player);
            this.exists = true;

            update();

        }

    }

    public MPlayer(MPlayer player) {
        this.UUID = player.getUUID();
        this.player = player.player;
        this.isOffline = player.isOffline;
        this.rank = player.rank;
        this.xp = player.xp;
        this.coins = player.coins;
        this.signature = player.signature;
        this.exists = player.exists;
        update();
    }

    public MPlayer(UUID uuid) {

        boolean notExists = true;
        String tmpName = "";

        this.UUID = uuid.toString();
        try {
            PreparedStatement st = sql.preparedStatement("SELECT * FROM users WHERE uuid=?");
            st.setString(1, uuid.toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                tmpName = rs.getString("playerName");
                notExists = false;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.exists = !notExists;
        this.isOffline = !exists || !Bukkit.getOfflinePlayer(uuid).isOnline()
                || !Core.instance.pluginMessenger.getBungeePlayers().contains(tmpName);
        this.player = tmpName;

        if (exists) {
            update();
        }

    }

    public void update() {
        try {

            PreparedStatement checkStmt = sql.preparedStatement("SELECT * FROM users WHERE uuid=?");
            checkStmt.setString(1, this.getUUID());
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                PreparedStatement createUser = sql.preparedStatement("INSERT INTO users" +
                        " (`uuid`, `playerName`, `rank`, `xp`, `coins`, `firstJoin`, `signature`, `vanished`) VALUES (?,?,?,?,?,?,?,?)");
                createUser.setString(1, this.getUUID());
                createUser.setString(2, player);
                createUser.setString(3, Ranks.DEFAULT.toString());
                createUser.setInt(4, 0);
                createUser.setInt(5, 0);
                createUser.setLong(6, System.currentTimeMillis());
                createUser.setString(7, "");
                createUser.setBoolean(8, false);
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
                this.signature = ChatColor.translateAlternateColorCodes('&', rs.getString("signature"));
                this.vanished = rs.getBoolean("vanished");
            }

            sql.closeConnection(checkStmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void vanish() {
        set("vanished", true);
        VanishManager.hidePlayer(getPlayer());
    }

    public void unVanish() {
        set("vanished", false);
        VanishManager.unHidePlayer(getPlayer());
    }

    public void setRank(Ranks ranks) {

        if (!exists) return;

        set("rank", ranks.toString());

    }

    public void setTitle(String str) {

        if (!exists) return;

        set("signature", str);

    }

    private void set(String column, Object value) {

        if (!exists) return;

        try {

            PreparedStatement st = sql.preparedStatement("UPDATE users SET `" + column + "`=? WHERE `uuid`=?");
            if (value instanceof Boolean) {
                st.setBoolean(1, (boolean) value);
            } else {
                st.setString(1, value.toString());
            }
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

    public Ranks getRankEnum() {
        return this.rank;
    }

    public int getXP() {
        return this.xp;
    }

    public int getCoins() {
        return this.coins;
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

    public String getTitle() {
        return this.signature;
    }

    public boolean isPermissible(Ranks rankEnum, boolean msg) {
        if (this.getRankEnum().getRank().hasPermissionLevel(rankEnum.getRank())) return true;
        else {
            if (msg) getPlayer().sendMessage(MessageColor.COLOR_ERROR + "You are not allowed to do this!");
            return false;
        }
    }

    public boolean exists() {
        return this.exists;
    }

    public boolean isVanished() {
        return this.vanished;
    }

    public static void registerMPlayer(String player) {
        if (playerMap.containsKey(player)) playerMap.remove(player, getMPlayer(player));
        playerMap.put(player, new MPlayer(player));
    }

    public static void registerMPlayer(Player player) {
        if (playerMap.containsKey(player.getName())) playerMap.remove(player.getName(), getMPlayer(player.getName()));
        playerMap.put(player.getName(), new MPlayer(player));
    }

    public static MPlayer getMPlayer(String player) {
        if (!playerMap.containsKey(player)) registerMPlayer(player);
        return playerMap.get(player);
    }

    public static MPlayer getMPlayer(java.util.UUID uuid) {
        return new MPlayer(uuid);
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
