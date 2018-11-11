package com.example.sanat.foregroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sanat on 11/9/2018.
 */

public class ExampleBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast Receiver called", Toast.LENGTH_SHORT).show();
        Intent intentService = new Intent(context, LocationUpdaterService.class);
        context.startService(intentService);

    }
}
