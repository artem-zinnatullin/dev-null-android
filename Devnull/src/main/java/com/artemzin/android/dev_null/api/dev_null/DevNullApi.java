package com.artemzin.android.dev_null.api.dev_null;

import com.artemzin.android.dev_null.api.network.NetworkException;
import com.artemzin.android.dev_null.api.network.NetworkRequest;

public class DevNullApi {

    private static final String BASE_URL = "http://devnull-as-a-service.com/";

    private DevNullApi() {}

    /**
     * F*ck, null() is deprecated method name in Java!
     * @return
     */
    public static String nullIt(String text) throws NetworkException {
        return NetworkRequest.newPostRequestInstance(BASE_URL + "dev/null", text).getResponse();
    }
}
