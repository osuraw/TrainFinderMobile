package com.example.osura.com.trainfinderdriver.ServiceClasses;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationService extends IntentService {

    private String tag ="TrainFinder_LocationService";
    private LocationManager loaderManager;
    private int trainId;
    TrainService trainService;
    private LocationListener locationListener= new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentDateandTime = sdf.format(new Date());
            StringBuilder stringBuilder = new StringBuilder("1,1,"+currentDateandTime);
            stringBuilder.append(","+Location.convert(location.getLatitude(),Location.FORMAT_DEGREES));
            stringBuilder.append(","+Location.convert(location.getLongitude(),Location.FORMAT_DEGREES));
            stringBuilder.append(",,"+location.getSpeed());
            stringBuilder.append(","+location.getBearing());
            trainService.SendLocationData(trainId,stringBuilder.toString());
            Log.i(tag,"onLocationChanged"+stringBuilder.toString());
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(tag,"onStatusChanged");
        }
        @Override
        public void onProviderEnabled(String provider) {
            Log.i(tag,"onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(tag,"onProviderDisabled");
        }
    };

    public LocationService(){super("LocationService");}

    @Override
    public void onCreate() {
        super.onCreate();
        trainService = new TrainService();
        loaderManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            loaderManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
            Location mobileLocation = loaderManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            System.out.println(mobileLocation.getLatitude());
        }
        catch (SecurityException e)
        {
            Log.i(tag,e.getMessage());
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        trainId = intent.getIntExtra("trainId",0);
        Log.i(tag,"Service starts");
    }
}
