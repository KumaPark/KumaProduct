package com.example.kuma.myapplication.Network.response;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.parser.JSONDefines;
import com.example.kuma.myapplication.Utils.KumaLog;

import org.json.JSONObject;

/**
 * Created by 재성 on 2016-08-09.
 */
public class ResLogin extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    private String m_strAppVerSion;

    private String m_strAppUrl;

    public ResLogin()
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

    public String getAppVerSion()
    {
        return m_strAppVerSion;
    }

    public String getAppUrl()
    {
        return m_strAppUrl;
    }

    @Override
    protected void parseXMLResponseData(JSONObject jsonObject) {
        try {
            KumaLog.d("============ ResLogin parseXMLResponseData  ");

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

//                JSONObject object = jsonObject.getJSONObject(JSONDefines.JSON_RESP.STR_APP_VERSION);
                try {
//                    m_strAppVerSion = object.getString(JSONDefines.JSON_RESP.STR_APP_VERSION_MAIN).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
//                    m_strAppUrl = object.getString(JSONDefines.JSON_RESP.STR_APP_VERSION_URL).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            KumaLog.i("+++ ResLogin STR_TAG_RESULT  " + m_strResult);
            KumaLog.i("+++ ResLogin STR_TAG_MSG  " + m_strMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        KumaLog.d("============ ResLogin parseXMLResponseData  ");
    }
}
