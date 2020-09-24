package com.example.fcmtester;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyNotificationManager {

    public static final String TYPE_FIREBASE = "type_firebase";
    public static final String TYPE_WIFI = "type_wifi";
    public static final int NOTIFICATION_ID = 123;
    private Context mContext;
    private NotificationManagerCompat managerCompat;

    public MyNotificationManager(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotification1(String from, String notification, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this.mContext, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager nmanager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel("my_channel_01",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            nmanager.createNotificationChannel(channel);
        }
        Notification.Builder builder = new Notification.Builder(this.mContext);
        Notification mNotification = builder
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle("Hello there")
                .setContentText(notification)
                .setSmallIcon(R.drawable.ic_launcher1)
                .build();

        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        nmanager.notify(NOTIFICATION_ID, mNotification);
    }

    public void showNotification(String title, String message, Intent intent, String type) {
        managerCompat = NotificationManagerCompat.from(mContext);
        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra("type", type);
        notificationIntent.putExtra("title", title);
        notificationIntent.putExtra("message", message);

        PendingIntent pintent = PendingIntent.getActivity(mContext, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(mContext, App.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher1)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pintent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        managerCompat.notify(1, notification);
    }
}
