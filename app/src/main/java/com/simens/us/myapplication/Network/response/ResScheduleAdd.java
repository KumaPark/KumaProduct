package com.simens.us.myapplication.Network.response;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.parser.JSONDefines;
import com.simens.us.myapplication.Utils.KumaLog;

import org.json.JSONObject;

public class ResScheduleAdd extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    public ResScheduleAdd()
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

    @Override
    protected void parseXMLResponseData(JSONObject jsonObject) {
        try {
            KumaLog.d("============ ResScheduleAdd parseXMLResponseData  ");

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

//                JSONObject obj = jsonObject.getJSONObject("data");
//                try {
//                    KumaLog.d("MainDeviceData : " + obj.toString());
//                    Gson gson = new Gson();
//                    mDeviceInfo = gson.fromJson(obj.toString(), DeviceInfo.class);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            KumaLog.i("+++ ResScheduleAdd STR_TAG_RESULT  " + m_strResult);
            KumaLog.i("+++ ResScheduleAdd STR_TAG_MSG  " + m_strMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        KumaLog.d("============ ResScheduleAdd parseXMLResponseData  ");
    }
}
