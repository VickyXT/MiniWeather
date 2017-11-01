package com.vickyxt.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.vickyxt.bean.City;

/**
 * Created by VickyXT on 2017/11/1.
 */

public class CityDB {
    public static final String CITY_DB_NAME = "city.db";
    private static final String CITY_TABLE_NAME = "city";
    private SQLiteDatabase db;

    public CityDB(Context context, String path){
        db = context.openOrCreateDatabase(path,context.MODE_PRIVATE,null);
    }

    public List<City> getAllCity(){
        List<City> list = new ArrayList<City>();
        Cursor c = db.rawQuery("SELECT * from" + CITY_TABLE_NAME,null);
        while (c.moveToNext()){
            String province = c.getString(c.getColumnIndex("province"));
            String city = c.getString(c.getColumnIndex("city"));
            String number = c.getString(c.getColumnIndex("number"));
            String allpy = c.getString(c.getColumnIndex("allpy"));
            String allfirstpy = c.getString(c.getColumnIndex("allfirstpy"));
            String firstpy = c.getString(c.getColumnIndex("firstpy"));
            City item = new City(province,city,number,firstpy,allpy,allfirstpy);
            list.add(item);
        }
        return list;
    }
}
