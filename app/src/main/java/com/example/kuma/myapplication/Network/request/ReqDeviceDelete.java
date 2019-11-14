package com.simens.us.myapplication.Network.request;

import android.content.Context;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.CommonResponse;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;
import com.simens.us.myapplication.Utils.SharedPref.ShareDataManager;
import com.simens.us.myapplication.Utils.SharedPref.SharedPref;

/**
 * Created by Kuma on 2018-02-22.
 */

public class ReqDeviceDelete extends RequestJSON
{

    private int m_nTag;
    //	시리얼번호
    private String serialNo = "";

    private StringBuffer m_sbParameter;

    private  Context mContext;
    public ReqDeviceDelete(Context context)
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
        return ProtocolDefines.UrlConstance.URL_DEVICE_DELETE;
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
