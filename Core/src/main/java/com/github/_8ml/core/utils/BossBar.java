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
