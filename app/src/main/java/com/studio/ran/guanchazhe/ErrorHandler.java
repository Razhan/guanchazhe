package com.studio.ran.guanchazhe;

import android.app.Activity;

import com.alp.library.exception.IErrorHandler;
import com.google.gson.Gson;

import javax.inject.Inject;

public final class ErrorHandler implements IErrorHandler {

    private static final String DEFAULT_ERROR = "An error has occurred";
    private static final String DEFAULT_NET_ERROR = "Please check your network";

    private final Gson gson;

    @Inject
    ErrorHandler() {
        this.gson = new Gson();
    }

    @Override
    public String getErrorMessage(Activity activity, Throwable throwable) {
        return null;
    }

    @Override
    public void showError(Activity activity, Throwable throwable) {

    }
}
