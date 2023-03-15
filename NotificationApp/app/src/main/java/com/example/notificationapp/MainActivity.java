package com.example.notificationapp;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button notify_btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notify_btn = (Button) findViewById(R.id.notify_btn);
        String channelId = "My Notification";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }

        notify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Notify Button Clicked", Toast.LENGTH_SHORT).show();
                CreateNotification("Button Notification","Notification sent on Button Click");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        CreateNotification("Background Notification","Notification sent on Background");
    }

    @Override
    protected void onResume() {
        super.onResume();
        CreateNotification("Resume Notification","Notification sent on Resume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        CreateNotification("Stop Notification","Notification sent on Stop");
    }

    public void CreateNotification(String title, String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification");
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setAutoCancel(true);
//                builder.setChannelId(channelId);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {POST_NOTIFICATIONS},13 );
            return;
        }
        notificationManagerCompat.notify(1, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 13) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Notification Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(MainActivity.this, "Notification Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }
}