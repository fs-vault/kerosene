package com.firestartermc.kerosene.event;

import com.firestartermc.kerosene.data.redis.Redis;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is called when a {@link Redis} Pub/Sub message is received on
 * one of the registered Pub/Sub channels. It is called immediately as the
 * message is received.
 *
 * This event allows for easy Pub/Sub message handling via Bukkit API
 * listener classes.
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

    @NotNull
    public String getChannel() {
        return this.channel;
    }

    @NotNull
    public String getMessage() {
        return this.message;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
