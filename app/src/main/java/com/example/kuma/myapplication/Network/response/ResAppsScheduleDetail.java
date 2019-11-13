package com.example.kuma.myapplication.Network.response;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.parser.JSONDefines;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.data.AppsScheduleInfo;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.JSONObject;

public class ResAppsScheduleDetail extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    private AppsScheduleInfo mScheduleInfo;

    public ResAppsScheduleDetail()
    {
        // TODO Auto-generated constructor stub
    }

    public String getResult()
    {
        return m_strResult;
    }

    public String getMsg()
    {
        return m_strMsg;
    }

    public  AppsScheduleInfo getScheduleInfo(){
        return this.mScheduleInfo;
    }

    @Override
    protected void parseXMLResponseData(JSONObject jsonObject) {
        try {
            KumaLog.d("============ ResAppsScheduleDetail parseXMLResponseData  ");

            try {
                m_strResult = jsonObject.getString(JSONDefines.JSON_RESP.STR_RESULT_CODE).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                m_strMsg = jsonObject.getString(JSONDefines.JSON_RESP.STR_RESULT_MSG).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if( m_strResult.equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS) ) {

                JSONObject obj = jsonObject.getJSONObject("data");
                try {
                    KumaLog.d("ResAppsScheduleDetail : " + obj.toString());
                    Gson gson = new Gson();
                    mScheduleInfo = gson.fromJson(obj.toString(), AppsScheduleInfo.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            KumaLog.i("+++ ResAppsScheduleDetail STR_TAG_RESULT  " + m_strResult);
            KumaLog.i("+++ ResAppsScheduleDetail STR_TAG_MSG  " + m_strMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        KumaLog.d("============ ResAppsScheduleDetail parseXMLResponseData  ");
    }
}
