package com.example.sanat.foregroundservice;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    ExampleBroadcastReceiver exampleBroadcastReceiver = new ExampleBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_text_input);

        // start the service
        //Intent intent = new Intent(this, LocationUpdaterService.class);
        //startService(intent);

        //start the broadcastreceiver
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(exampleBroadcastReceiver, filter);

    }

    public void startService(View v){
        String input = editText.getText().toString();

        Intent intent = new Intent(this, ExampleService.class);
        intent.putExtra("inputExtra", input);
        startService(intent);

    }

    public void stopService(View v){
        Intent intent = new Intent(this, ExampleService.class);
        stopService(intent);
    }
}
