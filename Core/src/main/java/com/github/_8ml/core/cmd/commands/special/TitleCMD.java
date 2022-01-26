/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.cmd.commands.special;
/*
Created by @8ML (https://github.com/8ML) on 5/9/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.config.MessageColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleCMD extends CMD {

    public TitleCMD() {
        super("title", new String[0], "", "/title", Ranks.VIP);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        MPlayer player = MPlayer.getMPlayer(paramPlayer.getName());

        if (paramArrayOfString.length == 0) {
            player.setTitle("");
            paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Removed your previous title!");
        } else {

            StringBuilder args = new StringBuilder(paramArrayOfString[0]);
            for (String s : paramArrayOfString) {
                if (s.equals(paramArrayOfString[0])) continue;
                args.append(" ").append(s);
            }

            if (args.toString().toCharArray().length > 16) {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Title cannot be more than 16 characters");
                return;
            }

            player.setTitle(args.toString());

            paramPlayer.sendMessage(MessageColor.COLOR_SUCCESS
                    + "Your title is now " + MessageColor.COLOR_MAIN + args + MessageColor.COLOR_SUCCESS + "!");

        }

    }
}
