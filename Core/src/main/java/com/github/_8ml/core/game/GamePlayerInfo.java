package com.github._8ml.core.game;
/*
Created by @8ML (https://github.com/8ML) on 5/18/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.storage.SQL;
import com.google.common.annotations.Beta;
import com.github._8ml.core.Core;
import org.junit.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Beta
public class GamePlayerInfo {

    static {

        try {

            Core.instance.sql.createTable(
                    "CREATE TABLE IF NOT EXISTS gamePlayerInfo " +
                            "(`uuid` VARCHAR(255)" +
                            ", `game` VARCHAR(255)" +
                            ", `map` VARCHAR(255))"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static Map<MPlayer, GamePlayerInfo> gamePlayerInfoMap = new HashMap<>();

    private final MPlayer player;

    private GamePlayerInfo(MPlayer player) {
        this.player = player;
    }

    public void set(String column, String value) {
        try {

            SQL sql = Core.instance.sql;

            PreparedStatement st = sql.preparedStatement("UPDATE gamePlayerInfo SET `" + column + "`=? WHERE `uuid`=?");
            st.setString(1, value);
            st.setString(2, player.getUUID());

            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getGame() {
        return get("game");
    }

    public String getMap() {
        return get("map");
    }

    private String get(String column) {
        try {

            SQL sql = Core.instance.sql;

            PreparedStatement st = sql.preparedStatement("SELECT * FROM gamePlayerInfo WHERE `uuid`=?");
            st.setString(1, this.player.getUUID());
            ResultSet rs = st.executeQuery();
            String result = "";
            if (rs.next()) {
                result = rs.getString(column);
            }
            sql.closeConnection(st);

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public MPlayer getPlayer() {
        return player;
    }

    @Beta
    public static GamePlayerInfo getGameInfo(MPlayer player) {
        GamePlayerInfo info = gamePlayerInfoMap.containsKey(player) ? gamePlayerInfoMap.get(player)
                : new GamePlayerInfo(player);
        Assert.assertNotNull("Info cannot be null (getGameInfo)", info);
        gamePlayerInfoMap.put(player, info);
        return gamePlayerInfoMap.get(player);
    }
}
