package club.mineplay.core.events;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.level.Level;
import club.mineplay.core.punishment.PunishInfo;
import club.mineplay.core.punishment.Punishment;
import club.mineplay.core.punishment.type.Mute;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatEvent implements Listener {

    public ChatEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        PunishInfo info = Punishment.getActivePunishment(player, Punishment.PunishType.MUTE);

        if (info.isActive()) {

            player.getPlayer().sendMessage(Mute.getMute(info).getPunishMessage());
            e.setCancelled(true);
            return;

        }

        e.setFormat(ChatColor.GRAY + "[" + ((int) Level.getLevelFromXP(player.getXP(), false)) + "] "
                + player.getRankEnum().getRank().getFullPrefixWithSpace()
                + player.getRankEnum().getRank().getNameColor() + player.getPlayer().getName()
                + ": " + player.getRankEnum().getRank().getChatColor() + e.getMessage());
    }
}
