package com.example.osura.com.trainfinderdriver.ServiceClasses;

import android.util.Log;
import android.widget.Switch;

import com.example.osura.com.trainfinderdriver.ActivityClasses.MainActivity;
import com.example.osura.com.trainfinderdriver.ActivityClasses.Status;
import com.example.osura.com.trainfinderdriver.ModelClasses.Train;
import com.example.osura.com.trainfinderdriver.ModelClasses.TrainSyncDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainService implements AsynkReturn {

    private Status statusActivity;
    private MainActivity mainActivity;
    private String tag ="TrainFinder_TrainService";

    public TrainService(){}

    public TrainService(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }
    public TrainService(Status statusActivity) {
        this.statusActivity=statusActivity;
    }

    public void setTrainList(){
        AsyncClass asyncClass =new AsyncClass();
        asyncClass.setAsyncReturnDelegate(this);
        asyncClass.setFromTag("setTrainList");
        asyncClass.execute("Search/Trains","GetTrains");
    }

    public void SendLocationData(int trainId,String data)
    {
        AsyncClass asyncClass =new AsyncClass();
        asyncClass.setAsyncReturnDelegate(this);
        asyncClass.setFromTag("SendLocationData");
        asyncClass.execute("Location/Setlocation?Id="+trainId+"&data="+data);
    }

    public void GetLiveFeed(int trainId)
    {
        AsyncClass asyncClass =new AsyncClass();
        asyncClass.setAsyncReturnDelegate(this);
        asyncClass.setFromTag("GetLiveFeed");
        asyncClass.execute("Search/GetTrainDetailsApp?trainId="+trainId);
    }

    @Override
    public void PassData(String dataReceive,String fromTag) {
        switch (fromTag) {
            case "setTrainList":mainActivity.UpdateUI(JsonListConverter(Train.class,dataReceive));
                break;
            case "SendLocationData":
                break;
            case "GetLiveFeed":statusActivity.UpdateUI(JsonConverter(dataReceive));
                break;
        }
    }

    private <T> List<T> JsonListConverter(Class<T> t,String data)
    {
        Gson gson =new Gson();
        Log.i(tag,data);
        Type type =TypeToken.getParameterized(List.class,t).getType();
        return gson.fromJson(data,type);
    }
//    private <T> T JsonConverter(Class<T> t,String data)
    private TrainSyncDto JsonConverter(String data)
    {
        Gson gson =new Gson();
        Log.i(tag,data);
        Type type = new TypeToken<TrainSyncDto>(){}.getType();
        return gson.fromJson(data,TrainSyncDto.class);
    }
}
