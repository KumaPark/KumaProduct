package com.example.kuma.myapplication.Network.response;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.parser.JSONDefines;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.data.DeviceInfo;
import com.example.kuma.myapplication.data.MainDeviceData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kuma on 2018-02-22.
 * 제품조회 상세
 */

public class ResDeviceInfo extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    private DeviceInfo mDeviceInfo;

    public ResDeviceInfo()
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

    public  DeviceInfo getDeviceInfo(){
        return this.mDeviceInfo;
    }

    @Override
    protected void parseXMLResponseData(JSONObject jsonObject) {
        try {
            KumaLog.d("============ ResDeviceInfo parseXMLResponseData  ");

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
                    KumaLog.d("MainDeviceData : " + obj.toString());
                    Gson gson = new Gson();
                    mDeviceInfo = gson.fromJson(obj.toString(), DeviceInfo.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            KumaLog.i("+++ ResDeviceInfo STR_TAG_RESULT  " + m_strResult);
            KumaLog.i("+++ ResDeviceInfo STR_TAG_MSG  " + m_strMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        KumaLog.d("============ ResDeviceInfo parseXMLResponseData  ");
    }
}
