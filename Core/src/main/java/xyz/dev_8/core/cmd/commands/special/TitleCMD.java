package xyz.dev_8.core.cmd.commands.special;
/*
Created by @8ML (https://github.com/8ML) on 5/9/2021
*/

import xyz.dev_8.core.cmd.CMD;
import xyz.dev_8.core.config.MessageColor;
import xyz.dev_8.core.player.hierarchy.Ranks;
import xyz.dev_8.core.player.MPlayer;
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

            if (args.toString().toCharArray().length > 16) {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Title cannot be more than 16 characters");
                return;
            }

            player.setTitle(args.toString());

            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS
                    + "Your title is now " + MessageColor.COLOR_MAIN + args + MessageColor.COLOR_SUCCESS + "!");

        }

    }
}
