package com.example.sanat.foregroundservice;

import android.app.Application;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "exampleservicechannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
