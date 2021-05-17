package net.clubcraft.core.cmd.commands;
/*
Created by @8ML (https://github.com/8ML) on 5/3/2021
*/

import net.clubcraft.core.cmd.CMD;
import net.clubcraft.core.config.Messages;
import net.clubcraft.core.player.hierarchy.Ranks;
import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.player.stats.ui.StatsUI;
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
