package club.mineplay.core.events.event;
/*
Created by @8ML (https://github.com/8ML) on 5/8/2021
*/

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends Event {

    private UpdateType type;

    private static final HandlerList HANDLERS = new HandlerList();

    public enum UpdateType {
        TICK, SECONDS
    }

    public UpdateEvent(UpdateType type) {
        this.type = type;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public UpdateType getType() {
        return this.type;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
