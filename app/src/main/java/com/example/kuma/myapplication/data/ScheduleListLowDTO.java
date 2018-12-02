package com.example.kuma.myapplication.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kuma on 2018-03-04.
 */

public class ScheduleListLowDTO implements Serializable {
    public int weekLow = 0;
    private ArrayList<ScheduleListDayDTO> mArrScheduleListDayDTO = new ArrayList<>();

    public ArrayList<ScheduleListDayDTO> getmArrScheduleListDayDTO() {
        return mArrScheduleListDayDTO;
    }

    public void setWeekLow(int weekLow) {
        this.weekLow = weekLow;
    }
}
