package com.github._8ml.core.module.hub.cosmetic.cosmetics.ui;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.ui.GUI;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages.CosmeticPage;

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
        addPage(new CosmeticPage(this.player));
        openPage(0);
    }
}
