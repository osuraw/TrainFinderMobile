package com.example.osura.com.trainfinderdriver.ServiceClasses.Helper;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AccessStorage {
    
    private static OutputStreamWriter writeToFile;
    private static FileOutputStream file;
    private static String tag ="TrainFinder_AccessStorage";
    
    public static void getPublicAlbumStorageDir(String fileName) {
        System.out.println(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()));
        String path = Environment.getExternalStorageDirectory().getAbsoluteFile()+"/data1";
        File dir =new File(path);
        if(!dir.exists())
            dir.mkdirs();
        try {
            File writeToFile = new File(dir.getPath()+fileName);
            file = new FileOutputStream(writeToFile);
            AccessStorage.writeToFile = new OutputStreamWriter(file);
            AccessStorage.writeToFile.write("");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(tag,e.getMessage());
        }
    }

    public static void Write(String data) {
        try {
            writeToFile.append("{\"FIELD1\":\""+data+"\"},\n");
            writeToFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(tag,e.getMessage());
        }
    }
}
