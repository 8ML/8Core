/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.player.options;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.Core;
import com.github._8ml.core.storage.SQL;
import org.bukkit.Material;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerOptions {

    static {

        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS preferences (`uuid` VARCHAR(255) NOT NULL" +
                    ", `key` VARCHAR(255) NOT NULL" +
                    ", `value` VARCHAR(255) NOT NULL)");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public enum Preference {

        /**
         * Contains all the preference types with its values
         * <p>
         * Values in index 1 will be the default value
         */

        PRIVATE_MESSAGE(new String[]{
                "FRIENDS_ONLY",
                "ANYONE",
                "OFF"
        }, Material.PAPER, 15),
        FRIEND_REQUEST(new String[]{
                "ANYONE",
                "OFF"
        }, Material.POPPY, 11),
        MENTION(new String[]{
                "ANYONE",
                "FRIENDS_ONLY",
                "OFF"
        }, Material.NOTE_BLOCK, 13);

        private final String[] values;
        private final Material uiMaterial;
        private final int uiSlot;


        /**
         *
         * @param values The values of the preference
         * @param uiMaterial The material to display in Preferences UI
         * @param uiSlot The slot to display it in Preferences UI
         */
        Preference(String[] values, Material uiMaterial, int uiSlot) {
            this.values = values;
            this.uiMaterial = uiMaterial;
            this.uiSlot = uiSlot;
        }

        public String[] getValues() {
            return values;
        }

        public Material getUiMaterial() {
            return uiMaterial;
        }

        public int getUiSlot() {
            return uiSlot;
        }
    }

    private static final Map<MPlayer, Map<Preference, String>> preferences = new HashMap<>();


    /**
     * @param player     Player to update preference for
     * @param preference Preference to update
     * @param value      New preference value
     */
    public static void updatePreference(MPlayer player, Preference preference, String value) {

        SQL sql = Core.instance.sql;

        try {

            PreparedStatement exists = sql.preparedStatement("SELECT * FROM preferences WHERE `uuid`=? AND `key`=?");
            exists.setString(1, player.getUUID());
            exists.setString(2, preference.name());
            ResultSet rs = exists.executeQuery();
            if (rs.next()) {
                PreparedStatement st = sql.preparedStatement("UPDATE preferences SET `value`=? WHERE `uuid`=? AND `key`=?");
                st.setString(1, value);
                st.setString(2, player.getUUID());
                st.setString(3, preference.name());
                try {
                    st.executeUpdate();
                } finally {
                    sql.closeConnection(st);
                }
            } else {

                PreparedStatement st = sql.preparedStatement("INSERT INTO preferences (`uuid`, `key`, `value`) VALUES (?,?,?)");
                st.setString(1, player.getUUID());
                st.setString(2, preference.name());
                st.setString(3, value);

                try {
                    st.execute();
                } finally {
                    sql.closeConnection(st);
                }

            }

            sql.closeConnection(exists);

            Map<Preference, String> preferencesMap = preferences.get(player);
            preferencesMap.put(preference, value);
            preferences.put(player, preferencesMap);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * @param player Player to fetch preferences from
     */
    public static void fetchPreferences(MPlayer player) {
        try {

            SQL sql = Core.instance.sql;

            Map<Preference, String> preferencesMap;

            if (!preferences.containsKey(player)) {
                preferencesMap = new HashMap<>();
            } else preferencesMap = preferences.get(player);

            boolean found = false;

            PreparedStatement st = sql.preparedStatement("SELECT * FROM preferences WHERE `uuid`=?");
            st.setString(1, player.getUUID());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                preferencesMap.put(Preference.valueOf(rs.getString("key")), rs.getString("value"));

            }

            preferences.put(player, preferencesMap);

            sql.closeConnection(st);

            for (Preference pref : Preference.values()) {

                if (!check(player, pref)) {
                    updatePreference(player, pref, pref.getValues()[0]);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param player     Player to check preference
     * @param preference Preference to check
     * @return Will return true if the preference is found in the database for the specified player
     */
    private static boolean check(MPlayer player, Preference preference) {
        try {

            SQL sql = Core.instance.sql;

            PreparedStatement st = sql.preparedStatement("SELECT * FROM preferences WHERE `uuid`=? AND `key`=?");
            st.setString(1, player.getUUID());
            st.setString(2, preference.name());

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


    /**
     * @param player     Player to get preference from
     * @param preference Preference to get
     * @return Returns the preference value
     */
    public static String getPreference(MPlayer player, Preference preference) {
        if (preferences.containsKey(player)) {

            Map<Preference, String> enumMap = preferences.get(player);
            if (enumMap.containsKey(preference)) {
                String enumKey = enumMap.get(preference);

                String[] values = preference.getValues();
                for (String value : values) {
                    if (enumKey.equals(value)) {
                        return value;
                    }
                }
            }

        }

        return preference.getValues()[0];
    }
}
