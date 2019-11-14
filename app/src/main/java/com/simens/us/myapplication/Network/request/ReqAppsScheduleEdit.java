package com.simens.us.myapplication.Network.request;

import android.content.Context;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.response.CommonResponse;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;

public class ReqAppsScheduleEdit extends RequestJSON
{

    private int m_nTag;
    //	회원고유번호
    private String memberPk = "";

    //	스케쥴 고유번호
    private String pk = "";

    //시작일
    private String startDate = "";
    //종료일
    private String endDate = "";
    //스케쥴 구분 ex) D : 데모, A : AS, O : 회사, E : 기타
    private String kind = "";
    //일정명
    private String title = "";
    //일정내용
    private String content = "";

    private StringBuffer m_sbParameter;

    private Context mContext;
    public ReqAppsScheduleEdit(Context context)
    {
        mContext = context;
    }

    public void setMemberPk(String memberPk) {
        this.memberPk = memberPk;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @Override
    protected ResponseProtocol createResponseProtocol()
    {
        return new CommonResponse();
    }

    @Override
    public String getURL()
    {
        return ProtocolDefines.UrlConstance.URL_MEMBER_SCHEDULE_EDIT;
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

        m_sbParameter.append("pk").append("=").append(pk).append("&");
        m_sbParameter.append("memberPk").append("=").append(memberPk).append("&");
        m_sbParameter.append("startDate").append("=").append(startDate).append("&");
        m_sbParameter.append("endDate").append("=").append(endDate).append("&");
        m_sbParameter.append("kind").append("=").append(kind).append("&");
        m_sbParameter.append("title").append("=").append(title).append("&");
        m_sbParameter.append("content").append("=").append(content).append("&");
        m_sbParameter.append("token").append("=").append(DeviceUtils.getToken(mContext));
        return m_sbParameter;
    }
}
