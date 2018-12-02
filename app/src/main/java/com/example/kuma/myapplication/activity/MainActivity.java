package com.example.kuma.myapplication.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaRecorder;
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
import android.widget.Toast;

import com.example.kuma.myapplication.BaseActivity;
import com.example.kuma.myapplication.Constance.Constance;
import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.request.ReqDeviceInfo;
import com.example.kuma.myapplication.Network.request.ReqLogin;
import com.example.kuma.myapplication.Network.request.ReqMainDeviceList;
import com.example.kuma.myapplication.Network.response.ResDeviceInfo;
import com.example.kuma.myapplication.Network.response.ResLogin;
import com.example.kuma.myapplication.Network.response.ResMainDeviceList;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.adapter.MyAdapter;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.data.DeviceInfo;
import com.example.kuma.myapplication.data.MainDeviceData;
import com.example.kuma.myapplication.ui.dialog.BarcodeTypeSelectDialog;
import com.example.kuma.myapplication.ui.dialog.CommonDialog;
import com.example.kuma.myapplication.ui.dialog.IDialogListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private final static int TAG_REQ_DEVICE_LIST = 100;
    private final static int TAG_REQ_EMPLOY_LIST = 101;
    private final static int TAG_REQ_DEVICE_INFO = 102;

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
            }
        }, 100);

        mFloatingActionButton.setOnClickListener(this);
        mFloatingActionButton.show();

        findViewById(R.id.btn_regi).setOnClickListener(this);
        findViewById(R.id.btn_equipment).setOnClickListener(this);

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
     * 메인 리스트
     */
    private void reqDataList()
    {
        try {
            ReqMainDeviceList reqMainDeviceList = new ReqMainDeviceList();

            reqMainDeviceList.setTag(TAG_REQ_DEVICE_LIST);
            reqMainDeviceList.setUserId("");
            reqMainDeviceList.setPassword("" );
            requestProtocol(true, reqMainDeviceList);
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
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            setMainListUI(resprotocol.getListData());
        }  else {
            if( !TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }
    /**
     * 장비 상세 요청
     */
    private void reqDeviceInfo()
    {
        try {
            ReqDeviceInfo reqDeviceInfo = new ReqDeviceInfo();

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
            reqDataList();
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

            default:
                break;
        }
    }

    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
        switch (nTag){
            case 1:
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
                dlgBarcode.setDialogListener(1, this);
                dlgBarcode.show();
                break;
            case R.id.btn_regi:
                gotoRegist("");
                break;
            case R.id.btn_equipment:
                move2OtherActivity(DeviceConditionActivity.class);
                break;
            default:
                break;

        }
    }
}
