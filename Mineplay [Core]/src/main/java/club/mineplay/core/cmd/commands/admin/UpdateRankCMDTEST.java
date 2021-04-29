package club.mineplay.core.cmd.commands.admin;
/*
Created by Sander on 4/25/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.config.Messages;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UpdateRankCMDTEST extends CMD {

    /*
    WARNING!

    THIS IS A COMMAND FOR TESTING PURPOSES DURING DEVELOPMENT
    THIS COMMAND SHOULD BE DISABLED IMMEDIATELY WHEN DEVELOPMENT IS DONE.


     */

    public UpdateRankCMDTEST() {
        super("ranktest", new String[0], "", "/rank <player> <rank>", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {
        if (paramArrayOfString.length == 2) {
            try {

                MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);

                Ranks rank = Ranks.valueOf(paramArrayOfString[1]);
                player.setRank(rank);

                Messages.sendSuccessMessage(paramPlayer);

            } catch (Exception e) {
                Messages.sendFailedMessage(paramPlayer);
            }
        } else {
            paramPlayer.sendMessage(getUsage());
        }
    }

}
