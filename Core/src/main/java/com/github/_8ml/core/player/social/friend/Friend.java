/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.player.social.friend;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.Core;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.options.PlayerOptions;
import com.github._8ml.core.storage.SQL;
import com.github._8ml.core.utils.DeveloperMode;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * This class manages friends.
 * You need to get the instance of this class through the getFriendManager(MPlayer) method
 * to add/remove/request and send private messages.
 */
public class Friend {

    static {

        try {

            Core.instance.sql.createTable("CREATE TABLE IF NOT EXISTS friends (`uuid` VARCHAR(255) PRIMARY KEY NOT NULL" +
                    ", `friendList` LONGTEXT NOT NULL" +
                    ", `requests` LONGTEXT NOT NULL)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static Map<MPlayer, Friend> friendManager = new HashMap<>();

    private final String PREFIX = ChatColor.AQUA + "";
    private final String playerFull;

    private final MPlayer player;
    private final SQL sql = Core.instance.sql;


    private Friend(MPlayer player) {
        this.player = player;
        this.playerFull = this.player.getRankEnum().getRank().getFullPrefixWithSpace()
                + this.player.getRankEnum().getRank().getNameColor()
                + this.player.getPlayerStr();
    }


    /**
     * This will send a request to the specified player
     *
     * @param player The player to send request to
     */
    private void sendRequest(MPlayer player) {

        if (player.equals(this.player)) {
            this.player.getPlayer().sendMessage(MessageColor.COLOR_ERROR + "You cannot add yourself as a friend!");
            return;
        }

        if (isFriendsWith(player)) {
            this.player.getPlayer().sendMessage(MessageColor.COLOR_ERROR + "You are already friends with this player!");
            return;
        }

        String preference = PlayerOptions.getPreference(player, PlayerOptions.Preference.FRIEND_REQUEST);

        if (preference.equals("OFF")) {
            this.player.getPlayer().sendMessage(MessageColor.COLOR_ERROR + player.getPlayerStr() + " has friend requests disabled!");
            return;
        }

        if (!player.isOffline()) {


            ComponentBuilder requestMessageBuilder = new ComponentBuilder();
            requestMessageBuilder.append("\n");
            requestMessageBuilder.append(StringUtils.center(ChatColor.YELLOW + "Friend request from " + this.playerFull, 10));
            requestMessageBuilder.append("\n");
            requestMessageBuilder.append(StringUtils.rightPad(ChatColor.GREEN + "" + ChatColor.BOLD + "[ACCEPT] ", 5))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend " + this.player.getPlayerStr()))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Click to accept!")));
            requestMessageBuilder.append(StringUtils.leftPad(ChatColor.RED + "" + ChatColor.BOLD + "[DECLINE]", 5))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend decline " + this.player.getPlayerStr()))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "Click to decline!")));
            requestMessageBuilder.append("\n");

            BaseComponent[] requestMessage = requestMessageBuilder.create();

            player.getPlayer().spigot().sendMessage(requestMessage);

        }

        this.player.getPlayer().sendMessage(this.PREFIX + ChatColor.GREEN + "You sent a request to " + player.getPlayerStr());

        Friend friendM = getFriendManager(player);

        friendM.addRequest(this.player);

    }


    /**
     * This will check if this.player has a friend request from the specified player
     *
     * @param player The player
     * @return Will return true if this.player has a request from the specified player.
     */
    public boolean checkRequest(MPlayer player) {

        boolean result = false;

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM friends WHERE `uuid`=?");
            st.setString(1, this.player.getUUID());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                List<MPlayer> requests = getList(rs.getString("requests"));
                if (!requests.isEmpty()) {
                    for (MPlayer p : requests) {
                        if (!p.getPlayerStr().equals(player.getPlayerStr())) continue;
                        result = true;

                    }

                }
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }


    /**
     * This will add the specified player to this.player's friend list by sending a request.
     * The specified player has to accept it for this.player and player to become friends.
     *
     * @param player The player to send a request to
     */
    public void addFriend(MPlayer player) {

        String targetFull = player.getRankEnum().getRank().getFullPrefixWithSpace()
                + player.getRankEnum().getRank().getNameColor()
                + player.getPlayerStr();


        if (checkRequest(player)) {

            removeRequest(player);

            add(player);
            getFriendManager(player).add(this.player);

            if (!this.player.isOffline()) {
                this.player.getPlayer().sendMessage(MessageColor.COLOR_SUCCESS + "You are now friends with " + targetFull + MessageColor.COLOR_SUCCESS + "!");
            }
            if (!player.isOffline()) {
                player.getPlayer().sendMessage(MessageColor.COLOR_SUCCESS + "You are now friends with " + this.playerFull + MessageColor.COLOR_SUCCESS + "!");
            }


        } else {

            sendRequest(player);

        }

    }


