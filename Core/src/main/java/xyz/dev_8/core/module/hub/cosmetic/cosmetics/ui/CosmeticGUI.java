package xyz.dev_8.core.module.hub.cosmetic.cosmetics.ui;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.ui.GUI;

public class CosmeticGUI extends GUI {

    private final MPlayer player;

    /**
     * @param player Player to show this gui to
     */
    public CosmeticGUI(MPlayer player) {
        super(player);
        this.player = player;
    }

    @Override
    public void init() {

    }
}
