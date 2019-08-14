package com.example.osura.com.trainfinderdriver.ModelClasses;

public class TrainSyncDto {
    String Speed;
    String Name;
    String delay;
    String reqSpeed;
    String distance;

    public TrainSyncDto(String Speed, String Name, String delay, String reqSpeed, String distance) {
        this.Speed = Speed;
        this.Name = Name;
        this.delay = delay;
        this.reqSpeed = reqSpeed;
        this.distance = distance;
    }

    public String getSpeed() {
        return Speed;
    }

    public String getNextStopName() {
        return Name;
    }

    public String getDelay() {
        return delay;
    }

    public String getReqSpeed() {
        return reqSpeed;
    }

    public String getDistance() {
        return distance;
    }
}
