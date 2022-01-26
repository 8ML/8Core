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
Created by @8ML (https://github.com/8ML) on 5/9/2021
*/

import com.github._8ml.core.bungee.Main;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class QuitEvent implements Listener {

    public QuitEvent(Main plugin) {
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);

        try {
            out.writeUTF("PROXY_QUIT " + e.getPlayer().getName());
            for (ServerInfo server : Main.instance.getProxy().getServers().values()) {
                if (server.getPlayers().isEmpty()) continue;
                server.sendData("BungeeCord", stream.toByteArray());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
