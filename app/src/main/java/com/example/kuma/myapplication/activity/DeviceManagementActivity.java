package com.example.kuma.myapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kuma.myapplication.BaseActivity;
import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.request.ReqDeviceDelete;
import com.example.kuma.myapplication.Network.request.ReqDeviceEdit;
import com.example.kuma.myapplication.Network.request.ReqDeviceInsert;
import com.example.kuma.myapplication.Network.request.ReqMainDeviceList;
import com.example.kuma.myapplication.Network.response.CommonResponse;
import com.example.kuma.myapplication.Network.response.ResMainDeviceList;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.data.DeviceInfo;
import com.example.kuma.myapplication.ui.dialog.CommonDialog;

/**
 * Created by Kuma on 2018-02-22.
 */

public class DeviceManagementActivity extends BaseActivity implements View.OnClickListener{
    private final static int TAG_REQ_DEVICE_INSERT = 1001;
    private final static int TAG_REQ_DEVICE_EDIT = 1002;
    private final static int TAG_REQ_DEVICE_DELETE = 1003;

    private final static int DLG_DEVICE_INSERT_REQ = 2001;
    private final static int DLG_DEVICE_EDIT_REQ = 2002;
    private final static int DLG_DEVICE_DELETE_REQ = 2003;
    private final static int DLG_DEVICE_RESULT = 2004;


    private final static int TAG_STATE_INSERT = 101;
    private final static int TAG_STATE_EDIT = 102;
    private final static int TAG_STATE_DELETE = 103;

    private int TAG_CURRENT_STATE = TAG_STATE_INSERT;

    private EditText mEvModel;
    private EditText mEvSerial;
    private EditText mEvMakeYear;
    private EditText mEvVersion;
    private EditText mEvEtc;

    private TextView mTvModel;
    private TextView mTvSerial;
    private TextView mTvMakeYear;
    private TextView mTvVersion;
    private TextView mTvEtc;

    private Button mBtnConfirm;
    private Button mBtnCancle;

    private DeviceInfo  mDeviceInfo;

    private String mSerialNum = "";

