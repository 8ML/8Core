package club.mineplay.core.player.stats.ui.pages;
/*
Created by Sander on 5/5/2021
*/

import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.level.Level;
import club.mineplay.core.ui.component.components.Button;
import club.mineplay.core.ui.component.components.Label;
import club.mineplay.core.ui.page.Page;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

import java.awt.*;
import java.util.UUID;

public class StatsPage extends Page {

    private final MPlayer t;

    public StatsPage(MPlayer t) {
        super("Stats | " + t.getPlayerStr(), 27, false);
        this.t = t;

        setFrameLabel(" ");
    }

    @Override
    public void onOpen() {

        String status;
        if (this.t.isOffline()) status = ChatColor.WHITE + "Status: " + ChatColor.RED + "Offline";
        else status = ChatColor.WHITE + "Status: " + ChatColor.GREEN + "Online";

        String rank;
        if (this.t.getRankEnum().equals(Ranks.DEFAULT)) rank = ChatColor.GRAY + "Default";
        else rank = this.t.getRankEnum().getRank().getFullPrefix();

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

        Label profile = new Label(ChatColor.YELLOW + "Profile", Material.PLAYER_HEAD, getParent());
        SkullMeta meta = (SkullMeta) profile.getMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(t.getUUID())));
        profile.setItemMeta(meta);
        profile.setLore(new String[]{"", ChatColor.WHITE + "Rank: " + rank,
                ChatColor.WHITE + "Coins: " + ChatColor.GOLD + this.t.getCoins(),
                ChatColor.WHITE + "Level: " + ChatColor.of(new Color(68, 85, 90)) + ((int) Level.getLevelFromXP(this.t.getXP(), false)),
                "",
                ChatColor.WHITE + "First Login:",
                ChatColor.GRAY + this.t.firstJoin(),
                "",
                status});

        Label leveling = new Label(ChatColor.YELLOW + "Mineplay Leveling", Material.ENCHANTED_BOOK, getParent());
        leveling.setLore(new String[]{"",
                ChatColor.GRAY + "Playing games will reward",
                ChatColor.GRAY + "you with " + ChatColor.LIGHT_PURPLE + "Experience" + ChatColor.GRAY + ", which",
                ChatColor.GRAY + "is required to level up.",
                "",
                ChatColor.WHITE + "Progress: " + ChatColor.GOLD + ((int) percentage) + "%",
                ChatColor.DARK_AQUA + "Level " + ChatColor.GREEN + iLevel
                        + ChatColor.GRAY + " [" + builder.toString() + ChatColor.GRAY + "] "
                        + ChatColor.DARK_AQUA + (iLevel + 1),
                "",
                ChatColor.WHITE + "XP required: " + ChatColor.DARK_AQUA + this.t.getXP()
                        + ChatColor.GRAY + "/"
                        + ChatColor.DARK_AQUA + Level.getXPFromLevel(iLevel + 1)});

        //Buttons

        Button exit = new Button(ChatColor.RED + "Click to Close", Material.BARRIER, getParent());
        exit.setOnClick(() -> {
            getParent().getPlayer().getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        //Registry
        addComponent(profile, 3);
        addComponent(leveling, 5);
        addComponent(exit, 22);

    }

    @Override
    public void onFrameClick() {

    }
}
