package com.example.osura.com.trainfinderdriver.ModelClasses;

public class Train
{
    private String Name;
    private int TID;

    public Train(int TID,String Name) {
        this.Name = Name;
        this.TID = TID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getTID() {
        return TID;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }

    public String toString()
    {
        return Name;
    }
}
