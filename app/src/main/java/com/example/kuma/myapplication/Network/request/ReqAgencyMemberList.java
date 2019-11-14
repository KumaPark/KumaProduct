package com.simens.us.myapplication.Network.request;

import android.content.Context;
import android.text.TextUtils;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.ResAgencyList;
import com.simens.us.myapplication.Network.response.ResAgencyMemberList;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;

public class ReqAgencyMemberList extends RequestJSON
{

    private int m_nTag;
    //	대리점고유번호 (0인 경우 본사)
    private String pk = "";

    private StringBuffer m_sbParameter;

    private Context mContext;
    public ReqAgencyMemberList(Context context)
    {
        mContext = context;
    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new ResAgencyMemberList();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_AGENCY_MEMBER_SEARCH;
    }

    public void setTag(int nTag)
    {
        m_nTag = nTag;
    }

    public void setPk(String pk) {
        this.pk = pk;
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
        m_sbParameter.append("pk").append("=").append(pk);

        return m_sbParameter;
    }
}
