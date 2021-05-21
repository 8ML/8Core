package xyz.dev_8.core.cmd.commands.staff;
/*
Created by @8ML (https://github.com/8ML) on 4/29/2021
*/

import xyz.dev_8.core.cmd.CMD;
import xyz.dev_8.core.player.hierarchy.Ranks;
import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.punishment.ui.PunishUI;
import org.bukkit.entity.Player;

public class PunishCMD extends CMD {

    public PunishCMD() {
        super("punish", new String[]{"p"}, "", "/punish <player> <reason>", Ranks.STAFF);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length > 1) {

            StringBuilder reason = new StringBuilder(paramArrayOfString[1]);
            for (int i = 2; i < paramArrayOfString.length; i++) {
                reason.append(" ").append(paramArrayOfString[i]);
            }

            new PunishUI(MPlayer.getMPlayer(paramArrayOfString[0]), MPlayer.getMPlayer(paramPlayer.getName()), reason.toString());


        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}