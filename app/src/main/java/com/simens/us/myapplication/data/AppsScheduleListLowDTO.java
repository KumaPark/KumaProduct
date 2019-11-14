package com.simens.us.myapplication.data;

import java.io.Serializable;
import java.util.ArrayList;

public class AppsScheduleListLowDTO implements Serializable {
    public int weekLow = 0;
    private ArrayList<AppsScheduleListDayDTO> mArrScheduleListDayDTO = new ArrayList<>();

    public ArrayList<AppsScheduleListDayDTO> getmArrScheduleListDayDTO() {
        return mArrScheduleListDayDTO;
    }

    public void setWeekLow(int weekLow) {
        this.weekLow = weekLow;
    }
}
