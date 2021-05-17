package net.clubcraft.core.cmd.commands;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import net.clubcraft.core.cmd.CMD;
import net.clubcraft.core.player.hierarchy.Ranks;
import net.clubcraft.core.utils.BookUtil;
import org.bukkit.entity.Player;

public class HelpCMD extends CMD {
    public HelpCMD() {
        super("help", new String[]{"h"}, "", "", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        BookUtil.displayHelpBook(paramPlayer);

    }

}
