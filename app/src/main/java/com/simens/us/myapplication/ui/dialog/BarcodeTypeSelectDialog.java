package com.simens.us.myapplication.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simens.us.myapplication.Constance.Constance;
import com.simens.us.myapplication.R;

/**
 * Created by Kuma on 2018-02-19.
 */

public class BarcodeTypeSelectDialog extends BaseDialog implements View.OnClickListener {


    public static final int DLG_TYPE_NOTI = 0;
    public static final int DLG_TYPE_YES_NO = 1;

    private int TAG_SELECTED_TYPE = Constance.TAG_CAPTURE_DEVICE;

    public static final int RESULT_OK       = 101;
    public static final int RESULT_CANCEL   = 0;

    private View mVSeleteedView;

    public BarcodeTypeSelectDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_barcode_select);

        findViewById(R.id.btn_dlg_yes).setOnClickListener(this);
        findViewById(R.id.btn_dlg_no).setOnClickListener(this);

        findViewById(R.id.barcode_type_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG_SELECTED_TYPE = Constance.TAG_CAPTURE_DEVICE;
                setSelectedBacground(v);
            }
        });

        mVSeleteedView = findViewById(R.id.barcode_type_device);

        findViewById(R.id.barcode_type_tranducer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG_SELECTED_TYPE = Constance.TAG_CAPTURE_TRANDUCER;
                setSelectedBacground(v);
            }
        });
        findViewById(R.id.barcode_type_foot_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG_SELECTED_TYPE = Constance.TAG_CAPTURE_FOOT_SWITCH;
                setSelectedBacground(v);
            }
        });

        findViewById(R.id.barcode_type_biopsy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG_SELECTED_TYPE = Constance.TAG_CAPTURE_BIOPSY;
                setSelectedBacground(v);
            }
        });
        findViewById(R.id.barcode_type_doggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG_SELECTED_TYPE = Constance.TAG_CAPTURE_DOGGLE;
                setSelectedBacground(v);
            }
        });
        findViewById(R.id.barcode_type_work_station).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG_SELECTED_TYPE = Constance.TAG_CAPTURE_WORK_STATION;
                setSelectedBacground(v);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        setResult(RESULT_CANCEL);
        super.onBackPressed();
    }

    private void setSelectedBacground(View view){

        if(mVSeleteedView != null && mVSeleteedView == view ) {
            return;
        }

        if( mVSeleteedView != null ) {
            mVSeleteedView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        mVSeleteedView = view;
        mVSeleteedView.setBackgroundColor(Color.parseColor("#aaFFB85A"));
    }

    @Override
    public void onClick(View v)
    {
        long lCurrentTime = System.currentTimeMillis();
        if((lCurrentTime - m_lBeforeClickedTime) < BaseDialog.CLICK_IGNORE_INTERVAL)
            return;
        m_lBeforeClickedTime = lCurrentTime;
        Intent intent = new Intent();
        switch(v.getId())
        {
            case R.id.btn_dlg_confirm:
                intent.putExtra("type", TAG_SELECTED_TYPE);
                setObject(intent);
                setResult(RESULT_OK);
                break;
            case R.id.btn_dlg_yes:
                intent.putExtra("type", TAG_SELECTED_TYPE);
                setObject(intent);
                setResult(RESULT_OK);
                break;
            case R.id.btn_dlg_no:
                setResult(RESULT_CANCEL);
                break;

        }
        dismiss();
    }
}
