/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.hub.events;
/*
Created by @8ML (https://github.com/8ML) on 5/8/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.UpdateEvent;
import com.github._8ml.core.module.hub.item.JoinItems;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.utils.NameTag;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    public JoinEvent(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        MPlayer pl = MPlayer.getMPlayer(e.getPlayer().getName());
        changeTag(pl);
        setScoreboard(pl);
        JoinItems.giveItems(e.getPlayer());
    }

    private int timer = 0;
    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (!e.getType().equals(UpdateEvent.UpdateType.SECONDS)) return;
        timer++;
        if (timer >= 5) {
            timer = 0;

            for (Player p : Core.onlinePlayers) {

                MPlayer pl = MPlayer.getMPlayer(p.getName());

                changeTag(pl);

            }
        }
    }

    private void changeTag(MPlayer player) {
        NameTag.changeTag(player.getPlayer(), player.getRankEnum().getRank().getFullPrefixWithSpace(), "",
                player.getRankEnum().equals(Ranks.DEFAULT) ? ChatColor.GRAY : player.getRankEnum().getRank().getNameColor(), player.getTitle());
    }

    private void setScoreboard(MPlayer player) {

        Core.instance.scoreBoard.setScoreboard(player.getPlayer());

    }

}
