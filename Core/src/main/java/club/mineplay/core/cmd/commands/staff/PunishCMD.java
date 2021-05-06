package club.mineplay.core.cmd.commands.staff;
/*
Created by Sander on 4/29/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.ui.PunishUI;
import org.bukkit.entity.Player;

public class PunishCMD extends CMD {

    public PunishCMD() {
        super("punish", new String[]{"p"}, "", "/punish <player> <reason>", Ranks.STAFF);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length > 1) {

            StringBuilder reason = new StringBuilder();
            for (int i = 1; i < paramArrayOfString.length; i++) {
                if (i == paramArrayOfString.length - 1) {
                    reason.append(paramArrayOfString[i]);
                } else {
                    reason.append(paramArrayOfString[i]).append(" ");
                }
            }

            new PunishUI(MPlayer.getMPlayer(paramArrayOfString[0]), MPlayer.getMPlayer(paramPlayer.getName()), reason.toString());


        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}
