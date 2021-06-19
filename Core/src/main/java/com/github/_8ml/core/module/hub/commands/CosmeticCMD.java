package com.github._8ml.core.module.hub.commands;
/*
Created by @8ML (https://github.com/8ML) on June 19 2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.module.hub.cosmetic.cosmetics.ui.CosmeticGUI;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.entity.Player;

public class CosmeticCMD extends CMD {

    public CosmeticCMD() {
        super("cosmetic", new String[0], "", "/cosmetic", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        new CosmeticGUI(MPlayer.getMPlayer(paramPlayer.getName()));

    }
}
