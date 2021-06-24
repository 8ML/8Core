package com.github._8ml.core.player.options.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on 5/16/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.options.PlayerOptions;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.page.Page;
import com.github._8ml.core.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public class OptionsPage extends Page {


    public OptionsPage(MPlayer player) {
        super("", 54, true);
        setTitle(player.getPlayerStr() + "'s options");
    }

    @Override
    protected void onPreOpen() {

    }

    @Override
    public void onOpen() {

        for (PlayerOptions.Preference preference : PlayerOptions.Preference.values()) {

            String currentPreference = PlayerOptions.getPreference(getParent().getPlayer(), preference);

            Button preferenceBtn = new Button(MessageColor.COLOR_HIGHLIGHT
                    + StringUtils.formatCapitalization(preference.name()
                    .replaceAll("_", " ")), preference.getUiMaterial(), getParent());

            preferenceBtn.setLore(new String[]{" ",
                    MessageColor.COLOR_MAIN + "Current Preference: " + ChatColor.AQUA
                            + StringUtils.formatCapitalization(currentPreference.replaceAll("_", " "))});

            preferenceBtn.setOnClick(() -> {
                String[] preferences =
                        preference.getValues();

                String pref =
                        Arrays.asList(preferences).indexOf(currentPreference) == preferences.length - 1
                                ? preferences[0] : preferences[Arrays.asList(preferences).indexOf(currentPreference) + 1];

                PlayerOptions.updatePreference(getParent().getPlayer(), preference, pref);

                refresh();
            });

            addComponent(preferenceBtn, preference.getUiSlot());

        }

        Button exit = new Button(MessageColor.COLOR_ERROR + "Close", Material.BARRIER, getParent());
        exit.setOnClick(() -> {
            getParent().getPlayer().getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        addComponent(exit, 40);

    }


    @Override
    public void onFrameClick() {

    }
}
