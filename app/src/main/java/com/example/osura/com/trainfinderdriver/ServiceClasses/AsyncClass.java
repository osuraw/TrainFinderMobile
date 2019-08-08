package com.example.osura.com.trainfinderdriver.ServiceClasses;

import android.os.AsyncTask;


public class AsyncClass extends AsyncTask<String,String,String>{

    public AsynkReturn asyncReturnDelegate = null;
    @Override
    protected String doInBackground(String... strings) {
        return WebService.DoGet(strings[0]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        p = new ProgressDialog(MainActivity.this);
//        p.setMessage("Please wait...It is downloading");
//        p.setIndeterminate(false);
//        p.setCancelable(false);
//        p.show();
    }

    @Override
    protected void onPostExecute(String data) {
        asyncReturnDelegate.PassData(data);
    }
}
