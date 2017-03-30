package com.alp.library.usecase;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

import static com.google.common.base.Preconditions.checkNotNull;

public final class UseCaseBuilder<T> {

    private Consumer<T> onSuccessCallback;
    private Consumer<? super Throwable> onErrorCallback;
    private Action onCompleteCallback;

    private UseCase useCase;

    public UseCaseBuilder(UseCase useCase) {
        onSuccessCallback = Functions.emptyConsumer();
        onErrorCallback = Functions.ERROR_CONSUMER;
        onCompleteCallback = Functions.EMPTY_ACTION;

        this.useCase = useCase;
    }

    public UseCaseBuilder<T> onSuccess(Consumer<T> consumer) {
        onSuccessCallback = checkNotNull(consumer);
        return this;
    }

    public UseCaseBuilder<T> onError(Consumer<? super Throwable> consumer) {
        onErrorCallback = checkNotNull(consumer);
        return this;
    }

    public UseCaseBuilder<T> onComplete(Action action) {
        onCompleteCallback = checkNotNull(action);
        return this;
    }

    public void build(Object... params) {
        useCase.execute(
                onSuccessCallback,
                onErrorCallback,
                onCompleteCallback,
                params
        );
    }

}