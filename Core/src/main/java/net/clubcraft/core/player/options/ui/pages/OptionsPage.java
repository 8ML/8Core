package net.clubcraft.core.player.options.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import net.clubcraft.core.player.MPlayer;
import net.clubcraft.core.player.options.PlayerOptions;
import net.clubcraft.core.ui.component.components.Button;
import net.clubcraft.core.ui.page.Page;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public class OptionsPage extends Page {

    public OptionsPage(MPlayer player) {
        super("", 54, true);
        setTitle(player.getPlayerStr() + "'s options");
    }

    @Override
    public void onOpen() {

        if (!PlayerOptions.preferences.containsKey(getParent().getPlayer())) {
            PlayerOptions.fetchPreferences(getParent().getPlayer());
            if (!PlayerOptions.preferences.get(getParent().getPlayer()).containsKey("PRIVATE_MESSAGE")) {
                PlayerOptions.updatePreference(getParent().getPlayer(), "PRIVATE_MESSAGE",
                        PlayerOptions.PrivateMessagePreference.FRIENDS_ONLY.toString());
            }
        }



        PlayerOptions.PrivateMessagePreference currentPreference =
                PlayerOptions.PrivateMessagePreference
                        .valueOf(PlayerOptions.preferences.get(getParent().getPlayer()).get("PRIVATE_MESSAGE"));

        Button privateMessagePreference = new Button(Material.PAPER, getParent());
        privateMessagePreference.setLabel(ChatColor.GREEN + "Update your preference for Private Messages");
        privateMessagePreference.setLore(new String[]{"",
                ChatColor.GRAY + "Current Preference: " + ChatColor.AQUA
                        + currentPreference.toString().replaceAll("_", " ")});

        privateMessagePreference.setOnClick(() -> {

            PlayerOptions.PrivateMessagePreference[] preferences =
                    PlayerOptions.PrivateMessagePreference.values();


            PlayerOptions.PrivateMessagePreference preference =
                    Arrays.asList(preferences).indexOf(currentPreference) == preferences.length - 1
                    ? preferences[0] : preferences[Arrays.asList(preferences).indexOf(currentPreference) + 1];

            PlayerOptions.updatePreference(getParent().getPlayer(), "PRIVATE_MESSAGE", preference.toString());

            getParent().openPage(0);

        });

        Button exit = new Button(ChatColor.RED + "Close", Material.BARRIER, getParent());
        exit.setOnClick(() -> {
            getParent().getPlayer().getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        addComponent(privateMessagePreference, 13);
        addComponent(exit, 40);

    }

    @Override
    public void onFrameClick() {

    }
}
