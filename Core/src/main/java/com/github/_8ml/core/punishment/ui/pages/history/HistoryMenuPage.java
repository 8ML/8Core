package com.github._8ml.core.punishment.ui.pages.history;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.page.Page;
import com.github._8ml.core.config.MessageColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class HistoryMenuPage extends Page {

    private final MPlayer target;

    public HistoryMenuPage(MPlayer target) {
        super("History", 54, true);

        this.target = target;

        setFrameLabel(" ");
    }

    @Override
    public void onOpen() {

        Button bans = new Button(MessageColor.COLOR_HIGHLIGHT + "Bans", Material.IRON_DOOR, getParent());
        bans.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + MessageColor.COLOR_HIGHLIGHT + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " bans"});
        bans.setOnClick(() -> {
            getParent().openPage(2);
        });

        Button mutes = new Button(MessageColor.COLOR_HIGHLIGHT + "Mutes", Material.WRITABLE_BOOK, getParent());
        mutes.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + MessageColor.COLOR_HIGHLIGHT + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " mutes"});
        mutes.setOnClick(() -> {
            getParent().openPage(3);
        });

        Button warns = new Button(MessageColor.COLOR_HIGHLIGHT + "Warns", Material.PAPER, getParent());
        warns.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + MessageColor.COLOR_HIGHLIGHT + this.target.getPlayerStr()  + "'s" + MessageColor.COLOR_MAIN + " warns"});
        warns.setOnClick(() -> {
            getParent().openPage(4);
        });

        Button kicks = new Button(MessageColor.COLOR_HIGHLIGHT + "Kicks", Material.STICK, getParent());
        kicks.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + MessageColor.COLOR_HIGHLIGHT + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " kicks"});
        kicks.setOnClick(() -> {
            getParent().openPage(5);
        });

        Button exit = new Button(MessageColor.COLOR_ERROR + "Go Back", Material.ARROW, getParent());
        exit.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Return to main menu"});
        exit.setOnClick(() -> {
            getParent().openPage(0);
        });

        addComponent(bans, 19);
        addComponent(mutes, 21);
        addComponent(warns, 23);
        addComponent(kicks, 25);
        addComponent(exit, 40);

    }

    @Override
    public void onFrameClick() {

    }
}
