/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github._8ml.core.module.hub.events;
/*
Created by @8ML (https://github.com/8ML) on 5/19/2021
*/

import com.github._8ml.core.Core;
import com.github._8ml.core.player.MPlayer;
import com.github._8ml.core.player.hierarchy.Ranks;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
            if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamageByPlayer(EntityDamageByEntityEvent e) {
        if (e.getEntityType().equals(EntityType.PLAYER)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryUse(InventoryClickEvent e) {
        MPlayer player = MPlayer.getMPlayer(e.getWhoClicked().getName());

        if (!player.isPermissible(Ranks.ADMIN, false) || player.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {

        MPlayer player = MPlayer.getMPlayer(e.getPlayer().getName());

        if (!player.isPermissible(Ranks.ADMIN, false) || player.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            e.setCancelled(true);
        }

    }

}
