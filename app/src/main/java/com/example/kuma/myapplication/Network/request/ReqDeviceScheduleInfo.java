package com.simens.us.myapplication.Network.request;

import android.content.Context;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.ResDeviceScheduleInfo;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;

/**
 * Created by Kuma on 2018-03-03.
 */

public class ReqDeviceScheduleInfo extends RequestJSON
{

    private int m_nTag;

    private StringBuffer m_sbParameter;

    private String pk = "";

    private Context mContext;

    public ReqDeviceScheduleInfo(Context context)
    {
        mContext = context;
    }

    public void setPk(String serialNo) {
        this.pk = serialNo;
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

        m_sbParameter.append("pk").append("=").append(pk).append("&");
        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));

        return m_sbParameter;
    }
}
