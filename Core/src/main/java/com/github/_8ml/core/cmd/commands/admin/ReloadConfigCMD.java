package com.github._8ml.core.cmd.commands.admin;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.config.Messages;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.entity.Player;

public class ReloadConfigCMD extends CMD {

    public ReloadConfigCMD() {
        super("reloadconfigs", new String[0], "Reload all configurations", "/reloadconfigs", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        ServerConfig.reloadAllConfigs();
        Messages.sendSuccessMessage(paramPlayer);

    }

}
