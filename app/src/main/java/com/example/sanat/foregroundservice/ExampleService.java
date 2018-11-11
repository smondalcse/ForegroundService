package com.example.sanat.foregroundservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static com.example.sanat.foregroundservice.App.CHANNEL_ID;

/**
 * Created by sanat on 11/8/2018.
 */

public class ExampleService extends Service {
    private static final String TAG = "ExampleService";

    @Override
    public void onCreate() {
        // called only once for this service life cycle
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // called everytime
        String input = intent.getStringExtra("inputExtra");

        Intent intents = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intents, PendingIntent.FLAG_ONE_SHOT);

        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("Foreground Service")
                        .setContentText("Foreground Message")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent).build();

        startForeground(1, notification);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, mBuilder.build());

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**********************************/




}
