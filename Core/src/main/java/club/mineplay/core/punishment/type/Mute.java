package club.mineplay.core.punishment.type;
/*
Created by @8ML (https://github.com/8ML) on 4/28/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.PunishInfo;
import club.mineplay.core.punishment.Punishment;
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
        msg = this.time.getUnit().equals(TimeUnit.PERMANENT) ? ChatColor.RED + "You are permanently muted for "
                + ChatColor.GRAY + this.reason : ChatColor.RED + "You are muted for " + time.getTimeLeft() + " " + time.getUnit().getFormatted()
                + " for "
                + ChatColor.GRAY + this.reason;

        if (!this.player.isOffline())
            this.player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);

        return msg;

    }

    public static Mute getMute(PunishInfo info) {
        return new Mute(info.getPlayer(), info.getExecutor(), info.getPunishTime(), info.getReason());
    }
}
