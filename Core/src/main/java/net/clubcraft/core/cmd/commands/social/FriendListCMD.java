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

import java.util.List;

public class FriendListCMD extends CMD {

    public FriendListCMD() {
        super("friendlist", new String[]{"flist", "friendslist", "friendl" , "fl"}, "", "/friendlist", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        Friend friendManager = Friend.getFriendManager(MPlayer.getMPlayer(paramPlayer.getName()));

        List<MPlayer> friends =  friendManager.getFriends();
        if (friends.isEmpty()) {
            paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "You do not have any friends!");
            return;
        }

        StringBuilder listBuilder = new StringBuilder(friends.get(0).getRankEnum().getRank().getFullPrefixWithSpace()
                + friends.get(0).getRankEnum().getRank().getNameColor() + friends.get(0).getPlayerStr());
        if (friends.size() > 1) {
            for (MPlayer friend : friends) {
                if (friends.indexOf(friend) == 0) continue;
                listBuilder.append("\n").append(friend.getRankEnum().getRank().getFullPrefixWithSpace()
                        + friend.getRankEnum().getRank().getNameColor() + friend.getPlayerStr());
            }
        }

        paramPlayer.sendMessage(listBuilder.toString());

    }
}
