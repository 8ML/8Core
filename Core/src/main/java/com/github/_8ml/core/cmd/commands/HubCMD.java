package com.github._8ml.core.cmd.commands;
/*
Created by @8ML (https://github.com/8ML) on June 28 2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.utils.ServerUtil;
import org.bukkit.entity.Player;

public class HubCMD extends CMD {

    public HubCMD() {
        super("hub", new String[]{"lobby"}, "Return to hub", "/hub", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {
        ServerUtil.sendToServer(paramPlayer, "Hub");
    }
}
