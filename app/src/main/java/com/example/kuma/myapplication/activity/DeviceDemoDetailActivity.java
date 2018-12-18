package com.example.kuma.myapplication.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuma.myapplication.BaseActivity;
import com.example.kuma.myapplication.Constance.Constance;
import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.request.ReqDeviceScheduleEdit;
import com.example.kuma.myapplication.Network.response.CommonResponse;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.data.ScheduleInfo;
import com.example.kuma.myapplication.ui.dialog.CommonDialog;


/**
 * Created by Kuma on 2018-03-07.
 */

public class DeviceDemoDetailActivity extends BaseActivity implements View.OnClickListener{
    private final static int TAG_REQ_DEVICE_EDIT = 1001;

    private final static int DLG_DEVICE_EDIT_REQ = 2001;
    private final static int DLG_DEVICE_RESULT = 2002;

    private final static int DATEPICK_START = 3001;
    private final static int DATEPICK_END = 3002;

    private int DATEPICK_STATE = DATEPICK_START;


    private EditText mEvModel;
    private EditText mEvDeliver;
    private EditText mEvReceiver;
    private EditText mEvHospital;
    private EditText mEvEtc;

    private TextView mTvStartDate;
    private TextView mTvEndDate;
    private TextView mTvState;

    private Button mBtnCancle;
    private Button mBtnConfirm;

    private ScheduleInfo mScheduleInfo;

