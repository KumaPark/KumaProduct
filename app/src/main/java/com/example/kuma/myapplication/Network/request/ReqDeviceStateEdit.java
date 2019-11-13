package com.example.kuma.myapplication.Network.request;

import android.content.Context;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.CommonResponse;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.Utils.DeviceUtils;

public class ReqDeviceStateEdit extends RequestJSON
{

    private int m_nTag;
    //	데모고유번호
    private String pk = "";
    //	변경할 상태
    private String state = "";


    private StringBuffer m_sbParameter;

    private Context mContext;
    public ReqDeviceStateEdit(Context context)
    {
        mContext = context;
    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new CommonResponse();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_DEVICE_SCHEDULE_STATE_EDIT;
    }

    public void setTag(int nTag)
    {
        m_nTag = nTag;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public Integer getTAG() {
        // TODO Auto-generated method stub
        return m_nTag;
    }
    @Override
    public StringBuffer getJson() {

        m_sbParameter = new StringBuffer();

        m_sbParameter.append("pk").append("=").append(pk).append("&");
        m_sbParameter.append("state").append("=").append(state).append("&");
        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));

        return m_sbParameter;
    }
}
