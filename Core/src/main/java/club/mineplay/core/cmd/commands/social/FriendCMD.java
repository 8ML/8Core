package club.mineplay.core.cmd.commands.social;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import club.mineplay.core.cmd.CMD;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.config.Messages;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.hierarchy.Ranks;
import club.mineplay.core.player.social.friend.Friend;
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

            friendManager.remove(pl);

        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }
}
