package club.mineplay.core.punishment.type;
/*
Created by Sander on 4/29/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.Punishment;
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
            player.getPlayer().sendMessage(getPunishMessage());
        }
    }

    @Override
    public String getPunishMessage() {
        return ChatColor.RED + "You were kicked by " + this.executor.getPlayerStr() + "\n"
                + ChatColor.WHITE + "Reason: " + ChatColor.GRAY + this.reason;
    }

}
