package com.github._8ml.core.module.hub.commands.admin;
/*
Created by @8ML (https://github.com/8ML) on July 07 2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.config.Messages;
import com.github._8ml.core.module.hub.npc.HubNPC;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.entity.Player;

public class NPCCMD extends CMD {

    public NPCCMD() {
        super("npc", new String[0], "", "/npc <game> <skin-uuid>", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length < 2) {
            paramPlayer.sendMessage(getUsage());
            return;
        }

        HubNPC.addNPC(paramArrayOfString[0], paramArrayOfString[1], paramPlayer.getLocation());
        Messages.sendSuccessMessage(paramPlayer);

    }
}
