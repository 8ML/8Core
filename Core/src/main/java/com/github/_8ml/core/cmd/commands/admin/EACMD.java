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
Created by @8ML (https://github.com/8ML) on 5/10/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.config.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


/**
 * There is actually no good use for this command but its kinda fun to play around with.
 * The handling of this command is located in FunEvent.class
 *
 * This command should be registered through CommandCenter.registerTestCommand()
 * or just be disabled/removed as it causes a lot of damage, trust me i tried.
 */
public class EACMD extends CMD {

    public static boolean ea = false;

    public EACMD() {
        super("ea", new String[0], "Boom", "/ea", Ranks.ADMIN);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        ea = !ea;
        Messages.sendSuccessMessage(paramPlayer);
        paramPlayer.sendMessage(MessageColor.COLOR_MAIN + "Explosive Arrows is now set to " + ChatColor.AQUA + ea);
    }
}
