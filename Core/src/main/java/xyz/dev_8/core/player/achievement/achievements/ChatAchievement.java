package xyz.dev_8.core.player.achievement.achievements;
/*
Created by @8ML (https://github.com/8ML) on 5/20/2021
*/

import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.player.achievement.Achievement;

public class ChatAchievement extends Achievement {

    public ChatAchievement() {
        super("Chat", "Say something in chat for the first time", 50, 50);
    }

    @Override
    protected void onComplete(MPlayer player) {

    }
}
