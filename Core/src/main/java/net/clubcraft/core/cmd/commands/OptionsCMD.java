package net.clubcraft.core.cmd.commands;
/*
Created by @8ML (https://github.com/8ML) on 5/17/2021
*/

import net.clubcraft.core.cmd.CMD;
import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.player.hierarchy.Ranks;
import net.clubcraft.core.player.options.ui.OptionsUI;
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
