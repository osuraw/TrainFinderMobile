package com.example.osura.com.trainfinderdriver.ActivityClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.osura.com.trainfinderdriver.ModelClasses.TrainSyncDto;
import com.example.osura.com.trainfinderdriver.R;
import com.example.osura.com.trainfinderdriver.ServiceClasses.TrainService;

import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Status extends AppCompatActivity {

    private String tag = "TrainFinder_StatusActivity";

    private TrainService trainService;

    private String name;
    private int trainId;

    TextView lbl_Delay, lbl_Speed, lbl_NextStop, lbl_Distance, lbl_ReqSpeed, lbl_Name, lbl_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        trainId = intent.getIntExtra("trainId", 0);

        lbl_Delay = findViewById(R.id.txt_Delay);
        lbl_Speed = findViewById(R.id.txt_speed);
        lbl_NextStop = findViewById(R.id.txt_next_stop);
        lbl_Distance = findViewById(R.id.txt_distance);
        lbl_ReqSpeed = findViewById(R.id.txt_speed_req);
        lbl_Name = findViewById(R.id.txt_name);
        lbl_ID = findViewById(R.id.txt_ID);

        lbl_Name.setText(name);
        lbl_ID.setText(String.valueOf(trainId));

        trainService = new TrainService(this);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                trainService.GetLiveFeed(trainId);
            }
        }, 0, 5000);
    }

    public void UpdateUI(TrainSyncDto trainSyncDtos) {
        lbl_Speed.setText(trainSyncDtos.getSpeed());
        lbl_ReqSpeed.setText(trainSyncDtos.getReqSpeed() + " Km/h");
        lbl_NextStop.setText(trainSyncDtos.getNextStopName());
        lbl_Delay.setText(trainSyncDtos.getDelay());
        lbl_Distance.setText(trainSyncDtos.getDistance());
    }
}
