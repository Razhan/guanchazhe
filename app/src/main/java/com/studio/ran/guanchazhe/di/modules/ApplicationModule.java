package com.studio.ran.guanchazhe.di.modules;

import android.content.Context;

import com.alp.library.di.JobThread;
import com.alp.library.di.UIThread;
import com.alp.library.exception.IErrorHandler;
import com.alp.library.usecase.executor.ExecutionThread;
import com.alp.library.usecase.executor.JobScheduler;
import com.alp.library.usecase.executor.UIScheduler;
import com.studio.ran.guanchazhe.ErrorHandler;
import com.studio.ran.guanchazhe.NewsApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final static int CONNECTION_TIMEOUT = 60;

    private final NewsApplication application;

    public ApplicationModule(NewsApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    @JobThread
    ExecutionThread provideThreadExecutor(JobScheduler jobThread) {
        return jobThread;
    }

    @Provides
    @Singleton
    @UIThread
    ExecutionThread providePostExecutionThread(UIScheduler uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    IErrorHandler provideErrorHandler(ErrorHandler handler) {
        return handler;
    }

}