    /**
     * This will decline this.player's incoming friend request from the specified player
     *
     * @param player The player
     */
    public void decline(MPlayer player) {

        if (isFriendsWith(player)) {
            this.player.getPlayer().sendMessage(MessageColor.COLOR_ERROR + player.getPlayerStr() + " is already your friend!");
            return;
        }

        if (!checkRequest(player)) {
            this.player.getPlayer().sendMessage(MessageColor.COLOR_ERROR + player.getPlayerStr() + " has never sent you a request!");
            return;
        }

        String targetFull = player.getRankEnum().getRank().getFullPrefixWithSpace()
                + player.getRankEnum().getRank().getNameColor()
                + player.getPlayerStr();

        this.player.getPlayer().sendMessage(MessageColor.COLOR_HIGHLIGHT + "You declined " + targetFull + MessageColor.COLOR_HIGHLIGHT + "'s friend request!");

        if (!player.isOffline()) {
            player.getPlayer().sendMessage(MessageColor.COLOR_ERROR + this.playerFull + " declined your friend request!");
        }

        removeRequest(player);

    }


    /**
     * This will force add the specified player to this.player's friend list
     *
     * @param player The player to add.
     */
    private void add(MPlayer player) {

        List<MPlayer> currentFriends = getFriends();
        currentFriends.add(player);
        String friendsStr = buildList(currentFriends);
        update("friendList", friendsStr);


    }


    /**
     * Thi swill remove the specified player from this.player's friend list
     *
     * @param player The player to remove
     */
    public void remove(MPlayer player) {

        if (this.player.getPlayerStr().equals(player.getPlayerStr())) {
            this.player.getPlayer().sendMessage(MessageColor.COLOR_ERROR + "You cannot remove yourself as a friend!");
            return;
        }

        List<MPlayer> friends = getFriends();
        friends.remove(player);
        String friendsStr = buildList(friends);
        update("friendList", friendsStr);

        Friend friendManager = getFriendManager(player);
        List<MPlayer> targetFriends = friendManager.getFriends();
        targetFriends.remove(this.player);
        String targetFriendsStr = friendManager.buildList(targetFriends);
        friendManager.update("friendList", targetFriendsStr);

        if (!player.isOffline()) {
            player.getPlayer().sendMessage(MessageColor.COLOR_ERROR + this.player.getPlayerStr() + " removed you from their friends list!");
            this.player.getPlayer().sendMessage(MessageColor.COLOR_ERROR + "Removed " + player.getPlayerStr() + " from your friends list!");
        }


    }


    /**
     * This will add the specified player to this.player's friend requests.
     *
     * @param player The player to add
     */
    public void addRequest(MPlayer player) {

        List<MPlayer> requests = getRequests();
        requests.add(player);
        String requestList = buildList(requests);
        update("requests", requestList);

    }


    /**
     * This will remove the specified player from this.player's requests
     *
     * @param player The player to remove
     */
    public void removeRequest(MPlayer player) {

        List<MPlayer> requests = getRequests();
        requests.remove(player);
        String requestsList = buildList(requests);
        update("requests", requestsList);
    }


    /**
     * This will return this.player's friend request
     *
     * @return List of MPlayer
     */
    public List<MPlayer> getRequests() {

        List<MPlayer> players = new ArrayList<>();

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM friends WHERE `uuid`=?");
            st.setString(1, this.player.getUUID());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                String requestsStr = rs.getString("requests");
                players = getList(requestsStr);

            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;

    }


