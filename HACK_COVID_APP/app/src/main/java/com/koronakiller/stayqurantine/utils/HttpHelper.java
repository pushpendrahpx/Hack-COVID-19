package com.koronakiller.stayqurantine.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
            if (requestPackage.getParams() != null && requestPackage.getMethod().equals("POST")) {
                Log.d(TAG, "getJsonData: setting params");
                Log.d(TAG, "getJsonData: " + requestPackage.getParams().get(KEY_USER_PARAMS).toJson());
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(requestPackage.getParams().get(KEY_USER_PARAMS).toJson());
                writer.flush();
                writer.close();
                os.close();
            }
            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "getJsonData: Response code is " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    Log.d(TAG, "getJsonData: data = " + response.toString());
                    return response.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

}
