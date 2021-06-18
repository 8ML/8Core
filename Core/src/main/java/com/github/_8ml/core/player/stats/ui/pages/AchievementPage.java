package com.github._8ml.core.player.stats.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on 5/20/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.ui.page.Page;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import com.github._8ml.core.player.achievement.Achievement;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.component.components.Label;

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
