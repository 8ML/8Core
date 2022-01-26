/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.security;
/*
Created by @8ML (https://github.com/8ML) on June 20 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.storage.SQL;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class is an extra security measure to prevent players from directly joining the individual
 * servers. Since the servers are offline, any player could impersonate another player since mojang
 * does not validate the account on offline servers.
 *
 * Bungee cord has "inbuilt" security for this if you set bungeecord = true in spigot.yml but if you forget
 * this, then this class will come in handy.
 *
 * The bungee plugin will add the player to the proxyPlayer table when a player joins, when the player then joins
 * the spigot server, it checks if the players name is located there. If it is, then it will allow them to join,
 * if not then it wil disallow them.
 */
public class OnlyProxyJoin implements Listener {

    static {

        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS proxy (`proxyPlayer` VARCHAR(100) PRIMARY KEY NOT NULL)");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public OnlyProxyJoin() {
        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);
    }

    private final SQL sql = Core.instance.sql;

    private boolean proxyCheck(Player player) {

        try {
            PreparedStatement st = sql.preparedStatement("SELECT * FROM proxy WHERE proxyPlayer=?");
            st.setString(1, player.getName());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                PreparedStatement del = sql.preparedStatement("DELETE FROM proxy WHERE proxyPlayer=?");
                del.setString(1, player.getName());
                del.execute();

                sql.closeConnection(st);
                sql.closeConnection(del);
                return true;
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    @EventHandler
    public void onConnect(PlayerLoginEvent e) {

        if (!proxyCheck(e.getPlayer())) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, MessageColor.COLOR_ERROR + "Could not establish connection!\n\n" +
                    "You have to connect using" + MessageColor.COLOR_HIGHLIGHT + " dev-8.com" + MessageColor.COLOR_ERROR + "!");
        }

    }

}
