package net.clubcraft.core.player.options.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.player.options.PlayerOptions;
import net.clubcraft.core.ui.component.components.Button;
import net.clubcraft.core.ui.page.Page;
import net.clubcraft.core.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public class OptionsPage extends Page {

    private Button privateMessagePreference;
    private PlayerOptions.PrivateMessagePreference currentPrivateMessagePreference;
    
    private Button friendRequestPreference;
    private PlayerOptions.FriendRequestPreference currentFriendRequestPreference;

    public OptionsPage(MPlayer player) {
        super("", 54, true);
        setTitle(player.getPlayerStr() + "'s options");
    }

    @Override
    public void onOpen() {

        initPrivateMessage();
        initFriendRequest();

        Button exit = new Button(ChatColor.RED + "Close", Material.BARRIER, getParent());
        exit.setOnClick(() -> {
            getParent().getPlayer().getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        addComponent(privateMessagePreference, 15);
        addComponent(friendRequestPreference, 11);
        addComponent(exit, 40);

    }

    private void initPrivateMessage() {
        if (PlayerOptions.check(getParent().getPlayer(), "PRIVATE_MESSAGE")) {
            PlayerOptions.updatePreference(getParent().getPlayer(), "PRIVATE_MESSAGE",
                    PlayerOptions.PrivateMessagePreference.FRIENDS_ONLY.toString());
        }

        currentPrivateMessagePreference =
                PlayerOptions.PrivateMessagePreference
                        .valueOf(PlayerOptions.preferences.get(getParent().getPlayer()).get("PRIVATE_MESSAGE"));

        privateMessagePreference = new Button(Material.PAPER, getParent());
        privateMessagePreference.setLabel(ChatColor.GREEN + "Private Messages");
        privateMessagePreference.setLore(new String[]{"",
                ChatColor.GRAY + "Current Preference: " + ChatColor.AQUA
                        + StringUtils.formatCapitalization(currentPrivateMessagePreference.toString().replaceAll("_", " "))});

        privateMessagePreference.setOnClick(() -> {

            PlayerOptions.PrivateMessagePreference[] preferences =
                    PlayerOptions.PrivateMessagePreference.values();


            PlayerOptions.PrivateMessagePreference preference =
                    Arrays.asList(preferences).indexOf(currentPrivateMessagePreference) == preferences.length - 1
                            ? preferences[0] : preferences[Arrays.asList(preferences).indexOf(currentPrivateMessagePreference) + 1];

            PlayerOptions.updatePreference(getParent().getPlayer(), "PRIVATE_MESSAGE", preference.toString());

            refresh();

        });
    }

    private void initFriendRequest() {
        if (PlayerOptions.check(getParent().getPlayer(), "FRIEND_REQUEST")) {
            PlayerOptions.updatePreference(getParent().getPlayer(), "FRIEND_REQUEST",
                    PlayerOptions.FriendRequestPreference.ANYONE.toString());
        }

        currentFriendRequestPreference =
                PlayerOptions.FriendRequestPreference
                        .valueOf(PlayerOptions.preferences.get(getParent().getPlayer()).get("FRIEND_REQUEST"));

        friendRequestPreference = new Button(Material.POPPY, getParent());
        friendRequestPreference.setLabel(ChatColor.GREEN + "Friend Requests");
        friendRequestPreference.setLore(new String[]{"",
                ChatColor.GRAY + "Current Preference: " + ChatColor.AQUA
                        + StringUtils.formatCapitalization(currentFriendRequestPreference.toString().replaceAll("_", " "))});

        friendRequestPreference.setOnClick(() -> {

            PlayerOptions.FriendRequestPreference[] preferences =
                    PlayerOptions.FriendRequestPreference.values();


            PlayerOptions.FriendRequestPreference preference =
                    Arrays.asList(preferences).indexOf(currentFriendRequestPreference) == preferences.length - 1
                            ? preferences[0] : preferences[Arrays.asList(preferences).indexOf(currentFriendRequestPreference) + 1];

            PlayerOptions.updatePreference(getParent().getPlayer(), "FRIEND_REQUEST", preference.toString());

            refresh();

        });
    }



    @Override
    public void onFrameClick() {

    }
}
