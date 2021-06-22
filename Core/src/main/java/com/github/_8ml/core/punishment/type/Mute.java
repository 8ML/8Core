package com.github._8ml.core.punishment.type;
/*
Created by @8ML (https://github.com/8ML) on 4/28/2021
*/

import com.github._8ml.core.config.MessageColor;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.punishment.PunishInfo;
import com.github._8ml.core.punishment.Punishment;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;

public class Mute extends Punishment {

    private final MPlayer player, executor;
    private final PunishTime time;
    private final String reason;

    public Mute(MPlayer player, MPlayer executor, PunishTime time, String reason) {
        super(PunishType.MUTE);

        this.player = player;
        this.executor = executor;
        this.time = time;
        this.reason = reason;
    }

    public void execute() {
        execute(this.executor, this.player, this.reason, this.time);
    }

    @Override
    public void onExecute() {
        if (!this.player.isOffline()) {

            this.player.getPlayer().sendMessage(getPunishMessage());

        }
    }

    @Override
    public String getPunishMessage() {

        String msg;
        msg = this.time.getUnit().equals(TimeUnit.PERMANENT) ? MessageColor.COLOR_ERROR + "You are permanently muted for "
                + MessageColor.COLOR_MAIN + this.reason : MessageColor.COLOR_ERROR + "You are muted for " + time.getTimeLeft() + " " + time.getUnit().getFormatted()
                + " for "
                + MessageColor.COLOR_MAIN + this.reason;

        if (!this.player.isOffline())
            this.player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);

        return msg;

    }

    public static Mute getMute(PunishInfo info) {
        return new Mute(info.getPlayer(), info.getExecutor(), info.getPunishTime(), info.getReason());
    }
}
