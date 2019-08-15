package com.example.osura.com.trainfinderdriver.ActivityClasses;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osura.com.trainfinderdriver.ModelClasses.Train;
import com.example.osura.com.trainfinderdriver.R;
import com.example.osura.com.trainfinderdriver.ServiceClasses.FirebaseService;
import com.example.osura.com.trainfinderdriver.ServiceClasses.LocationService;
import com.example.osura.com.trainfinderdriver.ServiceClasses.TrainService;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.example.osura.com.trainfinderdriver.ServiceClasses.RegistrationIntentService;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private String tag ="TrainFinder_MainActivity";
    private Spinner spinner;
    private Train trainSelected;
    TrainService trainService;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    //region MainActivityHandel
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckPermission();
        trainService = new TrainService(this);
        trainService.setTrainList();
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.cmbTrain);
        spinner.setOnItemSelectedListener(this);

        registerWithNotificationHubs();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        trainSelected = (Train) spinner.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    public void Btn_Attached_OnClick(View view) {
        Toast.makeText(this,"Train "+trainSelected.getName().toUpperCase()+" Attached To Server",Toast.LENGTH_LONG).show();
        Intent intent =new Intent(getApplicationContext(),Status.class);
        Intent intent1 =new Intent(getApplicationContext(), LocationService.class);
        intent.putExtra("trainId",trainSelected.getTID());
        intent.putExtra("name",trainSelected.getName());
        intent1.putExtra("trainId",trainSelected.getTID());
        startActivity(intent);
        startService(intent1);
        finish();
    }

    public void UpdateUI() {
        ArrayAdapter<Train> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, trainService.getTrainList());
        spinner.setAdapter(adapter);
    }

    private void CheckPermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(tag,"sss");
    }
    //endregion

    //region PushNotificationHandel
    public void registerWithNotificationHubs()
    {
        if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }
    // check for google play services on device
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(tag, "This device is not supported by Google Play Services.");
                finish();
            }
            return false;
        }
        return true;
    }
    //endregion
}
