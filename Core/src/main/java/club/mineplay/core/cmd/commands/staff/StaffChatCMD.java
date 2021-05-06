package club.mineplay.core.cmd.commands.staff;
/*
Created by Sander on 5/6/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.utils.StaffMSG;
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
        for (String arg : paramArrayOfString) {
            if (arg.equalsIgnoreCase(paramArrayOfString[0])) continue;
            msg.append(" ").append(arg);
        }

        StaffMSG.sendStaffMessage(msg.toString(), MPlayer.getMPlayer(paramPlayer.getName()));

    }
}