    private boolean  mBEditState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_manage);

        Intent intent = getIntent();
        if( intent != null && intent.getBooleanExtra("dataState", false))  {
            TAG_CURRENT_STATE = intent.getIntExtra("type", 102);
            mDeviceInfo   =  (DeviceInfo)intent.getSerializableExtra("detailData");
        } else {
            KumaLog.d(" mSerialNum: " + mSerialNum);
            if( intent.getStringExtra("serialNum") != null ) {
                mSerialNum =  intent.getStringExtra("serialNum");
            }
        }

        KumaLog.d(" mSerialNum: " + mSerialNum);
        if( TAG_CURRENT_STATE  == TAG_STATE_EDIT || TAG_CURRENT_STATE == TAG_STATE_INSERT ) {
            initInsertEdit();
        } else {
            initDelete();
        }
    }

    private void initInsertEdit(){
        View view = findViewById(R.id.view_insert_edit);

        view.setVisibility(View.VISIBLE);

        mEvModel = (EditText) view.findViewById(R.id.ev_model );
        mEvSerial = (EditText) view.findViewById(R.id.ev_serial );
        mEvMakeYear = (EditText) view.findViewById(R.id.ev_make );
        mEvVersion = (EditText) view.findViewById(R.id.ev_version );
        mEvEtc = (EditText) view.findViewById(R.id.ev_etc );

        mBtnCancle = (Button) view.findViewById(R.id.btn_cancle );
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm );

        mBtnConfirm.setOnClickListener(this);
        mBtnCancle.setOnClickListener(this);

        if( TAG_CURRENT_STATE  == TAG_STATE_EDIT )  {
            mBtnConfirm.setText(getResources().getString(R.string.str_btn_edit));
            mEvModel.setText(mDeviceInfo.getProductCode());
            mEvSerial.setText(mDeviceInfo.getSerialNo());
            mEvMakeYear.setText(mDeviceInfo.getMakeYear());
            mEvVersion.setText(mDeviceInfo.getVersion());
            mEvEtc.setText(mDeviceInfo.getMessage());
            mEvSerial.setEnabled(false);
            changeEditState(false);
        } else {
            mBtnConfirm.setText(getResources().getString(R.string.str_btn_insert));
            if( !TextUtils.isEmpty(mSerialNum)) {
                mEvSerial.setText(mSerialNum);
                mEvSerial.setEnabled(false);
            }
        }
    }

    private void changeEditState(boolean state) {
        mEvModel.setEnabled(state);
        mEvVersion.setEnabled(state);
        mEvMakeYear.setEnabled(state);
        mEvEtc.setEnabled(state);

        if( !state ) {
            mEvModel.setText(mDeviceInfo.getProductCode());
            mEvSerial.setText(mDeviceInfo.getSerialNo());
            mEvMakeYear.setText(mDeviceInfo.getMakeYear());
            mEvVersion.setText(mDeviceInfo.getVersion());
            mEvEtc.setText(mDeviceInfo.getMessage());
        }
    }

    private void initDelete(){
        View view = findViewById(R.id.view_delete);

        view.setVisibility(View.VISIBLE);

        mTvModel = (TextView) view.findViewById(R.id.tv_model );
        mTvSerial = (TextView) view.findViewById(R.id.tv_serial );
        mTvMakeYear = (TextView) view.findViewById(R.id.tv_make );
        mTvVersion = (TextView) view.findViewById(R.id.tv_version );
        mTvEtc = (TextView) view.findViewById(R.id.tv_etc );

        mBtnCancle = (Button) view.findViewById(R.id.btn_cancle );
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm );

        mBtnConfirm.setOnClickListener(this);
        mBtnCancle.setOnClickListener(this);

        mTvModel.setText(mDeviceInfo.getProductCode());
        mTvSerial.setText(mDeviceInfo.getSerialNo());
        mTvMakeYear.setText(mDeviceInfo.getMakeYear());
        mTvVersion.setText(mDeviceInfo.getVersion());
        mTvEtc.setText(mDeviceInfo.getMessage());
    }

    /**
     * 제품등록
     */
    private void reqDeviceInsert()
    {
        KumaLog.d("++++++++++++ reqDeviceInsert  ++++++++++++++");
        try {
            ReqDeviceInsert reqDeviceInsert = new ReqDeviceInsert();

            reqDeviceInsert.setTag(TAG_REQ_DEVICE_INSERT);
            reqDeviceInsert.setMakeYear(mEvMakeYear.getText().toString().trim());
            reqDeviceInsert.setMessage(mEvEtc.getText().toString().trim());
            reqDeviceInsert.setProductCode(mEvModel.getText().toString().trim());
            reqDeviceInsert.setSerialNo(mEvSerial.getText().toString().trim());
            reqDeviceInsert.setVersion(mEvVersion.getText().toString().trim());

            requestProtocol(true, reqDeviceInsert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 제품수정
     */
    private void reqDeviceEdit()
    {
        KumaLog.d("++++++++++++ reqDeviceEdit  ++++++++++++++");
        try {
            ReqDeviceEdit reqDeviceEdit = new ReqDeviceEdit();

            reqDeviceEdit.setTag(TAG_REQ_DEVICE_EDIT);
            reqDeviceEdit.setMakeYear(mEvMakeYear.getText().toString().trim());
            reqDeviceEdit.setMessage(mEvEtc.getText().toString().trim());
            reqDeviceEdit.setProductCode(mEvModel.getText().toString().trim());
            reqDeviceEdit.setSerialNo(mEvSerial.getText().toString().trim());
            reqDeviceEdit.setVersion(mEvVersion.getText().toString().trim());


            requestProtocol(true, reqDeviceEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 제품삭제
     */
    private void reqDeviceDelete()
    {
        KumaLog.d("++++++++++++ reqDeviceDelete  ++++++++++++++");
        try {
            ReqDeviceDelete reqDeviceDelete = new ReqDeviceDelete();

            reqDeviceDelete.setTag(TAG_REQ_DEVICE_DELETE);
            reqDeviceDelete.setSerialNo(mTvSerial.getText().toString().trim());

            requestProtocol(true, reqDeviceDelete);
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
    /**
     * 장비 상태 변경( 등록, 변경, 삭제 )
     */
    private void resDeviceManage(int tag, CommonResponse resprotocol)
    {
        KumaLog.d("++++++++++++resDeviceManage++++++++++++++");
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            String resultMsg = "";
            if( TAG_CURRENT_STATE  == TAG_STATE_INSERT ) {
                resultMsg = "등록되었습니다";
            } else if( TAG_CURRENT_STATE  == TAG_STATE_EDIT ) {
                resultMsg = "수정되었습니다";
            } else if( TAG_CURRENT_STATE  == TAG_STATE_DELETE ) {
                resultMsg = "삭제되었습니다";
            }
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
            case DLG_DEVICE_INSERT_REQ : {
                if( nResult == CommonDialog.RESULT_OK) {
                    reqDeviceInsert();
                }
                break;
            }
            case DLG_DEVICE_EDIT_REQ : {
                if( nResult == CommonDialog.RESULT_OK) {
                    reqDeviceEdit();
                }
                break;
            }
            case DLG_DEVICE_DELETE_REQ : {
                if( nResult == CommonDialog.RESULT_OK) {
                    reqDeviceDelete();
                }
                break;
            }
            case DLG_DEVICE_RESULT : {
                getAppManager().removeActivity(this);
                finish();
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
                if( TAG_CURRENT_STATE  == TAG_STATE_INSERT ) {
                    showDialog(DLG_DEVICE_INSERT_REQ, "등록하시겠습니까?", CommonDialog.DLG_TYPE_YES_NO);
                } else if( TAG_CURRENT_STATE  == TAG_STATE_EDIT ) {
                    if ( !mBEditState ) {
                        mBtnConfirm.setText(getResources().getString(R.string.str_btn_confirm));
                        mBEditState = true;
                        changeEditState(true);
                    } else {
                        showDialog(DLG_DEVICE_EDIT_REQ, "수정하시겠습니까?", CommonDialog.DLG_TYPE_YES_NO);
                    }
                } else if( TAG_CURRENT_STATE  == TAG_STATE_DELETE ) {
                    showDialog(DLG_DEVICE_DELETE_REQ, "삭제하시겠습니까?", CommonDialog.DLG_TYPE_YES_NO);
                }
                break;
            }
            case R.id.btn_cancle  : {
                if( TAG_CURRENT_STATE  == TAG_STATE_EDIT && mBEditState) {
                    mBEditState = false;
                    changeEditState(false);
                    mBtnConfirm.setText(getResources().getString(R.string.str_btn_edit));
                } else {
                    getAppManager().removeActivity(this);
                    finish();
                }
                break;
            }
            default:
                break;
        }

    }
}
