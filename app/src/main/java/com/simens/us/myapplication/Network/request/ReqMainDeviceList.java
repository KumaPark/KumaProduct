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
    //	제품종류 (M : 장비, P : 프로브 , A : 악세사리) - 지정하지 않은경우 전체
    private String kind = "";
    //	시작지점
    private String start = "";

    /**
     * : 가져올 갯수
     *     ex) 1번째 부터 10개를 가져올 경우 start : 1, count :10
     *     ex) 11번째 부터 10개를 가져올 경우 start : 11, count :10
     */
    private String count = "";

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

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public Integer getTAG() {
        // TODO Auto-generated method stub
        return m_nTag;
    }
    @Override
    public StringBuffer getJson() {

        m_sbParameter = new StringBuffer();

        m_sbParameter.append("kind").append("=").append(kind).append("&");
        m_sbParameter.append("start").append("=").append(start).append("&");
        m_sbParameter.append("count").append("=").append(count).append("&");
        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));

        return m_sbParameter;
    }
}
