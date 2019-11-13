package com.example.kuma.myapplication.Network.request;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.ResUserRegist;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;

public class ReqUserRegist extends RequestJSON
{

    private int m_nTag;
    private StringBuffer m_sbParameter;

    private String loginId = "";
    private String macAddr = "";
    private String tempPw = "";
    private String newPw = "";
    private String regId = "";

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getTempPw() {
        return tempPw;
    }

    public void setTempPw(String tempPw) {
        this.tempPw = tempPw;
    }

    public String getNewPw() {
        return newPw;
    }

    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public ReqUserRegist()
    {

    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResUserRegist();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_USER_REGIST;
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

        m_sbParameter.append("loginId").append("=").append(loginId).append("&");
        m_sbParameter.append("macAddr").append("=").append(macAddr).append("&");
        m_sbParameter.append("tempPw").append("=").append(tempPw).append("&");
        m_sbParameter.append("newPw").append("=").append(newPw).append("&");
        m_sbParameter.append("regId").append("=").append(regId);

        return m_sbParameter;
    }
}