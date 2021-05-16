package club.mineplay.core.player.stats.ui;
/*
Created by @8ML (https://github.com/8ML) on 5/5/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.stats.ui.pages.StatsPage;
import club.mineplay.core.ui.GUI;

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
