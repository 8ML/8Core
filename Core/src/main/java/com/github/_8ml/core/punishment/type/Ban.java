package com.github._8ml.core.punishment.type;
/*
Created by @8ML (https://github.com/8ML) on 4/28/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.config.ServerConfig;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.punishment.PunishInfo;
import com.github._8ml.core.punishment.Punishment;
import net.md_5.bungee.api.ChatColor;

public class Ban extends Punishment {

    private final MPlayer player, executor;
    private final String reason;
    private final PunishTime punishTime;

    public Ban(MPlayer player, MPlayer executor, PunishTime time, String reason) {
        super(PunishType.BAN);

        this.player = player;
        this.executor = executor;
        this.reason = reason;
        this.punishTime = time;
    }



    public void execute() {
        execute(executor, player, reason, punishTime);
    }

    @Override
    public void onExecute() {

        if (!this.player.isOffline()) {

            player.getPlayer().kickPlayer(getPunishMessage());

        }

    }

    @Override
    public String getPunishMessage() {

        String msg;

        msg = this.punishTime.getUnit().equals(TimeUnit.PERMANENT) ? MessageColor.COLOR_ERROR + "You are permanently banned! \n"
                + ChatColor.WHITE + "Reason: " + MessageColor.COLOR_MAIN + this.reason
                + "\n\nAppeal at " + ChatColor.GOLD + "" + ChatColor.LIGHT_PURPLE + "dev-8.com/appeal" :
                MessageColor.COLOR_ERROR + "You are banned for " + this.punishTime.getTimeLeft() + " " + this.punishTime.getUnit().getFormatted() + "! \n"
                        + ChatColor.WHITE + "Reason: " + MessageColor.COLOR_MAIN + this.reason
                        + "\n\nAppeal at " + ChatColor.LIGHT_PURPLE + "" + ChatColor.UNDERLINE + ServerConfig.SERVER_APPEAL_DOMAIN;


        return msg;
    }

    public static Ban getBan(PunishInfo info) {
        return new Ban(info.getPlayer(), info.getExecutor(), info.getPunishTime(), info.getReason());
    }
}
