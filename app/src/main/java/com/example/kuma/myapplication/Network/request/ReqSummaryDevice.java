package com.example.kuma.myapplication.Network.request;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.ResMainDeviceList;
import com.example.kuma.myapplication.Network.response.ResSummaryDevice;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;

/**
 * Created by Kuma on 2018-12-12.
 */

public class ReqSummaryDevice extends RequestJSON
{

    private int m_nTag;
    private StringBuffer m_sbParameter;

    public ReqSummaryDevice()
    {

    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResSummaryDevice();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_SUMMARY_DEVICE;
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
