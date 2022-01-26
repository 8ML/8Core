/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.bungee.events;
/*
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import com.github._8ml.core.bungee.Main;
import com.github._8ml.core.bungee.storage.SQL;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinEvent implements Listener {

    public JoinEvent(Plugin plugin) {
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    private final SQL sql = Main.instance.sql;

    @EventHandler
    public void onServerConnect(ServerConnectEvent e) {

        System.out.println(e.getPlayer().getName() + " Connected!");

        try {

            PreparedStatement exists = sql.preparedStatement("SELECT * FROM proxy WHERE proxyPlayer=?");
            exists.setString(1, e.getPlayer().getName());
            ResultSet rs = exists.executeQuery();
            if (!rs.next()) {
                PreparedStatement st = sql.preparedStatement("INSERT INTO proxy (proxyPlayer) VALUES(?)");
                st.setString(1, e.getPlayer().getName());
                try {
                    st.execute();
                } finally {
                    sql.getConnection().close();
                }
            }
            sql.getConnection().close();

            if (e.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(stream);

                try {
                    out.writeUTF("PROXY_JOIN " + e.getTarget().getName() + " " + e.getPlayer().getName());
                    for (ServerInfo server : Main.instance.getProxy().getServers().values()) {
                        if (server.getPlayers().isEmpty()) continue;
                        server.sendData("BungeeCord", stream.toByteArray());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
