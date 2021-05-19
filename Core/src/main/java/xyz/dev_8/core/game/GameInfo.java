package xyz.dev_8.core.game;
/*
Created by @8ML (https://github.com/8ML) on 5/18/2021
*/

import com.google.common.annotations.Beta;
import xyz.dev_8.core.Core;
import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.storage.SQL;
import org.junit.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Beta
public class GameInfo {

    private static Map<MPlayer, GameInfo> gameInfoMap = new HashMap<>();

    private final MPlayer player;

    private GameInfo(MPlayer player) {
        this.player = player;
    }

    public String getGame() {
        //return get("game");
        return "";
    }

    public String getMap() {
        //return get("map");
        return "";
    }

    private String get(String column) {
        try {

            SQL sql = Core.instance.sql;

            PreparedStatement st = sql.preparedStatement("SELECT * FROM gameInfo WHERE `uuid`=?");
            st.setString(1, this.player.getUUID());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(column);
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public MPlayer getPlayer() {
        return player;
    }

    @Beta
    public static GameInfo getGameInfo(MPlayer player) {
        GameInfo info = gameInfoMap.containsKey(player) ? gameInfoMap.get(player)
                : new GameInfo(player);
        Assert.assertNotNull("Info cannot be null (getGameInfo)", info);
        gameInfoMap.put(player, info);
        return gameInfoMap.get(player);
    }
}
