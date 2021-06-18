package com.github._8ml.core.cmd.commands;
/*
Created by Sander on 23.05.2021
*/

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.hierarchy.Ranks;

public class PingCMD extends CMD {

    public PingCMD() {
        super("ping", new String[0], "", "/ping", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        CraftPlayer p = (CraftPlayer) paramPlayer;
        paramPlayer.sendMessage(MessageColor.COLOR_MAIN + "Your ping is "
                + MessageColor.COLOR_HIGHLIGHT + p.getHandle().ping + " ms");

    }
}
