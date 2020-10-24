package com.example.fcmtester;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "channel1";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        WifiReceiver wifiReceiver = new WifiReceiver();
        registerReceiver(wifiReceiver, intentFilter);
    }

    private void createNotificationChannel() {
        try{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                NotificationChannel channel1 = new NotificationChannel(
                        CHANNEL_ID,
                        "Channel 1",
                        NotificationManager.IMPORTANCE_HIGH
                );

                channel1.setDescription("This is a sample notification");

                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
