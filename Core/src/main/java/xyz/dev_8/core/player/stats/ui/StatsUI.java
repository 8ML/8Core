package xyz.dev_8.core.player.stats.ui;
/*
Created by @8ML (https://github.com/8ML) on 5/5/2021
*/

import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.player.stats.ui.pages.StatsPage;
import xyz.dev_8.core.ui.GUI;

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
        openPage(0);
    }
}
