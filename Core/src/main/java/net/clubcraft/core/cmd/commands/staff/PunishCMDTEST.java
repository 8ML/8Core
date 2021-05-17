package net.clubcraft.core.cmd.commands.staff;
/*
Created by @8ML (https://github.com/8ML) on 4/28/2021
*/

import net.clubcraft.core.cmd.CMD;
import net.clubcraft.core.config.Messages;
import net.clubcraft.core.player.hierarchy.Ranks;
import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.punishment.Punishment;
import net.clubcraft.core.punishment.type.Ban;
import net.clubcraft.core.punishment.type.Mute;
import org.bukkit.entity.Player;

public class PunishCMDTEST extends CMD {

    public PunishCMDTEST() {
        super("punishtest", new String[0], "", "-", Ranks.STAFF);
    }

      /*
    WARNING!

    THIS IS A COMMAND FOR TESTING PURPOSES DURING DEVELOPMENT
    THIS COMMAND SHOULD BE DISABLED IMMEDIATELY WHEN DEVELOPMENT IS DONE.

     */

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length == 4) {

            try {

                Punishment.PunishType type = Punishment.PunishType.valueOf(paramArrayOfString[0]);
                Punishment.TimeUnit unit = Punishment.TimeUnit.valueOf(paramArrayOfString[3]);
                Punishment.PunishTime time = new Punishment.PunishTime(unit, Long.parseLong(paramArrayOfString[2]));

                switch (type) {
                    case BAN:
                        new Ban(MPlayer.getMPlayer(paramArrayOfString[1]), MPlayer
                                .getMPlayer(paramPlayer.getName()), time, "This is just a test")
                                .execute();
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                    case MUTE:
                        new Mute(MPlayer.getMPlayer(paramArrayOfString[1]), MPlayer
                                .getMPlayer(paramPlayer.getName()), time, "This is just a test")
                                .execute();
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                }

            } catch (Exception e) {
                Messages.sendFailedMessage(paramPlayer);
            }

        }

    }
}
