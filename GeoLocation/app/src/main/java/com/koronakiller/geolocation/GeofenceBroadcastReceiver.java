package com.koronakiller.geolocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.koronakiller.geolocation.utils.AppNotificationManager;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = GeofenceBroadcastReceiver.class.getSimpleName();
    private AppNotificationManager notificationManager;


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: broadcast received");
//        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
//
//        if (event.hasError()) {
//            Log.d(TAG, "onReceive: error occured code is " + GeofenceStatusCodes.getStatusCodeString(event.getErrorCode()));
//        }
//
//        int transitionType = event.getGeofenceTransition();
//        Toast.makeText(context, "Transition happen", Toast.LENGTH_SHORT).show();
//        if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER || transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
//            List<Geofence> geofences = event.getTriggeringGeofences();
//            triggerNotif(context);
//
//            Toast.makeText(context, "STAY HOME!!!!", Toast.LENGTH_SHORT).show();
//        } else {
//            Log.e(TAG, "onReceive: Invaid type" + transitionType);
//        }
    }

    private void triggerNotif(Context context) {
        notificationManager = AppNotificationManager.getInstance(context);
        notificationManager.registerNotificationChannel();
        notificationManager.triggerNotification();

    }


}
