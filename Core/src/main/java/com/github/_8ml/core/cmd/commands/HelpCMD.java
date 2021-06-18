package com.github._8ml.core.cmd.commands;
/*
Created by @8ML (https://github.com/8ML) on 4/24/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.utils.BookUtil;
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
