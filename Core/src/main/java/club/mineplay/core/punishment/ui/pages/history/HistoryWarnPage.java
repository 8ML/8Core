package club.mineplay.core.punishment.ui.pages.history;
/*
Created by @8ML (https://github.com/8ML) on 5/4/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.PunishInfo;
import club.mineplay.core.punishment.Punishment;
import club.mineplay.core.ui.component.components.Button;
import club.mineplay.core.ui.page.Page;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

import java.util.List;

public class HistoryWarnPage extends Page {

    private final MPlayer target;

    public HistoryWarnPage(MPlayer target) {
        super("Warns | " + target.getPlayerStr(), 54, true);

        this.target = target;

        setFrameLabel(" ");
    }

    @Override
    public void onOpen() {

        int slot = 10;

        List<PunishInfo> punishInfoList = Punishment.getPunishments(this.target, Punishment.PunishType.WARN);

        for (PunishInfo i : punishInfoList) {

            Button b = new Button(ChatColor.YELLOW + String.valueOf(i.getID()), Material.PAPER, getParent());

            b.setLore(new String[]{"",
                    org.bukkit.ChatColor.WHITE + "Staff: " + org.bukkit.ChatColor.YELLOW + i.getExecutor().getPlayerStr(),
                    org.bukkit.ChatColor.WHITE + "Reason: " + org.bukkit.ChatColor.YELLOW + i.getReason()});

            addComponent(b, slot);
            slot++;
            if (slot > 16) slot = 10;

        }

    }

    @Override
    public void onFrameClick() {

    }
}
