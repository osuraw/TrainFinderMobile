package com.example.osura.com.trainfinderdriver.ServiceClasses;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncClass extends AsyncTask<String,String,String>{

    private String tag ="TrainFinder_AsyncTaskService";
    private String fromTag;

    public void setFromTag(String fromTag) {
        this.fromTag = fromTag;
    }
    private AsynkReturn asyncReturnDelegate;

    public void setAsyncReturnDelegate(AsynkReturn asyncReturnDelegate) {
        this.asyncReturnDelegate = asyncReturnDelegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response;
        Log.i(tag,"doInBackground");
        response = WebService.DoGet(strings[0]);
        return response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(tag,"onPreExecute");
    }

    @Override
    protected void onPostExecute(String data) {
        Log.i(tag,"onPostExecute");
        asyncReturnDelegate.PassData(data,fromTag);
    }
}
