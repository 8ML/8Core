package xyz.dev_8.core.cmd.commands.admin;
/*
Created by @8ML (https://github.com/8ML) on 5/3/2021
*/

import xyz.dev_8.core.cmd.CMD;
import xyz.dev_8.core.config.Messages;
import xyz.dev_8.core.player.hierarchy.Ranks;
import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.player.level.Level;
import org.bukkit.entity.Player;

public class XPCMD extends CMD {

    public XPCMD() {
        super("xp", new String[0], "", "/xp <player> (add,remove,set,reset) [amount]", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length == 3) {

            MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);
            if (!player.exists()) {
                Messages.sendFailedMessage(paramPlayer);
                return;
            }

            try {

                switch (paramArrayOfString[1].toUpperCase()) {
                    case "ADD":
                        Level.addXP(player, Integer.parseInt(paramArrayOfString[2]));
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                    case "REMOVE":
                        Level.removeXP(player, Integer.parseInt(paramArrayOfString[2]));
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                    case "SET":
                        Level.setXP(player, Integer.parseInt(paramArrayOfString[2]));
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                }

            } catch (NumberFormatException e) {
                Messages.sendSuccessMessage(paramPlayer);
            }


        } else if (paramArrayOfString.length == 2) {

            MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);

            if (paramArrayOfString[1].equalsIgnoreCase("reset")) {

                Level.resetXP(player);
                Messages.sendSuccessMessage(paramPlayer);

            } else {
                paramPlayer.sendMessage(getUsage());
            }
        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}