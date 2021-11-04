package com.github._8ml.core.module.game.games.platform.achievements;
/*
Created by @8ML (https://github.com/8ML) on August 14 2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.achievement.Achievement;

public class PlatformSurvivorAchievement extends Achievement {

    public PlatformSurvivorAchievement() {
        super("Survivor", "Win a game!", "Platform", 100, 500);
    }

    @Override
    protected void onComplete(MPlayer player) {

    }
}
