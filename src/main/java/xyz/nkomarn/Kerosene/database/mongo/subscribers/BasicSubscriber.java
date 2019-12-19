package xyz.nkomarn.Kerosene.database.mongo.subscribers;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class BasicSubscriber<T> implements Subscriber<T> {
    private final long requested;

    public BasicSubscriber() {
        this(Integer.MAX_VALUE);
    }

    public BasicSubscriber(long requested) {
        this.requested = requested;
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(requested);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onNext(T t) {}

    @Override
    public void onComplete() {}
}
