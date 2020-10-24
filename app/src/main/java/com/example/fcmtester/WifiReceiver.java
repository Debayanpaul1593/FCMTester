package com.example.fcmtester;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
        MyNotificationManager myNotificationManager = new MyNotificationManager(context);
        switch(wifiStateExtra){
            case WifiManager.WIFI_STATE_ENABLED:

                myNotificationManager.showNotification("Wifi state changed!", "Wifi Enabled!", new Intent(context.getApplicationContext(), MainActivity.class), MyNotificationManager.TYPE_WIFI);
                Log.d("wifi", "enabled");
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                myNotificationManager.showNotification("Wifi state changed!", "Wifi Disabled!", new Intent(context.getApplicationContext(), MainActivity.class), MyNotificationManager.TYPE_WIFI);
                Log.d("wifi", "disabled");
                break;
        }
    }
}
