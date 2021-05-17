package net.clubcraft.core.cmd.commands.staff;
/*
Created by @8ML (https://github.com/8ML) on 5/6/2021
*/

import net.clubcraft.core.cmd.CMD;
import net.clubcraft.core.player.hierarchy.Ranks;
import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.utils.StaffMSG;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StaffChatCMD extends CMD {

    public StaffChatCMD() {
        super("staffchat", new String[]{"sc"}, "", "/staffchat <message>", Ranks.BUILD_TEAM);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length == 0) {
            paramPlayer.sendMessage(getUsage());
            return;
        }

        StringBuilder msg = new StringBuilder(paramArrayOfString[0]);
        for (int i = 1; i < paramArrayOfString.length; i++) {
            msg.append(" ").append(paramArrayOfString[i]);
        }

        StaffMSG.sendStaffMessage(ChatColor.GRAY + msg.toString(), MPlayer.getMPlayer(paramPlayer.getName()));

    }
}
