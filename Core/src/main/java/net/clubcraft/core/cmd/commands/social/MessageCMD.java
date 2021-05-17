package net.clubcraft.core.cmd.commands.social;
/*
Created by @8ML (https://github.com/8ML) on 5/17/2021
*/

import net.clubcraft.core.cmd.CMD;
import net.clubcraft.core.config.MessageColor;
import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.player.hierarchy.Ranks;
import net.clubcraft.core.player.social.friend.Friend;
import org.bukkit.entity.Player;

public class MessageCMD extends CMD {

    public MessageCMD() {
        super("msg", new String[]{"message"}, "", "/msg <player> <message>", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        Friend friendManager = Friend.getFriendManager(MPlayer.getMPlayer(paramPlayer.getName()));

        if (paramArrayOfString.length > 1) {

            MPlayer recipient;
            if (MPlayer.exists(paramArrayOfString[0])) recipient = MPlayer.getMPlayer(paramArrayOfString[0]);
            else {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Recipient does not exist!");
                return;
            }

            if (recipient.isOffline()) {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Recipient is offline!");
                return;
            }

            StringBuilder message = new StringBuilder(paramArrayOfString[1]);
            for (int i = 2; i < paramArrayOfString.length; i++) {
                message.append(" ").append(paramArrayOfString[i]);
            }

            friendManager.sendMessage(paramPlayer, recipient.getPlayer(), message.toString());


        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}
