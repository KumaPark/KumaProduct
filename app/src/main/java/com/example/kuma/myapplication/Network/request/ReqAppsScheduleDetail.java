package com.example.kuma.myapplication.Network.request;

import android.content.Context;
import android.text.TextUtils;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.ResAppsScheduleDetail;
import com.example.kuma.myapplication.Network.response.ResAppsScheduleList;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.Utils.DeviceUtils;

public class ReqAppsScheduleDetail extends RequestJSON {

    private int m_nTag;

    private StringBuffer m_sbParameter;

    private Context mContext;

    private String pk = "";

    public ReqAppsScheduleDetail(Context context) {
        mContext = context;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @Override
    protected ResponseProtocol createResponseProtocol() {
        return new ResAppsScheduleDetail();
    }

    @Override
    public String getURL() {
        return ProtocolDefines.UrlConstance.URL_MEMBER_SCHEDULE_DETAIL;
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
        m_sbParameter.append("pk").append("=").append(pk);

        return m_sbParameter;
    }
}
