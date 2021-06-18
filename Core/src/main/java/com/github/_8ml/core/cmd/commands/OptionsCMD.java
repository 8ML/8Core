package com.github._8ml.core.cmd.commands;
/*
Created by @8ML (https://github.com/8ML) on 5/17/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.player.options.ui.OptionsUI;
import org.bukkit.entity.Player;

public class OptionsCMD extends CMD {

    public OptionsCMD() {
        super("options", new String[0], "", "/options", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        new OptionsUI(MPlayer.getMPlayer(paramPlayer.getName()));

    }
}
