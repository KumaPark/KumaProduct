package com.example.kuma.myapplication.Network.request;

import android.content.Context;
import android.text.TextUtils;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.CommonResponse;
import com.example.kuma.myapplication.Network.response.ResAgencyList;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.Utils.DeviceUtils;

public class ReqAgencyList extends RequestJSON
{

    private int m_nTag;
    private StringBuffer m_sbParameter;

    private Context mContext;
    public ReqAgencyList(Context context)
    {
        mContext = context;
    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResAgencyList();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_AGENCY_SEARCH;
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
        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));

        return m_sbParameter;
    }
}