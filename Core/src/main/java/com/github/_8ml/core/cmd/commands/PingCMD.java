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
