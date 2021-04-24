package club.mineplay.core.events;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.level.Level;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class ChatEvent implements Listener {

    public ChatEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        MPlayer player = MPlayer.getMPlayer(e.getPlayer());
        e.setMessage(Color.WHITE + e.getMessage());
        e.setFormat(Color.GRAY + "(" + Level.getLevelFrom(player.getXP()) + ")"
                + ChatColor.RESET + player.getRank().getRank().getFullPrefix() + " "
                + Color.WHITE + player.getPlayer().getName() + ":");
    }

}
