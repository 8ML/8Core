package com.github._8ml.core.module.hub.cosmetic.cosmetics.ui;
/*
Created by @8ML (https://github.com/8ML) on June 18 2021
*/

import com.github._8ml.core.module.hub.cosmetic.Cosmetic;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages.CosmeticMainPage;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.ui.GUI;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.pages.CosmeticPage;

public class CosmeticUI extends GUI {

    public static Cosmetic.CosmeticType cosmeticMenuSelected = null;

    /**
     * @param player Player to show this gui to
     */
    public CosmeticUI(MPlayer player) {
        super(player);

        initialize();
    }

    @Override
    public void init() {
        addPage(new CosmeticMainPage());
        addPage(new CosmeticPage(getPlayer()));
        openPage(0);
    }
}
