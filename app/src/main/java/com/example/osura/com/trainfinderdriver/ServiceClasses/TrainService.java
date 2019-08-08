package com.example.osura.com.trainfinderdriver.ServiceClasses;

import android.util.Log;

import com.example.osura.com.trainfinderdriver.ActivityClasses.MainActivity;
import com.example.osura.com.trainfinderdriver.ModelClasses.Train;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TrainService implements AsynkReturn {
    private MainActivity mainActivity;
    private String tag ="TrainFinder_TrainService";
    public TrainService(){}

    public TrainService(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    private  List<Train> trainList =new ArrayList<>();

    public  List<Train> getTrainList() {
        return trainList;
    }

    public void setTrainList(){
        AsyncClass asynkClass =new AsyncClass();
        asynkClass.asyncReturnDelegate =this;
        asynkClass.execute("Search/Trains","GetTrains");
    }

    public void SendLocationData(int trainId,String data)
    {
        AsyncClass asynkClass =new AsyncClass();
        asynkClass.asyncReturnDelegate =this;
        asynkClass.execute("Location/Setlocation?Id="+trainId+"&data="+data);
    }

    public void GetLiveFeed(int trainId,String data)
    {
        AsyncClass asynkClass =new AsyncClass();
        asynkClass.asyncReturnDelegate =this;
        asynkClass.execute("Location/Setlocation?Id="+trainId+"&data="+data);
    }

    @Override
    public void PassData(String dataReceive) {
        if(!dataReceive.equals("\"OK\"")&&!dataReceive.equals(""))
        {
            Gson gson =new Gson();
            trainList = gson.fromJson(dataReceive,new TypeToken<List<Train>>(){}.getType());
            mainActivity.UpdateUI();
        }
        Log.i(tag,dataReceive);
    }
}
