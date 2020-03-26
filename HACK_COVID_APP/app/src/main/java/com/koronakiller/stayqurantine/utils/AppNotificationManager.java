package com.koronakiller.stayqurantine.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.koronakiller.stayqurantine.R;

public class AppNotificationManager {
    private static final String TAG = "AppNotificationManager";
    private static AppNotificationManager appNotificationManager;
    private Context context;
    private NotificationManagerCompat notificationManagerCompat;
    private NotificationManager notificationManager;

    private AppNotificationManager(Context context) {
        this.context = context;
        this.notificationManagerCompat = NotificationManagerCompat.from(context); //FIXME WE'll use this <----
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.notificationManager = context.getSystemService(NotificationManager.class);
        }
    }

    public static AppNotificationManager getInstance(Context context) {
        if (appNotificationManager == null) {
            synchronized (context) {
                if (appNotificationManager == null)
                    appNotificationManager = new AppNotificationManager(context);
                return appNotificationManager;
            }
        }
        return appNotificationManager;
    }

    public void registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(context.getResources().getString(R.string.id_tech_channel_name), context.getResources().getString(R.string.tech_channel_name), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true);
            channel.setDescription(context.getResources().getString(R.string.tech_channel_description));
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void triggerNotification() {
        Intent i = new Intent();
        androidx.core.app.TaskStackBuilder taskStackBuilder = androidx.core.app.TaskStackBuilder.create(context);//COre
        taskStackBuilder.addNextIntentWithParentStack(i);
        PendingIntent pi = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getResources().getString(R.string.id_tech_channel_name))
                .setContentTitle("Tech News")
                .setContentIntent(pi)
                .setContentInfo("today this mobile is Lunched")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("thus s asdadad  adasfa ssf fasda srwdawrq j jzsdfgdAXD VDAW"))
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setSmallIcon(R.drawable.ic_launcher_foreground);
//                .setOngoing(true);
        try {
            notificationManagerCompat.notify(0, builder.build());
        } catch (NullPointerException e) {
            Log.d(TAG, "triggerNotification: catch");
            e.printStackTrace();
        }
    }


    public void cancelNotification(int id) {
        notificationManager.cancel(id);
    }

//    public void updateNotification() {
//        Intent i = new Intent(context, null);
//        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
//        taskStackBuilder.addNextIntentWithParentStack(i);
//
//        PendingIntent pi = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.img);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getResources().getString(R.string.id_tech_channel_name))
//                .setContentTitle("Tech News")
//                .setContentIntent(pi)
//                .setContentInfo("today this mobile is Lunched")
//                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
//                .setSmallIcon(R.drawable.ic_my_location_black_24dp).setAutoCancel(true);
//        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setBigContentTitle("Taksh Send this").setSummaryText("this is YT screenshot"));
//        try {
//            notificationManagerCompat.notify(0, builder.build());
//        } catch (NullPointerException e) {
//            Log.d(TAG, "triggerNotification: catch");
//            e.printStackTrace();
//        }
//
//    }

}
