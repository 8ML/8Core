package com.github._8ml.core.player.stats.ui;
/*
Created by @8ML (https://github.com/8ML) on 5/5/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.stats.ui.pages.StatsPage;
import com.github._8ml.core.ui.GUI;
import com.github._8ml.core.player.stats.ui.pages.AchievementPage;

public class StatsUI extends GUI {

    private final MPlayer target;

    public StatsUI(MPlayer player, MPlayer target) {
        super(player);

        this.target = target;

        initialize();
    }

    @Override
    public void init() {
        addPage(new StatsPage(target));
        addPage(new AchievementPage(target));
        openPage(0);
    }
}
