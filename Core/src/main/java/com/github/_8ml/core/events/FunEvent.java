package com.github._8ml.core.events;
/*
Created by @8ML (https://github.com/8ML) on 5/10/2021
*/

import com.github._8ml.core.cmd.commands.admin.EACMD;
import com.github._8ml.core.Core;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * This class along with EACMD.class should probably be either removed
 * or used with caution as I can confirm this is pretty destructive.
 */
public class FunEvent implements Listener {

    public FunEvent(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent e) {

        if (!EACMD.ea) return;

        if (e.getEntity().getType().equals(EntityType.ARROW)) {

            e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), 200f, false);
            e.getEntity().remove();

        }
    }

}
