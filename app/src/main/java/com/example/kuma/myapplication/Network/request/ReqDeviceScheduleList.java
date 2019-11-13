package com.example.kuma.myapplication.Network.request;

import android.content.Context;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.ResDeviceScheduleList;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.Utils.DeviceUtils;

/**
 * Created by Kuma on 2018-03-03.
 * 데모정보 조회 리스트
 */

public class ReqDeviceScheduleList extends RequestJSON
{

    private int m_nTag;

    private StringBuffer m_sbParameter;

    private Context mContext;

    private String month = "";



    public ReqDeviceScheduleList(Context context)
    {
        mContext = context;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResDeviceScheduleList();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_DEVICE_SCHEDULE_LIST;
    }

    public void setTag(int nTag)
    {
        m_nTag = nTag;
    }

    @Override
    public Integer getTAG() {
        // TODO Auto-generated method stub
        return m_nTag;
    }
    @Override
    public StringBuffer getJson() {

        m_sbParameter = new StringBuffer();

        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext)).append("&");
        m_sbParameter.append("month").append("=").append(month);
        return m_sbParameter;
    }
}
