/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.cmd.commands.admin;
/*
Created by @8ML (https://github.com/8ML) on 5/3/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.player.level.Level;
import com.github._8ml.core.config.Messages;
import org.bukkit.entity.Player;

public class XPCMD extends CMD {

    public XPCMD() {
        super("xp", new String[0], "", "/xp <player> (add,remove,set,reset) [amount]", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length == 3) {

            MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);
            if (!player.exists()) {
                Messages.sendFailedMessage(paramPlayer);
                return;
            }

            try {

                switch (paramArrayOfString[1].toUpperCase()) {
                    case "ADD":
                        Level.addXP(player, Integer.parseInt(paramArrayOfString[2]));
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                    case "REMOVE":
                        Level.removeXP(player, Integer.parseInt(paramArrayOfString[2]));
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                    case "SET":
                        Level.setXP(player, Integer.parseInt(paramArrayOfString[2]));
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                }

            } catch (NumberFormatException e) {
                Messages.sendSuccessMessage(paramPlayer);
            }


        } else if (paramArrayOfString.length == 2) {

            MPlayer player = MPlayer.getMPlayer(paramArrayOfString[0]);

            if (paramArrayOfString[1].equalsIgnoreCase("reset")) {

                Level.resetXP(player);
                Messages.sendSuccessMessage(paramPlayer);

            } else {
                paramPlayer.sendMessage(getUsage());
            }
        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}
