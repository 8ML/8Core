package club.mineplay.core.events;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.level.Level;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
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
        MPlayer player = MPlayer.getMPlayer(e.getPlayer());
        e.setFormat(ChatColor.GRAY + "[" + Level.getLevelFrom(player.getXP()) + "] "
                + player.getRankEnum().getRank().getFullPrefixWithSpace()
                + player.getRankEnum().getRank().getNameColor() + player.getPlayer().getName()
                + ": " + player.getRankEnum().getRank().getChatColor() + e.getMessage());
    }
}
