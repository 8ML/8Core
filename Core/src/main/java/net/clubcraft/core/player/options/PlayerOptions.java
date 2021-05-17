package net.clubcraft.core.player.options;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import net.clubcraft.core.Core;
import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.storage.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PlayerOptions {

    public enum PrivateMessagePreference {
        FRIENDS_ONLY, ANYONE, OFF
    }

    public static void updatePreference(MPlayer player, String preference, String value) {

        SQL sql = Core.instance.sql;

        try {

            PreparedStatement exists = sql.preparedStatement("SELECT * FROM preferences WHERE `uuid`=? AND `key`=?");
            exists.setString(1, player.getUUID());
            exists.setString(2, preference);
            ResultSet rs = exists.executeQuery();
            if (rs.next()) {
                PreparedStatement st = sql.preparedStatement("UPDATE preferences SET `value`=? WHERE `uuid`=? AND `key`=?");
                st.setString(1, value);
                st.setString(2, player.getUUID());
                st.setString(3, preference);
                try {
                    st.executeUpdate();
                } finally {
                    sql.closeConnection(st);
                }
            } else {

                PreparedStatement st = sql.preparedStatement("INSERT INTO preferences (`uuid`, `key`, `value`) VALUES (?,?,?)");
                st.setString(1, player.getUUID());
                st.setString(2, preference);
                st.setString(3, value);

                try {
                    st.execute();
                } finally {
                    sql.closeConnection(st);
                }

            }

            sql.closeConnection(exists);

            Map<String, String> preferencesMap = preferences.get(player);
            preferencesMap.put(preference, value);
            preferences.put(player, preferencesMap);



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void fetchPreferences(MPlayer player) {
        try {

            SQL sql = Core.instance.sql;

            Map<String, String> preferencesMap;

            if (!preferences.containsKey(player)) {
                preferencesMap = new HashMap<>();
            } else preferencesMap = preferences.get(player);

            PreparedStatement st = sql.preparedStatement("SELECT * FROM preferences WHERE `uuid`=?");
            st.setString(1, player.getUUID());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                preferencesMap.put(rs.getString("key"), rs.getString("value"));

            }

            preferences.put(player, preferencesMap);

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static final Map<MPlayer, Map<String, String>> preferences = new HashMap<>();
}
