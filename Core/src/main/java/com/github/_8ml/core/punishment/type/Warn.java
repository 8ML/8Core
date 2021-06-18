package com.github._8ml.core.punishment.type;
/*
Created by @8ML (https://github.com/8ML) on 4/29/2021
*/

import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.punishment.Punishment;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

public class Warn extends Punishment {

    private final MPlayer player, executor;
    private final String reason;

    public Warn(MPlayer player, MPlayer executor, String reason) {
        super(PunishType.WARN);

        this.player = player;
        this.executor = executor;
        this.reason = reason;
    }

    public void execute() {
        execute(executor, player, reason, new PunishTime(0));
    }

    @Override
    public void onExecute() {
        if (!player.isOffline()) {
            player.getPlayer().sendMessage(getPunishMessage());
        }
    }

    @Override
    public String getPunishMessage() {

        if (!this.player.isOffline())
            this.player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_CAT_HISS, 1f, 1f);

        return ChatColor.RED + "You were warned by " + this.executor.getPlayerStr() + " for " + ChatColor.GRAY + this.reason;
    }
}
