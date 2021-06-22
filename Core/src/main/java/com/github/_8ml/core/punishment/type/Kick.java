package com.github._8ml.core.punishment.type;
/*
Created by @8ML (https://github.com/8ML) on 4/29/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.punishment.Punishment;
import net.md_5.bungee.api.ChatColor;

public class Kick extends Punishment {

    private final MPlayer player, executor;
    private final String reason;

    public Kick(MPlayer player, MPlayer executor, String reason) {
        super(PunishType.KICK);

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
            player.getPlayer().kickPlayer(getPunishMessage());
        }
    }

    @Override
    public String getPunishMessage() {
        return MessageColor.COLOR_ERROR + "You were kicked by " + this.executor.getPlayerStr() + "\n"
                + ChatColor.WHITE + "Reason: " + MessageColor.COLOR_MAIN + this.reason;
    }

}
