package club.mineplay.core.punishment.ui.pages.history;
/*
Created by Sander on 5/4/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.PunishInfo;
import club.mineplay.core.punishment.Punishment;
import club.mineplay.core.ui.component.components.Button;
import club.mineplay.core.ui.page.Page;
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
    }

    @Override
    public void onOpen() {

        int slot = 10;
        int activeSlot = 10;

        List<PunishInfo> punishInfoList = Punishment.getPunishments(this.target, Punishment.PunishType.MUTE);

        for (PunishInfo i : punishInfoList) {

            String time;
            if (i.getOriginalTime().getUnit().equals(Punishment.TimeUnit.PERMANENT)) time = "Permanent";
            else time = i.getOriginalTime().getTimeLeft()
                    + " " + i.getOriginalTime().getUnit().getFormatted();

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
                }
            });

            addComponent(b, slot);
            slot++;
            if (slot > 16) slot = activeSlot;

        }

    }

    @Override
    public void onFrameClick() {

    }
}
