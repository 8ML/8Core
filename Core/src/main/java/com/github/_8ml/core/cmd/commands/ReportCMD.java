package com.github._8ml.core.cmd.commands;
/*
Created by Sander on 22.05.2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.utils.StaffMSG;

public class ReportCMD extends CMD {

    public ReportCMD() {
        super("report", new String[0], "", "/report <player> <reason>", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length > 1) {

            MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);
            if (!player.exists()) {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Player does not exist");
                return;
            }

            StringBuilder reason = new StringBuilder(paramArrayOfString[1]);
            for (int i = 2; i < paramArrayOfString.length; i++) {
                reason.append(" ").append(paramArrayOfString[i]);
            }

            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "You reported "
                    + paramArrayOfString[0] + " for " + ChatColor.GRAY + reason.toString());

            StaffMSG.sendStaffMessage(ChatColor.WHITE + paramPlayer.getName()
                    + " reported " + paramArrayOfString[0]
                    + " in " + ChatColor.GRAY + Core.instance.pluginMessenger.getServer(player.getPlayerStr())
                    + ChatColor.WHITE + " for " + ChatColor.GRAY
                    + reason.toString(), "");

        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}
