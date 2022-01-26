/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on June 22 2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.events.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Boss;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.*;

public class BossBar implements Listener {

    private final static List<org.bukkit.boss.BossBar> bossBars = new ArrayList<>();
    private final static Map<org.bukkit.boss.BossBar, Long> bossBarTimer = new HashMap<>();
    private final static long secondsToClear = 20;

    public BossBar(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void sendMessage(String msg, BarColor color) {

        if (!bossBars.isEmpty()) {

            Iterator<org.bukkit.boss.BossBar> it = bossBars.iterator();

            while(it.hasNext()) {
                org.bukkit.boss.BossBar bar = it.next();
                bar.setVisible(!bar.isVisible());
                it.remove();
            }

        }


        org.bukkit.boss.BossBar bossBar = Bukkit.createBossBar(
                msg, color, BarStyle.SOLID
        );

        bossBar.removeFlag(BarFlag.CREATE_FOG);
        bossBar.removeFlag(BarFlag.DARKEN_SKY);
        bossBar.removeFlag(BarFlag.PLAY_BOSS_MUSIC);

        for (Player p : Core.onlinePlayers) {
            bossBar.addPlayer(p);
        }

        bossBar.setVisible(true);
        bossBars.add(bossBar);
        bossBarTimer.put(bossBar, System.currentTimeMillis() + (secondsToClear * 1000));

    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {

        if (e.getType().equals(UpdateEvent.UpdateType.SECONDS)) {

            Iterator<org.bukkit.boss.BossBar> it = bossBarTimer.keySet().iterator();

            while(it.hasNext()) {
                org.bukkit.boss.BossBar bars = it.next();
                if (bossBarTimer.get(bars) <= System.currentTimeMillis()) {

                    bars.setVisible(false);
                    bossBars.remove(bars);

                    it.remove();

                }
            }

        }

    }

}
