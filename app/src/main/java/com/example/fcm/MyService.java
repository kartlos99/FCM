package com.example.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

public class MyService extends Service {

    private static final String TAG = "myServise";
    public static final String CHANEL_ID = "mechanel";
    private static final String CHANEL_NAME = "asd";
    private static final String CHANEL_DESC = "dsa";
    private String lastValue = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "servise onCreate");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

//        displayNotification("onCreate");

        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("tbilisi");
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue().toString();
                Log.d(TAG, "On_Change " + text);
                if (!lastValue.equals(text)) {
                    displayNotification(text);
                    lastValue = text;
                }
//                Log.d("FromDB", text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Start_Command");

//        Log.d(TAG, "servise started");

        return START_NOT_STICKY;// super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "servise Destroy");
        super.onDestroy();
    }

    void displayNotification(String text) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 22, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(text)
                .setContentTitle("TitleInServise")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        Log.d(TAG, "displey NOTIF " + text);
//        managerCompat.notify(1, mBuilder.build());
        startForeground(1, mBuilder.build());
    }
}
