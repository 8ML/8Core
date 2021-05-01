package club.mineplay.core.punishment.type;
/*
Created by Sander on 4/28/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.PunishInfo;
import club.mineplay.core.punishment.Punishment;
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
        String msg = "";
        if (this.punishTime.getUnit().equals(TimeUnit.PERMANENT)) msg = ChatColor.RED + "\nYou are permanently banned! \n"
                + ChatColor.WHITE + "Reason: " + ChatColor.GRAY + this.reason
                + "\n\nAppeal at " + ChatColor.GRAY + "mineplay.club/appeal";
        else msg = ChatColor.RED + "\nYou are banned for " + this.punishTime.getTimeLeft() + " " + this.punishTime.getUnit().getFormatted() + "! \n"
                + ChatColor.WHITE + "Reason: " + ChatColor.GRAY + this.reason
                + "\n\nAppeal at " + ChatColor.GRAY + "mineplay.club/appeal";

        return msg;
    }

    public static Ban getBan(PunishInfo info) {
        return new Ban(info.getPlayer(), info.getExecutor(), info.getPunishTime(), info.getReason());
    }
}
