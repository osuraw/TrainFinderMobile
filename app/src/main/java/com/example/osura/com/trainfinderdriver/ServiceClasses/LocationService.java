package com.example.osura.com.trainfinderdriver.ServiceClasses;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationService extends IntentService {

    private String tag ="TrainFinder_LocationService";
    private LocationManager loaderManager;
    private int trainId;
    TrainService trainService;
    private OutputStreamWriter file;
    FileOutputStream fOut;
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
            try {
                file.append("{\"FIELD1\":\""+stringBuilder.toString()+"\"},\n");
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            loaderManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0,locationListener);
            getPublicAlbumStorageDir("/LocationLog.txt");
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void getPublicAlbumStorageDir(String fileName) {
        System.out.println(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()));
        String path = Environment.getExternalStorageDirectory().getAbsoluteFile()+"/data1";
        File dir =new File(path);
        if(!dir.exists())
            dir.mkdirs();

        try {
            File file = new File(dir.getPath()+fileName);
            fOut = new FileOutputStream(file);
            this.file = new OutputStreamWriter(fOut);
            this.file.write("");
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
}
