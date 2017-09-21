package com.vickyxt.miniweather;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by VickyXT on 2017/9/21.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.weather_info);
    }
}
