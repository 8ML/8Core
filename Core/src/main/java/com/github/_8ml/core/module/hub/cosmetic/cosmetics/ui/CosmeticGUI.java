package com.github._8ml.core.module.hub.cosmetic.cosmetics.ui;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages.CosmeticsPage;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.ui.GUI;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages.GadgetsPage;

public class CosmeticGUI extends GUI {


    /**
     * @param player Player to show this gui to
     */
    public CosmeticGUI(MPlayer player) {
        super(player);

        initialize();
    }

    @Override
    public void init() {
        addPage(new CosmeticsPage());
        addPage(new GadgetsPage(getPlayer()));
        openPage(0);
    }
}
