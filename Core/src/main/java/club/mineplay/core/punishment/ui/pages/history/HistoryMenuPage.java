package club.mineplay.core.punishment.ui.pages.history;
/*
Created by Sander on 5/4/2021
*/

import club.mineplay.core.config.MessageColor;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.ui.component.components.Button;
import club.mineplay.core.ui.page.Page;
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

        Button bans = new Button(MessageColor.COLOR_MAIN + "Bans", Material.IRON_DOOR, getParent());
        bans.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + ChatColor.YELLOW + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " bans"});
        bans.setOnClick(() -> {
            getParent().openPage(2);
        });

        Button mutes = new Button(MessageColor.COLOR_MAIN + "Mutes", Material.WRITABLE_BOOK, getParent());
        mutes.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + ChatColor.YELLOW + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " mutes"});
        mutes.setOnClick(() -> {
            getParent().openPage(3);
        });

        Button warns = new Button(MessageColor.COLOR_MAIN + "Warns", Material.PAPER, getParent());
        warns.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + ChatColor.YELLOW + this.target.getPlayerStr()  + "'s" + MessageColor.COLOR_MAIN + " warns"});
        warns.setOnClick(() -> {
            getParent().openPage(4);
        });

        Button kicks = new Button(MessageColor.COLOR_MAIN + "Kicks", Material.STICK, getParent());
        kicks.setLore(new String[]{"",
                MessageColor.COLOR_MAIN + "Click to view all of " + ChatColor.YELLOW + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " kicks"});
        kicks.setOnClick(() -> {
            getParent().openPage(5);
        });

        Button exit = new Button(ChatColor.RED + "Go Back", Material.ARROW, getParent());
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
