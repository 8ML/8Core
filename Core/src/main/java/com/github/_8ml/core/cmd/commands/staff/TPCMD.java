package com.github._8ml.core.cmd.commands.staff;
/*
Created by Sander on 23.05.2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.entity.Player;
import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.config.Messages;

public class TPCMD extends CMD {

    public TPCMD() {
        super("tp", new String[0], "", "/tp <player>\n/tp <player> <player>\n/tp here <player>\ntp all", Ranks.STAFF);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length > 1) {

            if (paramArrayOfString[0].equalsIgnoreCase("here")) {

                MPlayer pl;
                if (MPlayer.exists(paramArrayOfString[1])) {
                    pl = MPlayer.getMPlayer(paramArrayOfString[1]);
                } else {
                    paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Player does not exist!");
                    return;
                }

                if (!pl.isOffline()) {

                    pl.getPlayer().teleport(paramPlayer);
                    sendTeleportMessage(paramPlayer, pl.getPlayer().getName(), paramPlayer.getName());


                } else {

                    Messages.sendNotExistsMessage(paramPlayer, paramArrayOfString[1]);

                }

            } else {

                MPlayer pl1;
                MPlayer pl2;

                if (MPlayer.exists(paramArrayOfString[0])) {
                    pl1 = MPlayer.getMPlayer(paramArrayOfString[0]);
                } else {
                    Messages.sendNotExistsMessage(paramPlayer, paramArrayOfString[0]);
                    return;
                }
                if (MPlayer.exists(paramArrayOfString[1])) {
                    pl2 = MPlayer.getMPlayer(paramArrayOfString[1]);
                } else {
                    Messages.sendNotExistsMessage(paramPlayer, paramArrayOfString[1]);
                    return;
                }

                if (pl1.isOffline()) {
                    Messages.sendPlayerIsOfflineMessage(paramPlayer, pl1.getPlayerStr());
                    return;
                }
                if (pl2.isOffline()) {
                    Messages.sendPlayerIsOfflineMessage(paramPlayer, pl2.getPlayerStr());
                    return;
                }

                pl1.getPlayer().teleport(pl2.getPlayer());
                sendTeleportMessage(paramPlayer, pl1.getPlayer().getName(), pl2.getPlayer().getName());

            }

        } else {

            if (paramArrayOfString.length == 0) {
                paramPlayer.sendMessage(getUsage());
                return;
            }

            if (paramArrayOfString[0].equalsIgnoreCase("all")) {

                if (!MPlayer.getMPlayer(paramPlayer.getName()).isPermissible(Ranks.ADMIN, true)) return;

                for (Player p : Core.onlinePlayers) {

                    p.teleport(paramPlayer);

                }

                sendTeleportMessage(paramPlayer, "all", paramPlayer.getName());
            } else {

                MPlayer pl;
                if (MPlayer.exists(paramArrayOfString[0])) {
                    pl = MPlayer.getMPlayer(paramArrayOfString[0]);
                } else {
                    Messages.sendNotExistsMessage(paramPlayer, paramArrayOfString[0]);
                    return;
                }

                if (pl.isOffline()) {
                    Messages.sendPlayerIsOfflineMessage(paramPlayer, pl.getPlayerStr());
                    return;
                }

                paramPlayer.teleport(pl.getPlayer());
                sendTeleportMessage(paramPlayer, paramPlayer.getName(), pl.getPlayerStr());

            }

        }

    }

    private void sendTeleportMessage(Player player, String player2, String player3) {
        player.sendMessage(MessageColor.COLOR_SUCCESS + "Teleported " + player2 + " to " + player3);
    }

}
