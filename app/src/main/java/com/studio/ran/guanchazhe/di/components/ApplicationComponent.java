package com.studio.ran.guanchazhe.di.components;

import android.content.Context;

import com.alp.library.di.JobThread;
import com.alp.library.di.UIThread;
import com.alp.library.exception.IErrorHandler;
import com.alp.library.usecase.executor.ExecutionThread;
import com.studio.ran.guanchazhe.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    @JobThread
    ExecutionThread executionThread();

    @UIThread
    ExecutionThread postExecutionThread();

    IErrorHandler errorHandler();

}