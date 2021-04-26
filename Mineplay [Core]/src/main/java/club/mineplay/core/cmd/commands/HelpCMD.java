package club.mineplay.core.cmd.commands;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.utils.BookUtil;
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
