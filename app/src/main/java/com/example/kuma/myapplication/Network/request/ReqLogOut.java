package com.example.kuma.myapplication.Network.request;

import android.content.Context;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.CommonResponse;
import com.example.kuma.myapplication.Network.response.ResLogin;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.Utils.DeviceUtils;

public class ReqLogOut extends RequestJSON
{

    private int m_nTag;
    private StringBuffer m_sbParameter;
    private Context mContext;

    public ReqLogOut(Context context)
    {
        mContext = context;
    }
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
        return ProtocolDefines.UrlConstance.URL_LOGOUT;
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