    /**
     * This will get this.player's friend list
     *
     * @return List of MPlayer
     */
    public List<MPlayer> getFriends() {

        List<MPlayer> players = new ArrayList<>();

        try {
            PreparedStatement st = sql.preparedStatement("SELECT * FROM friends WHERE `uuid`=?");
            st.setString(1, this.player.getUUID());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                String friendsListStr = rs.getString("friendList");
                players = getList(friendsListStr);

                sql.closeConnection(st);
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;

    }


    /**
     * This will update the specified column with the specified list of uuid's seperated by comma
     *
     * @param column Column to update
     * @param list   String of uuid's seperated by comma
     */
    public void update(String column, String list) {
        try {

            PreparedStatement st = sql.preparedStatement("UPDATE friends SET `" + column + "`=? WHERE `uuid`=?");
            st.setString(1, list);
            st.setString(2, this.player.getUUID());

            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Checks if the specified player is on this.player's friend list
     *
     * @param player The player to check
     * @return Will return true if the specified player is on this.player's friend list
     */
    public boolean isFriendsWith(MPlayer player) {

        for (MPlayer p : getFriends()) {
            if (p.getUUID().equalsIgnoreCase(player.getUUID())) return true;
        }

        return false;
    }


    /**
     * This will build a String of uuid's seperated by a comma from a List of MPlayer
     *
     * @param list List of MPlayer
     * @return String of uuid's seperated by comma
     */
    private String buildList(List<MPlayer> list) {
        StringBuilder requestListBuilder;
        String requestList;

        if (list.isEmpty()) {
            requestList = "";
        } else {

            requestListBuilder = new StringBuilder(list.get(0).getUUID());
            if (list.size() > 1) {

                for (int i = 1; i < list.size(); i++) {
                    requestListBuilder.append(",").append(list.get(i).getUUID());
                }

            }
            requestList = requestListBuilder.toString();

        }

        return requestList;
    }


    /**
     * This will build a player list from a String of uuid's seperated by a comma
     *
     * @param strList The uuid's seperated by comma
     * @return List of MPlayer
     */
    private List<MPlayer> getList(String strList) {

        DeveloperMode.log("GetList: " + strList);

        if (strList.equals("")) return new ArrayList<>();

        List<MPlayer> list = new ArrayList<>();

        String[] array;
        if (strList.contains(",")) {
            array = strList.split(",");
        } else {
            array = new String[]{strList};
        }

        for (String str : array) {
            MPlayer pl = MPlayer.getMPlayer(UUID.fromString(str));
            DeveloperMode.log(pl.toString());
            list.add(pl);
        }

        return list;

    }


    /**
     * This map keeps track of the last recipient of the player so they can simply
     * reply using /r instead of /msg player
     */
    public static final Map<MPlayer, MPlayer> lastRecipient = new HashMap<>();


    /**
     * This will send a private message through bungee if the receivers
     * private message preference allows it.
     *
     * @param sender   The sending player
     * @param receiver The receiving Player
     * @param message  The message
     */
    public void sendMessage(Player sender, MPlayer receiver, String message) {

        if (MPlayer.getMPlayer(sender.getName()).equals(receiver)) {
            sender.sendMessage(MessageColor.COLOR_ERROR + "You cannot message yourself!");
            return;
        }

        String preferenceSender = PlayerOptions.getPreference(MPlayer.getMPlayer(sender.getName()), PlayerOptions.Preference.PRIVATE_MESSAGE);
        String preferenceReceiver = PlayerOptions.getPreference(receiver, PlayerOptions.Preference.PRIVATE_MESSAGE);


        if (preferenceSender.equals("OFF")) {
            sender.sendMessage(MessageColor.COLOR_ERROR + "You have private messaging disabled");
            return;
        }


        if (preferenceReceiver.equals("OFF")) {
            sender.sendMessage(MessageColor.COLOR_ERROR + receiver.getPlayerStr() + " has private messaging disabled!");
            return;
        }

        if (preferenceReceiver.equals("FRIENDS_ONLY")) {
            if (!isFriendsWith(receiver)) {
                sender.sendMessage(MessageColor.COLOR_ERROR + "Only " + receiver.getPlayerStr() + "'s friends can message them");
                return;
            }
        }

        String msgRecipient = ChatColor.LIGHT_PURPLE + sender.getName() + ChatColor.DARK_GRAY + " ->" + ChatColor.LIGHT_PURPLE + " You: " + ChatColor.GRAY + message;
        String msgSender = ChatColor.LIGHT_PURPLE + "You " + ChatColor.DARK_GRAY + "-> " + ChatColor.LIGHT_PURPLE + receiver.getPlayerStr() + ": " + ChatColor.GRAY + message;
        sender.sendMessage(msgSender);

        lastRecipient.put(MPlayer.getMPlayer(sender.getName()), receiver);
        lastRecipient.put(receiver, MPlayer.getMPlayer(sender.getName()));

        Core.instance.pluginMessenger.sendPluginMessage("MESSAGE_CHANNEL", "",
                Collections.singletonList(receiver.getPlayerStr()), msgRecipient);

    }


    /**
     * This will return an instance of this class
     *
     * @param player The player to manage
     * @return Instance of Friend
     */
    public static Friend getFriendManager(MPlayer player) {
        if (friendManager.containsKey(player)) return friendManager.get(player);
        Friend fm = new Friend(player);
        friendManager.put(player, fm);
        return fm;
    }


    /**
     * This will add the player to the database if they are not already there
     *
     * @param player The player to initialize
     */
    public static void initialize(MPlayer player) {
        try {

            SQL sql = Core.instance.sql;

            PreparedStatement st = sql.preparedStatement("SELECT * FROM friends WHERE `uuid`=?");
            st.setString(1, player.getUUID());
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                PreparedStatement create = sql.preparedStatement("INSERT INTO friends (`uuid`, `friendList`, `requests`) VALUES (?,?,?)");
                create.setString(1, player.getUUID());
                create.setString(2, "");
                create.setString(3, "");
                try {
                    create.execute();
                } finally {
                    sql.closeConnection(create);
                }
            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
