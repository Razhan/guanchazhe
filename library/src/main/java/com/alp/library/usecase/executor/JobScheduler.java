package com.alp.library.usecase.executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class JobScheduler implements ExecutionThread {

    @Inject
    JobScheduler() {
    }

    @Override
    public Scheduler getScheduler() {
        return Schedulers.newThread();
    }

}