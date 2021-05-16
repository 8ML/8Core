package club.mineplay.core.player.options.ui;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.options.ui.pages.OptionsPage;
import club.mineplay.core.ui.GUI;

public class OptionsUI extends GUI {

    private final MPlayer player;

    public OptionsUI(MPlayer player) {
        super(player);
        this.player = player;
        initialize();
    }

    @Override
    public void init() {
        addPage(new OptionsPage(this.player));
        openPage(0);
    }
}
