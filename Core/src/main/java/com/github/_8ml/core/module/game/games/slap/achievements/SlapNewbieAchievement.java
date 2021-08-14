package com.github._8ml.core.module.game.games.slap.achievements;
/*
Created by @8ML (https://github.com/8ML) on July 04 2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.achievement.Achievement;

public class SlapNewbieAchievement extends Achievement {

    public SlapNewbieAchievement() {
        super("slap Newbie", "slap a player into the void", "slap", 100, 200);
    }

    @Override
    protected void onComplete(MPlayer player) { }
}
