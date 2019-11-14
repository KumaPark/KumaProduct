package com.simens.us.myapplication.Network.response;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.parser.JSONDefines;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.data.DeviceInfo;
import com.simens.us.myapplication.data.ScheduleInfo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kuma on 2018-03-03.
 */

public class ResDeviceScheduleList extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    private ArrayList<ScheduleInfo> arrResult = new ArrayList<>();

    public ResDeviceScheduleList()
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

    public  ArrayList<ScheduleInfo> getListData(){
        return this.arrResult;
    }

    @Override
    protected void parseXMLResponseData(JSONObject jsonObject) {
        try {
            KumaLog.d("============ ResDeviceScheduleList parseXMLResponseData  ");

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
                        KumaLog.d(" ScheduleInfo : " + objList.length());
                        Gson gson = new Gson();
                        for (int i = 0; i < objList.length(); i++) {
                            JSONObject obj = objList.getJSONObject(i);
                            ScheduleInfo data = gson.fromJson(obj.toString(), ScheduleInfo.class);
                            arrResult.add(data);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            KumaLog.i("+++ ResDeviceScheduleList STR_TAG_RESULT  " + m_strResult);
            KumaLog.i("+++ ResDeviceScheduleList STR_TAG_MSG  " + m_strMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        KumaLog.d("============ ResDeviceScheduleList parseXMLResponseData  ");
    }
}
