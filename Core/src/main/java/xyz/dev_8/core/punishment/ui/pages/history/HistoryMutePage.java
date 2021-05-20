package xyz.dev_8.core.punishment.ui.pages.history;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import xyz.dev_8.core.Core;
import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.punishment.PunishInfo;
import xyz.dev_8.core.punishment.Punishment;
import xyz.dev_8.core.ui.component.components.Button;
import xyz.dev_8.core.ui.page.Page;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

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

            Button b = new Button(ChatColor.YELLOW + String.valueOf(i.getID()), Material.PAPER, getParent());
            ItemMeta bM = b.getMeta();
            if (i.isActive()) { bM.addEnchant(Enchantment.DAMAGE_ALL, 1, true); activeSlot++; }
            b.setItemMeta(bM);

            b.setLore(new String[]{"",
                    org.bukkit.ChatColor.WHITE + "Staff: " + org.bukkit.ChatColor.YELLOW + i.getExecutor().getPlayerStr(),
                    org.bukkit.ChatColor.WHITE + "Duration: " + org.bukkit.ChatColor.YELLOW
                            + time,
                    org.bukkit.ChatColor.WHITE + "Reason: " + org.bukkit.ChatColor.YELLOW + i.getReason(),
                    org.bukkit.ChatColor.WHITE + "Active: " + org.bukkit.ChatColor.YELLOW + i.isActive()});

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
