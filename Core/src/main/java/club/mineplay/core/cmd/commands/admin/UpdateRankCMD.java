package club.mineplay.core.cmd.commands.admin;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.Messages;
import club.mineplay.core.player.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
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
