package com.github._8ml.core.player.achievement.achievements;
/*
Created by @8ML (https://github.com/8ML) on 5/20/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.achievement.Achievement;

public class ChatAchievement extends Achievement {

    public ChatAchievement() {
        super("Chat", "Say something in chat for the first time", 50, 50);
    }

    @Override
    protected void onComplete(MPlayer player) {

    }
}
