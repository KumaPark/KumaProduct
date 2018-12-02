package com.example.kuma.myapplication.Network.request;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.CommonResponse;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;

/**
 * Created by Kuma on 2018-02-22.
 * 제품수정
 */

public class ReqDeviceEdit extends RequestJSON
{

    private int m_nTag;
    //	시리얼번호
    private String serialNo = "";
    //	장비모델
    private String productCode = "";
    //	제조년
    private String makeYear = "";
    //	OS버젼
    private String version = "";
    //	비고
    private String message = "";

    private StringBuffer m_sbParameter;


    public ReqDeviceEdit()
    {

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

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setMakeYear(String makeYear) {
        this.makeYear = makeYear;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMessage(String message) {
        this.message = message;
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
        m_sbParameter.append("productCode").append("=").append(productCode).append("&");
        m_sbParameter.append("makeYear").append("=").append(makeYear).append("&");
        m_sbParameter.append("version").append("=").append(version).append("&");
        m_sbParameter.append("message").append("=").append(message);

        return m_sbParameter;
    }
}
