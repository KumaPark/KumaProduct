package com.simens.us.myapplication.Network.request;

import android.content.Context;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.ResLogin;
import com.simens.us.myapplication.Network.response.ResMainDeviceList;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;

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

    private Context mContext;

    public ReqMainDeviceList(Context context)
    {
        mContext = context;
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
        m_sbParameter.append("loginPw").append("=").append(password).append("&");
        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));

        return m_sbParameter;
    }
}
