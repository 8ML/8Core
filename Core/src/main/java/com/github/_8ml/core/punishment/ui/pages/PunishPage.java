package com.github._8ml.core.punishment.ui.pages;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.punishment.Punishment;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.component.components.Label;
import com.github._8ml.core.ui.page.Page;
import com.github._8ml.core.utils.StaffMSG;
import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.punishment.type.Ban;
import com.github._8ml.core.punishment.type.Kick;
import com.github._8ml.core.punishment.type.Mute;
import com.github._8ml.core.punishment.type.Warn;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class PunishPage extends Page {

    private final MPlayer target;
    private final MPlayer executor;
    private final String reason;
    private final List<Integer> graySlots = new ArrayList<>();

    private Punishment.PunishType type;
    private Punishment.PunishTime time;

    public PunishPage(MPlayer target, MPlayer executor, String reason) {

        super("Punish " + target.getPlayerStr() , 54, true);

        this.target = target;
        this.executor = executor;
        this.reason = reason;

        setFrameLabel(ChatColor.WHITE + "Reason: " + ChatColor.GRAY + reason);
        setFrameLore(new String[]{"", ChatColor.GREEN + "Click to view history"});
    }

    @Override
    public void onOpen() {

        //MUTE

        Button muteOne = new Button(MessageColor.COLOR_MAIN + "1 Day", Material.LIME_DYE, getParent());
        muteOne.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "1.0 Days"});
        muteOne.setOnClick(() -> {
            this.type = Punishment.PunishType.MUTE;
            this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 1);
            new Mute(this.target, this.executor, this.time,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        Button muteTwo = new Button(MessageColor.COLOR_MAIN + "7 Days", Material.YELLOW_DYE, getParent());
        muteTwo.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "7.0 Days"});
        muteTwo.setOnClick(() -> {
            this.type = Punishment.PunishType.MUTE;
            this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 7);
            new Mute(this.target, this.executor, this.time,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        Button muteThree = new Button(MessageColor.COLOR_MAIN + "30 Days", Material.ORANGE_DYE, getParent());
        muteThree.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "30.0 Days"});
        muteThree.setOnClick(() -> {
            this.type = Punishment.PunishType.MUTE;
            this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 30);
            new Mute(this.target, this.executor, this.time,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        Button muteFour = new Button(MessageColor.COLOR_MAIN + "60 Days", Material.RED_DYE, getParent());
        muteFour.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to mute for " + ChatColor.YELLOW + "60.0 Days"});
        muteFour.setOnClick(() -> {
            this.type = Punishment.PunishType.MUTE;
            this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 60);
            new Mute(this.target, this.executor, this.time,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        Button mutePerm = new Button(ChatColor.RED + "Permanent", Material.REDSTONE, getParent());
        mutePerm.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to mute " + ChatColor.YELLOW + "Permanently"});
        mutePerm.setOnClick(() -> {
            this.type = Punishment.PunishType.MUTE;
            this.time = new Punishment.PunishTime();
            new Mute(this.target, this.executor, this.time,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        //BAN

        Button banOne = new Button(MessageColor.COLOR_MAIN + "7 Days", Material.LIME_DYE, getParent());
        banOne.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "7.0 Days"});
        banOne.setOnClick(() -> {
            this.type = Punishment.PunishType.BAN;
            this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 7);
            new Ban(this.target, this.executor, this.time,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        Button banTwo = new Button(MessageColor.COLOR_MAIN + "30 Days", Material.GREEN_DYE, getParent());
        banTwo.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "30.0 Days"});
        banTwo.setOnClick(() -> {
            this.type = Punishment.PunishType.BAN;
            this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 30);
            new Ban(this.target, this.executor, this.time,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        Button banThree = new Button(MessageColor.COLOR_MAIN + "60 Days", Material.ORANGE_DYE, getParent());
        banThree.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "60.0 Days"});
        banThree.setOnClick(() -> {
            this.type = Punishment.PunishType.BAN;
            this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 60);
            new Ban(this.target, this.executor, this.time,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        Button banFour = new Button(MessageColor.COLOR_MAIN + "90 Days", Material.RED_DYE, getParent());
        banFour.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to ban for " + ChatColor.YELLOW + "90.0 Days"});
        banFour.setOnClick(() -> {
            this.type = Punishment.PunishType.BAN;
            this.time = new Punishment.PunishTime(Punishment.TimeUnit.DAYS, 90);
            new Ban(this.target, this.executor, this.time,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        Button banPerm = new Button(ChatColor.RED + "Permanent", Material.REDSTONE, getParent());
        banPerm.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to ban " + ChatColor.YELLOW + "Permanently"});
        banPerm.setOnClick(() -> {
            this.type = Punishment.PunishType.BAN;
            this.time = new Punishment.PunishTime();
            new Ban(this.target, this.executor, this.time,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        //OTHER

        Button warn = new Button(MessageColor.COLOR_MAIN + "Warn", Material.PAPER, getParent());
        warn.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to warn"});
        warn.setOnClick(() -> {
            this.type = Punishment.PunishType.WARN;
            this.time = new Punishment.PunishTime();
            new Warn(this.target, this.executor,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        Button kick = new Button(MessageColor.COLOR_MAIN + "Kick", Material.STICK, getParent());
        kick.setLore(new String[]{"", MessageColor.COLOR_MAIN + "Click to kick"});
        kick.setOnClick(() -> {
            this.type = Punishment.PunishType.KICK;
            this.time = new Punishment.PunishTime();
            new Kick(this.target, this.executor,
                    this.reason).execute();
            sendStaffMessage();

            executor.getPlayer().closeInventory();
            getParent().unregisterHandlers();
        });

        //CATEGORY

        Label muteCat = new Label(ChatColor.GREEN + "Mute", Material.WRITABLE_BOOK, getParent());
        muteCat.setLore(new String[]{"", MessageColor.COLOR_MAIN + "List of all the mutes"});

        Label banCat = new Label(ChatColor.GREEN + "Ban", Material.IRON_DOOR, getParent());
        banCat.setLore(new String[]{"", MessageColor.COLOR_MAIN + "List of all the bans"});

        addComponent(banCat, 10);
        addComponent(banOne, 11);
        addComponent(banTwo, 12);
        addComponent(banThree, 13);
        addComponent(banFour, 14);
        addComponent(banPerm, 16);

        addComponent(muteCat, 37);
        addComponent(muteOne, 38);
        addComponent(muteTwo, 39);
        addComponent(muteThree, 40);
        addComponent(muteFour, 41);
        addComponent(mutePerm, 43);

        addComponent(warn, 22);
        addComponent(kick, 31);

    }

    @Override
    public void onFrameClick() {
        getParent().openPage(1);
    }

    private void sendStaffMessage() {

        String t = "";
        boolean time = (this.type.equals(Punishment.PunishType.BAN) || this.type.equals(Punishment.PunishType.MUTE));
        String type = this.type.name().toLowerCase() + "ed";
        boolean np = (this.type.equals(Punishment.PunishType.KICK) || this.type.equals(Punishment.PunishType.WARN));

        StringBuilder msg = new StringBuilder(ChatColor.RED + t + " " + this.target.getPlayerStr());
        if (time && !this.time.getUnit()
                .equals(Punishment.TimeUnit.PERMANENT)) msg.append(" for ").append(this.time.getTimeLeft()).append(" ")
                .append(this.time.getUnit().getFormatted());
        if (this.time.getUnit().equals(Punishment.TimeUnit.PERMANENT) && !np) msg.append(" permanently");

        StaffMSG.sendStaffMessage(msg.toString(), executor);

    }
}