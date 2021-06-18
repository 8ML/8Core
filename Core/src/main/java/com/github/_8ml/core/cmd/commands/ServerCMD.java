package com.github._8ml.core.cmd.commands;
/*
Created by Sander on 22.05.2021
*/

import com.github._8ml.core.cmd.CMD;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.utils.ServerUtil;

public class ServerCMD extends CMD {

    public ServerCMD() {
        super("server", new String[0], "", "/server <server>", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length == 1) {
            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Connecting to server....");

            ServerUtil.sendToServer(paramPlayer, paramArrayOfString[0]);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.instance, () -> {
                if (paramPlayer.isOnline()) {
                    paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Could not connect you to that server!");
                }
            }, 20L);

        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}
