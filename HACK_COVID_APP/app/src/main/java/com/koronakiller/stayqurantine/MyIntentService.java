package com.koronakiller.stayqurantine;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.koronakiller.stayqurantine.utils.AppNotificationManager;
import com.koronakiller.stayqurantine.utils.RequestPackage;

public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";
    private static final String ENTRY_ENDPOINT = "";

    public MyIntentService() {
        super("Broadcast");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        requestPackage.setEndPoint(ENTRY_ENDPOINT);
        AppNotificationManager.getInstance(getApplicationContext()).triggerNotification();
    }
}
