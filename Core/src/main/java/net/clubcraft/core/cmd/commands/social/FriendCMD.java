package net.clubcraft.core.cmd.commands.social;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import net.clubcraft.core.cmd.CMD;
import net.clubcraft.core.config.MessageColor;
import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.player.hierarchy.Ranks;
import net.clubcraft.core.player.social.friend.Friend;
import org.bukkit.entity.Player;

public class FriendCMD extends CMD {

    public FriendCMD() {
        super("friend", new String[]{"friends" , "f"}, "", "/friend <player> [remove]", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        Friend friendManager = Friend.getFriendManager(MPlayer.getMPlayer(paramPlayer.getName()));

        if (paramArrayOfString.length == 1) {

            MPlayer pl;
            if (MPlayer.exists(paramArrayOfString[0])) pl = MPlayer.getMPlayer(paramArrayOfString[0]);
            else {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Player does not exist!");
                return;
            }

            friendManager.addFriend(pl);

        } else if (paramArrayOfString.length == 2) {

            MPlayer pl;
            if (MPlayer.exists(paramArrayOfString[0])) pl = MPlayer.getMPlayer(paramArrayOfString[0]);
            else {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Player does not exist!");
                return;
            }


            switch (paramArrayOfString[1].toLowerCase()) {
                case "remove":
                    friendManager.remove(pl);
                    break;
                case "decline":
                    friendManager.decline(pl);
                    break;
                default:
                    paramPlayer.sendMessage(getUsage());
                    break;

            }

        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}
