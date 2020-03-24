package com.koronakiller.geolocation.utils;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpHelper {
    public static final String KEY_USER_PARAMS = "KEY_USER_PARAMS";
    private static final String TAG = "HttpHelper";

    public static String getJsonData(RequestPackage requestPackage) throws IOException {
        Log.d(TAG, "getJsonData: ");
        String address = requestPackage.getEndPoint();

        try {
            Log.d(TAG, "getJsonData: ");
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestPackage.getMethod());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(10000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            if (requestPackage != null && requestPackage.getMethod().equals("POST")) {
                Log.d(TAG, "getJsonData: setting params");
                OutputStream os = connection.getOutputStream();
                byte[] input = requestPackage.getParams().get(KEY_USER_PARAMS).toJson().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "getJsonData: Response code is " + responseCode);

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

}
