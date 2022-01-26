/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.cmd.commands.test;
/*
Created by @8ML (https://github.com/8ML) on 4/28/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.config.Messages;
import com.github._8ml.core.punishment.Punishment;
import com.github._8ml.core.punishment.type.Ban;
import com.github._8ml.core.punishment.type.Mute;
import org.bukkit.entity.Player;

public class PunishCMDTEST extends CMD {

    public PunishCMDTEST() {
        super("punishtest", new String[0], "", "-", Ranks.STAFF);
    }

      /*
    WARNING!

    THIS IS A COMMAND FOR TESTING PURPOSES DURING DEVELOPMENT
    THIS COMMAND SHOULD BE DISABLED IMMEDIATELY WHEN DEVELOPMENT IS DONE.

     */

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length == 4) {

            try {

                Punishment.PunishType type = Punishment.PunishType.valueOf(paramArrayOfString[0]);
                Punishment.TimeUnit unit = Punishment.TimeUnit.valueOf(paramArrayOfString[3]);
                Punishment.PunishTime time = new Punishment.PunishTime(unit, Long.parseLong(paramArrayOfString[2]));

                switch (type) {
                    case BAN:
                        new Ban(MPlayer.getMPlayer(paramArrayOfString[1]), MPlayer
                                .getMPlayer(paramPlayer.getName()), time, "This is just a test")
                                .execute();
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                    case MUTE:
                        new Mute(MPlayer.getMPlayer(paramArrayOfString[1]), MPlayer
                                .getMPlayer(paramPlayer.getName()), time, "This is just a test")
                                .execute();
                        Messages.sendSuccessMessage(paramPlayer);
                        break;
                }

            } catch (Exception e) {
                Messages.sendFailedMessage(paramPlayer);
            }

        }

    }
}
