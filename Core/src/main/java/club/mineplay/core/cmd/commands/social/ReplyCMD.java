package club.mineplay.core.cmd.commands.social;
/*
Created by @8ML (https://github.com/8ML) on 5/17/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.hierarchy.Ranks;
import club.mineplay.core.player.social.friend.Friend;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ReplyCMD extends CMD {

    public ReplyCMD() {
        super("r", new String[]{"reply"}, "", "/r <message>", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        Friend friendManager = Friend.getFriendManager(MPlayer.getMPlayer(paramPlayer.getName()));

        if (paramArrayOfString.length == 0) {
            paramPlayer.sendMessage(getUsage());
            return;
        }

        if (Friend.lastRecipient.containsKey(paramPlayer)) {

            MPlayer recipient = MPlayer.getMPlayer(Friend.lastRecipient.get(paramPlayer).getName());

            if (recipient.isOffline()) {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Recipient went offline!");
                Friend.lastRecipient.remove(paramPlayer);
                return;
            }

            StringBuilder message = new StringBuilder(paramArrayOfString[0]);
            for (int i = 1; i < paramArrayOfString.length; i++) {

                message.append(" ").append(paramArrayOfString[i]);

            }

            friendManager.sendMessage(paramPlayer, recipient.getPlayer(), message.toString());

        } else {
            paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "You do not have any previous conversations");
        }

    }
}
