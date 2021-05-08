package club.mineplay.core.punishment.ui;
/*
Created by Sander on 4/29/2021
*/

import club.mineplay.core.Core;
import club.mineplay.core.config.MessageColor;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.PunishInfo;
import club.mineplay.core.punishment.Punishment;
import club.mineplay.core.punishment.type.Ban;
import club.mineplay.core.punishment.type.Kick;
import club.mineplay.core.punishment.type.Mute;
import club.mineplay.core.punishment.type.Warn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Deprecated
public class OldPunishUI implements Listener {

    private final MPlayer target;
    private final MPlayer executor;
    private final String reason;
    private final List<Integer> graySlots = new ArrayList<>();

    private Punishment.PunishType type;
    private Punishment.PunishTime time;

    private final String session;

    public OldPunishUI(MPlayer target, MPlayer executor, String reason) {

        this.target = target;
        this.executor = executor;
        this.reason = reason;

        Core.instance.getServer().getPluginManager().registerEvents(this, Core.instance);

        this.session = UUID.randomUUID().toString();

    }

    public void openUI() {

        Inventory inv = Bukkit.createInventory(null, 54, "Punish " + this.target.getPlayerStr());

        //Frame

        ItemStack frame = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta frameMeta = frame.getItemMeta();
        assert frameMeta != null;
        frameMeta.setDisplayName(ChatColor.WHITE + "Reason: " + this.reason);
        List<String> frameLore = Arrays.asList(ChatColor.GREEN + "Click to view history", "", ChatColor.DARK_GRAY + this.session);
        frameMeta.setLore(frameLore);
        frame.setItemMeta(frameMeta);

        for (int i = 0; i < 9; i++) {

            inv.setItem(i, frame);
            graySlots.add(i);

        }

        for (int i = 45; i < 54; i++) {

            inv.setItem(i, frame);
            graySlots.add(i);

        }

        inv.setItem(9, frame);
        inv.setItem(18, frame);
        inv.setItem(27, frame);
        inv.setItem(36, frame);
        inv.setItem(17, frame);
        inv.setItem(26, frame);
        inv.setItem(35, frame);
        inv.setItem(44, frame);
        graySlots.addAll(Arrays.asList(9, 18, 27, 36, 17, 26, 35, 44));

        //MUTE

        ItemStack muteOne = new ItemStack(Material.LIME_DYE);
        ItemMeta muteOneMeta = muteOne.getItemMeta();
        assert muteOneMeta != null;
        muteOneMeta.setDisplayName(MessageColor.COLOR_MAIN + "1 Day");
        List<String> muteOneLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "1.0 Days");
        muteOneMeta.setLore(muteOneLore);
        muteOne.setItemMeta(muteOneMeta);

