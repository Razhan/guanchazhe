package com.alp.library.exception;

import android.app.Activity;

public interface IErrorHandler {

    String getErrorMessage(Activity activity, Throwable throwable);

    void showError(Activity activity, Throwable throwable);

}
