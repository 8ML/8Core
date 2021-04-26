package club.mineplay.core.events;
/*
Created by Sander on 4/25/2021
*/

import club.mineplay.core.config.MessageColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandEvent implements Listener {

    public CommandEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().toLowerCase().startsWith("/say")
                || e.getMessage().toLowerCase().startsWith("/me")
                || e.getMessage().toLowerCase().startsWith("/minecraft:say")
                || e.getMessage().toLowerCase().startsWith("/minecraft:me")) {
            e.setCancelled(true);
        }
    }

}
