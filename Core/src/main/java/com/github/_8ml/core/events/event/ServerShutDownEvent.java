package com.github._8ml.core.events.event;
/*
Created by @8ML (https://github.com/8ML) on June 25 2021
*/

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerShutDownEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }


    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}