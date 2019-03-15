package com.test.september.util;



import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    public static void sendOkHttpRequest(final String address, final okhttp3.Callback callback) {
//         final String UserAccessToken
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
//                .addHeader("token", UserAccessToken)
                .build();
        client.newCall(request).enqueue(callback);

    }
}