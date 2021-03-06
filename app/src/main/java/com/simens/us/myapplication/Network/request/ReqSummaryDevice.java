package com.simens.us.myapplication.Network.request;

import android.content.Context;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.ResMainDeviceList;
import com.simens.us.myapplication.Network.response.ResSummaryDevice;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;

/**
 * Created by Kuma on 2018-12-12.
 */

public class ReqSummaryDevice extends RequestJSON
{

    private int m_nTag;
    private StringBuffer m_sbParameter;

    private Context mContext;

    public ReqSummaryDevice(Context context)
    {
        mContext = context;
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

        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));
        return m_sbParameter;
    }
}
