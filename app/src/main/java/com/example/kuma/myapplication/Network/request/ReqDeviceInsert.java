package com.simens.us.myapplication.Network.request;

import android.content.Context;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.CommonResponse;
import com.simens.us.myapplication.Network.response.ResLogin;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;

/**
 * Created by Kuma on 2018-02-22.
 * 제품등록
 */

public class ReqDeviceInsert extends RequestJSON
{

    private int m_nTag;
    //	시리얼번호
    private String serialNo = "";
    //	장비모델
    private String kind = "";
    //	제조년
    private String makeDate = "";
    //	OS버젼
    private String osPk = "";
    //	비고
    private String modelPk = "";


    private StringBuffer m_sbParameter;

    private Context mContext;
    public ReqDeviceInsert(Context context)
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
        return ProtocolDefines.UrlConstance.URL_DEVICE_INSERT;
    }

    public void setTag(int nTag)
    {
        m_nTag = nTag;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public void setOsPk(String osPk) {
        this.osPk = osPk;
    }

    public void setModelPk(String modelPk) {
        this.modelPk = modelPk;
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
        m_sbParameter.append("kind").append("=").append(kind).append("&");
        m_sbParameter.append("makeDate").append("=").append(makeDate).append("&");
        m_sbParameter.append("osPk").append("=").append(osPk).append("&");
        m_sbParameter.append("modelPk").append("=").append(modelPk).append("&");
        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));

        return m_sbParameter;
    }
}
