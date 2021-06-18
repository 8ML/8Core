package com.github._8ml.core.cmd.commands.special;
/*
Created by @8ML (https://github.com/8ML) on 5/9/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.config.MessageColor;
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
