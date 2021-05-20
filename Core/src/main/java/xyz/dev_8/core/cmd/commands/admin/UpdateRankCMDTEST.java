package xyz.dev_8.core.cmd.commands.admin;
/*
Created by @8ML (https://github.com/8ML) on 4/25/2021
*/

import xyz.dev_8.core.cmd.CMD;
import xyz.dev_8.core.config.Messages;
import xyz.dev_8.core.player.hierarchy.Ranks;
import xyz.dev_8.core.player.MPlayer;
import org.bukkit.entity.Player;
import xyz.dev_8.core.utils.StringUtils;

public class UpdateRankCMDTEST extends CMD {

    /*
    WARNING!

    THIS IS A COMMAND FOR TESTING PURPOSES DURING DEVELOPMENT
    THIS COMMAND SHOULD BE DISABLED IMMEDIATELY WHEN DEVELOPMENT IS DONE.

     */

    public UpdateRankCMDTEST() {
        super("ranktest", new String[0], "", "/ranktest <player> <rank>", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {
        if (paramArrayOfString.length == 2) {
            try {

                MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);

                Ranks rank = Ranks.valueOf(StringUtils.replaceMultiple(paramArrayOfString[1].toUpperCase(),
                        new String[]{"BUILDER", "BUILDTEAM", "BUILD"},
                        "BUILD_TEAM"));

                player.setRank(rank);
                player.update();

                Messages.sendSuccessMessage(paramPlayer);

            } catch (Exception e) {
                Messages.sendFailedMessage(paramPlayer);
            }
        } else {
            paramPlayer.sendMessage(getUsage());
        }
    }

}
