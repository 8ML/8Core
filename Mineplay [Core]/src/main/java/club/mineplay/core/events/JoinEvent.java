package club.mineplay.core.events;
/*
Created by Sander on 4/24/2021
*/

import club.mineplay.core.player.MPlayer;
import club.mineplay.core.player.currency.Coin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinEvent implements Listener {

    public JoinEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        MPlayer.registerMPlayer(e.getPlayer());
        e.setJoinMessage("");

        if (!e.getPlayer().hasPlayedBefore()) {
            Coin.addCoins(MPlayer.getMPlayer(e.getPlayer()), 50);
        }

    }

}
