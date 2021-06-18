package com.github._8ml.core.events.event;
/*
Created by @8ML (https://github.com/8ML) on 5/9/2021
*/

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ProxyJoinEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;

    public ProxyJoinEvent(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return this.player;
    }
}
