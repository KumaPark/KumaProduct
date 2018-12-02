package com.example.kuma.myapplication.Network.response;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.parser.JSONDefines;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.data.MainDeviceData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kuma on 2018-01-20.
 */

public class ResMainDeviceList extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    private ArrayList<MainDeviceData> arrResult = new ArrayList<>();

    public ResMainDeviceList()
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

    public  ArrayList<MainDeviceData> getListData(){
        return this.arrResult;
    }

    @Override
    protected void parseXMLResponseData(JSONObject jsonObject) {
        try {
            KumaLog.d("============ ResMainDeviceList parseXMLResponseData  ");

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

                JSONArray objList = jsonObject.getJSONArray("data");
                try {
                    if( objList.length() > 0 ) {
                        KumaLog.d("MainDeviceData : " + objList.length());
                        Gson gson = new Gson();
                        for (int i = 0; i < objList.length(); i++) {
                            JSONObject obj = objList.getJSONObject(i);
                            MainDeviceData data = gson.fromJson(obj.toString(), MainDeviceData.class);
                            arrResult.add(data);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            KumaLog.i("+++ ResMainDeviceList STR_TAG_RESULT  " + m_strResult);
            KumaLog.i("+++ ResMainDeviceList STR_TAG_MSG  " + m_strMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        KumaLog.d("============ ResMainDeviceList parseXMLResponseData  ");
    }
}
