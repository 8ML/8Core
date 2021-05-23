package xyz.dev_8.core.cmd.commands;
/*
Created by Sander on 22.05.2021
*/

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.dev_8.core.Core;
import xyz.dev_8.core.cmd.CMD;
import xyz.dev_8.core.config.MessageColor;
import xyz.dev_8.core.player.hierarchy.Ranks;
import xyz.dev_8.core.utils.ServerUtil;

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
