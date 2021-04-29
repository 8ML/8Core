package club.mineplay.core.punishment.type;
/*
Created by Sander on 4/29/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.punishment.Punishment;
import org.bukkit.ChatColor;

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
        return ChatColor.RED + "You were warned by " + this.executor.getPlayerStr() + " for " + ChatColor.GRAY + this.reason;
    }
}
