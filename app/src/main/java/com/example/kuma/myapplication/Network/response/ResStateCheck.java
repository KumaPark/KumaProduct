package com.example.kuma.myapplication.Network.response;

import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.parser.JSONDefines;
import com.example.kuma.myapplication.Utils.KumaLog;

import org.json.JSONObject;

public class ResStateCheck extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    //등록여부 ex) 1 : 정상등록 됨 , 0 : 미등록 (등록요청중인상태)
    private int registed = 0;
    //활동여부 (90일) ex) 1 : 정상적인 활동, 0 : 90일이상 활동이 없음
    private int actived = 0;
    //비밀번호변경여부 (90일) ex) 1 : 비밀번호 정상변경, 0 : 90일이상 변경이 없음
    private int pwChanged = 0;

    public ResStateCheck()
    {
        // TODO Auto-generated constructor stub
    }

    public int getRegisted() {
        return registed;
    }

    public int getActived() {
        return actived;
    }

    public int getPwChanged() {
        return pwChanged;
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
            KumaLog.d("============ ResStateCheck parseXMLResponseData  ");

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
                    registed = jsonObject.getInt("registed");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    actived = jsonObject.getInt("actived");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    pwChanged = jsonObject.getInt("pwChanged");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            KumaLog.i("+++ ResStateCheck STR_TAG_RESULT  " + m_strResult);
            KumaLog.i("+++ ResStateCheck STR_TAG_MSG  " + m_strMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        KumaLog.d("============ ResStateCheck parseXMLResponseData  ");
    }
}
