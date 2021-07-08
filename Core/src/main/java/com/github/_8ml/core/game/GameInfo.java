package com.github._8ml.core.game;
/*
Created by @8ML (https://github.com/8ML) on July 04 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.storage.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameInfo {

    static {

        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS gameInfo (" +
                    "`serverName` VARCHAR(255) PRIMARY KEY NOT NULL" +
                    ", `gameType` VARCHAR(255) NOT NULL" +
                    ", `onlinePlayers` INT NOT NULL" +
                    ", `minPlayers` INT NOT NULL" +
                    ", `maxPlayers` INT NOT NULL" +
                    ", `state` VARCHAR(255) NOT NULL)"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static final SQL sql = Core.instance.sql;

    private final String serverName;

    private String gameType;
    private int maxPlayers;
    private int minPlayers;
    private int onlinePlayers;
    private String state;

    private GameInfo(String serverName) {
        this.serverName = serverName;

        if (serverName.equals("")) return;

        fetch();

    }

    private boolean fetch() {
        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM gameInfo WHERE `serverName`=?");
            st.setString(1, serverName);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                this.gameType = rs.getString("gameType");
                this.maxPlayers = rs.getInt("maxPlayers");
                this.minPlayers = rs.getInt("minPlayers");
                this.onlinePlayers = rs.getInt("onlinePlayers");
                this.state = rs.getString("state");

                sql.closeConnection(st);

                return true;
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void update(String column, Object value) {

        try {

            PreparedStatement st = sql.preparedStatement("UPDATE gameInfo SET `" + column + "`=? WHERE `serverName`=?");
            if (value instanceof Integer) st.setInt(1, (int) value);
            else st.setString(1, String.valueOf(value));
            st.setString(2, serverName);

            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void create(String gameType, int maxPlayers, int minPlayers, int onlinePlayers) {

        try {
            PreparedStatement create = sql.preparedStatement("INSERT INTO gameInfo (" +
                    "`serverName`" +
                    ", `gameType`" +
                    ", `onlinePlayers`" +
                    ", `minPlayers`" +
                    ", `maxPlayers`" +
                    ", `state`" +
                    ") VALUES (" +
                    "?,?,?,?,?,?" +
                    ")");

            create.setString(1, serverName);
            create.setString(2, gameType);
            create.setInt(3, onlinePlayers);
            create.setInt(4, minPlayers);
            create.setInt(5, maxPlayers);
            create.setString(6, "Waiting");

            try {
                create.execute();
            } finally {
                sql.closeConnection(create);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getServerName() {
        return serverName;
    }

    public String getGameType() {
        return gameType;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public String getState() {
        return state;
    }

    public boolean isOffline() {
        return state.equalsIgnoreCase("Offline");
    }

    public static void storeInfo(String serverName, String gameType, int maxPlayers, int minPlayers, int onlinePlayers) {

        GameInfo info = new GameInfo(serverName);
        if (!info.fetch()) {

            info.create(gameType, maxPlayers, minPlayers, onlinePlayers);

        }

    }

    public static void updateInfo(String serverName, String column, Object value) {

        GameInfo info = new GameInfo(serverName);
        info.update(column, value);

    }

    public static GameInfo getInfo(String serverName) {
        return new GameInfo(serverName);
    }

    public static List<GameInfo> getServers() {

        List<GameInfo> servers = new ArrayList<>();

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM gameInfo");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                servers.add(getInfo(rs.getString("serverName")));
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servers;

    }

    public static List<GameInfo> getServers(String type) {

        List<GameInfo> servers = new ArrayList<>();

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM gameInfo WHERE `gameType`=?");
            st.setString(1, type);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                servers.add(getInfo(rs.getString("serverName")));
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servers;
    }

    public static void init() {
        new GameInfo("");
    }
}
