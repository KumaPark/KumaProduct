package com.example.kuma.myapplication.Network.request;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.ResDeviceScheduleList;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;

/**
 * Created by Kuma on 2018-03-03.
 * 데모정보 조회 리스트
 */

public class ReqDeviceScheduleList extends RequestJSON
{

    private int m_nTag;

    private StringBuffer m_sbParameter;


    public ReqDeviceScheduleList()
    {

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

        return m_sbParameter;
    }
}
