package com.example.osura.com.trainfinderdriver.ServiceClasses;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.osura.com.trainfinderdriver.ActivityClasses.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.microsoft.windowsazure.messaging.NotificationHub;

import java.util.concurrent.TimeUnit;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "TrainFinder_RegService";
    String FCM_token = null;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences sharedPreferences = getDefaultSharedPreferences(this);
        String regID;
        String token="";

        try {
            //get FCM Token
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    FCM_token = instanceIdResult.getToken();
                }
            });
            TimeUnit.SECONDS.sleep(2);

            //Check device already registered with notification hub
            //If SharedPreferences Contains value for registerID device registered with NH
            //If FCMToken not equals

            if (!(token=sharedPreferences.getString("FCMToken", "")).equals(FCM_token)||((regID=sharedPreferences.getString("registrationID", "")) == "")){
                regID = WebService.DoGet("Notification/GetRegistrationId?pns_FCM_Token="+FCM_token);
                Gson gson = new Gson();
                regID=gson.fromJson(regID,String.class);
                WebService.DoGet("Notification/Notification/updateRegistration?id="+regID+"&platform=fcm&token="+FCM_token);
                sharedPreferences.edit().putString("registrationID", regID ).apply();
                sharedPreferences.edit().putString("FCMToken", FCM_token ).apply();
            }

            else { Log.i(TAG,"Previously Registered Successfully - RegId : " + regID); }


        }
        catch (Exception e) {
            Log.e(TAG, "Failed to complete registration", e);
        }
    }
}