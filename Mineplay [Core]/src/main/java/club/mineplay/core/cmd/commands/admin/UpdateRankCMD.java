package club.mineplay.core.cmd.commands.admin;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UpdateRankCMD extends CMD {

    public UpdateRankCMD() {
        super("rank", new String[0], "", "/rank <player> <rank>", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {
        if (paramArrayOfString.length == 2) {
            try {

                MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);

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
