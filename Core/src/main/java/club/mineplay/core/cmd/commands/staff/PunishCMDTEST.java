package club.mineplay.core.cmd.commands.staff;
/*
Created by Sander on 4/28/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.Messages;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.Punishment;
import club.mineplay.core.punishment.type.Ban;
import club.mineplay.core.punishment.type.Mute;
import org.bukkit.entity.Player;

public class PunishCMDTEST extends CMD {

    public PunishCMDTEST() {
        super("punishtest", new String[0], "", "-", Ranks.STAFF);
    }

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
