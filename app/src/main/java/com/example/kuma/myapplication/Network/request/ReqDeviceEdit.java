package com.example.kuma.myapplication.Network.request;

import android.content.Context;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.CommonResponse;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.Utils.DeviceUtils;

/**
 * Created by Kuma on 2018-02-22.
 * 제품수정
 */

public class ReqDeviceEdit extends RequestJSON
{

    private int m_nTag;
    //	고유번호
    private String pk = "";
    //	모델번호
    private String modelPk = "";
    //	제조일 (장비인경우 : YYYY-MM-DD 형식, 그외 : YYYY형식)
    private String makeDate = "";
    //	OS버젼 (장비인 경우에만 해당)
    private String osPk = "";

    private StringBuffer m_sbParameter;

    private Context mContext;
    public ReqDeviceEdit(Context context)
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
        return ProtocolDefines.UrlConstance.URL_DEVICE_EDIT;
    }

    public void setTag(int nTag)
    {
        m_nTag = nTag;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public void setModelPk(String modelPk) {
        this.modelPk = modelPk;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public void setOsPk(String osPk) {
        this.osPk = osPk;
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
        m_sbParameter.append("modelPk").append("=").append(modelPk).append("&");
        m_sbParameter.append("makeDate").append("=").append(makeDate).append("&");
        m_sbParameter.append("osPk").append("=").append(osPk).append("&");
        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));

        return m_sbParameter;
    }
}
