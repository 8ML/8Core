package club.mineplay.core.punishment.type;
/*
Created by Sander on 4/28/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.Punishment;
import net.md_5.bungee.api.ChatColor;

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
        if (this.time.getUnit().equals(TimeUnit.PERMANENT)) msg = ChatColor.RED + "Shh! you are permanently muted for "
                + ChatColor.GRAY + this.reason;
        else msg = msg = ChatColor.RED + "Shh! you are muted for " + time.getTimeLeft() + " " + time.getUnit().getFormatted() + " for "
                + ChatColor.GRAY + this.reason;

        return msg;

    }
}
