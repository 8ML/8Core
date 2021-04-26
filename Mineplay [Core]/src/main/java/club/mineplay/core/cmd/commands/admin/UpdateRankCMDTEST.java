package club.mineplay.core.cmd.commands.admin;
/*
Created by Sander on 4/25/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import org.bukkit.entity.Player;

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

                MPlayer player = MPlayer.getMPlayer(paramPlayer);
                Ranks rank = Ranks.valueOf(paramArrayOfString[1]);

                player.setRank(rank);

                paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Success!");

            } catch (Exception e) {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Failed!");
            }
        } else {
            paramPlayer.sendMessage(getUsage());
        }
    }

}