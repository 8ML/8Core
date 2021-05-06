package club.mineplay.core.cmd.commands;
/*
Created by Sander on 5/3/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.Messages;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.stats.ui.StatsUI;
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
                Messages.sendFailedMessage(paramPlayer); return;}

        } else {

            pl = MPlayer.getMPlayer(paramPlayer.getName());

        }
        new StatsUI(MPlayer.getMPlayer(paramPlayer.getName()), pl);

    }
}
