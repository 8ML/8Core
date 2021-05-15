package club.mineplay.core.cmd.commands.admin;
/*
Created by Sander on 4/27/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.Messages;
import club.mineplay.core.player.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.currency.Coin;
import org.bukkit.entity.Player;

public class CoinCMD extends CMD {

    public CoinCMD() {
        super("coin", new String[0], "", "/coin <player> (add,remove,set,reset) [amount]", Ranks.ADMIN);
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
                        Coin.addCoins(player, Integer.parseInt(paramArrayOfString[2]), true);
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                    case "REMOVE":
                        Coin.removeCoins(player, Integer.parseInt(paramArrayOfString[2]));
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                    case "SET":
                        Coin.setCoins(player, Integer.parseInt(paramArrayOfString[2]));
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                }

            } catch (NumberFormatException e) {
                Messages.sendSuccessMessage(paramPlayer);
            }


        } else if (paramArrayOfString.length == 2) {

            MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);

            if (paramArrayOfString[1].equalsIgnoreCase("reset")) {

                Coin.resetCoins(player);
                Messages.sendSuccessMessage(paramPlayer);

            } else {
                paramPlayer.sendMessage(getUsage());
            }
        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}
