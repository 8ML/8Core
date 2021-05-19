package xyz.dev_8.core.cmd.commands.admin;
/*
Created by @8ML (https://github.com/8ML) on 5/10/2021
*/

import xyz.dev_8.core.cmd.CMD;
import xyz.dev_8.core.config.Messages;
import xyz.dev_8.core.player.hierarchy.Ranks;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EACMD extends CMD {

    public static boolean ea = false;

    public EACMD() {
        super("ea", new String[0], "Boom", "/ea", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        ea = !ea;
        Messages.sendSuccessMessage(paramPlayer);
        paramPlayer.sendMessage(ChatColor.GRAY + "Explosive Arrows is now set to " + ChatColor.AQUA + String.valueOf(ea));
    }
}
