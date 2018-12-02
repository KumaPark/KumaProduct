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
import com.example.kuma.myapplication.Network.request.ReqLogin;
import com.example.kuma.myapplication.Network.request.ReqMainDeviceList;
import com.example.kuma.myapplication.Network.response.ResLogin;
import com.example.kuma.myapplication.Network.response.ResMainDeviceList;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.adapter.MyAdapter;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.data.MainDeviceData;
import com.example.kuma.myapplication.ui.dialog.BarcodeTypeSelectDialog;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private final static int TAG_REQ_DEVICE_LIST = 100;
    private final static int TAG_REQ_EMPLOY_LIST = 101;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MainDeviceData> dddd = new ArrayList<>();
    FloatingActionButton mFloatingActionButton;
    Handler mHandler = new Handler();

    RelativeLayout mRlToolbar;
    Toolbar toolbar;
    int MAX_VERTICAL_OFFSET = 396;


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
                if (mRlToolbar.getAlpha() < 0.5 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    mFloatingActionButton.hide();
                } else if (mRlToolbar.getAlpha() > 0.5 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    mFloatingActionButton.show();
                }
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reqDataList();
            }
        }, 100);

        mFloatingActionButton.setOnClickListener(this);
    }

    private void setMainListUI(ArrayList<MainDeviceData> data){
        mAdapter = new MyAdapter(data);

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        KumaLog.d("onActivityResult: .");
        if (resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            String re = scanResult.getContents();
            String message = re;
            KumaLog.d( "onActivityResult: ." + re);
            Toast.makeText(this, re, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onResponseProtocol(int nTag, ResponseProtocol resProtocol) {
        switch (nTag){
            case TAG_REQ_DEVICE_LIST:
                resDataList((ResMainDeviceList)resProtocol);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
        switch (nTag){
            case 1:
                if( ddd.getObject() == null ) {
                    return;
                }
               int type = ((Intent)ddd.getObject()).getIntExtra("type", Constance.TAG_CAPTURE_DEVICE);
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
    BarcodeTypeSelectDialog ddd;
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fab:
                ddd = new BarcodeTypeSelectDialog(this);
                ddd.setDialogListener(1, this);
                ddd.show();
                break;
            default:
                break;

        }
    }
}
