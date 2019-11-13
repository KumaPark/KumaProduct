package com.example.kuma.myapplication.data;

import java.io.Serializable;
import java.util.ArrayList;

public class AppsScheduleListDayDTO implements Serializable {
    public String year = "";
    public String month = "";
    public String day = "";
    public String diplayDay = "";
    public int dayNum =  0;
    private ArrayList<AppsScheduleInfo> arrScheduleInfo = new ArrayList<>();

    public ArrayList<AppsScheduleInfo> getScheduleInfoList() {
        return arrScheduleInfo;
    }

    public void setScheduleInfoList(AppsScheduleInfo info) {
        this.arrScheduleInfo.add(info);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDiplayDay(String diplayDay) {
        this.diplayDay = diplayDay;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }
}
