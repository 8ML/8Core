package com.github._8ml.core.cmd.commands.staff;
/*
Created by @8ML (https://github.com/8ML) on July 01 2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.entity.Player;

public class VanishCMD extends CMD {

    public VanishCMD() {
        super("vanish", new String[0], "Poof! gone", "/vanish", Ranks.STAFF);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        MPlayer player = MPlayer.getMPlayer(paramPlayer.getName());
        if (!player.isVanished()) {
            player.vanish();
            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Poof! you are now gone.");
        } else {
            player.unVanish();
            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Poof! you are now back.");
        }

    }
}
