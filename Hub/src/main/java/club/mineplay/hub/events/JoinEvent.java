package club.mineplay.hub.events;
/*
Created by Sander on 5/8/2021
*/

import club.mineplay.core.events.event.UpdateEvent;
import club.mineplay.core.hierarchy.Ranks;
import club.mineplay.core.player.MPlayer;
import club.mineplay.core.utils.NameTag;
import club.mineplay.hub.Hub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    public JoinEvent(Hub plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        MPlayer pl = MPlayer.getMPlayer(e.getPlayer().getName());
        changeTag(pl);
    }

    private int timer = 0;
    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (!e.getType().equals(UpdateEvent.UpdateType.SECONDS)) return;
        timer++;
        if (timer >= 5) {
            timer = 0;

            for (Player p : Bukkit.getOnlinePlayers()) {

                MPlayer pl = MPlayer.getMPlayer(p.getName());

                changeTag(pl);

            }
        }
    }

    private void changeTag(MPlayer player) {
        NameTag.changeTag(player.getPlayer(), player.getRankEnum().getRank().getFullPrefixWithSpace(), "",
                player.getRankEnum().equals(Ranks.DEFAULT) ? ChatColor.GRAY : ChatColor.WHITE);
        if (player.getTitle().equals("")) {
            NameTag.removeTitle(player.getPlayer());
        } else {
            NameTag.changeTitle(player.getPlayer(), player.getTitle());
        }
    }

}
