package com.vickyxt.app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.vickyxt.db.CityDB;

import java.io.File;

/**
 * Created by VickyXT on 2017/11/1.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyAPP";

    private static MyApplication myApplication;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG,"MyApplication->Oncreate");

        myApplication = this;
    }

    public static MyApplication getInstance(){
        return myApplication;
    }

    private CityDB openCityDB(){
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + getPackageName()
                + File.separator + "database1"
                + File.separator
                +CityDB.CITY_DB_NAME;
        File db = new File(path);
        Log.d(TAG,path);
    }
}
