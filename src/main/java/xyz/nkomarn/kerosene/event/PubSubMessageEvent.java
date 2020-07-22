package xyz.nkomarn.kerosene.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event fired when a Pub/Sub message is received.
 */
public class PubSubMessageEvent extends Event {
    
    private static final HandlerList handlers = new HandlerList();
    private final String channel;
    private final String message;

    public PubSubMessageEvent(final String channel, final String message) {
        this.channel = channel;
        this.message = message;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
