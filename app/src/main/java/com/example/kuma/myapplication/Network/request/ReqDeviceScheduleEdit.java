package com.example.kuma.myapplication.Network.request;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.CommonResponse;
import com.example.kuma.myapplication.Network.response.ResDeviceScheduleInfo;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;

/**
 * Created by Kuma on 2018-03-03.
 */

public class ReqDeviceScheduleEdit extends RequestJSON
{

    private int m_nTag;

    private StringBuffer m_sbParameter;

    //시리얼번호
    private String serialNo = "";
    //제품상태
    private String state = "";
    //병원명
    private String hostpital = "";
    //데모시작일
    private String startDate = "";
    //데모종료일
    private String endDate = "";
    //배달자
    private String deliver = "";
    //수령자
    private String receiver = "";
    //비고
    private String message = "";

    public ReqDeviceScheduleEdit()
    {

    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setHostpital(String hostpital) {
        this.hostpital = hostpital;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
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
        return ProtocolDefines.UrlConstance.URL_DEVICE_SCHEDULE_EDIT;
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

        m_sbParameter.append("hostpital").append("=").append(hostpital).append("&");
        m_sbParameter.append("state").append("=").append(state).append("&");
        m_sbParameter.append("startDate").append("=").append(startDate).append("&");
        m_sbParameter.append("endDate").append("=").append(endDate).append("&");
        m_sbParameter.append("deliver").append("=").append(deliver).append("&");
        m_sbParameter.append("receiver").append("=").append(receiver).append("&");
        m_sbParameter.append("message").append("=").append(message).append("&");
        m_sbParameter.append("serialNo").append("=").append(serialNo);

        return m_sbParameter;
    }
}
