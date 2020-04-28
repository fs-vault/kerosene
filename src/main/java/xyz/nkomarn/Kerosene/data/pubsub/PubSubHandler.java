package xyz.nkomarn.Kerosene.data.pubsub;

import org.bukkit.Bukkit;
import redis.clients.jedis.JedisPubSub;
import xyz.nkomarn.Kerosene.event.PubSubMessageEvent;

/**
 * A Pub/Sub listener that calls PubSubMessageEvent when a message is received.
 */
public class PubSubHandler extends JedisPubSub {
    @Override
    public void onMessage(final String channel, final String message) {
        if (!(message.trim().length() < 1)) {
            Bukkit.getPluginManager().callEvent(new PubSubMessageEvent(channel, message));
        }
    }
}
