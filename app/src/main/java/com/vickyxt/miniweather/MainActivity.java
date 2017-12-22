package com.vickyxt.miniweather;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.vickyxt.bean.TodayWeather;
import com.vickyxt.util.NetUtil;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by VickyXT on 2017/9/21.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private static final int SHOW_ERROR = 0;
    private static final int UPDATE_TODAY_WEATHER = 1;
    private ImageView mUpdateBtn;
    private ProgressBar updateProgress;

    private ImageView mCitySelect;

    private TextView cityTv,timeTv,humidityTv,weekTv,pmDataTv,pmQualityTv,temperatureTv,climateTv,windTv,cityNameTv,temperatureNowTv;
    private ArrayList<HashMap<String, TextView>> forecastTvArr;
    private ImageView weatherImg,pmImg;

    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather)msg.obj);
                    break;
                case SHOW_ERROR:
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    void initView(){

        cityNameTv = (TextView) findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        humidityTv = (TextView) findViewById(R.id.humidity);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);
        temperatureNowTv = (TextView) findViewById(R.id.temperature_now);
        forecastTvArr = new ArrayList<HashMap<String, TextView>>();
        for (int i = 0; i < 4; i++) {
            HashMap<String, TextView> item = new HashMap<String, TextView>();
            forecastTvArr.add(item);
        }
        forecastTvArr.get(0).put("date", (TextView)findViewById(R.id.date1));
        forecastTvArr.get(0).put("temp", (TextView)findViewById(R.id.temp1));
        forecastTvArr.get(0).put("type", (TextView)findViewById(R.id.cli1));
        forecastTvArr.get(0).put("fengli", (TextView)findViewById(R.id.feng1));
        forecastTvArr.get(1).put("date", (TextView)findViewById(R.id.date2));
        forecastTvArr.get(1).put("temp", (TextView)findViewById(R.id.temp2));
        forecastTvArr.get(1).put("type", (TextView)findViewById(R.id.cli2));
        forecastTvArr.get(1).put("fengli", (TextView)findViewById(R.id.feng2));
        forecastTvArr.get(2).put("date", (TextView)findViewById(R.id.date3));
        forecastTvArr.get(2).put("temp", (TextView)findViewById(R.id.temp3));
        forecastTvArr.get(2).put("type", (TextView)findViewById(R.id.cli3));
        forecastTvArr.get(2).put("fengli", (TextView)findViewById(R.id.feng3));
        forecastTvArr.get(3).put("date", (TextView)findViewById(R.id.date4));
        forecastTvArr.get(3).put("temp", (TextView)findViewById(R.id.temp4));
        forecastTvArr.get(3).put("type", (TextView)findViewById(R.id.cli4));
        forecastTvArr.get(3).put("fengli", (TextView)findViewById(R.id.feng4));
        updateView();
    }

    private void updateView(){
        SharedPreferences sharedPreferences = (SharedPreferences)getSharedPreferences("config",MODE_PRIVATE);
        String cityCode = sharedPreferences.getString("main_city_code","101010100");


        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE){
            Log.d("myWeather","网络ok");
            queryWeather(cityCode);
        }
        else{
            Log.d("myWeather","网络挂了");
            Toast.makeText(MainActivity.this,"网络挂了",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.weather_info);

        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);

        updateProgress = (ProgressBar)findViewById(R.id.title_update_progress);
        updateProgress.setOnClickListener(this);

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE){
            Log.d("myWeather","网络ok");
            Toast.makeText(MainActivity.this,"网络ok！",Toast.LENGTH_LONG).show();
        }
        else{
            Log.d("myWeather","网络挂了");
            Toast.makeText(MainActivity.this,"网络挂了",Toast.LENGTH_LONG).show();
        }
        mCitySelect = (ImageView)findViewById(R.id.title_city_manager);
        mCitySelect.setOnClickListener(this);

        initView();
    }
    /**
     *
     * @param cityCode
     */
    private void queryWeather(String cityCode){
        final String TAG = "Method";
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        try {
            String url = "http://zhwnlapi.etouch.cn/Ecalender/api/v2/weather?citykey=";
            url += cityCode;
            Log.d("Method", url);

            OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
            OkHttpClient client = mBuilder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.d(TAG,e.toString());
                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // 注：该回调是子线程，非主线程
                    Log.d(TAG,"callback thread id is "+Thread.currentThread().getId());
                    TodayWeather todayWeather = new TodayWeather();
                    String json = response.body().string();
                    Log.d(TAG,json);
                    JSONObject map = com.alibaba.fastjson.JSON.parseObject(json, new TypeReference<JSONObject>() {});
                    JSONObject meta = map.getJSONObject("meta");
                    int status = meta.getInteger("status");
                    if (status == 1000) {
                        JSONObject evn = map.getJSONObject("evn");
                        JSONObject observe = map.getJSONObject("observe");
                        JSONArray forecast = map.getJSONArray("forecast");

                        todayWeather.setCity(meta.getString("city"));
                        todayWeather.setUpdatetime(meta.getString("up_time"));
                        todayWeather.setWendu(observe.getString("temp"));
                        todayWeather.setShidu(observe.getString("shidu"));
                        todayWeather.setPm25(evn.getString("pm25"));
                        todayWeather.setQuality(evn.getString("quality"));
                        todayWeather.setFengxiang(observe.getString("wd"));

                        ArrayList<HashMap<String, String>> arr = new ArrayList<HashMap<String, String>>();
                        int i = 0;
                        for (Object data: forecast) {
                            data = (JSONObject) data;
                            String date = ((JSONObject) data).getString("date");
                            String high = ((JSONObject) data).getString("high");
                            String low = ((JSONObject) data).getString("low");
                            JSONObject day = ((JSONObject) data).getJSONObject("day");
                            String wp = day.getString("wp");
                            String wthr = day.getString("wthr");
                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("date", getDayOfWeek(date));
                            item.put("high", high);
                            item.put("low", low);
                            item.put("fengli", wp);
                            item.put("type", wthr);
                            arr.add(item);
                            if (i == 1) {
                                todayWeather.setDate(getDayOfWeek(date));
                                todayWeather.setHigh(high);
                                todayWeather.setLow(low);
                                todayWeather.setFengli(wp);
                                todayWeather.setType(wthr);
                            }
                            i++;
                        }
                        todayWeather.setForecast(arr);

                        Message msg = new Message();
                        msg.what = UPDATE_TODAY_WEATHER;
                        msg.obj = todayWeather;
                        mHandler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = SHOW_ERROR;
                        msg.obj = "请求城市错误";
                        mHandler.sendMessage(msg);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDayOfWeek(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String week = "星期";
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                week += "天";
                break;
            case 2:
                week += "一";
                break;
            case 3:
                week += "二";
                break;
            case 4:
                week += "三";
                break;
            case 5:
                week += "四";
                break;
            case 6:
                week += "五";
                break;
            case 7:
                week += "六";
                break;
            default:
                break;
        }
        return week;
    }

    @Override
    public void onClick(View view){

        if(view.getId() == R.id.title_city_manager){
            Intent i = new Intent(this, SelectCity.class);
            //startActivity(i);
            startActivityForResult(i,1);
        }

        if (view.getId() == R.id.title_update_btn){

            updateProgress.setVisibility(View.VISIBLE);
            mUpdateBtn.setVisibility(View.INVISIBLE);
            updateView();
        }

        if (view.getId() == R.id.title_update_progress){

            updateProgress.setVisibility(View.INVISIBLE);
            mUpdateBtn.setVisibility(View.VISIBLE);
            updateView();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1 && resultCode == RESULT_OK){
            String newCityCode = data.getStringExtra("cityCode");
            Log.d("myWeather","选择的城市代码为"+newCityCode);
            SharedPreferences sharedPreferences = (SharedPreferences) getSharedPreferences("config",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("main_city_code",newCityCode);
            editor.commit();

            updateView();
        }
    }

    void updateTodayWeather(TodayWeather todayWeather){
        cityNameTv.setText(todayWeather.getCity()+"天气");
        cityTv.setText(todayWeather.getCity());
        timeTv.setText(todayWeather.getUpdatetime()+"发布");
        humidityTv.setText("湿度:"+ todayWeather.getShidu());
        pmDataTv.setText(todayWeather.getPm25());
        pmQualityTv.setText(todayWeather.getQuality());
        weekTv.setText(todayWeather.getDate());
        temperatureTv.setText(todayWeather.getLow()+"~"+todayWeather.getHigh());
        climateTv.setText(todayWeather.getType());
        windTv.setText("风力:"+ todayWeather.getFengli());
        for (int i = 0; i < 4; i++) {
            forecastTvArr.get(i).get("date").setText(todayWeather.getForecast().get(i).get("date"));
            forecastTvArr.get(i).get("temp").setText(todayWeather.getForecast().get(i).get("low")
                    + "~" + todayWeather.getForecast().get(i).get("high"));
            forecastTvArr.get(i).get("fengli").setText(todayWeather.getForecast().get(i).get("fengli"));
            forecastTvArr.get(i).get("type").setText(todayWeather.getForecast().get(i).get("type"));
        }
        Toast.makeText(MainActivity.this,"更新成功！",Toast.LENGTH_SHORT).show();
        updateProgress.setVisibility(View.INVISIBLE);
        mUpdateBtn.setVisibility(View.VISIBLE);


//      更新pm25图片
        int pm25 = Integer.parseInt(todayWeather.getPm25());
        if (pm25 <= 50){
            pmImg.setImageResource(R.drawable.biz_plugin_weather_0_50);
        }else if (pm25 <= 100 && pm25 >50){
            pmImg.setImageResource(R.drawable.biz_plugin_weather_51_100);
        }else if (pm25 <= 150 && pm25 >100){
            pmImg.setImageResource(R.drawable.biz_plugin_weather_101_150);
        }else if (pm25 <= 200 && pm25 >150){
            pmImg.setImageResource(R.drawable.biz_plugin_weather_151_200);
        }else if (pm25 <= 300 && pm25 >200){
            pmImg.setImageResource(R.drawable.biz_plugin_weather_201_300);
        }else if (pm25 >300){
            pmImg.setImageResource(R.drawable.biz_plugin_weather_greater_300);
        }
    }

}
