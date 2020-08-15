package com.firestartermc.kerosene.data.redis.pubsub;

import io.lettuce.core.pubsub.RedisPubSubListener;
import org.bukkit.Bukkit;
import com.firestartermc.kerosene.event.PubSubMessageEvent;

/**
 * A Pub/Sub listener that calls PubSubMessageEvent when a message is received.
 */
public class PubSubHandler implements RedisPubSubListener<String, String> {

    @Override
    public void message(String channel, String message) {
        if (message.trim().length() < 1) {
            return;
        }

        Bukkit.getPluginManager().callEvent(new PubSubMessageEvent(channel, message));
    }

    @Override
    public void message(String pattern, String channel, String message) {
    }

    @Override
    public void subscribed(String channel, long count) {
    }

    @Override
    public void psubscribed(String pattern, long count) {
    }

    @Override
    public void unsubscribed(String channel, long count) {
    }

    @Override
    public void punsubscribed(String pattern, long count) {
    }
}
