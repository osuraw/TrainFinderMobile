package com.example.osura.com.trainfinderdriver.ModelClasses;

public class Train
{
    private String tag ="TrainFinder_TrainModel";

    private String Name;

    private int TID;

    public Train(int TID,String Name) {
        this.Name = Name;
        this.TID = TID;
    }

    public String getName() {
        return Name;
    }

    public int getTID() {
        return TID;
    }

    public String toString()
    {
        return Name;
    }
}
