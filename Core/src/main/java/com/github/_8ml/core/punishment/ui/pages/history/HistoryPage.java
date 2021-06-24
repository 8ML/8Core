package com.github._8ml.core.punishment.ui.pages.history;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.punishment.PunishInfo;
import com.github._8ml.core.punishment.Punishment;
import com.github._8ml.core.ui.component.Component;
import com.github._8ml.core.ui.component.components.Button;
import com.github._8ml.core.ui.page.MultiplePage;
import com.github._8ml.core.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HistoryPage extends MultiplePage {

    private final MPlayer target;

    public HistoryPage(MPlayer target) {
        super("", true);

        this.target = target;

        setFrameLabel(" ");
    }

    @Override
    protected void onPreOpen() {
        assert HistoryMenuPage.selectedHistoryPage != null;
        setTitle(StringUtils.formatCapitalization(HistoryMenuPage.selectedHistoryPage.name() + "s") + " | " + target.getPlayerStr());
    }

    @Override
    protected List<Component> onOpenMultiple() {
        List<Component> components = new ArrayList<>();

        int slot = 10;
        int activeSlot = 10;

        assert HistoryMenuPage.selectedHistoryPage != null;
        List<PunishInfo> punishInfoList = Punishment.getPunishments(this.target, HistoryMenuPage.selectedHistoryPage);

        for (PunishInfo i : punishInfoList) {

            String time = i.getOriginalTime().getUnit().equals(Punishment.TimeUnit.PERMANENT)
                    ? "Permanent" : i.getOriginalTime().getTimeLeft() + " " + i.getOriginalTime().getUnit().getFormatted();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String date = format.format(Date.from(Instant.ofEpochMilli(i.getWhen())));

            Button b = new Button(MessageColor.COLOR_HIGHLIGHT + date, Material.PAPER, getParent());
            ItemMeta bM = b.getMeta();
            if (i.isActive() 
                    && !HistoryMenuPage.selectedHistoryPage.equals(Punishment.PunishType.KICK) 
                    && !HistoryMenuPage.selectedHistoryPage.equals(Punishment.PunishType.WARN)) { 
                bM.addEnchant(Enchantment.DAMAGE_ALL, 1, true); activeSlot++; 
            }
            bM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            b.setItemMeta(bM);

            boolean noActiveOrDuration = (HistoryMenuPage.selectedHistoryPage.equals(Punishment.PunishType.WARN)
                    || HistoryMenuPage.selectedHistoryPage.equals(Punishment.PunishType.KICK));

            b.setLore(new String[]{
                    " ",
                    MessageColor.COLOR_HIGHLIGHT + "ID: " + org.bukkit.ChatColor.WHITE + i.getID(),
                    " ",
                    MessageColor.COLOR_HIGHLIGHT + "Staff: " + org.bukkit.ChatColor.WHITE + i.getExecutor().getPlayerStr(),
                    noActiveOrDuration ? "" : MessageColor.COLOR_HIGHLIGHT + "Duration: " + org.bukkit.ChatColor.WHITE
                            + time,
                    MessageColor.COLOR_HIGHLIGHT + "Reason: " + org.bukkit.ChatColor.WHITE + i.getReason(),
                    noActiveOrDuration ? "" : MessageColor.COLOR_HIGHLIGHT + "Active: " + org.bukkit.ChatColor.WHITE + i.isActive()
            });

            b.setOnClick(() -> {
                if (i.isActive()) {
                    Punishment.removePunishment(i.getID());
                    refresh();
                }
            });
            
            components.add(b);

        }
        
        return components;
    }

    @Override
    public void onFrameClick() {

    }
}
