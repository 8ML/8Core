package club.mineplay.core.events;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.utils.StaffMSG;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LeaveEvent implements Listener {

    public LeaveEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage("");

        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());
        if (player.isPermissible(Ranks.BUILD_TEAM)) {
            StaffMSG.sendStaffMessage(ChatColor.YELLOW + "quit.", player);
        }

        MPlayer.removeMPlayer(player);

    }

}
