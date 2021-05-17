package net.clubcraft.core.cmd.commands.admin;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import net.clubcraft.core.cmd.CMD;
import net.clubcraft.core.config.Messages;
import net.clubcraft.core.player.hierarchy.Ranks;
import net.clubcraft.core.player.MPlayer;
import org.bukkit.entity.Player;

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


                Ranks rank = Ranks.valueOf(paramArrayOfString[1]);
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
