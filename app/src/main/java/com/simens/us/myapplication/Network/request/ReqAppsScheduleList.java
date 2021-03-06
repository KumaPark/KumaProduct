package com.simens.us.myapplication.Network.request;

import android.content.Context;
import android.text.TextUtils;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.ResAppsScheduleList;
import com.simens.us.myapplication.Network.response.ResDeviceScheduleList;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;

public class ReqAppsScheduleList extends RequestJSON {

    private int m_nTag;

    private StringBuffer m_sbParameter;

    private Context mContext;

    private String month = "";

    public ReqAppsScheduleList(Context context) {
        mContext = context;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    protected ResponseProtocol createResponseProtocol() {
        return new ResAppsScheduleList();
    }

    @Override
    public String getURL() {
        return ProtocolDefines.UrlConstance.URL_MEMBER_SCHEDULE;
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

        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext)).append("&");
        m_sbParameter.append("month").append("=").append(month);

        return m_sbParameter;
    }
}