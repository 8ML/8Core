package xyz.dev_8.core.module.hub.events;
/*
Created by @8ML (https://github.com/8ML) on 5/19/2021
*/

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.dev_8.core.Core;
import xyz.dev_8.core.player.MPlayer;
import xyz.dev_8.core.player.hierarchy.Ranks;

public class InteractionEvent implements Listener {

    public InteractionEvent(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        if (!player.isPermissible(Ranks.ADMIN, false) || player.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        if (!player.isPermissible(Ranks.ADMIN, false) || player.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        if (!player.isPermissible(Ranks.ADMIN, false) || player.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntityType().equals(EntityType.PLAYER)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

}
