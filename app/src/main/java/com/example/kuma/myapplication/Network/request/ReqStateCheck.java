package com.simens.us.myapplication.Network.request;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.ResStateCheck;
import com.simens.us.myapplication.Network.response.ResVersion;
import com.simens.us.myapplication.Network.response.ResponseProtocol;

public class ReqStateCheck extends RequestJSON
{

    private int m_nTag;
    private StringBuffer m_sbParameter;

    private String macAddr;

    public ReqStateCheck()
    {

    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResStateCheck();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_STATE_CHECK;
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
        m_sbParameter.append("macAddr").append("=").append(macAddr);
        return m_sbParameter;
    }
}