    private boolean  mBEditState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_demo_detail);

        Intent intent = getIntent();
        mScheduleInfo   =  (ScheduleInfo)intent.getSerializableExtra("detailData");

        init();
        mArrDeviceCode = getResources().getStringArray(R.array.arr_device_state_code);
    }

    private void init(){
        View view = findViewById(R.id.view_device_demo_detail);

        mEvModel = (EditText) view.findViewById(R.id.ev_model );
        mTvStartDate = (TextView) view.findViewById(R.id.tv_startdate );
        mTvEndDate = (TextView) view.findViewById(R.id.tv_enddate );
        mEvDeliver = (EditText) view.findViewById(R.id.ev_deliver );
        mEvReceiver = (EditText) view.findViewById(R.id.ev_receiver );
        mEvHospital = (EditText) view.findViewById(R.id.ev_hospital );
        mTvState = (TextView) view.findViewById(R.id.tv_state );
        mEvEtc = (EditText) view.findViewById(R.id.ev_etc );

        mBtnCancle = (Button) view.findViewById(R.id.btn_cancle );
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm );

        mBtnConfirm.setOnClickListener(this);
        mBtnCancle.setOnClickListener(this);

        mBtnConfirm.setText(getResources().getString(R.string.str_btn_edit));

        mEvModel.setText(mScheduleInfo.getProductCode());
        mTvStartDate.setText(mScheduleInfo.getStartDate());
        mTvEndDate.setText(mScheduleInfo.getEndDate());
        mEvDeliver.setText(mScheduleInfo.getDeliver());
        mEvReceiver.setText(mScheduleInfo.getReceiver());
        mEvHospital.setText(mScheduleInfo.getHospital());
        mEvEtc.setText(mScheduleInfo.getMessage());

        changeEditState(false);
    }

    private void changeEditState(boolean state) {
        mEvModel.setEnabled(state);

        mEvDeliver.setEnabled(state);
        mEvReceiver.setEnabled(state);
        mEvHospital.setEnabled(state);
        mEvEtc.setEnabled(state);

        if( !state ) {
            mEvModel.setText(mScheduleInfo.getProductCode());
            mTvStartDate.setText(mScheduleInfo.getStartDate());
            mTvEndDate.setText(mScheduleInfo.getEndDate());
            mEvDeliver.setText(mScheduleInfo.getDeliver());
            mEvReceiver.setText(mScheduleInfo.getReceiver());
            mEvHospital.setText(mScheduleInfo.getHospital());
            mEvEtc.setText(mScheduleInfo.getMessage());
            mTvState.setOnClickListener(null);
            mTvStartDate.setOnClickListener(null);
            mTvEndDate.setOnClickListener(null);
        }  else {
            mTvState.setOnClickListener(this);
            mTvStartDate.setOnClickListener(this);
            mTvEndDate.setOnClickListener(this);
        }
    }

    private void showDatePicker(String[] arrDate){
        int mYear = Integer.parseInt(arrDate[0]);
        int mMonth = Integer.parseInt(arrDate[1]) - 1;
        int mDay = Integer.parseInt(arrDate[2]);
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth )
            {
                int mYear = year;
                int mMonth = monthOfYear + 1;
                int mDay = dayOfMonth;
                if( DATEPICK_STATE == DATEPICK_START ) {
                    mTvStartDate.setText(getDateText( mYear, mMonth, mDay ));
                } else {
                    mTvEndDate.setText(getDateText( mYear, mMonth, mDay ));
                }
            }
        }, mYear, mMonth, mDay).show();
    }

    private String getDateText(int year, int monthOfYear, int dayOfMonth) {
        String strResult = "";

        String strDate = "";
        String strDay = "";

        if( monthOfYear < 10 ) {
            strDate = "0" + monthOfYear;
        } else {
            strDate = "" + monthOfYear;
        }

        if( dayOfMonth < 10 ) {
            strDay = "0" + dayOfMonth;
        } else {
            strDay = "" + dayOfMonth;
        }

        strResult = year + "-" + strDate + "-" + strDay;
        return strResult;
    }

    /**
     * 데모 일정 수정
     */
    private void reqDeviceScheduleEdit()
    {
        KumaLog.d("++++++++++++ reqDeviceScheduleEdit  ++++++++++++++");
        try {
            ReqDeviceScheduleEdit reqReqDeviceScheduleEdit = new ReqDeviceScheduleEdit(this);

            reqReqDeviceScheduleEdit.setTag(TAG_REQ_DEVICE_EDIT);
            reqReqDeviceScheduleEdit.setSerialNo(mScheduleInfo.getSerialNo());
            reqReqDeviceScheduleEdit.setHostpital(mEvHospital.getText().toString().trim());
            reqReqDeviceScheduleEdit.setStartDate(mTvStartDate.getText().toString().trim());
            reqReqDeviceScheduleEdit.setEndDate(mTvEndDate.getText().toString().trim());
            reqReqDeviceScheduleEdit.setDeliver(mEvDeliver.getText().toString().trim());
            reqReqDeviceScheduleEdit.setReceiver(mEvReceiver.getText().toString().trim());
            reqReqDeviceScheduleEdit.setState(mArrDeviceCode[mNSeletedDeviceState]);
            reqReqDeviceScheduleEdit.setMessage(mEvEtc.getText().toString().trim());


            requestProtocol(true, reqReqDeviceScheduleEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialog(int nTag, String msg,  int nType){
        CommonDialog commonDia = new CommonDialog(this);
        commonDia.setType(nType);
        commonDia.setDialogListener(nTag,this);
        commonDia.setCancelable(false);
        commonDia.setMessage(msg);
        commonDia.show();
    }
    String[] mArrDeviceCode;
    String[] arrStateName;
    private int mNSeletedDeviceState = 0;

    private void  showState(){
        mArrDeviceCode = null;
        arrStateName = null;
        mArrDeviceCode = getResources().getStringArray(R.array.arr_device_state_code);
        arrStateName  = new String[mArrDeviceCode.length];

        for(int i = 0; i < mArrDeviceCode.length; i++  ) {
            arrStateName[i] = Constance.changeStringToState(mArrDeviceCode[i]);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( DeviceDemoDetailActivity.this);

        alertDialogBuilder.setTitle("장비 상태 선택");
        alertDialogBuilder.setItems(arrStateName,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mNSeletedDeviceState = id;
                        mTvState.setText(arrStateName[mNSeletedDeviceState]);
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    /**
     * 데모 일정 변경
     */
    private void resDeviceManage(int tag, CommonResponse resprotocol)
    {
        KumaLog.d("++++++++++++resDeviceManage++++++++++++++");
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            String resultMsg = "수정되었습니다";
            showDialog(DLG_DEVICE_RESULT, resultMsg, CommonDialog.DLG_TYPE_NOTI);
        }  else {
            if( !TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }
    @Override
    public void onResponseProtocol(int nTag, ResponseProtocol resProtocol) {
        resDeviceManage(nTag, (CommonResponse)resProtocol );
    }


    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
        switch (nTag)  {
            case DLG_DEVICE_EDIT_REQ : {
                if( nResult == CommonDialog.RESULT_OK) {
                    reqDeviceScheduleEdit();
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {

        int id  =  view.getId();
        switch (id)  {
            case R.id.btn_confirm  : {
                if ( !mBEditState ) {
                    mBtnConfirm.setText(getResources().getString(R.string.str_btn_confirm));
                    mBEditState = true;
                    changeEditState(true);
                } else {
                    showDialog(DLG_DEVICE_EDIT_REQ, "수정하시겠습니까?", CommonDialog.DLG_TYPE_YES_NO);
                }
                break;
            }
            case R.id.btn_cancle  : {
                if( mBEditState ) {
                    mBEditState = false;
                    changeEditState(false);
                    mBtnConfirm.setText(getResources().getString(R.string.str_btn_edit));
                } else {
                    getAppManager().removeActivity(this);
                    finish();
                }
                break;
            }
            case R.id.tv_startdate  : {
                DATEPICK_STATE = DATEPICK_START;
                showDatePicker(mScheduleInfo.startDate.split("-")  );
                break;
            }
            case R.id.tv_enddate  : {
                DATEPICK_STATE = DATEPICK_END;
                showDatePicker(mScheduleInfo.endDate.split("-")  );
                break;
            }
            case R.id.tv_state  : {
                showState();
                break;
            }

            default:
                break;
        }

    }
}
