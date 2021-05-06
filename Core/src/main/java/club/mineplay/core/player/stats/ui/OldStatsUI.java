package club.mineplay.core.player.stats.ui;
/*
Created by Sander on 5/3/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.level.Level;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.awt.*;
import java.util.List;
import java.util.*;

@Deprecated
public class OldStatsUI implements Listener {

    private final String session;
    private final MPlayer player;
    private final Player executor;

    public OldStatsUI(MPlayer player, Player executor) {
        this.session = UUID.randomUUID().toString();
        this.player = player;
        this.executor = executor;
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.instance);

    }

    public void open() {

        Inventory inv = Bukkit.createInventory(null, 27, "Stats | " + this.player.getPlayerStr());

        ItemStack frame = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta frameMeta = frame.getItemMeta();
        assert frameMeta != null;
        frameMeta.setDisplayName(" ");
        List<String> frameLore = Collections.singletonList(ChatColor.DARK_GRAY + this.session);
        frameMeta.setLore(frameLore);
        frame.setItemMeta(frameMeta);

        for (int i = 9; i < 18; i++) {
            inv.setItem(i, frame);
        }

        ItemStack profile = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta profileMeta = (SkullMeta) profile.getItemMeta();
        assert profileMeta != null;

        profileMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(this.player.getUUID())));
        profileMeta.setDisplayName(ChatColor.YELLOW + "Profile");

        String status;
        if (this.player.isOffline()) status = ChatColor.WHITE + "Status: " + ChatColor.RED + "Offline";
        else status = ChatColor.WHITE + "Status: " + ChatColor.GREEN + "Online";

        String rank;
        if (this.player.getRankEnum().equals(Ranks.DEFAULT)) rank = ChatColor.GRAY + "Default";
        else rank = this.player.getRankEnum().getRank().getFullPrefix();

        List<String> profileLore = Arrays.asList("", ChatColor.WHITE + "Rank: " + rank,
                ChatColor.WHITE + "Coins: " + ChatColor.GOLD + this.player.getCoins(),
                ChatColor.WHITE + "Level: " + ChatColor.of(new Color(68, 85, 90)) + ((int) Level.getLevelFromXP(this.player.getXP(), false)),
                "",
                ChatColor.WHITE + "First Login:",
                ChatColor.GRAY + this.player.firstJoin(),
                "",
                status);

        profileMeta.setLore(profileLore);
        profile.setItemMeta(profileMeta);

        ItemStack leveling = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta levelingMeta = leveling.getItemMeta();
        assert levelingMeta != null;

        levelingMeta.setDisplayName(ChatColor.YELLOW + "Mineplay Leveling");

        //Level calculations

        double level = Level.getLevelFromXP(this.player.getXP(), true);
        double levelF = Math.floor(level);
        int iLevel = (int) levelF;
        double percentage = (level - Math.floor(level)) * 100;
        double percentage10 = percentage / 10;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            if (i == 0 && percentage10 < 1) { builder.append(ChatColor.DARK_GRAY).append("-"); continue; }
            if (i <= percentage10) builder.append(ChatColor.GREEN).append("-");
            else builder.append(ChatColor.DARK_GRAY).append("-");
        }

        List<String> levelingLore = Arrays.asList("",
                ChatColor.GRAY + "Playing games will reward",
                ChatColor.GRAY + "you with " + ChatColor.LIGHT_PURPLE + "Experience" + ChatColor.GRAY + ", which",
                ChatColor.GRAY + "is required to level up.",
                "",
                ChatColor.WHITE + "Progress: " + ChatColor.GOLD + ((int) percentage) + "%",
                ChatColor.DARK_AQUA + "Level " + ChatColor.GREEN + iLevel
                        + ChatColor.GRAY + " [" + builder.toString() + ChatColor.GRAY + "] "
                        + ChatColor.DARK_AQUA + (iLevel + 1),
                "",
                ChatColor.WHITE + "XP required: " + ChatColor.DARK_AQUA + this.player.getXP()
                        + ChatColor.GRAY + "/"
                        + ChatColor.DARK_AQUA + Level.getXPFromLevel(iLevel + 1));

        levelingMeta.setLore(levelingLore);
        leveling.setItemMeta(levelingMeta);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        assert exitMeta != null;

        exitMeta.setDisplayName(ChatColor.RED + "Click to Close");

        exit.setItemMeta(exitMeta);

        inv.setItem(3, profile);
        inv.setItem(5, leveling);
        inv.setItem(22, exit);

        this.executor.openInventory(inv);

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().toUpperCase().startsWith("STATS | ")) {
            if (ChatColor.stripColor(
                    Objects.requireNonNull(
                            Objects.requireNonNull(
                                    Objects.requireNonNull(
                                            Objects.requireNonNull(e.getClickedInventory()).getItem(9))
                                            .getItemMeta()).getLore()).get(0)).equalsIgnoreCase(this.session)) {

                e.setCancelled(true);

                if (e.getSlot() == 22) {

                    e.getWhoClicked().closeInventory();

                }


            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().toUpperCase().startsWith("STATS | ")) {
            if (ChatColor.stripColor(
                    Objects.requireNonNull(
                            Objects.requireNonNull(
                                    Objects.requireNonNull(
                                            Objects.requireNonNull(e.getInventory()).getItem(9))
                                            .getItemMeta()).getLore()).get(0)).equalsIgnoreCase(this.session)) {

                HandlerList.unregisterAll(this);


            }
        }
    }

}
