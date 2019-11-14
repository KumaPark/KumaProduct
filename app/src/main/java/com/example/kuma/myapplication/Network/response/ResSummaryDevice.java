package com.simens.us.myapplication.Network.response;

import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.parser.JSONDefines;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.data.DeviceSummary;
import com.simens.us.myapplication.data.ScheduleInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kuma on 2018-12-12.
 */

public class ResSummaryDevice extends ResponseProtocol{

    private String m_strResult = "";

    private String m_strMsg = "";

    //	장비
    private DeviceSummary m_DataProduct;
    //	프로브
    private DeviceSummary m_Datarobe;
    //	악세사리
    private DeviceSummary m_DataAcc;

    public ResSummaryDevice()
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

    public  DeviceSummary getProductSummaryInfo(){
        return this.m_DataProduct;
    }
    public  DeviceSummary getProbeSummaryInfo(){
        return this.m_Datarobe;
    }
    public  DeviceSummary getAccSummaryInfo(){
        return this.m_DataAcc;
    }

    public int getTotalCnt(DeviceSummary data){
        int nTotal = 0;
        nTotal += Integer.parseInt(data.R);
        nTotal += Integer.parseInt(data.N);
        nTotal += Integer.parseInt(data.MO);
        nTotal += Integer.parseInt(data.DM);
        nTotal += Integer.parseInt(data.MH);
        nTotal += Integer.parseInt(data.T);
        nTotal += Integer.parseInt(data.F);
        nTotal += Integer.parseInt(data.D);
        nTotal += Integer.parseInt(data.S);
        return nTotal;
    }

    public int getDemoCnt(DeviceSummary data){
        int demo = 0;
        demo = Integer.parseInt(data.DM);
        return demo;
    }

    public int getOfficeCnt(DeviceSummary data){
        int office = 0;
        office = Integer.parseInt(data.R);
        return office;
    }

    public int getOouSideCnt(DeviceSummary data){
        int nTotal = 0;
        nTotal += Integer.parseInt(data.MO);
        nTotal += Integer.parseInt(data.DM);
        nTotal += Integer.parseInt(data.MH);
        return nTotal;
    }

    public int getMoveCnt(DeviceSummary data){
        int move = 0;
        move += Integer.parseInt(data.MO);
        move += Integer.parseInt(data.MH);
        return move;
    }
    public int getRepairCnt(DeviceSummary data){
        int cnt = 0;
        cnt = Integer.parseInt(data.F);
        return cnt;
    }

    public int getBreakCnt(DeviceSummary data){
        int cnt = 0;
        cnt = Integer.parseInt(data.T);
        return cnt;
    }
    public int getEtcCnt(DeviceSummary data){
        int nTotal = 0;
        nTotal += Integer.parseInt(data.T);
        nTotal += Integer.parseInt(data.F);
        nTotal += Integer.parseInt(data.D);
        return nTotal;
    }

    @Override
    protected void parseXMLResponseData(JSONObject jsonObject) {
        try {
            KumaLog.d("============ ResSummaryDevice parseXMLResponseData  ");

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
                Gson gson = new Gson();
                try {
                    JSONObject  objProduct = obj.getJSONObject("product");
                    KumaLog.d("ResSummaryDevice product >>> : " + objProduct.toString());
                    m_DataProduct = gson.fromJson(objProduct.toString(), DeviceSummary.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject  objProbe = obj.getJSONObject("probe");
                    KumaLog.d("ResSummaryDevice objProbe >>: " + objProbe.toString());
                    m_Datarobe = gson.fromJson(objProbe.toString(), DeviceSummary.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject  objAcc = obj.getJSONObject("acc");
                    KumaLog.d("ResSummaryDevice acc >>> : " + objAcc.toString());
                    m_DataAcc = gson.fromJson(objAcc.toString(), DeviceSummary.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            KumaLog.i("+++ ResSummaryDevice STR_TAG_RESULT  " + m_strResult);
            KumaLog.i("+++ ResSummaryDevice STR_TAG_MSG  " + m_strMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        KumaLog.d("============ ResSummaryDevice parseXMLResponseData  ");
    }
}
