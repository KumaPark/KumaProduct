package com.example.kuma.myapplication.Network.request;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.response.ResLogin;
import com.example.kuma.myapplication.Network.response.ResMainDeviceList;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;

/**
 * Created by Kuma on 2018-01-20.
 */

public class ReqMainDeviceList extends RequestJSON
{

    private int m_nTag;
    //	회원 아이디
    private String userId = "";
    private String password = "";
    //	디바이스아이디
    private String m_strDeviceId = "";

    private StringBuffer m_sbParameter;


    public ReqMainDeviceList()
    {

    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResMainDeviceList();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_MAIN_LIST;
    }

    public void setTag(int nTag)
    {
        m_nTag = nTag;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Integer getTAG() {
        // TODO Auto-generated method stub
        return m_nTag;
    }
    @Override
    public StringBuffer getJson() {

        m_sbParameter = new StringBuffer();

        m_sbParameter.append("loginId").append("=").append(userId).append("&");
        m_sbParameter.append("loginPw").append("=").append(password);

        return m_sbParameter;
    }
}