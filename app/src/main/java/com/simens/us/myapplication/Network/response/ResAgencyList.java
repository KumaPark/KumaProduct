package com.simens.us.myapplication.Network.response;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.parser.JSONDefines;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.data.DeviceInfo;
import com.simens.us.myapplication.data.DropBoaxCommonDTO;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResAgencyList extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    private ArrayList<DropBoaxCommonDTO> mArrDropBoaxCommonDTO = new ArrayList<>();
//{"result":"0000","message":"","data":[{"pk":0,"name":"본사"},{"pk":1,"name":"대리점1"},{"pk":2,"name":"대리점2"},{"pk":3,"name":"대리점3"}]}
    public ResAgencyList()
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

    public ArrayList<DropBoaxCommonDTO> getmArrDropBoaxCommonDTO() {
        return mArrDropBoaxCommonDTO;
    }

    @Override
    protected void parseXMLResponseData(JSONObject jsonObject) {
        try {
            KumaLog.d("============ ResAgencyList parseXMLResponseData  ");

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
                        Gson gson = new Gson();
                        for (int i = 0; i < objList.length(); i++) {
                            JSONObject obj = objList.getJSONObject(i);
                            DropBoaxCommonDTO data = gson.fromJson(obj.toString(), DropBoaxCommonDTO.class);
                            mArrDropBoaxCommonDTO.add(data);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            KumaLog.i("+++ ResAgencyList STR_TAG_RESULT  " + m_strResult);
            KumaLog.i("+++ ResAgencyList STR_TAG_MSG  " + m_strMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        KumaLog.d("============ ResAgencyList parseXMLResponseData  ");
    }
}
