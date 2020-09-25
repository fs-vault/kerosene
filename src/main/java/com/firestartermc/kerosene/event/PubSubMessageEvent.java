package com.firestartermc.kerosene.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Fired when a Pub/Sub message is received on subscribed channels.
 * This event is fired asynchronously.
 */
public class PubSubMessageEvent extends Event {
    
    private static final HandlerList handlers = new HandlerList();

    private final String channel;
    private final String message;

    public PubSubMessageEvent(@NotNull String channel, @NotNull String message) {
        super(true);
        this.channel = channel;
        this.message = message;
    }

    public @NotNull String getChannel() {
        return this.channel;
    }

    public @NotNull String getMessage() {
        return this.message;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }

}
