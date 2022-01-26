/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.cmd.commands.staff;
/*
Created by @8ML (https://github.com/8ML) on July 01 2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.entity.Player;

public class VanishCMD extends CMD {

    public VanishCMD() {
        super("vanish", new String[0], "Poof! gone", "/vanish", Ranks.STAFF);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        MPlayer player = MPlayer.getMPlayer(paramPlayer.getName());
        if (!player.isVanished()) {
            player.vanish();
            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Poof! you are now gone.");
        } else {
            player.unVanish();
            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS + "Poof! you are now back.");
        }

    }
}
