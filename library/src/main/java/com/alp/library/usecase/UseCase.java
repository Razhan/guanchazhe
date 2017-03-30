package com.alp.library.usecase;

import com.alp.library.usecase.executor.ExecutionThread;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public abstract class UseCase {

    private final ExecutionThread postExecutionThread;
    private final ExecutionThread executionThread;
    private Disposable disposable;

    public UseCase(ExecutionThread executionThread, ExecutionThread postExecutionThread) {
        this.executionThread = executionThread;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Observable<?> buildUseCaseObservable(Object... params);

    void execute(Consumer onNext, Consumer<? super Throwable> onError, Action onComplete,
                 Object... params) {
        disposable = this.buildUseCaseObservable(params)
                .subscribeOn(executionThread.getScheduler())
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(onNext, onError, onComplete);
    }

    public void dispose() {
        if (disposable == null) {
            return;
        }

        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
