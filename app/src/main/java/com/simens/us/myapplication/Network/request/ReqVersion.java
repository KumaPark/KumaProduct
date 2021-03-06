package com.simens.us.myapplication.Network.request;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.ResSummaryDevice;
import com.simens.us.myapplication.Network.response.ResVersion;
import com.simens.us.myapplication.Network.response.ResponseProtocol;

public class ReqVersion extends RequestJSON
{

    private int m_nTag;
    private StringBuffer m_sbParameter;

    public ReqVersion()
    {

    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResVersion();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_VERSION;
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
