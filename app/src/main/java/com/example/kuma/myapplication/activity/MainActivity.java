package com.simens.us.myapplication.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.simens.us.myapplication.BaseActivity;
import com.simens.us.myapplication.Constance.Constance;
import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.request.ReqAgencyList;
import com.simens.us.myapplication.Network.request.ReqDestinationList;
import com.simens.us.myapplication.Network.request.ReqDeviceInfo;
import com.simens.us.myapplication.Network.request.ReqMainDeviceList;
import com.simens.us.myapplication.Network.request.ReqSummaryDevice;
import com.simens.us.myapplication.Network.response.ResAgencyList;
import com.simens.us.myapplication.Network.response.ResDestinationList;
import com.simens.us.myapplication.Network.response.ResDeviceInfo;
import com.simens.us.myapplication.Network.response.ResMainDeviceList;
import com.simens.us.myapplication.Network.response.ResSummaryDevice;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.adapter.MyAdapter;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.data.DeviceInfo;
import com.simens.us.myapplication.data.DeviceSummary;
import com.simens.us.myapplication.data.MainDeviceData;
import com.simens.us.myapplication.ui.dialog.BarcodeTypeSelectDialog;
import com.simens.us.myapplication.ui.dialog.CommonDialog;
import com.simens.us.myapplication.ui.dialog.IDialogListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private final static int TAG_REQ_DEVICE_LIST = 100;
    private final static int TAG_REQ_EMPLOY_LIST = 101;
    private final static int TAG_REQ_DEVICE_INFO = 102;
    private final static int TAG_REQ_SUMMARY_DEVICE = 103;

    private final static int TAG_REQ_DESTINATION_LIST = 104;
    private final static int TAG_REQ_AGENCY_LIST = 105;

    private final static int DLG_BARCODE = 201;

    //	장비
    private DeviceSummary m_DataProduct;
    //	프로브
    private DeviceSummary m_Datarobe;
    //	악세사리
    private DeviceSummary m_DataAcc;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MainDeviceData> dddd = new ArrayList<>();
    FloatingActionButton mFloatingActionButton;
    Handler mHandler = new Handler();

    RelativeLayout mRlToolbar;
    Toolbar toolbar;
    int MAX_VERTICAL_OFFSET = 396;

    private String mSerialNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mRlToolbar = (RelativeLayout) findViewById(R.id.rl_toolbar);

        AppBarLayout  appBar = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float alpha = Float.valueOf(-verticalOffset) / MAX_VERTICAL_OFFSET; // 1.0〜0.0 の間で変動

                toolbar.setAlpha(alpha);
                mRlToolbar.setAlpha(alpha);
