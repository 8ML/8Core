package club.mineplay.core.cmd.commands.admin;
/*
Created by Sander on 5/3/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.Messages;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.level.Level;
import org.bukkit.entity.Player;

public class XPCMD extends CMD {

    public XPCMD() {
        super("xp", new String[0], "", "/xp <player> (add,remove,set,reset) [amount]", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length == 3) {

            MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);

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