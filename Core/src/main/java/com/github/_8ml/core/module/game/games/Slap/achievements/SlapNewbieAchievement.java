package com.github._8ml.core.module.game.games.Slap.achievements;
/*
Created by @8ML (https://github.com/8ML) on July 04 2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.achievement.Achievement;

public class SlapNewbieAchievement extends Achievement {

    public SlapNewbieAchievement() {
        super("Slap Newbie", "Slap a player into the void", "Slap", 100, 200);
    }

    @Override
    protected void onComplete(MPlayer player) { }
}
