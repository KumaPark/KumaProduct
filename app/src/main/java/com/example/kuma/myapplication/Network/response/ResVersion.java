package com.example.kuma.myapplication.Network.response;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.parser.JSONDefines;
import com.example.kuma.myapplication.Utils.KumaLog;

import org.json.JSONObject;

public class ResVersion extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    private String version;

    private String forceUpdate;

    public ResVersion()
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

    public boolean isUpdate(String curViersion) {
        KumaLog.line();
        KumaLog.i(">>>> isUpdate server Viersion : " + version);
        KumaLog.i(">>>> isUpdate curViersion : " + curViersion);
        if(Integer.parseInt(curViersion.replace(".", ""))
                < Integer.parseInt(version.replace(".", "")) ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isForceUpdate(){
        KumaLog.line();
        KumaLog.i(">>>> isForceUpdate forceUpdate : " + forceUpdate);
        if( forceUpdate.equals("0")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void parseXMLResponseData(JSONObject jsonObject) {
        try {
            KumaLog.d("============ ResVersion parseXMLResponseData  ");

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
                    version = obj.getString("version").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    forceUpdate = obj.getString("forceUpdate").toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            KumaLog.i("+++ ResVersion STR_TAG_RESULT  " + m_strResult);
            KumaLog.i("+++ ResVersion STR_TAG_MSG  " + m_strMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        KumaLog.d("============ ResVersion parseXMLResponseData  ");
    }
}
