package xyz.nkomarn.Kerosene.database.subscribers;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * A simple Mongo subscriber wrapper.
 */
public abstract class SimpleSubscriber<T> implements Subscriber<T> {
    private final long amount;

    public SimpleSubscriber() {
        this(Integer.MAX_VALUE);
    }

    public SimpleSubscriber(long amount) {
        this.amount = amount;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(amount);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onNext(T t) { }

    @Override
    public void onComplete() { }
}
