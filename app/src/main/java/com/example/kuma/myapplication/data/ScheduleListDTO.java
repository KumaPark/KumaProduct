package com.example.kuma.myapplication.data;

import com.example.kuma.myapplication.adapter.DeviceScheduleListAdapter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kuma on 2018-03-04.
 */

public class ScheduleListDTO implements Serializable {
    private ArrayList<ScheduleListDayDTO> mArrScheduleListDayDTO = new ArrayList<>();
    public int viewType = DeviceScheduleListAdapter.VIEW_TYPE_DATE;
    public String deviceName = "";
    public String serialNo = "";
    public int lowNumber = 0;

    public int nCompanyCnt = 0;
    public int nInvalbCnt = 0;
    public int nMoveCnt = 0;


    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getLowNumber() {
        return lowNumber;
    }

    public void setLowNumber(int lowNumber) {
        this.lowNumber = lowNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getnCompanyCnt() {
        return nCompanyCnt;
    }

    public void setnCompanyCnt(int nCompanyCnt) {
        this.nCompanyCnt = nCompanyCnt;
    }

    public int getnInvalbCnt() {
        return nInvalbCnt;
    }

    public void setnInvalbCnt(int nInvalbCnt) {
        this.nInvalbCnt = nInvalbCnt;
    }

    public int getnMoveCnt() {
        return nMoveCnt;
    }

    public void setnMoveCnt(int nMoveCnt) {
        this.nMoveCnt = nMoveCnt;
    }

    public ArrayList<ScheduleListDayDTO> getmArrScheduleListDayDTO() {
        return mArrScheduleListDayDTO;
    }
}
