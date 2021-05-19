package xyz.dev_8.core.cmd.commands.social;
/*
Created by @8ML (https://github.com/8ML) on 5/17/2021
*/

import xyz.dev_8.core.cmd.CMD;
import xyz.dev_8.core.config.MessageColor;
import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.player.hierarchy.Ranks;
import xyz.dev_8.core.player.social.friend.Friend;
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