        ItemStack muteTwo = new ItemStack(Material.YELLOW_DYE);
        ItemMeta muteTwoMeta = muteTwo.getItemMeta();
        assert muteTwoMeta != null;
        muteTwoMeta.setDisplayName(MessageColor.COLOR_MAIN + "7 Days");
        List<String> muteTwoLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "7.0 Days");
        muteTwoMeta.setLore(muteTwoLore);
        muteTwo.setItemMeta(muteTwoMeta);

        ItemStack muteThree = new ItemStack(Material.ORANGE_DYE);
        ItemMeta muteThreeMeta = muteThree.getItemMeta();
        assert muteThreeMeta != null;
        muteThreeMeta.setDisplayName(MessageColor.COLOR_MAIN + "30 Days");
        List<String> muteThreeLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "30.0 Days");
        muteThreeMeta.setLore(muteThreeLore);
        muteThree.setItemMeta(muteThreeMeta);

        ItemStack muteFour = new ItemStack(Material.RED_DYE);
        ItemMeta muteFourMeta = muteFour.getItemMeta();
        assert muteFourMeta != null;
        muteFourMeta.setDisplayName(MessageColor.COLOR_MAIN + "60 Days");
        List<String> muteFourLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "60.0 Days");
        muteFourMeta.setLore(muteFourLore);
        muteFour.setItemMeta(muteFourMeta);

        ItemStack mutePerm = new ItemStack(Material.REDSTONE);
        ItemMeta mutePermMeta = mutePerm.getItemMeta();
        assert mutePermMeta != null;
        mutePermMeta.setDisplayName(ChatColor.RED + "Permanent");
        List<String> mutePermLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to mute " + ChatColor.YELLOW + "Permanently");
        mutePermMeta.setLore(mutePermLore);
        mutePerm.setItemMeta(mutePermMeta);
        
        //BAN

        ItemStack banOne = new ItemStack(Material.LIME_DYE);
        ItemMeta banOneMeta = banOne.getItemMeta();
        assert banOneMeta != null;
        banOneMeta.setDisplayName(MessageColor.COLOR_MAIN + "7 Days");
        List<String> banOneLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "7.0 Days");
        banOneMeta.setLore(banOneLore);
        banOne.setItemMeta(banOneMeta);

        ItemStack banTwo = new ItemStack(Material.YELLOW_DYE);
        ItemMeta banTwoMeta = banTwo.getItemMeta();
        assert banTwoMeta != null;
        banTwoMeta.setDisplayName(MessageColor.COLOR_MAIN + "30 Days");
        List<String> banTwoLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "30.0 Days");
        banTwoMeta.setLore(banTwoLore);
        banTwo.setItemMeta(banTwoMeta);

        ItemStack banThree = new ItemStack(Material.ORANGE_DYE);
        ItemMeta banThreeMeta = banThree.getItemMeta();
        assert banThreeMeta != null;
        banThreeMeta.setDisplayName(MessageColor.COLOR_MAIN + "60 Days");
        List<String> banThreeLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "60.0 Days");
        banThreeMeta.setLore(banThreeLore);
        banThree.setItemMeta(banThreeMeta);

        ItemStack banFour = new ItemStack(Material.RED_DYE);
        ItemMeta banFourMeta = banFour.getItemMeta();
        assert banFourMeta != null;
        banFourMeta.setDisplayName(MessageColor.COLOR_MAIN + "90 Days");
        List<String> banFourLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "90.0 Days");
        banFourMeta.setLore(banFourLore);
        banFour.setItemMeta(banFourMeta);

        ItemStack banPerm = new ItemStack(Material.REDSTONE);
        ItemMeta banPermMeta = banPerm.getItemMeta();
        assert banPermMeta != null;
        banPermMeta.setDisplayName(ChatColor.RED + "Permanent");
        List<String> banPermLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to ban " + ChatColor.YELLOW + "Permanently");
        banPermMeta.setLore(banPermLore);
        banPerm.setItemMeta(banPermMeta);
        
        //OTHER
        
        ItemStack warn = new ItemStack(Material.PAPER);
        ItemMeta warnMeta = warn.getItemMeta();
        assert warnMeta != null;
        warnMeta.setDisplayName(MessageColor.COLOR_MAIN + "Warn");
        List<String> warnLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to warn");
        warnMeta.setLore(warnLore);
        warn.setItemMeta(warnMeta);

        ItemStack kick = new ItemStack(Material.STICK);
        ItemMeta kickMeta = kick.getItemMeta();
        assert kickMeta != null;
        kickMeta.setDisplayName(MessageColor.COLOR_MAIN + "Kick");
        List<String> kickLore = Arrays.asList("", MessageColor.COLOR_MAIN + "Click to kick");
        kickMeta.setLore(kickLore);
        kick.setItemMeta(kickMeta);

        //CATEGORY

        ItemStack muteCat = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta muteCatMeta = muteCat.getItemMeta();
        assert muteCatMeta != null;
        muteCatMeta.setDisplayName(ChatColor.GREEN + "Mute");
        List<String> muteCatLore = Arrays.asList("", MessageColor.COLOR_MAIN + "List of all the mutes");
        muteCatMeta.setLore(muteCatLore);
        muteCat.setItemMeta(muteCatMeta);

        ItemStack banCat = new ItemStack(Material.IRON_DOOR);
        ItemMeta banCatMeta = banCat.getItemMeta();
        assert banCatMeta != null;
        banCatMeta.setDisplayName(ChatColor.GREEN + "Ban");
        List<String> banCatLore = Arrays.asList("", MessageColor.COLOR_MAIN + "List of all the bans");
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


            ItemStack i = Objects.requireNonNull(e.getClickedInventory()).getItem(0);

            if (ChatColor.stripColor(Objects.requireNonNull(Objects.requireNonNull(Objects
                    .requireNonNull(i).getItemMeta())
                    .getLore()).get(2)).equals(this.session)) {

                e.setCancelled(true);

                if (graySlots.contains(e.getSlot())) {


                    e.getWhoClicked().closeInventory();
                    openHistoryMenu();



                }

                switch (e.getSlot()) {
                    case 11:
                        this.type = Punishment.PunishType.BAN;
                        this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 7);
                        new Ban(this.target, this.executor, this.time,
                                this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 12:
                        this.type = Punishment.PunishType.BAN;
                        this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 30);
                        new Ban(this.target, this.executor, this.time,
                                this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 13:
                        this.type = Punishment.PunishType.BAN;
                        this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 60);
                        new Ban(this.target, this.executor, this.time,
                                this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 14:
                        this.type = Punishment.PunishType.BAN;
                        this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 90);
                        new Ban(this.target, this.executor, this.time,
                                this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 16:
                        this.type = Punishment.PunishType.BAN;
                        this.time = new Punishment.PunishTime();
                        new Ban(this.target, this.executor, this.time,
                                this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 38:
                        this.type = Punishment.PunishType.MUTE;
                        this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 1);
                        new Mute(this.target, this.executor, this.time,
                                this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 39:
                        this.type = Punishment.PunishType.MUTE;
                        this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 7);
                        new Mute(this.target, this.executor, this.time,
                                this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 40:
                        this.type = Punishment.PunishType.MUTE;
                        this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 30);
                        new Mute(this.target, this.executor, this.time,
                                this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 41:
                        this.type = Punishment.PunishType.MUTE;
                        this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 60);
                        new Mute(this.target, this.executor, this.time,
                                this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 43:
                        this.type = Punishment.PunishType.MUTE;
                        this.time = new Punishment.PunishTime();
                        new Mute(this.target, this.executor, this.time,
                                this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 22:
                        this.type = Punishment.PunishType.WARN;
                        this.time = new Punishment.PunishTime(0);
                        new Warn(this.target, this.executor, this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;
                    case 31:
                        this.type = Punishment.PunishType.KICK;
                        this.time = new Punishment.PunishTime(0);
                        new Kick(this.target, this.executor, this.reason).execute();
                        sendStaffMessage();
                        e.getWhoClicked().closeInventory();
                        HandlerList.unregisterAll(this);
                        break;

                }

            }


        } else if (e.getView().getTitle().equalsIgnoreCase("History")) {

            ItemStack i = Objects.requireNonNull(e.getClickedInventory()).getItem(0);

            if (ChatColor.stripColor(Objects.requireNonNull(Objects.requireNonNull(Objects
                    .requireNonNull(i).getItemMeta())
                    .getLore()).get(1)).equals(this.session)) {

                e.setCancelled(true);

                switch (e.getSlot()) {
                    case 19:
                        e.getWhoClicked().closeInventory();
                        openHistory("BANS");
                        break;
                    case 21:
                        e.getWhoClicked().closeInventory();
                        openHistory("MUTES");
                        break;
                    case 23:
                        e.getWhoClicked().closeInventory();
                        openHistory("WARNS");
                        break;
                    case 25:
                        e.getWhoClicked().closeInventory();
                        openHistory("KICKS");
                        break;
                    case 40:
                        e.getWhoClicked().closeInventory();
                        openUI();
                        break;
                }
            }

        } else if(e.getView().getTitle().startsWith("History |")) {

            ItemStack i = Objects.requireNonNull(e.getClickedInventory()).getItem(0);

            if (ChatColor.stripColor(Objects.requireNonNull(Objects.requireNonNull(Objects
                    .requireNonNull(i).getItemMeta())
                    .getLore()).get(2)).equals(this.session)) {

                e.setCancelled(true);

                if (e.getCurrentItem() != null) {
                    if (!e.getCurrentItem().getEnchantments().isEmpty()) {

                        int id = Integer.parseInt(ChatColor.stripColor(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName()));

                        Punishment.removePunishment(id);

                        String view = e.getView().getTitle().split(" ")[2].toLowerCase();

                        e.getWhoClicked().closeInventory();
                        openHistory(view);

                        for (Player p : Bukkit.getOnlinePlayers()) {
                            MPlayer pl = MPlayer.getMPlayer(p.getName());
                            if (pl.isPermissible(Ranks.STAFF)) {
                                p.sendMessage(ChatColor.RED + this.executor.getPlayerStr()
                                        + " removed a punishment from " + this.target.getPlayerStr() + "!");
                            }
                        }

                    }
                }

            }

        }

    }


    public void openHistoryMenu() {

        Inventory inv = Bukkit.createInventory(null, 54, "History");

        ItemStack frame = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta frameMeta = frame.getItemMeta();
        assert frameMeta != null;
        frameMeta.setDisplayName(ChatColor.WHITE + "History");

        List<String> lore = Arrays.asList("", ChatColor.DARK_GRAY + this.session);
        frameMeta.setLore(lore);
        frame.setItemMeta(frameMeta);

        for (int i = 0; i < 9; i++) {

            inv.setItem(i, frame);
            graySlots.add(i);

        }

        for (int i = 45; i < 54; i++) {

            inv.setItem(i, frame);
            graySlots.add(i);

        }

        inv.setItem(9, frame);
        inv.setItem(18, frame);
        inv.setItem(27, frame);
        inv.setItem(36, frame);
        inv.setItem(17, frame);
        inv.setItem(26, frame);
        inv.setItem(35, frame);
        inv.setItem(44, frame);

        ItemStack bans = new ItemStack(Material.IRON_DOOR);
        ItemMeta bansMeta = bans.getItemMeta();
        assert bansMeta != null;
        bansMeta.setDisplayName(MessageColor.COLOR_MAIN + "Bans");
        List<String> bansLore = Arrays.asList("",
                MessageColor.COLOR_MAIN + "Click to view all of " + ChatColor.YELLOW + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " bans");
        bansMeta.setLore(bansLore);
        bans.setItemMeta(bansMeta);
        
        ItemStack mutes = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta mutesMeta = mutes.getItemMeta();
        assert mutesMeta != null;
        mutesMeta.setDisplayName(MessageColor.COLOR_MAIN + "Mutes");
        List<String> mutesLore = Arrays.asList("",
                MessageColor.COLOR_MAIN + "Click to view all of " + ChatColor.YELLOW + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " mutes");
        mutesMeta.setLore(mutesLore);
        mutes.setItemMeta(mutesMeta);

        ItemStack warns = new ItemStack(Material.PAPER);
        ItemMeta warnsMeta = warns.getItemMeta();
        assert warnsMeta != null;
        warnsMeta.setDisplayName(MessageColor.COLOR_MAIN + "Warns");
        List<String> warnsLore = Arrays.asList("",
                MessageColor.COLOR_MAIN + "Click to view all of " + ChatColor.YELLOW + this.target.getPlayerStr()  + "'s" + MessageColor.COLOR_MAIN + " warns");
        warnsMeta.setLore(warnsLore);
        warns.setItemMeta(warnsMeta);

        ItemStack kicks = new ItemStack(Material.STICK);
        ItemMeta kicksMeta = kicks.getItemMeta();
        assert kicksMeta != null;
        kicksMeta.setDisplayName(MessageColor.COLOR_MAIN + "Kicks");
        List<String> kicksLore = Arrays.asList("",
                MessageColor.COLOR_MAIN + "Click to view all of " + ChatColor.YELLOW + this.target.getPlayerStr() + "'s" + MessageColor.COLOR_MAIN + " kicks");
        kicksMeta.setLore(kicksLore);
        kicks.setItemMeta(kicksMeta);

        ItemStack exit = new ItemStack(Material.ARROW);
        ItemMeta exitMeta = exit.getItemMeta();
        assert exitMeta != null;
        exitMeta.setDisplayName(ChatColor.RED + "Go Back");
        List<String> exitLore = Arrays.asList("",
                MessageColor.COLOR_MAIN + "Return to main menu");
        exitMeta.setLore(exitLore);
        exit.setItemMeta(exitMeta);
        
        inv.setItem(19, bans);
        inv.setItem(21, mutes);
        inv.setItem(23, warns);
        inv.setItem(25, kicks);
        inv.setItem(40, exit);

        this.executor.getPlayer().openInventory(inv);

    }

    private void openHistory(String menu) {

        Inventory inv = null;

        int slot = 10;
        int activeSlot = 10;

        switch (menu) {
            case "BANS":
                inv = Bukkit.createInventory(null, 54, "History | Bans");

                ItemStack frame = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta frameMeta = frame.getItemMeta();
                assert frameMeta != null;
                frameMeta.setDisplayName(ChatColor.WHITE + "Reason: " + this.reason);
                List<String> frameMetaLore = Arrays.asList("", "", ChatColor.DARK_GRAY + this.session);

                frameMeta.setLore(frameMetaLore);
                frame.setItemMeta(frameMeta);


                for (int i = 0; i < 9; i++) {

                    inv.setItem(i, frame);
                    graySlots.add(i);

                }

                for (int i = 45; i < 54; i++) {

                    inv.setItem(i, frame);
                    graySlots.add(i);

                }

                inv.setItem(9, frame);
                inv.setItem(18, frame);
                inv.setItem(27, frame);
                inv.setItem(36, frame);
                inv.setItem(17, frame);
                inv.setItem(26, frame);
                inv.setItem(35, frame);
                inv.setItem(44, frame);

                List<PunishInfo> punishInfoList = Punishment.getPunishments(this.target, Punishment.PunishType.BAN);

                for (PunishInfo i : punishInfoList) {

                    ItemStack h = new ItemStack(Material.PAPER);
                    ItemMeta hM = h.getItemMeta();
                    assert hM != null;

                    String time;
                    if (i.getOriginalTime().getUnit().equals(Punishment.TimeUnit.PERMANENT)) time = "Permanent";
                    else time = i.getOriginalTime().getTimeLeft()
                            + " " + i.getOriginalTime().getUnit().getFormatted();

                    List<String> hL = Arrays.asList("", 
                            ChatColor.WHITE + "Staff: " + ChatColor.YELLOW + i.getExecutor().getPlayerStr(), 
                            ChatColor.WHITE + "Duration: " + ChatColor.YELLOW 
                                    + time,
                            ChatColor.WHITE + "Reason: " + ChatColor.YELLOW + i.getReason(),
                            ChatColor.WHITE + "Active: " + ChatColor.YELLOW + i.isActive());
                    
                    if (i.isActive()) { hM.addEnchant(Enchantment.DAMAGE_ALL, 1, true); activeSlot++; }

                    hM.setDisplayName(ChatColor.YELLOW + String.valueOf(i.getID()));
                    hM.setLore(hL);
                    
                    h.setItemMeta(hM);
                    inv.setItem(slot, h);
                    
                    slot++;
                    if (slot > 16) slot = activeSlot;
                }
                break;
            case "MUTES":
                inv = Bukkit.createInventory(null, 54, "History | Mutes");

                ItemStack frame2 = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta frameMeta2 = frame2.getItemMeta();
                assert frameMeta2 != null;
                frameMeta2.setDisplayName(ChatColor.WHITE + "Reason: " + this.reason);
                List<String> frameMetaLore2 = Arrays.asList("", "", ChatColor.DARK_GRAY + this.session);

                frameMeta2.setLore(frameMetaLore2);

                frame2.setItemMeta(frameMeta2);


                for (int i = 0; i < 9; i++) {

                    inv.setItem(i, frame2);
                    graySlots.add(i);

                }

                for (int i = 45; i < 54; i++) {

                    inv.setItem(i, frame2);
                    graySlots.add(i);

                }

                inv.setItem(9, frame2);
                inv.setItem(18, frame2);
                inv.setItem(27, frame2);
                inv.setItem(36, frame2);
                inv.setItem(17, frame2);
                inv.setItem(26, frame2);
                inv.setItem(35, frame2);
                inv.setItem(44, frame2);

                List<PunishInfo> punishInfoList2 = Punishment.getPunishments(this.target, Punishment.PunishType.MUTE);

                for (PunishInfo i : punishInfoList2) {

                    ItemStack h = new ItemStack(Material.PAPER);
                    ItemMeta hM = h.getItemMeta();
                    assert hM != null;

                    String time;
                    if (i.getOriginalTime().getUnit().equals(Punishment.TimeUnit.PERMANENT)) time = "Permanent";
                    else time = i.getOriginalTime().getTimeLeft()
                            + " " + i.getOriginalTime().getUnit().getFormatted();

                    List<String> hL = Arrays.asList("",
                            ChatColor.WHITE + "Staff: " + ChatColor.YELLOW + i.getExecutor().getPlayerStr(),
                            ChatColor.WHITE + "Duration: " + ChatColor.YELLOW
                                    + time,
                            ChatColor.WHITE + "Reason: " + ChatColor.YELLOW + i.getReason(),
                            ChatColor.WHITE + "Active: " + ChatColor.YELLOW + i.isActive());

                    if (i.isActive()) { hM.addEnchant(Enchantment.DAMAGE_ALL, 1, true); activeSlot++; }

                    hM.setDisplayName(ChatColor.YELLOW + String.valueOf(i.getID()));
                    hM.setLore(hL);

                    h.setItemMeta(hM);
                    inv.setItem(slot, h);

                    slot++;
                    if (slot > 16) slot = activeSlot;
                }
                break;
            case "WARNS":
                inv = Bukkit.createInventory(null, 54, "History | Warns");

                ItemStack frame3 = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta frameMeta3 = frame3.getItemMeta();
                assert frameMeta3 != null;
                frameMeta3.setDisplayName(ChatColor.WHITE + "Reason: " + this.reason);

                List<String> frameMetaLore3 = Arrays.asList("", "", ChatColor.DARK_GRAY + this.session);

                frameMeta3.setLore(frameMetaLore3);

                frame3.setItemMeta(frameMeta3);

                for (int i = 0; i < 9; i++) {

                    inv.setItem(i, frame3);
                    graySlots.add(i);

                }

                for (int i = 45; i < 54; i++) {

                    inv.setItem(i, frame3);
                    graySlots.add(i);

                }
                

                inv.setItem(9, frame3);
                inv.setItem(18, frame3);
                inv.setItem(27, frame3);
                inv.setItem(36, frame3);
                inv.setItem(17, frame3);
                inv.setItem(26, frame3);
                inv.setItem(35, frame3);
                inv.setItem(44, frame3);

                List<PunishInfo> punishInfoList3 = Punishment.getPunishments(this.target, Punishment.PunishType.WARN);

                for (PunishInfo i : punishInfoList3) {

                    ItemStack h = new ItemStack(Material.PAPER);
                    ItemMeta hM = h.getItemMeta();
                    assert hM != null;

                    List<String> hL = Arrays.asList("",
                            ChatColor.WHITE + "Staff: " + ChatColor.YELLOW + i.getExecutor().getPlayerStr(),
                            ChatColor.WHITE + "Reason: " + ChatColor.YELLOW + i.getReason());

                    if (i.isActive()) { hM.addEnchant(Enchantment.DAMAGE_ALL, 1, true); activeSlot++; }

                    hM.setDisplayName(ChatColor.YELLOW + String.valueOf(i.getID()));
                    hM.setLore(hL);

                    h.setItemMeta(hM);
                    inv.setItem(slot, h);

                    slot++;
                    if (slot > 16) slot = activeSlot;
                }
                break;
            case "KICKS":
                inv = Bukkit.createInventory(null, 54, "History | Kicks");
                
                ItemStack frame4 = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta frameMeta4 = frame4.getItemMeta();
                assert frameMeta4 != null;
                frameMeta4.setDisplayName(ChatColor.WHITE + "Reason: " + this.reason);

                List<String> frameMetaLore4 = Arrays.asList("", "", ChatColor.DARK_GRAY + this.session);

                frameMeta4.setLore(frameMetaLore4);

                frame4.setItemMeta(frameMeta4);

                for (int i = 0; i < 9; i++) {

                    inv.setItem(i, frame4);
                    graySlots.add(i);

                }

                for (int i = 45; i < 54; i++) {

                    inv.setItem(i, frame4);
                    graySlots.add(i);

                }

                inv.setItem(9, frame4);
                inv.setItem(18, frame4);
                inv.setItem(27, frame4);
                inv.setItem(36, frame4);
                inv.setItem(17, frame4);
                inv.setItem(26, frame4);
                inv.setItem(35, frame4);
                inv.setItem(44, frame4);

                List<PunishInfo> punishInfoList4 = Punishment.getPunishments(this.target, Punishment.PunishType.KICK);

                for (PunishInfo i : punishInfoList4) {

                    ItemStack h = new ItemStack(Material.PAPER);
                    ItemMeta hM = h.getItemMeta();
                    assert hM != null;
                    List<String> hL = Arrays.asList("",
                            ChatColor.WHITE + "Staff: " + ChatColor.YELLOW + i.getExecutor().getPlayerStr(),
                            ChatColor.WHITE + "Reason: " + ChatColor.YELLOW + i.getReason());

                    if (i.isActive()) { hM.addEnchant(Enchantment.DAMAGE_ALL, 1, true); activeSlot++; }

                    hM.setDisplayName(ChatColor.YELLOW + String.valueOf(i.getID()));
                    hM.setLore(hL);

                    h.setItemMeta(hM);
                    inv.setItem(slot, h);

                    slot++;
                    if (slot > 16) slot = activeSlot;
                }
                break;
        }

        if (inv != null) this.executor.getPlayer().openInventory(inv);

    }

    private void sendStaffMessage() {

        String t = "";
        boolean ti = false;
        switch (this.type) {
            case BAN:
                t = "banned";
                ti = true;
                break;
            case MUTE:
                t = "muted";
                ti = true;
                break;
            case WARN:
                t = "warned";
                break;
            case KICK:
                t = "kicked";
                break;
        }

        boolean np = (this.type.equals(Punishment.PunishType.KICK) || this.type.equals(Punishment.PunishType.WARN));

        StringBuilder msg = new StringBuilder(ChatColor.RED + this.executor.getPlayerStr() + " " + t + " " + this.target.getPlayerStr());
        if (ti && !this.time.getUnit()
                .equals(Punishment.TimeUnit.PERMANENT)) msg.append(" for ").append(this.time.getTimeLeft()).append(" ")
                .append(this.time.getUnit().getFormatted());
        if (this.time.getUnit().equals(Punishment.TimeUnit.PERMANENT) && !np) msg.append(" permanently");

        for (Player p : Bukkit.getOnlinePlayers()) {

            MPlayer pl = MPlayer.getMPlayer(p.getName());
            if (pl.isPermissible(Ranks.STAFF)) {
                p.sendMessage(msg.toString());
            }

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
