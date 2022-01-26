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
Created by @8ML (https://github.com/8ML) on 5/3/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.stats.ui.StatsUI;
import com.github._8ml.core.config.Messages;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.entity.Player;

public class StatsCMD extends CMD {

    public StatsCMD() {
        super("stats", new String[0], "", "/stats [player]", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        MPlayer pl;
        if (paramArrayOfString.length == 1) {

            if (MPlayer.exists(paramArrayOfString[0])) pl = MPlayer.getMPlayer(paramArrayOfString[0]);
            else {
                Messages.sendFailedMessage(paramPlayer); return;
            }

        } else {

            pl = MPlayer.getMPlayer(paramPlayer.getName());

        }
        new StatsUI(MPlayer.getMPlayer(paramPlayer.getName()), pl);

    }
}
