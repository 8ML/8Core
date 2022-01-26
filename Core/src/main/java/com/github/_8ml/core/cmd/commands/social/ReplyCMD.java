/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.cmd.commands.social;
/*
Created by @8ML (https://github.com/8ML) on 5/17/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.player.social.friend.Friend;
import com.github._8ml.core.config.MessageColor;
import org.bukkit.entity.Player;

public class ReplyCMD extends CMD {

    public ReplyCMD() {
        super("r", new String[]{"reply"}, "", "/r <message>", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        MPlayer player = MPlayer.getMPlayer(paramPlayer.getName());
        Friend friendManager = Friend.getFriendManager(player);

        if (paramArrayOfString.length == 0) {
            paramPlayer.sendMessage(getUsage());
            return;
        }

        if (Friend.lastRecipient.containsKey(player)) {

            MPlayer recipient = Friend.lastRecipient.get(player);

            if (recipient.isOffline()) {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Recipient went offline!");
                Friend.lastRecipient.remove(player);
                return;
            }

            StringBuilder message = new StringBuilder(paramArrayOfString[0]);
            for (int i = 1; i < paramArrayOfString.length; i++) {

                message.append(" ").append(paramArrayOfString[i]);

            }

            friendManager.sendMessage(paramPlayer, recipient, message.toString());

        } else {
            paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "You do not have any previous conversations");
        }

    }
}
