package com.vickyxt.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.vickyxt.app.MyApplication;
import com.vickyxt.bean.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by VickyXT on 2017/10/18.
 */

public class SelectCity extends Activity implements View.OnClickListener{

    private ImageView mBackBtn;
    private ListView mListView;
    private List<City> mCityList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_city);


        initCityView();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_back:

                break;
            default:
                break;
        }
    }

    private void initCityView(){

        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.city_list);
        MyApplication myApplication = (MyApplication) getApplication();
        mCityList = myApplication.getCityList();

        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();


        for (City city : mCityList){
            Map<String,Object> item = new HashMap<String, Object>();
            item.put("city_name", city.getCity());
            item.put("city_number", city.getNumber());
            data.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.city_item,
                new String[] {"city_name", "city_number"},
                new int[] {R.id.city_name, R.id.city_number});

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView)parent;
                Map<String,Object> map = (Map<String, Object>) listView.getItemAtPosition(position);
                Intent i = new Intent();
                String num = (String) map.get("city_number");
                i.putExtra("cityCode",num);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }
}
