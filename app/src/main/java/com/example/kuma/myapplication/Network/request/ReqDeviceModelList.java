package com.example.kuma.myapplication.Network.request;

import android.content.Context;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.ResAppsScheduleList;
import com.example.kuma.myapplication.Network.response.ResDeviceModelList;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.Utils.DeviceUtils;

public class ReqDeviceModelList extends RequestJSON {

    private int m_nTag;

    private StringBuffer m_sbParameter;

    private Context mContext;

    private String kind = "";

    public ReqDeviceModelList(Context context) {
        mContext = context;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    protected ResponseProtocol createResponseProtocol() {
        return new ResDeviceModelList();
    }

    @Override
    public String getURL() {
        return ProtocolDefines.UrlConstance.URL_DEVICE_MODEL_LIST;
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
        m_sbParameter.append("kind").append("=").append(kind);

        return m_sbParameter;
    }
}
