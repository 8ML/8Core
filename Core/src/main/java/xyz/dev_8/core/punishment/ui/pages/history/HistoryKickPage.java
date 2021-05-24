package xyz.dev_8.core.punishment.ui.pages.history;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.punishment.PunishInfo;
import xyz.dev_8.core.punishment.Punishment;
import xyz.dev_8.core.ui.component.components.Button;
import xyz.dev_8.core.ui.page.Page;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class HistoryKickPage extends Page {

    private final MPlayer target;

    public HistoryKickPage(MPlayer target) {
        super("Kicks | " + target.getPlayerStr(), 54, true);

        this.target = target;

        setFrameLabel(" ");
    }

    @Override
    public void onOpen() {

        int slot = 10;

        List<PunishInfo> punishInfoList = Punishment.getPunishments(this.target, Punishment.PunishType.KICK);

        for (PunishInfo i : punishInfoList) {

            if (slot >= 44) continue;

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String date = format.format(Date.from(Instant.ofEpochMilli(i.getWhen())));

            Button b = new Button(ChatColor.YELLOW + String.valueOf(date), Material.PAPER, getParent());

            b.setLore(new String[]{"",
                    org.bukkit.ChatColor.YELLOW + "ID: " + org.bukkit.ChatColor.WHITE + i.getID(),
                    "",
                    org.bukkit.ChatColor.YELLOW + "Staff: " + org.bukkit.ChatColor.WHITE + i.getExecutor().getPlayerStr(),
                    org.bukkit.ChatColor.YELLOW + "Reason: " + org.bukkit.ChatColor.WHITE + i.getReason()});

            addComponent(b, slot);
            slot++;
            if (getFrameSlots().contains(slot)) slot+=2;

        }

    }

    @Override
    public void onFrameClick() {

    }
}
