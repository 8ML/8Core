package com.github._8ml.core.cmd.commands;
/*
Created by @8ML (https://github.com/8ML) on 5/3/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.stats.ui.StatsUI;
import com.github._8ml.core.config.Messages;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.entity.Player;

public class StatsCMD extends CMD {

    public StatsCMD() {
        super("stats", new String[0], "", "/stats [player]", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        MPlayer pl;
        if (paramArrayOfString.length == 1) {

            if (MPlayer.exists(paramArrayOfString[0])) pl = MPlayer.getMPlayer(paramArrayOfString[0]);
            else {
                Messages.sendFailedMessage(paramPlayer); return;
            }

        } else {

            pl = MPlayer.getMPlayer(paramPlayer.getName());

        }
        new StatsUI(MPlayer.getMPlayer(paramPlayer.getName()), pl);

    }
}
