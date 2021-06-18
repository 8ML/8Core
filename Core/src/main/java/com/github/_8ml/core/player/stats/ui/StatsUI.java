package com.github._8ml.core.player.stats.ui;
/*
Created by @8ML (https://github.com/8ML) on 5/5/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.stats.ui.pages.StatsPage;
import com.github._8ml.core.ui.GUI;
import com.github._8ml.core.player.stats.ui.pages.AchievementPage;

public class StatsUI extends GUI {

    private final MPlayer t;

    public StatsUI(MPlayer player, MPlayer t) {
        super(player);

        this.t = t;

        initialize();
    }

    @Override
    public void init() {
        addPage(new StatsPage(t));
        addPage(new AchievementPage(t));
        openPage(0);
    }
}
