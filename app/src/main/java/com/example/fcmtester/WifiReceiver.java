package com.example.fcmtester;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/*
      Good Faith Statement & Confidentiality : The below code is part of IMPACTO Suite of products .
      Sirma Business Consulting India reserves all rights to this code . No part of this code should
      be copied, stored or transmitted in any form for whatsoever reason without prior written consent
      of Sirma Business Consulting (India).Employees or developers who have access to this code shall
      take all reasonable precautions to protect the source code and documentation, and preserve its
      confidential, proprietary and trade secret status in perpetuity.Any breach of the obligations
      to protect confidentiality of IMPACTO may cause immediate and irreparable harm to Sirma Business
      Consulting, which cannot be adequately compensated by monetary damages. Accordingly, any breach
      or threatened breach of confidentiality shall entitle Sirma Business Consulting to seek preliminary
      and permanent injunctive relief in addition to such remedies as may otherwise be available.

      //But by the grace of God We are what we are, and his grace to us was not without effect. No,
      //We worked harder than all of them--yet not We, but the grace of God that was with us.
      ----------------------------------------------------------------------------------------------
      |Version No  | Changed by | Date         | Change Tag  | Changes Done
      ----------------------------------------------------------------------------------------------
      |0.1 Beta    | Debayan     | Feb 2, 2018  | #DBP00001   | Initial writing
      ----------------------------------------------------------------------------------------------

*/public class WifiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && !info.isConnected()) {
            // Do your work.
            Log.d("wifi", "not connected");
            // e.g. To check the Network Name or other info:
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
        }*/

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
