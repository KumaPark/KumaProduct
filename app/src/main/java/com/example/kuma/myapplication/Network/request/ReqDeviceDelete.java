package com.example.kuma.myapplication.Network.request;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.CommonResponse;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;

/**
 * Created by Kuma on 2018-02-22.
 */

public class ReqDeviceDelete extends RequestJSON
{

    private int m_nTag;
    //	시리얼번호
    private String serialNo = "";

    private StringBuffer m_sbParameter;


    public ReqDeviceDelete()
    {

    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new CommonResponse();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_DEVICE_DELETE;
    }

    public void setTag(int nTag)
    {
        m_nTag = nTag;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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
