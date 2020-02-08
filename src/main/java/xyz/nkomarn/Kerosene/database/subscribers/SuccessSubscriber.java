package xyz.nkomarn.Kerosene.database.subscribers;

import com.mongodb.reactivestreams.client.Success;

/**
 * A simple subscriber to run on success or failure.
 */
public abstract class SuccessSubscriber extends SimpleSubscriber<Success> {
    private boolean success;

    public SuccessSubscriber() {
        this(Integer.MAX_VALUE);
    }

    public SuccessSubscriber(long amount) {
        super(amount);
    }

    public abstract void onSuccess();
    public abstract void onFail();

    @Override
    public void onNext(Success success) {
        this.success = true;
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        onFail();
    }

    @Override
    public void onComplete() {
        if (this.success) onSuccess();
        else onFail();
    }
}
