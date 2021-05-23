package xyz.dev_8.core.cmd.commands;
/*
Created by Sander on 23.05.2021
*/

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import xyz.dev_8.core.cmd.CMD;
import xyz.dev_8.core.config.MessageColor;
import xyz.dev_8.core.player.hierarchy.Ranks;

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
