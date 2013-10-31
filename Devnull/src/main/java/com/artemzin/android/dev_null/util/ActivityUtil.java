package com.artemzin.android.dev_null.util;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class ActivityUtil {

    private ActivityUtil() {}

    public static void hideSoftKeyboard(Activity activity) {
        try {
            ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // do nothing
        }
    }
}
