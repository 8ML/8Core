package club.mineplay.core.cmd.commands.special;
/*
Created by Sander on 5/9/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleCMD extends CMD {

    public TitleCMD() {
        super("title", new String[0], "", "/title", Ranks.VIP);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        MPlayer player = MPlayer.getMPlayer(paramPlayer.getName());

        if (paramArrayOfString.length == 0) {
            player.setTitle("");
            paramPlayer.sendMessage(ChatColor.RED + "Removed your previous title!");
        } else {

            StringBuilder args = new StringBuilder(paramArrayOfString[0]);
            for (String s : paramArrayOfString) {
                if (s.equals(paramArrayOfString[0])) continue;
                args.append(" ").append(s);
            }

            player.setTitle(args.toString());

            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS
                    + "Your title is now " + MessageColor.COLOR_MAIN + args.toString() + MessageColor.COLOR_SUCCESS + "!");

        }

    }
}
