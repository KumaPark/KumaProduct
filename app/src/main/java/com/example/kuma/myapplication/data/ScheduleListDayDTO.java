package com.example.kuma.myapplication.data;

import java.io.Serializable;

/**
 * Created by Kuma on 2018-03-04.
 */

public class ScheduleListDayDTO implements Serializable {
    public String year = "";
    public String month = "";
    public String day = "";
    public String diplayDay = "";

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
}
