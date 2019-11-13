package com.example.kuma.myapplication.Network.request;

import android.content.Context;
import android.text.TextUtils;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.ResAgencyList;
import com.example.kuma.myapplication.Network.response.ResDestinationList;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.Utils.DeviceUtils;

public class ReqDestinationList extends RequestJSON
{

    private int m_nTag;
    //	대리점고유번호 (0인 경우 본사)
    private String pk = "";

    private StringBuffer m_sbParameter;

    private Context mContext;
    public ReqDestinationList(Context context)
    {
        mContext = context;
    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResDestinationList();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_DESTNATION_LIST;
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