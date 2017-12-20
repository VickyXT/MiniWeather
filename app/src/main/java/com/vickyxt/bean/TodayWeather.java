package com.vickyxt.bean;

/**
 * Created by VickyXT on 2017/10/16.
 */

public class TodayWeather {
    private String city;
    private String updatetime;
    private String wendu;
    private String shidu;
    private String pm25;
    private String quality;
    private String fengxiang;
    private String fengli;
    private String date;
    private String high;
    private String low;
    private String type;

    private String date1;
    private String low1;
    private String high1;
    private String fengli1;
    private String type1;

    public TodayWeather() {
    }

    public String getCity() {
        return city;
    }

    public String getPm25() {
        return pm25;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public String getDate() {
        return date;
    }

    public String getFengli() {
        return fengli;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getQuality() {
        return quality;
    }

    public String getShidu() {
        return shidu;
    }

    public String getType() {
        return type;
    }

    public String getWendu() {
        return wendu;
    }

    public String getDate1(){
        return date1;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate1(String date1) { this.date1 = date1;}

    @Override
    public String toString(){
        return "TodayWeather{"+
                "city=" + city + '\'' +
                ", updatetime=" + updatetime + '\'' +
                ", wendu=" + wendu + '\'' +
                ", shidu=" + shidu + '\'' +
                ", pm25=" + pm25 + '\'' +
                ", quality=" + quality + '\'' +
                ", fengli=" + fengli + '\'' +
                ", fengxiang=" + fengxiang + '\'' +
                ", date=" + date + '\'' +
                ", high=" + high + '\'' +
                ", low=" + low + '\'' +
                ", type=" + type + '\'' +
                ", date1=" + date1 + '\'' +
                '}';
    }
}
