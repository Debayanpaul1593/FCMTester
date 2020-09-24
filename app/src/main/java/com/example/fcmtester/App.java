package com.example.fcmtester;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;

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

*/public class App extends Application {
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
