package com.example.sanat.foregroundservice;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocationUpdaterService extends Service
{
    private static final String TAG = "LocationUpdaterService";
    
    public static final int TWO_MINUTES = 10000; // 120 seconds
    public static Boolean isRunning = false;
    //https://stackoverflow.com/questions/14478179/background-service-with-location-listener-in-android
    //https://stackoverflow.com/questions/14478179/background-service-with-location-listener-in-android

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //mLocationListener = new LocationUpdaterListener();
        super.onCreate();
    }

    Handler mHandler = new Handler();
    Runnable mHandlerTask = new Runnable(){
        @Override
        public void run() {
            if (!isRunning) {
                //startListening();
                Toast.makeText(LocationUpdaterService.this, "Hanler Run", Toast.LENGTH_SHORT).show();
                AsyncTask_SaveLocationHistory asyncTask_saveLocationHistory = new AsyncTask_SaveLocationHistory();
                asyncTask_saveLocationHistory.execute();
            }
            mHandler.postDelayed(mHandlerTask, TWO_MINUTES);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandlerTask.run();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //stopListening();
        mHandler.removeCallbacks(mHandlerTask);
        super.onDestroy();
    }

    public String getCurrentDateTime() {
        String date_time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(new Date());
        return date_time;
    }

    private class AsyncTask_SaveLocationHistory extends AsyncTask<Void, Void, String> {
        ProgressDialog Dialog;

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "AsyncTask_SaveCallHistory: onPreExecute");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            SoapObject request = new SoapObject(getResources().getString(R.string.NAMESPACE),
                    getResources().getString(R.string.METHOD_NAME_SaveLocationHistory));

            request.addPropertyIfValue("emp_id", "20170746");
            request.addPropertyIfValue("emp_number", "01712995265");
            request.addPropertyIfValue("address", "Static Testing Locaton for service");
            request.addPropertyIfValue("lat", "11.111");
            request.addPropertyIfValue("lng", "22.222");
            request.addPropertyIfValue("time", getCurrentDateTime());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            // HttpTransportSE httpTransport = new HttpTransportSE(URL);
            HttpTransportSE httpTransport = new HttpTransportSE(getResources().getString(R.string.URL));

            try {
                httpTransport.call(getResources().getString(R.string.SOAP_ACTION_SaveLocationHistory), envelope);
                SoapObject newob = (SoapObject) envelope.bodyIn;
                int count = newob.getPropertyCount();
                //List<CallLogModel> c = new ArrayList<>();
                if (count == 0) {
                    return "0";
                }

                Object response = (Object) envelope.getResponse();

                return response.toString();

            } catch (Exception e) {

                return "0";
            }

        }

        @Override
        protected void onPostExecute(String  c) {


        }
    }

}