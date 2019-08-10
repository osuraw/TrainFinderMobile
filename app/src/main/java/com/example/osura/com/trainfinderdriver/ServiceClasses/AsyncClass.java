package com.example.osura.com.trainfinderdriver.ServiceClasses;

import android.os.AsyncTask;
import android.util.Log;


public class AsyncClass extends AsyncTask<String,String,String>{

    public AsynkReturn asyncReturnDelegate = null;
    private String tag ="TrainFinder_AsyncTaskService";

    @Override
    protected String doInBackground(String... strings) {
        Log.i(tag,"doInBackground");
        return WebService.DoGet(strings[0]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(tag,"onPreExecute");
    }

    @Override
    protected void onPostExecute(String data) {
        Log.i(tag,"onPostExecute");
        asyncReturnDelegate.PassData(data);
    }
}
