package com.simens.us.myapplication.Network.request;

import android.content.Context;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.CommonResponse;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;

public class ReqDeviceScheduleInsert extends RequestJSON
{

    private int m_nTag;

    private StringBuffer m_sbParameter;

    //제품고유번호
    private String productPk  = "";
    private String kind  = "";

    // 목적지 고유번호
    private String destinationPk = "";
    //데모시작일
    private String startDate = "";
    //데모종료일
    private String endDate = "";

    //수산자 구분(본사 및 대리점고유번호)
    private String receiverAgencyPk = "";
    //수신자 (회원고유번호)
    private String receiver = "";
    //비고
    private String message = "";

    private Context mContext;

    public ReqDeviceScheduleInsert(Context context)
    {
        mContext = context;
    }

    public void setProductPk(String productPk) {
        this.productPk = productPk;
    }

    public void setDestinationPk(String destinationPk) {
        this.destinationPk = destinationPk;
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

    public void setReceiverAgencyPk(String receiverAgencyPk) {
        this.receiverAgencyPk = receiverAgencyPk;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new CommonResponse();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_DEVICE_SCHEDULE_ADD;
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

        m_sbParameter.append("productPk").append("=").append(productPk).append("&");
        m_sbParameter.append("destinationPk").append("=").append(destinationPk).append("&");
        m_sbParameter.append("startDate").append("=").append(startDate).append("&");
        m_sbParameter.append("endDate").append("=").append(endDate).append("&");
        m_sbParameter.append("receiverAgencyPk").append("=").append(receiverAgencyPk).append("&");
        m_sbParameter.append("message").append("=").append(message).append("&");
        m_sbParameter.append("receiver").append("=").append(receiver).append("&");
        m_sbParameter.append("kind").append("=").append(kind).append("&");

        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));

        return m_sbParameter;
    }
}
