/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.player.currency;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.Core;
import com.github._8ml.core.storage.SQL;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Coin {

    private final static SQL sql = Core.instance.sql;


    /**
     * This will add the specified coins to the specified player.
     *
     * @param player The player to add to
     * @param coins  The coins to add
     * @param msg    If true, this will send a message with a sound to the player
     */
    public static void addCoins(MPlayer player, int coins, boolean msg) {

        try {

            int currentCoins = player.getCoins();
            int newCoins = currentCoins + coins;

            PreparedStatement st = sql.preparedStatement("UPDATE users SET coins=? WHERE uuid=?");
            st.setInt(1, newCoins);
            st.setString(2, player.getUUID());

            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);

                if (player.getPlayer() != null) {
                    player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 3f);
                    if (msg) player.getPlayer().sendMessage(ChatColor.GOLD + "+" + coins + " coins");
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        player.update();

    }


    /**
     * This will remove the specified coins from the specified player
     *
     * @param player The player to remove from
     * @param coins  The coins to remove
     */
    public static void removeCoins(MPlayer player, int coins) {
        try {

            int currentCoins = player.getCoins();
            int newCoins = currentCoins - coins;

            PreparedStatement st = sql.preparedStatement("UPDATE users SET coins=? WHERE uuid=?");
            st.setInt(1, newCoins);
            st.setString(2, player.getUUID());

            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        player.update();
    }


    /**
     * This will set the specified player's coins to the specified coins
     *
     * @param player The player to set the coins
     * @param coins  The coins to set
     */
    public static void setCoins(MPlayer player, int coins) {
        try {

            PreparedStatement st = sql.preparedStatement("UPDATE users SET coins=? WHERE uuid=?");
            st.setInt(1, coins);
            st.setString(2, player.getUUID());

            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        player.update();
    }


    /**
     * This will reset the specified player's coins
     *
     * @param player The player to reset
     */
    public static void resetCoins(MPlayer player) {
        try {

            PreparedStatement st = sql.preparedStatement("UPDATE users SET coins=? WHERE uuid=?");
            st.setInt(1, 0);
            st.setString(2, player.getUUID());

            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        player.update();
    }

}
