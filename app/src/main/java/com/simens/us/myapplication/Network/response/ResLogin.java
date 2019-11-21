package com.simens.us.myapplication.Network.response;

import com.simens.us.myapplication.AppManager;
import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.parser.JSONDefines;
import com.simens.us.myapplication.Utils.KumaLog;

import org.json.JSONObject;

/**
 * Created by 재성 on 2016-08-09.
 */
public class ResLogin extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    private String m_strToken;

    private int permission;

    private String name;

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

    public String getToken()
    {
        return m_strToken;
    }

    public int getPermission() {
        return permission;
    }

    public String getName() {
        return name;
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
                try {
                    m_strToken = jsonObject.getString("token").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    permission = jsonObject.getInt("permission");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    name = jsonObject.getString("name").toString();
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
