package com.github._8ml.core.cmd.commands.test;
/*
Created by @8ML (https://github.com/8ML) on 4/25/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.config.Messages;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.entity.Player;
import com.github._8ml.core.utils.StringUtils;

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
