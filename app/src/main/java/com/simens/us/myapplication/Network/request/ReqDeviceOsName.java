package com.simens.us.myapplication.Network.request;

import android.content.Context;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.ResDeviceModelList;
import com.simens.us.myapplication.Network.response.ResDeviceOsName;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;

public class ReqDeviceOsName extends RequestJSON {

    private int m_nTag;

    private StringBuffer m_sbParameter;

    private Context mContext;

    public ReqDeviceOsName(Context context) {
        mContext = context;
    }

    @Override
    protected ResponseProtocol createResponseProtocol() {
        return new ResDeviceOsName();
    }

    @Override
    public String getURL() {
        return ProtocolDefines.UrlConstance.URL_DEVICE_OS_LIST;
    }

    public void setTag(int nTag) {
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
