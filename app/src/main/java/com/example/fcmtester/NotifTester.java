package com.example.fcmtester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;

import com.example.fcmtester.databinding.ActivityNotifTesterBinding;

public class NotifTester extends AppCompatActivity {

    private ActivityNotifTesterBinding  binding;
    private NotificationManagerCompat managerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notif_tester);
        managerCompat = NotificationManagerCompat.from(this);
        binding.btnSend.setOnClickListener(view -> {
            Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher1)
                    .setContentTitle(binding.title.getText().toString())
                    .setContentText(binding.message.getText().toString())
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();

            managerCompat.notify(1, notification);
        });
    }
}
