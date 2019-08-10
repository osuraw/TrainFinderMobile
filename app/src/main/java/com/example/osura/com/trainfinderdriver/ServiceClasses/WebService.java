package com.example.osura.com.trainfinderdriver.ServiceClasses;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebService {

    private static String path ="https://trainfinderapi.azurewebsites.net/api/";
    private static String tag ="TrainFinder_WebService";
    public static String DoGet(String path)
    {
        Log.i(tag,"DoGet");
        String returnData="";
        HttpURLConnection urlConnection=null;
        try {
            URL url =new URL(WebService.path+path);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream inputStream  = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=bufferedReader.readLine())!=null)
                returnData+=line;
        } catch (MalformedURLException e) {
            Log.e(tag,e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(tag,e.getMessage());
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return returnData;
    }
}
