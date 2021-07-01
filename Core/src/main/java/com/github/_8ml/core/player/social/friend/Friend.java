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

    private void add(MPlayer player) {

        List<MPlayer> currentFriends = getFriends();
        currentFriends.add(player);
        String friendsStr = buildList(currentFriends);
        update("friendList", friendsStr);


    }

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

    public void addRequest(MPlayer player) {

        List<MPlayer> requests = getRequests();
        requests.add(player);
        String requestList = buildList(requests);
        update("requests", requestList);

    }

    public void removeRequest(MPlayer player) {

        List<MPlayer> requests = getRequests();
        requests.remove(player);
        String requestsList = buildList(requests);
        update("requests", requestsList);
    }

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

    public boolean isFriendsWith(MPlayer player) {

        for (MPlayer p : getFriends()) {
            if (p.getUUID().equalsIgnoreCase(player.getUUID())) return true;
        }

        return false;
    }

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


    public static final Map<MPlayer, MPlayer> lastRecipient = new HashMap<>();

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

    public static Friend getFriendManager(MPlayer player) {
        if (friendManager.containsKey(player)) return friendManager.get(player);
        Friend fm = new Friend(player);
        friendManager.put(player, fm);
        return fm;
    }

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
