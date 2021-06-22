package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on June 22 2021
*/

import com.github._8ml.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

public class BossBar {

    public static void sendMessage(String msg, BarColor color) {

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

    }

}
