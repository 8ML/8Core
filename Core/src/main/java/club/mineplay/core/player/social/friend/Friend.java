package club.mineplay.core.player.social.friend;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.options.PlayerOptions;
import club.mineplay.core.storage.SQL;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Friend {

    public final static Map<MPlayer, Friend> friendManager = new HashMap<>();

    private final String PREFIX = ChatColor.AQUA + "[FRIEND] ";

    private final MPlayer player;
    private final SQL sql = Core.instance.sql;

    public Friend(MPlayer player) {
        this.player = player;
    }

    public void sendRequest(MPlayer player) {

        if (!player.isOffline()) {

            BaseComponent[] requestMessage =
                    new ComponentBuilder(this.PREFIX + ChatColor.GREEN + this.player.getPlayerStr() + " is requesting to be your friend! ")
                    .append(ChatColor.GREEN + "" + ChatColor.BOLD + "CLICK TO ACCEPT")
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend " + this.player.getPlayerStr()))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.YELLOW + "/friend " + this.player.getPlayerStr())))
                    .create();

            player.getPlayer().spigot().sendMessage(requestMessage);

        }

        this.player.getPlayer().sendMessage(this.PREFIX + ChatColor.GREEN + "You sent a request to " + player.getPlayerStr());

        Friend friendM = friendManager.containsKey(player) ? friendManager.get(player)
                : new Friend(player);

        friendM.addRequest(player);

    }

    public boolean checkRequest(MPlayer player) {

        boolean result = false;

        try {

            PreparedStatement st = sql.preparedStatement("SELECT * FROM friends WHERE `uuid`=?");
            st.setString(1, this.player.getUUID());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                String[] requests = rs.getString("requests").split(",");
                if (Arrays.asList(requests).contains(player.getPlayerStr())) {

                    result = true;

                    sql.closeConnection(st);

                }

            }

            sql.closeConnection(st);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    public void addFriend(MPlayer player) {

        if (checkRequest(player)) {

            add(player);
            if (!this.player.isOffline()) {
                this.player.getPlayer().sendMessage(this.PREFIX + ChatColor.GREEN + player.getPlayerStr() + " is now your friend!");
            }
            if (!player.isOffline()) {
                player.getPlayer().sendMessage(this.PREFIX + ChatColor.GREEN + this.player.getPlayerStr() + " is now your friend!");
            }


        } else {

            sendRequest(player);

        }

    }

    private void add(MPlayer player) {
        try {

            List<MPlayer> currentFriends = getFriends();
            currentFriends.add(player);

            StringBuilder friendsList = new StringBuilder(currentFriends.get(0).getPlayerStr());
            for (MPlayer friend : currentFriends) {
                if (currentFriends.indexOf(friend) == 0) continue;
                friendsList.append(",").append(friend.getPlayerStr());
            }

            PreparedStatement st = sql.preparedStatement("INSERT INTO friends SET `friendList`=? WHERE `uuid`=?");
            st.setString(1, friendsList.toString());
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

    public void remove(MPlayer player) {

        List<MPlayer> friends = getFriends();
        friends.remove(player);

        try {

            StringBuilder friendsList = new StringBuilder(friends.get(0).getPlayerStr());
            for (MPlayer pl : friends) {
                if (friends.indexOf(pl) == 0) continue;
                friendsList.append(",").append(pl.getPlayerStr());
            }

            PreparedStatement st = sql.preparedStatement("UPDATE friends SET `friendList`=? WHERE `uuid`=?");
            st.setString(1, friendsList.toString());
            st.setString(2, this.player.getUUID());

            try {
                st.executeUpdate();
            } finally {
                sql.closeConnection(st);
            }

            if (!player.isOffline()) {
                player.getPlayer().sendMessage(this.PREFIX + ChatColor.RED + "You are no longer friends with " + this.player.getPlayerStr());
                this.player.getPlayer().sendMessage(this.PREFIX + ChatColor.RED + "You are no longer friends with " + player.getPlayerStr());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<MPlayer> getFriends() {

        try {
            List<MPlayer> players = new ArrayList<>();
            PreparedStatement st = sql.preparedStatement("SELECT * FROM friends WHERE `uuid`=?");
            st.setString(1, this.player.getUUID());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                String[] friends = rs.getString("friendList").split(",");
                for (String friend : friends) {
                    players.add(MPlayer.getMPlayer(friend));
                }

                sql.closeConnection(st);


            }

            sql.closeConnection(st);

            return players;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();

    }

    public void addRequest(MPlayer player) {
        try {

            List<MPlayer> requests = new ArrayList<>();

            PreparedStatement st = sql.preparedStatement("SELECT * FROM friends WHERE `uuid`=?");
            st.setString(1, this.player.getUUID());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                String[] requestArray = rs.getString("requests").split(",");
                for (String playerStr : requestArray) {
                    requests.add(MPlayer.getMPlayer(playerStr));
                }


            }

            requests.add(player);

            sql.closeConnection(st);

            StringBuilder requestListBuilder = new StringBuilder(requests.get(0).getPlayerStr());
            for (MPlayer pl : requests) {
                if (requests.indexOf(pl) == 0) continue;
                requestListBuilder.append(",").append(pl.getPlayerStr());
            }

            PreparedStatement update = sql.preparedStatement("UPDATE friends SET `requests`=? WHERE uuid=?");
            update.setString(1, requestListBuilder.toString());
            update.setString(2, this.player.getUUID());

            try {
                update.executeUpdate();
            } finally {
                sql.closeConnection(update);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static final Map<Player, Player> lastRecipient = new HashMap<>();

    public void sendMessage(Player sender, Player receiver, String message) {

        if (!PlayerOptions.preferences.get(player).containsKey("PRIVATE_MESSAGE"))
            PlayerOptions.updatePreference(player, "PRIVATE_MESSAGE",
                    PlayerOptions.PrivateMessagePreference.FRIENDS_ONLY.toString());

        PlayerOptions.PrivateMessagePreference preference =
                PlayerOptions.PrivateMessagePreference
                        .valueOf(PlayerOptions.preferences.get(player).get("PRIVATE_MESSAGE"));

        if (preference.equals(PlayerOptions.PrivateMessagePreference.OFF)) {
            sender.sendMessage(MessageColor.COLOR_ERROR + receiver.getName() + " has private messaging disabled!");
            return;
        }

        if (preference.equals(PlayerOptions.PrivateMessagePreference.FRIENDS_ONLY)) {
            if (!getFriends().contains(MPlayer.getMPlayer(sender.getName()))) {
                sender.sendMessage(MessageColor.COLOR_ERROR + "Only " + receiver.getName() + "'s friends can message them");
                return;
            }
        }

        String msgRecipient = ChatColor.AQUA + "[FRIEND] " + ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + message;
        String msgSender = ChatColor.AQUA +  "[FRIEND] " + ChatColor.WHITE + "YOU -> " + receiver.getName() + ": " + message;
        sender.sendMessage(msgSender);

        lastRecipient.put(sender, receiver);
        lastRecipient.put(receiver, sender);

        Core.instance.pluginMessenger.sendPluginMessage("FRIEND_CHANNEL", "",
                Collections.singletonList(receiver.getName()), msgRecipient);

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
                create.setString(2, player.getPlayerStr() + ",");
                create.setString(3, player.getPlayerStr() + ",");
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
