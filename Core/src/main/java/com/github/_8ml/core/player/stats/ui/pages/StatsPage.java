package com.github._8ml.core.player.stats.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on 5/5/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.achievement.Achievement;
import com.github._8ml.core.player.level.Level;
import com.github._8ml.core.ui.page.Page;
import com.github._8ml.core.Core;
import com.github._8ml.core.player.hierarchy.Ranks;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.component.components.Label;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class StatsPage extends Page {

    private final MPlayer t;

    public StatsPage(MPlayer t) {
        super(t.getPlayerStr() + "'s Profile", 27, false);
        this.t = t;

        setFrameLabel(" ");
    }

    @Override
    public void onOpen() {

        String status = this.t.isOffline() ? ChatColor.WHITE + "Status: " + ChatColor.RED + "Offline" : ChatColor.WHITE
                + "Status: " + ChatColor.GREEN + "Online" +ChatColor.GRAY + " ("
                + Core.instance.pluginMessenger.getServer(t.getPlayerStr()) + ")";

        String rank = this.t.getRankEnum().equals(Ranks.DEFAULT)
                ? "Default" : this.t.getRankEnum().getRank().getFullPrefix();

        //Level calculations

        double level = Level.getLevelFromXP(this.t.getXP(), true);
        double levelF = Math.floor(level);
        int iLevel = (int) levelF;
        double percentage = (level - Math.floor(level)) * 100;
        double percentage10 = percentage / 10;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            if (i == 0 && percentage10 < 1) {
                builder.append(ChatColor.DARK_GRAY).append("-");
                continue;
            }
            if (i <= percentage10) builder.append(ChatColor.GREEN).append("-");
            else builder.append(ChatColor.DARK_GRAY).append("-");
        }

        //Gray Line
        for (int i = 9; i < 18; i++) {
            Label gray = new Label(" ", Material.GRAY_STAINED_GLASS_PANE, getParent());
            addComponent(gray, i);
        }

        //Labels

        Label profile = new Label(ChatColor.YELLOW + t.getPlayerStr() + "'s Profile", Material.PLAYER_HEAD, getParent());
        SkullMeta meta = (SkullMeta) profile.getMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(t.getUUID())));
        profile.setItemMeta(meta);
        profile.setLore(new String[]{"", ChatColor.WHITE + "Rank: " + rank,
                ChatColor.WHITE + "Coins: " + ChatColor.GOLD + this.t.getCoins(),
                ChatColor.WHITE + "Level: " + ChatColor.AQUA + ((int) Level.getLevelFromXP(this.t.getXP(), false)),
                "",
                ChatColor.WHITE + "First Login: " +
                ChatColor.GRAY + this.t.firstJoin(),
                "",
                status});

        Label leveling = new Label(ChatColor.YELLOW + "8Core Leveling", Material.ENCHANTED_BOOK, getParent());
        leveling.setLore(new String[]{"",
                ChatColor.GRAY + "Playing games will reward",
                ChatColor.GRAY + "you with " + ChatColor.LIGHT_PURPLE + "Experience" + ChatColor.GRAY + ", which",
                ChatColor.GRAY + "is required to level up.",
                "",
                ChatColor.WHITE + "Progress: " + ChatColor.GOLD + ((int) percentage) + "%",
                ChatColor.GRAY + "Level " + ChatColor.GREEN + iLevel
                        + ChatColor.GRAY + " [" + builder + ChatColor.GRAY + "] "
                        + ChatColor.GRAY + (iLevel + 1),
                "",
                ChatColor.WHITE + "Required XP: " + ChatColor.GRAY + this.t.getXP() +
                        ChatColor.DARK_GRAY + "/"
                        + ChatColor.GRAY + Level.getXPFromLevel(iLevel + 1)});

        //Buttons
        //-> Achievements

        Set<Achievement> achievementClasses = Achievement.getAchievementClasses();
        int achievementsCompleted = 0;
        for (Achievement achievement : achievementClasses) {
            if (achievement.hasAchievement(t)) achievementsCompleted++;
        }


        Button achievements = new Button(ChatColor.YELLOW + "Achievements "
                + ChatColor.GRAY + "(" + ChatColor.GREEN + "Click" + ChatColor.GRAY + ")",
                Material.BOOK, getParent());

        achievements.setLore(new String[]{"",
                ChatColor.GRAY + "Track your progress and",
                ChatColor.GRAY + "complete achievements",
                ChatColor.GRAY + "for " + ChatColor.GOLD
                        + "Coins " + ChatColor.GRAY + "and " + ChatColor.LIGHT_PURPLE
                        + "XP" + ChatColor.GRAY + ".",
                "",
                ChatColor.GRAY + "Completed: " + ChatColor.LIGHT_PURPLE + achievementsCompleted
                        + ChatColor.GRAY + "/" + achievementClasses.size()});

        achievements.setOnClick(() -> {
            getParent().openPage(1);
        });

        Button exit = new Button(ChatColor.RED + "Close", Material.BARRIER, getParent());
        exit.setOnClick(() -> {
            getParent().getPlayer().getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        //Registry
        addComponent(profile, 2);
        addComponent(achievements, 4);
        addComponent(leveling, 6);
        addComponent(exit, 22);

    }

    @Override
    public void onFrameClick() {

    }
}
