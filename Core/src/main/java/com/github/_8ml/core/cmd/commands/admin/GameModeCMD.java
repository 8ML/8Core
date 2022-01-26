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
Created by @8ML (https://github.com/8ML) on 4/26/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.utils.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameModeCMD extends CMD {

    public GameModeCMD() {
        super("gm", new String[0], "", "", Ranks.ADMIN);
    }

    public Map<Player, GameMode> modeMap = new HashMap<>();

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        if (paramArrayOfString.length == 0) {

            if (paramPlayer.getGameMode().equals(GameMode.SURVIVAL)) {
                paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to Creative.");
                paramPlayer.setGameMode(GameMode.CREATIVE);
            } else {
                paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to Survival.");
                paramPlayer.setGameMode(GameMode.SURVIVAL);
            }

        } else {
            if (paramArrayOfString[0].equalsIgnoreCase("s")) {

                if (paramPlayer.getGameMode().equals(GameMode.SPECTATOR)) {
                    if (modeMap.containsKey(paramPlayer)) {

                        paramPlayer.setGameMode(modeMap.get(paramPlayer));



                        paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to "
                                + StringUtils.formatCapitalization(paramPlayer.getGameMode().toString()) + ".");

                    } else {
                        paramPlayer.setGameMode(GameMode.SURVIVAL);
                        paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to Survival.");
                    }
                } else {

                    modeMap.put(paramPlayer, paramPlayer.getGameMode());

                    paramPlayer.setGameMode(GameMode.SPECTATOR);
                    paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Changed to Spectator.");
                }

            }
        }

    }
}
