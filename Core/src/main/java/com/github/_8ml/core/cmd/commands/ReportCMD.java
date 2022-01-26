/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.cmd.commands;
/*
Created by Sander on 22.05.2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.utils.StaffMSG;

public class ReportCMD extends CMD {

    public ReportCMD() {
        super("report", new String[0], "", "/report <player> <reason>", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length > 1) {

            MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);
            if (!player.exists()) {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Player does not exist");
                return;
            }

            StringBuilder reason = new StringBuilder(paramArrayOfString[1]);
            for (int i = 2; i < paramArrayOfString.length; i++) {
                reason.append(" ").append(paramArrayOfString[i]);
            }

            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "You reported "
                    + paramArrayOfString[0] + " for " + MessageColor.COLOR_MAIN + reason.toString());

            StaffMSG.sendStaffMessage(ChatColor.WHITE + paramPlayer.getName()
                    + " reported " + paramArrayOfString[0]
                    + " in " + MessageColor.COLOR_MAIN + Core.instance.pluginMessenger.getServer(player.getPlayerStr())
                    + ChatColor.WHITE + " for " + MessageColor.COLOR_MAIN
                    + reason.toString(), "");

        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}
