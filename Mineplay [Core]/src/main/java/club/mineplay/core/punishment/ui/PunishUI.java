package club.mineplay.core.punishment.ui;
/*
Created by Sander on 4/29/2021
*/

import club.mineplay.core.Main;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.player.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PunishUI implements Listener {

    private final MPlayer target;
    private final MPlayer executor;
    private final String reason;

    public PunishUI(MPlayer target, MPlayer executor, String reason) {

        this.target = target;
        this.executor = executor;
        this.reason = reason;

        Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);

    }

    public void openUI() {

        Inventory inv = Bukkit.createInventory(null, 54, "Punish " + this.target.getPlayerStr());

        //Frame
        ItemStack frame = new ItemStack(Material.GRAY_STAINED_GLASS);
        ItemMeta frameMeta = frame.getItemMeta();
        assert frameMeta != null;
        frameMeta.setDisplayName("Reason: " + this.reason);
        frame.setItemMeta(frameMeta);

        for (int i = 0; i < 9; i++) {

            inv.setItem(i, frame);

        }

        for (int i = 45; i < 54; i++) {

            inv.setItem(i, frame);

        }

        inv.setItem(9, frame);
        inv.setItem(18, frame);
        inv.setItem(27, frame);
        inv.setItem(36, frame);
        inv.setItem(17, frame);
        inv.setItem(26, frame);
        inv.setItem(35, frame);
        inv.setItem(44, frame);

        //MUTE

        ItemStack muteOne = new ItemStack(Material.LIME_DYE);
        ItemMeta muteOneMeta = muteOne.getItemMeta();
        assert muteOneMeta != null;
        muteOneMeta.setDisplayName(MessageColor.COLOR_MAIN + "1 Day");
        List<String> muteOneLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "1.0 Days",
                "");
        muteOneMeta.setLore(muteOneLore);
        muteOne.setItemMeta(muteOneMeta);

        ItemStack muteTwo = new ItemStack(Material.YELLOW_DYE);
        ItemMeta muteTwoMeta = muteTwo.getItemMeta();
        assert muteTwoMeta != null;
        muteTwoMeta.setDisplayName(MessageColor.COLOR_MAIN + "7 Days");
        List<String> muteTwoLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "7.0 Days",
                "");
        muteTwoMeta.setLore(muteTwoLore);
        muteTwo.setItemMeta(muteTwoMeta);

        ItemStack muteThree = new ItemStack(Material.ORANGE_DYE);
        ItemMeta muteThreeMeta = muteThree.getItemMeta();
        assert muteThreeMeta != null;
        muteThreeMeta.setDisplayName(MessageColor.COLOR_MAIN + "30 Days");
        List<String> muteThreeLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "30.0 Days",
                "");
        muteThreeMeta.setLore(muteThreeLore);
        muteThree.setItemMeta(muteThreeMeta);

        ItemStack muteFour = new ItemStack(Material.RED_DYE);
        ItemMeta muteFourMeta = muteFour.getItemMeta();
        assert muteFourMeta != null;
        muteFourMeta.setDisplayName(MessageColor.COLOR_MAIN + "60 Days");
        List<String> muteFourLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "60.0 Days",
                "");
        muteFourMeta.setLore(muteFourLore);
        muteFour.setItemMeta(muteFourMeta);

        ItemStack mutePerm = new ItemStack(Material.REDSTONE);
        ItemMeta mutePermMeta = mutePerm.getItemMeta();
        assert mutePermMeta != null;
        mutePermMeta.setDisplayName(ChatColor.RED + "Permanent");
        List<String> mutePermLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to mute " + ChatColor.YELLOW + "Permanently",
                "");
        mutePermMeta.setLore(mutePermLore);
        mutePerm.setItemMeta(mutePermMeta);
        
        //BAN

        ItemStack banOne = new ItemStack(Material.LIME_DYE);
        ItemMeta banOneMeta = banOne.getItemMeta();
        assert banOneMeta != null;
        banOneMeta.setDisplayName(MessageColor.COLOR_MAIN + "7 Days");
        List<String> banOneLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "7.0 Days",
                "");
        banOneMeta.setLore(banOneLore);
        banOne.setItemMeta(banOneMeta);

        ItemStack banTwo = new ItemStack(Material.YELLOW_DYE);
        ItemMeta banTwoMeta = banTwo.getItemMeta();
        assert banTwoMeta != null;
        banTwoMeta.setDisplayName(MessageColor.COLOR_MAIN + "30 Days");
        List<String> banTwoLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "30.0 Days",
                "");
        banTwoMeta.setLore(banTwoLore);
        banTwo.setItemMeta(banTwoMeta);

        ItemStack banThree = new ItemStack(Material.ORANGE_DYE);
        ItemMeta banThreeMeta = banThree.getItemMeta();
        assert banThreeMeta != null;
        banThreeMeta.setDisplayName(MessageColor.COLOR_MAIN + "60 Days");
        List<String> banThreeLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "60.0 Days",
                "");
        banThreeMeta.setLore(banThreeLore);
        banThree.setItemMeta(banThreeMeta);

        ItemStack banFour = new ItemStack(Material.RED_DYE);
        ItemMeta banFourMeta = banFour.getItemMeta();
        assert banFourMeta != null;
        banFourMeta.setDisplayName(MessageColor.COLOR_MAIN + "90 Days");
        List<String> banFourLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "90.0 Days",
                "");
        banFourMeta.setLore(banFourLore);
        banFour.setItemMeta(banFourMeta);

        ItemStack banPerm = new ItemStack(Material.REDSTONE);
        ItemMeta banPermMeta = banPerm.getItemMeta();
        assert banPermMeta != null;
        banPermMeta.setDisplayName(ChatColor.RED + "Permanent");
        List<String> banPermLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to ban " + ChatColor.YELLOW + "Permanently",
                "");
        banPermMeta.setLore(banPermLore);
        banPerm.setItemMeta(banPermMeta);
        
        //OTHER
        
        ItemStack warn = new ItemStack(Material.PAPER);
        ItemMeta warnMeta = warn.getItemMeta();
        assert warnMeta != null;
        warnMeta.setDisplayName(MessageColor.COLOR_MAIN + "Warn");
        List<String> warnLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to warn", "");
        warnMeta.setLore(warnLore);
        warn.setItemMeta(warnMeta);

        ItemStack kick = new ItemStack(Material.STICK);
        ItemMeta kickMeta = kick.getItemMeta();
        assert kickMeta != null;
        kickMeta.setDisplayName(MessageColor.COLOR_MAIN + "Kick");
        List<String> kickLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to kick", "");
        kickMeta.setLore(kickLore);
        kick.setItemMeta(kickMeta);

        //CATEGORY

        ItemStack muteCat = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta muteCatMeta = muteCat.getItemMeta();
        assert muteCatMeta != null;
        muteCatMeta.setDisplayName(ChatColor.GREEN + "Mute");
        List<String> muteCatLore = Arrays.asList("", MessageColor.COLOR_MAIN + "List of all the mutes", "");
        muteCatMeta.setLore(muteCatLore);
        muteCat.setItemMeta(muteCatMeta);

        ItemStack banCat = new ItemStack(Material.IRON_DOOR);
        ItemMeta banCatMeta = banCat.getItemMeta();
        assert banCatMeta != null;
        banCatMeta.setDisplayName(ChatColor.GREEN + "Ban");
        List<String> banCatLore = Arrays.asList("", MessageColor.COLOR_MAIN + "List of all the bans", "");
        banCatMeta.setLore(banCatLore);
        banCat.setItemMeta(banCatMeta);

        //ADD TO INV

        inv.setItem(10, banCat);
        inv.setItem(11, banOne);
        inv.setItem(12, banTwo);
        inv.setItem(13, banThree);
        inv.setItem(14, banFour);
        inv.setItem(16, banPerm);

        inv.setItem(37, muteCat);
        inv.setItem(38, muteOne);
        inv.setItem(39, muteTwo);
        inv.setItem(40, muteThree);
        inv.setItem(41, muteFour);
        inv.setItem(43, mutePerm);

        inv.setItem(22, warn);
        inv.setItem(31, kick);

        this.executor.getPlayer().openInventory(inv);

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getView().getTitle().toUpperCase().startsWith("PUNISH")) {

            e.setCancelled(true);


        }

    }

    public String getReason() {
        return reason;
    }

    public MPlayer getExecutor() {
        return executor;
    }

    public MPlayer getTarget() {
        return target;
    }
}
