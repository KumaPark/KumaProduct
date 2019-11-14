package com.simens.us.myapplication.Network.request;

import android.content.Context;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.ResDestinationList;
import com.simens.us.myapplication.Network.response.ResDeviceAbleList;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;

public class ReqDeviceAbleList extends RequestJSON
{

    private int m_nTag;
    //	장비모델 및 OS (장비모델 및 OS버젼)
    private String osPk = "";
    //	제품종류 (M : 장비, P : 프로브 , A : 악세사리)
    private String kind = "";
    //	데모시작예정일
    private String startDate = "";
    //	데모종료예정일
    private String endDate = "";

    private StringBuffer m_sbParameter;


    private Context mContext;

    public ReqDeviceAbleList(Context context)
    {
        mContext = context;
    }

    public void setOsPk(String osPk) {
        this.osPk = osPk;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResDeviceAbleList();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_DEVICE_SCHEDULE_ABLE_LIST;
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
        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext)).append("&");
        m_sbParameter.append("osPk").append("=").append(osPk).append("&");
        m_sbParameter.append("kind").append("=").append(kind).append("&");
        m_sbParameter.append("startDate").append("=").append(startDate).append("&");
        m_sbParameter.append("endDate").append("=").append(endDate);

        return m_sbParameter;
    }
}
