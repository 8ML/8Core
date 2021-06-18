package com.github._8ml.core.cmd.commands.admin;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.config.Messages;
import org.bukkit.entity.Player;
import com.github._8ml.core.utils.StringUtils;

public class UpdateRankCMD extends CMD {

    public UpdateRankCMD() {
        super("rank", new String[0], "", "/rank <player> <rank>", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {
        if (paramArrayOfString.length == 2) {
            try {

                MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);
                if (!player.exists()) {
                    Messages.sendFailedMessage(paramPlayer);
                    return;
                }


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
