package com.github._8ml.core.punishment.ui.pages.history;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.punishment.PunishInfo;
import com.github._8ml.core.punishment.Punishment;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.page.Page;
import org.bukkit.inventory.ItemFlag;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class HistoryMutePage extends Page {

    private final MPlayer target;

    public HistoryMutePage(MPlayer target) {
        super("Mutes | " + target.getPlayerStr(), 54, true);

        this.target = target;

        setFrameLabel(" ");
    }

    @Override
    public void onOpen() {

        int slot = 10;
        int activeSlot = 10;

        List<PunishInfo> punishInfoList = Punishment.getPunishments(this.target, Punishment.PunishType.MUTE);

        for (PunishInfo i : punishInfoList) {

            if (slot >= 44) continue;

            String time = i.getOriginalTime().getUnit().equals(Punishment.TimeUnit.PERMANENT)
                    ? "Permanent" : i.getOriginalTime().getTimeLeft() + " " + i.getOriginalTime().getUnit().getFormatted();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String date = format.format(Date.from(Instant.ofEpochMilli(i.getWhen())));

            Button b = new Button(MessageColor.COLOR_HIGHLIGHT + String.valueOf(date), Material.PAPER, getParent());
            ItemMeta bM = b.getMeta();
            if (i.isActive()) { bM.addEnchant(Enchantment.DAMAGE_ALL, 1, true); activeSlot++; }
            bM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            b.setItemMeta(bM);

            b.setLore(new String[]{"",
                    MessageColor.COLOR_HIGHLIGHT + "ID: " + org.bukkit.ChatColor.WHITE + i.getID(),
                    "",
                    MessageColor.COLOR_HIGHLIGHT + "Staff: " + org.bukkit.ChatColor.WHITE + i.getExecutor().getPlayerStr(),
                    MessageColor.COLOR_HIGHLIGHT + "Duration: " + org.bukkit.ChatColor.WHITE
                            + time,
                    MessageColor.COLOR_HIGHLIGHT + "Reason: " + org.bukkit.ChatColor.WHITE + i.getReason(),
                    MessageColor.COLOR_HIGHLIGHT + "Active: " + org.bukkit.ChatColor.WHITE + i.isActive()});

            b.setOnClick(() -> {
                if (i.isActive()) {
                    Punishment.removePunishment(i.getID());
                    refresh();
                }
            });

            addComponent(b, slot);
            slot++;
            if (getFrameSlots().contains(slot)) slot+=2;

        }

    }

    @Override
    public void onFrameClick() {

    }
}
