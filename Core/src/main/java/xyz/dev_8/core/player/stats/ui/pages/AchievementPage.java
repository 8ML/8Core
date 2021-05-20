package xyz.dev_8.core.player.stats.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on 5/20/2021
*/

import org.bukkit.ChatColor;
import org.bukkit.Material;
import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.player.achievement.Achievement;
import xyz.dev_8.core.ui.component.components.Button;
import xyz.dev_8.core.ui.component.components.Label;
import xyz.dev_8.core.ui.page.Page;

public class AchievementPage extends Page {

    private final MPlayer player;

    public AchievementPage(MPlayer player) {
        super(player.getPlayerStr() + "'s Achievements", 45, true);
        this.player = player;
    }

    @Override
    public void onOpen() {

        int num = 10;

        for (Achievement achievement : Achievement.getAchievementClasses()) {

            if (num >= 25) continue;

            Label ach = new Label(Material.BOOK, getParent());
            ach.setLabel(ChatColor.GREEN + achievement.getName());
            ach.setLore(new String[]{
                    ChatColor.GRAY + achievement.getDescription(),
                    "",
                    achievement.hasAchievement(player) ? ChatColor.GREEN + "Completed!"
                            : ChatColor.RED + "Not Completed!"
            });

            addComponent(ach, num);
            num++;
            if (getFrameSlots().contains(num)) num+=2;

        }

        Button exit = new Button(ChatColor.RED + "Close", Material.BARRIER, getParent());
        exit.setOnClick(() -> {
            player.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        addComponent(exit, 31);

    }

    @Override
    public void onFrameClick() {

    }
}
