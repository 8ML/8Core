package com.github._8ml.core.cmd.commands.social;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import com.github._8ml.core.cmd.CMD;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.player.social.friend.Friend;
import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.game.GameInfo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendCMD extends CMD {

    private final int playersPerPage = 5;

    public FriendCMD() {
        super("friend", new String[]{"friends" , "f"}, "",
                "/friend list\n/friend <player>\n/friend decline <player>\n/friend remove <player>", Ranks.DEFAULT);
    }

    @Override
    public void execute(Player paramPlayer, String[] paramArrayOfString) {

        Friend friendManager = Friend.getFriendManager(MPlayer.getMPlayer(paramPlayer.getName()));
        List<MPlayer> friends =  friendManager.getFriends();
        List<Set<MPlayer>> pages = new ArrayList<>();

        int counter = 0;
        int pageCount = 0;
        Set<MPlayer> page = new HashSet<>();
        for (MPlayer friend : friends) {
            if (counter >= playersPerPage) {
                counter = 0;
                pageCount++;
                pages.add(page);
                page = new HashSet<>();
            }

            page.add(friend);
            counter++;
        }

        pages.add(page);


        if (paramArrayOfString.length == 1) {

            if (paramArrayOfString[0].equalsIgnoreCase("list")) {

                if (friends.isEmpty()) {
                    paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "You do not have any friends!");
                    return;
                }

                String header = ChatColor.GOLD + "Friends (Page 1 of " + pages.size() + ")";
                String content = buildPage(pages.get(0));

                paramPlayer.sendMessage(" ");
                paramPlayer.sendMessage(header);
                paramPlayer.sendMessage(content);
                paramPlayer.sendMessage(" ");

            } else {

                MPlayer pl;
                if (MPlayer.exists(paramArrayOfString[0])) pl = MPlayer.getMPlayer(paramArrayOfString[0]);
                else {
                    paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Player does not exist!");
                    return;
                }

                friendManager.addFriend(pl);


            }

        } else if (paramArrayOfString.length == 2) {

            if (paramArrayOfString[0].equalsIgnoreCase("list")) {

                if (friends.isEmpty()) {
                    paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "You do not have any friends!");
                    return;
                }

                try {

                    int pageNum = Integer.parseInt(paramArrayOfString[1]);
                    if (pageNum > pages.size() || pageNum == 0) {
                        paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Page not found!");
                        return;
                    }

                    String header = ChatColor.GOLD + "Friends (Page " + pageNum + " of " + pages.size() + ")";
                    String content = buildPage(pages.get(pageNum - 1));

                    paramPlayer.sendMessage(" ");
                    paramPlayer.sendMessage(header);
                    paramPlayer.sendMessage(content);
                    paramPlayer.sendMessage(" ");

                    return;


                } catch (NumberFormatException e) {
                    paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Invalid page number!");
                    return;
                }

            }

            MPlayer pl;
            if (MPlayer.exists(paramArrayOfString[1])) pl = MPlayer.getMPlayer(paramArrayOfString[1]);
            else {
                paramPlayer.sendMessage(MessageColor.COLOR_ERROR + "Player does not exist!");
                return;
            }

            switch (paramArrayOfString[0].toLowerCase()) {
                case "remove":
                    friendManager.remove(pl);
                    break;
                case "decline":
                    friendManager.decline(pl);
                    break;
            }

        } else {
            paramPlayer.sendMessage(getUsage());
        }

    }

    private String buildPage(Set<MPlayer> page) {

        StringBuilder pageBuilder = new StringBuilder();
        for (MPlayer pageEntry : page) {

            GameInfo gameInfo = GameInfo.getGameInfo(pageEntry);
            Core.instance.getLogger().info(String.valueOf(gameInfo));
            String info = pageEntry.isOffline() ? MessageColor.COLOR_ERROR + "is Offline"
                    : gameInfo.getGame().equals("") ? MessageColor.COLOR_HIGHLIGHT + "is in a Lobby"
                    : MessageColor.COLOR_HIGHLIGHT + "is playing " + gameInfo.getGame();

            pageBuilder.append(pageEntry.getRankEnum().getRank().getFullPrefixWithSpace())
                    .append(pageEntry.getRankEnum().getRank().getNameColor())
                    .append(pageEntry.getPlayerStr())
                    .append(" ")
                    .append(info)
                    .append("\n");

        }

        return pageBuilder.toString();
    }
}
