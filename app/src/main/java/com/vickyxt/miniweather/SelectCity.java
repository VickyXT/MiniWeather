package com.vickyxt.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import java.util.Collections;
import java.util.Comparator;
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
    private ClearEditText mClearEditText;
    private List<City> mCityList;
    private List<City> filterDataList;
    private SimpleAdapter adapter;
    private ArrayList<Map<String,Object>> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_city);
        initCityView();
        initClearEditText();
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

        mData = converter(mCityList);

        updateListView();
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

    private void initClearEditText(){
        mClearEditText = (ClearEditText)findViewById(R.id.search_city);
        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //当输入框内值为空，更新为原来列表，否则为过滤数据列表
                filterData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void updateListView() {
        adapter = new SimpleAdapter(this, mData, R.layout.city_item,
                new String[] {"city_name", "city_number"},
                new int[] {R.id.city_name, R.id.city_number});

        mListView.setAdapter(adapter);
    }

    // 数据格式转换 ArrayList<City> -> ArrayList<Map<String,Object>>
    private ArrayList<Map<String,Object>> converter(List<City> cityList) {
        ArrayList<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        for (City city : cityList){
            Map<String,Object> item = new HashMap<String, Object>();
            item.put("city_name", city.getCity());
            item.put("city_number", city.getNumber());
            data.add(item);
        }
        return data;
    }


    //根据输入框中的值过滤数据并更新mListView
    private void filterData(String filterStr){
        filterDataList = new ArrayList<City>();
        Log.d("Filter",filterStr);

        if (TextUtils.isEmpty(filterStr)){
            // 为空，保持原数据状态
            filterDataList = mCityList;
        }
        else {
            // 有内容
            filterDataList.clear();
            for (City city: mCityList){
                if (city.getCity().indexOf(filterStr.toString()) != -1){
                    filterDataList.add(city);
                }
            }
        }
        //根据a-z排序
//        Collections.sort(filterDataList, pinyinComparator);
        mData = converter(filterDataList);
        updateListView();
    }
}