//                appBarText.alpha = 1 - alpha
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d("kuma"," mRlToolbar.getAlpha() " + recyclerView.getAlpha());
//                if (mRlToolbar.getAlpha() < 0.5 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
//                    mFloatingActionButton.hide();
//                } else if (mRlToolbar.getAlpha() > 0.5 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
//                    mFloatingActionButton.show();
//                }
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reqDataList();
                reqDestinationList();
                reqAgencyList();
            }
        }, 100);

        mFloatingActionButton.setOnClickListener(this);
        mFloatingActionButton.show();

        findViewById(R.id.ll_regist).setOnClickListener(this);
        findViewById(R.id.ll_equipment).setOnClickListener(this);
        findViewById(R.id.ll_employ_schedule).setOnClickListener(this);

    }

    private void gotoEdit(DeviceInfo data){
        Intent intent = new Intent(MainActivity.this,DeviceManagementActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailData",data);
        intent.putExtras(bundle);
        intent.putExtra("dataState",  true);
        intent.putExtra("type",  102);
        startActivity(intent);
    }
    private void gotoRegist(String serialNum){
        Intent intent = new Intent(MainActivity.this, DeviceManagementActivity.class);
        intent.putExtra("dataState",  false);
        intent.putExtra("serialNum",  serialNum);
        intent.putExtra("type",  101);
        startActivity(intent);
    }


    private void setMainListUI(ArrayList<DeviceInfo> data){
        mAdapter = new MyAdapter(data, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DeviceInfo data) {
                gotoEdit(data);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }

    /**
     * 목적지 조회
     */
    private void reqDestinationList() {
        try {
            ReqDestinationList reqDestinationList = new ReqDestinationList(this);

            reqDestinationList.setTag(TAG_REQ_DESTINATION_LIST);
            requestProtocol(true, reqDestinationList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 목적지 조회
     */
    private void resDestinationList(ResDestinationList resprotocol) {
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resDestinationList  ++++++++++++++");
            Constance.mArrDestinationList.clear();
            Constance.mArrDestinationList.addAll(resprotocol.getmArrDropBoaxCommonDTO());

        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }

    /**
     * 본사 및 대리점명 조회
     */
    private void reqAgencyList() {
        try {
            ReqAgencyList reqAgencyList = new ReqAgencyList(this);

            reqAgencyList.setTag(TAG_REQ_AGENCY_LIST);
            requestProtocol(true, reqAgencyList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 본사 및 대리점명 조회
     */
    private void resAgencyList(ResAgencyList resprotocol) {
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resAgencyList  ++++++++++++++");
            Constance.mArrAgencyList.clear();
            Constance.mArrAgencyList.addAll(resprotocol.getmArrDropBoaxCommonDTO());

            KumaLog.i("+++ ResAgencyList Constance.mArrAgencyList.size()  " + Constance.mArrAgencyList.size());
        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }




    private void setSummaryDEviceInfoUI(){
//        m_DataProduct = resprotocol.getProductSummaryInfo();
//        m_Datarobe = resprotocol.getProbeSummaryInfo();
//        m_DataAcc = resprotocol.getAccSummaryInfo();
    }
    /**
     * 메인 리스트
     */
    private void reqDataList()
    {
        try {
            ReqAgencyList reqAgencyList = new ReqAgencyList(this);

            reqAgencyList.setTag(TAG_REQ_DEVICE_LIST);
            requestProtocol(true, reqAgencyList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     /**
     * 메인 리스트
     */
    private void resDataList(ResMainDeviceList resprotocol)
    {
        KumaLog.d("++++++++++++resDataList++++++++++++++");
//        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
//            setMainListUI(resprotocol.getListData());
//        }  else {
//            if( !TextUtils.isEmpty(resprotocol.getMsg())) {
//                showSimpleMessagePopup(resprotocol.getMsg());
//            } else {
//                showSimpleMessagePopup();
//            }
//        }
    }
    /**
     * 장비 상세 요청
     */
    private void reqDeviceInfo()
    {
        try {
            ReqDeviceInfo reqDeviceInfo = new ReqDeviceInfo(this);

            reqDeviceInfo.setTag(TAG_REQ_DEVICE_INFO);
            reqDeviceInfo.setSerialNo(mSerialNum);
            requestProtocol(true, reqDeviceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 장비 상세 요청
     */
    private void resDeviceInfo(ResDeviceInfo resprotocol)
    {
        KumaLog.d("++++++++++++resDataList++++++++++++++");
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            gotoEdit(resprotocol.getDeviceInfo());
        }  else {
            CommonDialog commonDia = new CommonDialog(this);
            commonDia.setType(CommonDialog.DLG_TYPE_YES_NO);
            commonDia.setDialogListener(1, new IDialogListener() {
                @Override
                public void onDialogResult(int nTag, int nResult, Dialog dialog) {
                    if( nResult ==  CommonDialog.RESULT_OK) {
                        gotoRegist(mSerialNum);
                    }
                }
            });
            commonDia.setCancelable(false);
            commonDia.setMessage("제품이 존재하지않습니다. 등록하시겠습니까?");
            commonDia.show();

//            if( !TextUtils.isEmpty(resprotocol.getMsg())) {
//                showSimpleMessagePopup(resprotocol.getMsg());
//            } else {
//                showSimpleMessagePopup();
//            }
        }
    }

    /**
     * 제품요약집계
     */
    private void reqSummaryDeviceInfo()
    {
        try {
            ReqSummaryDevice reqSummaryDevice = new ReqSummaryDevice(this);

            reqSummaryDevice.setTag(TAG_REQ_SUMMARY_DEVICE);
            requestProtocol(true, reqSummaryDevice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 제품요약집계
     */
    private void resSummaryDeviceInfo(ResSummaryDevice resprotocol)
    {
        KumaLog.d("++++++++++++resDataList++++++++++++++");
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            m_DataProduct = resprotocol.getProductSummaryInfo();
            m_Datarobe = resprotocol.getProbeSummaryInfo();
            m_DataAcc = resprotocol.getAccSummaryInfo();
        }  else {
            if( !TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }


    private boolean  onPauseState = false;
    @Override
    protected void onPause() {
        super.onPause();
        KumaLog.d("onPause >>> ");
        onPauseState = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        KumaLog.d("onResume >>> ");
        if( onPauseState ) {
            onPauseState = false;
//            reqDataList();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        KumaLog.d("onActivityResult requestCode >>  " + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            mSerialNum = scanResult.getContents();
            KumaLog.d( "onActivityResult: ." + mSerialNum);
            reqDeviceInfo();
        }
    }
    @Override
    public void onResponseProtocol(int nTag, ResponseProtocol resProtocol) {
        switch (nTag){
            case TAG_REQ_DEVICE_LIST:
                resDataList((ResMainDeviceList)resProtocol);
                break;
            case TAG_REQ_DEVICE_INFO:
                resDeviceInfo((ResDeviceInfo)resProtocol);
                break;
            case TAG_REQ_SUMMARY_DEVICE:
                resSummaryDeviceInfo((ResSummaryDevice)resProtocol);
                break;
            case TAG_REQ_DESTINATION_LIST : {
                resDestinationList((ResDestinationList)resProtocol);
                break;
            }
            case TAG_REQ_AGENCY_LIST : {
                resAgencyList((ResAgencyList)resProtocol);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
        switch (nTag){
            case DLG_BARCODE:
                if( dlgBarcode.getObject() == null ) {
                    return;
                }
               int type = ((Intent)dlgBarcode.getObject()).getIntExtra("type", Constance.TAG_CAPTURE_DEVICE);
                KumaLog.d(" type : " + type);
                //                바코드 스캐너 동작
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setCaptureActivity(BarcodeCaptureActivity.class);
                integrator.initiateScan();
                break;
            default:
                break;
        }
    }
    BarcodeTypeSelectDialog dlgBarcode;
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fab:
                dlgBarcode = new BarcodeTypeSelectDialog(this);
                dlgBarcode.setDialogListener(DLG_BARCODE, this);
                dlgBarcode.show();
                break;
            case R.id.ll_regist:
                gotoRegist("");
                break;
            case R.id.ll_equipment:
                move2OtherActivity(DeviceConditionActivity.class);
                break;
            case R.id.ll_employ_schedule:
                move2OtherActivity(AppsScheduleActivity.class);
                break;
            default:
                break;
        }
    }
}
