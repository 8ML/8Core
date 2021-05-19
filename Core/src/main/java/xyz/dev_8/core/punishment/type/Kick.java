package xyz.dev_8.core.punishment.type;
/*
Created by @8ML (https://github.com/8ML) on 4/29/2021
*/

import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.punishment.Punishment;
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
        return ChatColor.RED + "You were kicked by " + this.executor.getPlayerStr() + "\n"
                + ChatColor.WHITE + "Reason: " + ChatColor.GRAY + this.reason;
    }

}
