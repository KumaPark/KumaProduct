package com.example.kuma.myapplication.Network.request;

import android.content.Context;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.ResDeviceInfo;
import com.example.kuma.myapplication.Network.response.ResMainDeviceList;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.Utils.DeviceUtils;

/**
 * Created by Kuma on 2018-02-22.
 * 제품조회 상세
 */

public class ReqDeviceInfo extends RequestJSON
{

    private int m_nTag;
    //	시리얼번호
    private String serialNo = "";

    private StringBuffer m_sbParameter;

    private Context mContext;

    public ReqDeviceInfo(Context context)
    {
        mContext = context;

    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResDeviceInfo();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_DEVICE_INFO;
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

        m_sbParameter.append("serialNo").append("=").append(serialNo).append("&");
        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));

        return m_sbParameter;
    }
}
