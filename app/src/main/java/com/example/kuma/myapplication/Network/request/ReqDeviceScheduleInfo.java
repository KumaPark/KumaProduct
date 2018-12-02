package com.example.kuma.myapplication.Network.request;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.ResDeviceScheduleInfo;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;

/**
 * Created by Kuma on 2018-03-03.
 */

public class ReqDeviceScheduleInfo extends RequestJSON
{

    private int m_nTag;

    private StringBuffer m_sbParameter;

    private String serialNo = "";

    public ReqDeviceScheduleInfo()
    {

    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResDeviceScheduleInfo();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_DEVICE_SCHEDULE_INFO;
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

        m_sbParameter.append("serialNo").append("=").append(serialNo);

        return m_sbParameter;
    }
}
