package com.simens.us.myapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simens.us.myapplication.BaseActivity;
import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.request.ReqAgencyMemberList;
import com.simens.us.myapplication.Network.request.ReqDeviceDelete;
import com.simens.us.myapplication.Network.request.ReqDeviceEdit;
import com.simens.us.myapplication.Network.request.ReqDeviceInsert;
import com.simens.us.myapplication.Network.request.ReqDeviceModelList;
import com.simens.us.myapplication.Network.request.ReqDeviceOsName;
import com.simens.us.myapplication.Network.request.ReqMainDeviceList;
import com.simens.us.myapplication.Network.request.ReqModelOSList;
import com.simens.us.myapplication.Network.response.CommonResponse;
import com.simens.us.myapplication.Network.response.ResAgencyMemberList;
import com.simens.us.myapplication.Network.response.ResDeviceModelList;
import com.simens.us.myapplication.Network.response.ResDeviceOsName;
import com.simens.us.myapplication.Network.response.ResMainDeviceList;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.data.DeviceInfo;
import com.simens.us.myapplication.data.DropBoaxCommonDTO;
import com.simens.us.myapplication.ui.dialog.CommonDialog;
import com.simens.us.myapplication.ui.dialog.DropBoxCommonDialog;

import java.util.ArrayList;

/**
 * Created by Kuma on 2018-02-22.
 */

public class DeviceManagementActivity extends BaseActivity implements View.OnClickListener{
    private final static int TAG_REQ_DEVICE_INSERT = 1001;
    private final static int TAG_REQ_DEVICE_EDIT = 1002;
    private final static int TAG_REQ_DEVICE_DELETE = 1003;
    private final static int TAG_REQ_DEVICE_OS_LIST = 1004;
    private final static int TAG_REQ_DEVICE_TYPE_LIST = 1005;
    private final static int TAG_REQ_DEVICE_MODEL_LIST = 1006;


    private final static int DLG_DEVICE_INSERT_REQ = 2001;
    private final static int DLG_DEVICE_EDIT_REQ = 2002;
    private final static int DLG_DEVICE_DELETE_REQ = 2003;
    private final static int DLG_DEVICE_RESULT = 2004;



    public final static int TAG_STATE_INSERT = 101;
    public final static int TAG_STATE_EDIT = 102;
    public final static int TAG_STATE_DELETE = 103;

    private int TAG_CURRENT_STATE = TAG_STATE_INSERT;

    private String TAG_DEVICE_TYPE = "M";

    private RelativeLayout mRlSelectModel;
    private RelativeLayout mRlModelType;

    private TextView mTvSelectModelTitle;
    private TextView mTvSelectModel;
    private TextView mTvModelType;
    private TextView mTvMakeDate;

    private EditText mEvMakeDate;
    private EditText mEvEtc;
    private EditText mEvSerialNum;

    private Button mBtnConfirm;
    private Button mBtnDelete;

    private DeviceInfo  mDeviceInfo;

    private String mSerialNum = "";

    private boolean  mBEditState = false;

    private ArrayList<DropBoaxCommonDTO> mArrDeviceOsList = new ArrayList<>();
    private ArrayList<DropBoaxCommonDTO> mArrModelList = new ArrayList<>();
    private ArrayList<DropBoaxCommonDTO> mArrDeviceTypeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_manage);

        Intent intent = getIntent();

        if( intent != null ) {
            if(  intent.getBooleanExtra("dataState", false))  {
                mDeviceInfo   =  (DeviceInfo)intent.getSerializableExtra("detailData");
                mSerialNum = mDeviceInfo.getSerialNo();
            } else {
                if( intent.getStringExtra("serialNum") != null ) {
                    mSerialNum =  intent.getStringExtra("serialNum");
                }
            }
            TAG_CURRENT_STATE = intent.getIntExtra("type", TAG_REQ_DEVICE_INSERT);
            TAG_DEVICE_TYPE =  intent.getStringExtra("device_type");
        }


        KumaLog.d(" mSerialNum: " + mSerialNum);
        init();
        insertTypeInfo();
    }

    private void insertTypeInfo(){
//        제품종류(M : 장비, P : 프로브 , A : 악세사리)
        DropBoaxCommonDTO  data1 = new DropBoaxCommonDTO();
        data1.setModelKind("M");
        data1.setName("장비");

        DropBoaxCommonDTO  data2 = new DropBoaxCommonDTO();
        data2.setModelKind("P");
        data2.setName("프로브");

        DropBoaxCommonDTO  data3 = new DropBoaxCommonDTO();
        data3.setModelKind("A");
        data3.setName("악세사리");

        mArrDeviceTypeList.add(data1);
        mArrDeviceTypeList.add(data2);
        mArrDeviceTypeList.add(data3);

        mTvModelType.setText(mArrDeviceTypeList.get(0).getName());
        mTvModelType.setTag(mArrDeviceTypeList.get(0).getModelKind());
    }

    private  void init(){
        View view = findViewById(R.id.view_insert_edit);

        view.setVisibility(View.VISIBLE);

        mRlSelectModel = (RelativeLayout) view.findViewById(R.id.rl_select_model );
        mRlModelType = (RelativeLayout) view.findViewById(R.id.rl_model_type );

        mTvSelectModelTitle = (TextView) view.findViewById(R.id.tv_select_model_title );
        mTvSelectModel = (TextView) view.findViewById(R.id.tv_select_model );
        mTvModelType = (TextView) view.findViewById(R.id.tv_model_type );
        mTvMakeDate = (TextView) view.findViewById(R.id.tv_make_date );

        mEvMakeDate = (EditText) view.findViewById(R.id.ev_make_date );
        mEvEtc = (EditText) view.findViewById(R.id.ev_etc );
        mEvSerialNum = (EditText) view.findViewById(R.id.ev_serial );


        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm );
        mBtnDelete = (Button) view.findViewById(R.id.btn_delete );

        mBtnDelete.setVisibility(View.GONE);
        mTvModelType.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mRlSelectModel.setOnClickListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if( TextUtils.isEmpty(mSerialNum)) {
                    reqDeviceOsName();
                } else {
                    initBarcode();
                }
            }
        },100);

        findViewById(R.id.iv_back ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppManager().removeActivity(DeviceManagementActivity.this);
                finish();
            }
        });
    }

    private void initBarcode(){
        if( !TAG_DEVICE_TYPE.equals("M")) {
            mEvMakeDate.setHint("제조년도를 입력해주세요.(ex 2002)");
            mEvMakeDate.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
            reqDeviceModelList(TAG_DEVICE_TYPE);
        } else {
            mEvMakeDate.setHint("제조일을 입력해주세요.(ex  2002-09-14)");
            mEvMakeDate.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});
            reqDeviceOsName();
        }

        for( DropBoaxCommonDTO data  : mArrDeviceTypeList ) {
            if( data.getModelKind().equals(TAG_DEVICE_TYPE)) {
                mTvModelType.setText(data.getName());
                mTvModelType.setTag(data.getModelKind());
            }
        }


        if( TAG_CURRENT_STATE  == TAG_STATE_EDIT )  {
            mBtnConfirm.setText(getResources().getString(R.string.str_btn_edit));
            mEvSerialNum.setText(mDeviceInfo.getSerialNo());
            mEvSerialNum.setEnabled(false);
            mEvMakeDate.setText(mDeviceInfo.getMakeDate());
            mEvEtc.setText(mDeviceInfo.getMessage());

            mBtnDelete.setVisibility(View.VISIBLE);
            mTvModelType.setEnabled(false);
            mRlModelType.setBackgroundResource(R.drawable.back_input_disable);
            mEvSerialNum.setBackgroundResource(R.drawable.back_input_disable);
        } else {
            mBtnConfirm.setText(getResources().getString(R.string.str_btn_insert));
            if( !TextUtils.isEmpty(mSerialNum)) {
                mEvSerialNum.setText(mSerialNum);
            }
        }
    }
    /**
     * 장비의OS버젼정보 조회
     */
    private void reqDeviceOsName() {
        try {
            ReqDeviceOsName reqDeviceOsName = new ReqDeviceOsName(this);

            reqDeviceOsName.setTag(TAG_REQ_DEVICE_OS_LIST);

            requestProtocol(true, reqDeviceOsName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 장비의OS버젼정보 조회
     */
    private void resDeviceOsName(ResDeviceOsName resprotocol) {
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resDeviceOsName  ++++++++++++++  ");
            mArrDeviceOsList.clear();
            mArrDeviceOsList.addAll(resprotocol.getmArrDropBoaxCommonDTO());
            mTvSelectModelTitle.setText("장비 모델  : ");

            if( TAG_CURRENT_STATE  == TAG_STATE_EDIT )  {
                for(DropBoaxCommonDTO data : mArrDeviceOsList){
                    if( data.getPk() == Integer.parseInt(mDeviceInfo.getOsPk())) {
                        mTvSelectModel.setText(data.getName());
                        mTvSelectModel.setTag(data.getPk());
                        break;
                    }
                }
            } else {
                mTvSelectModel.setText(mArrDeviceOsList.get(0).getName());
                mTvSelectModel.setTag(mArrDeviceOsList.get(0).getPk());
            }

        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }

    /**
     * 제품모델 조회
     */
    private void reqDeviceModelList(String strType) {
        try {
            ReqDeviceModelList reqDeviceModelList = new ReqDeviceModelList(this);

            reqDeviceModelList.setTag(TAG_REQ_DEVICE_MODEL_LIST);
            reqDeviceModelList.setKind(strType);
            requestProtocol(true, reqDeviceModelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 장비의OS버젼정보 조회
     */
    private void resDeviceModelList(ResDeviceModelList resprotocol) {
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resDeviceModelList  ++++++++++++++  ");
            mArrModelList.clear();
            mArrModelList.addAll(resprotocol.getmArrDropBoaxCommonDTO());

            mTvSelectModelTitle.setText("모델명 : ");
            if( TAG_CURRENT_STATE  == TAG_STATE_EDIT )  {
                for(DropBoaxCommonDTO data : mArrModelList){
                    if( data.getPk() == Integer.parseInt(mDeviceInfo.getModelPk())) {
                        mTvSelectModel.setText(data.getName());
                        mTvSelectModel.setTag(data.getPk());
                        break;
                    }
                }
                if( TAG_DEVICE_TYPE.equals("M")){

                } else {
                    mTvSelectModel.setTag(mDeviceInfo.getModelPk());
                }
            } else {
                mTvSelectModel.setText(mArrModelList.get(0).getName());
                mTvSelectModel.setTag(mArrModelList.get(0).getPk());
            }


        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }

    /**
     * 제품등록
     */
    private void reqDeviceInsert()
    {
        KumaLog.d("++++++++++++ reqDeviceInsert  ++++++++++++++");
        try {
            ReqDeviceInsert reqDeviceInsert = new ReqDeviceInsert(this);

            String strKind = (String)mTvModelType.getTag();
            reqDeviceInsert.setTag(TAG_REQ_DEVICE_INSERT);

            reqDeviceInsert.setKind((String)mTvModelType.getTag());

            reqDeviceInsert.setSerialNo(mEvSerialNum.getText().toString().trim());

            String strMakeDate = "";

            if( strKind.equals("M")){
                String strDate = mEvMakeDate.getText().toString().trim();
                strMakeDate = strDate.substring(0, 4) +  "-"  + strDate.substring(4, 6) +  "-" + strDate.substring(6, 8);
                reqDeviceInsert.setOsPk(String.valueOf(mTvSelectModel.getTag()));
                reqDeviceInsert.setModelPk("");
            } else {
                strMakeDate = mEvMakeDate.getText().toString().trim();
                reqDeviceInsert.setModelPk(String.valueOf(mTvSelectModel.getTag()));
                reqDeviceInsert.setOsPk("");
            }

            reqDeviceInsert.setMakeDate(strMakeDate);

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
            ReqDeviceEdit reqDeviceEdit = new ReqDeviceEdit(this);
//            pk : 고유번호
//            modelPk : 모델번호
//            osPk : OS버젼 (장비인 경우에만 해당)
//            makeDate : 제조일 (장비인경우 : YYYY-MM-DD 형식, 그외 : YYYY형식)
//            state : 상태 (N:정상, T:상태이상)
            reqDeviceEdit.setTag(TAG_REQ_DEVICE_EDIT);
            reqDeviceEdit.setMakeDate(mEvMakeDate.getText().toString().trim());
//            reqDeviceEdit.setMessage(mEvEtc.getText().toString().trim());
            String strKind = (String)mTvModelType.getTag();

            if( strKind.equals("M")){
                reqDeviceEdit.setOsPk(String.valueOf(mTvSelectModel.getTag()));
                reqDeviceEdit.setModelPk("");
            } else {
                reqDeviceEdit.setModelPk(String.valueOf(mTvSelectModel.getTag()));
                reqDeviceEdit.setOsPk("");
            }
            reqDeviceEdit.setPk(mDeviceInfo.getPk());
            reqDeviceEdit.setState("N");
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
            ReqDeviceDelete reqDeviceDelete = new ReqDeviceDelete(this);

            reqDeviceDelete.setTag(TAG_REQ_DEVICE_DELETE);
            reqDeviceDelete.setPk(mDeviceInfo.getPk());

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

    /**
     * 장비 상태 변경( 등록, 변경, 삭제 )
     */
    private void resDeviceDelete(CommonResponse resprotocol)
    {
        KumaLog.d("++++++++++++resDeviceManage++++++++++++++");
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            String resultMsg = "";
            resultMsg = "삭제되었습니다";
            showDialog(DLG_DEVICE_RESULT, resultMsg, CommonDialog.DLG_TYPE_NOTI);
        }  else {
            if( !TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }

    private void showDropBoxCommonDialog(final int Tag, ArrayList<DropBoaxCommonDTO> data){
        DropBoxCommonDialog dlg = new DropBoxCommonDialog(this);

        dlg.setOnItemClickListener(new DropBoxCommonDialog.OnItemClickListener() {
            @Override
            public void onItemClick(int tag, DropBoaxCommonDTO data) {
                if( TAG_REQ_DEVICE_OS_LIST  == tag)  {
                    mTvSelectModel.setText(data.getName());
                    mTvSelectModel.setTag(data.getPk());
                } else if( TAG_REQ_DEVICE_TYPE_LIST  == tag)  {
                    mTvModelType.setText(data.getName());
                    mTvModelType.setTag(data.getModelKind());

                    if( !data.getModelKind().equals("M")) {
                        mEvMakeDate.setHint("제조년도를 입력해주세요.(ex 2002)");
                        mEvMakeDate.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
                        reqDeviceModelList(data.getModelKind());
                    } else {
                        mEvMakeDate.setHint("제조일을 입력해주세요.(ex  20020914)");
                        mEvMakeDate.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                        reqDeviceOsName();
                    }
                }
            }
        });
        dlg.show();

        dlg.setData(data);
        dlg.setDataTag(Tag);

//
//        dlgProductChoide.setDialogListener(DLG_PRODUCT_SELECT, this);
//        dlgProductChoide.show();
//        dlgProductChoide.setData(data);

    }
    @Override
    public void onResponseProtocol(int nTag, ResponseProtocol resProtocol) {
        switch (nTag)  {
            case TAG_REQ_DEVICE_OS_LIST : {
                resDeviceOsName((ResDeviceOsName) resProtocol );
                break;
            }
            case TAG_REQ_DEVICE_MODEL_LIST : {
                resDeviceModelList((ResDeviceModelList) resProtocol );
                break;
            }
            case TAG_REQ_DEVICE_INSERT : {
                resDeviceManage(nTag, (CommonResponse)resProtocol );
                break;
            }
            case TAG_REQ_DEVICE_EDIT : {
                resDeviceManage(nTag, (CommonResponse)resProtocol );
                break;
            }

            case TAG_REQ_DEVICE_DELETE : {
                resDeviceDelete((CommonResponse)resProtocol );
                break;
            }
            default:
                break;
        }
//
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
                    showDialog(DLG_DEVICE_EDIT_REQ, "수정하시겠습니까?", CommonDialog.DLG_TYPE_YES_NO);
                }
                break;
            }
            case R.id.btn_delete  : {
                showDialog(DLG_DEVICE_DELETE_REQ, "삭제하시겠습니까?", CommonDialog.DLG_TYPE_YES_NO);
                break;
            }

            case R.id.btn_cancle  : {
                if( TAG_CURRENT_STATE  == TAG_STATE_EDIT && mBEditState) {
                    mBEditState = false;
                    mBtnConfirm.setText(getResources().getString(R.string.str_btn_edit));
                } else {
                    getAppManager().removeActivity(this);
                    finish();
                }
                break;
            }
            case R.id.rl_select_model  :
                String strType = String.valueOf(mTvModelType.getTag()) ;
                if( !strType.equals("M")) {
                    showDropBoxCommonDialog(TAG_REQ_DEVICE_OS_LIST, mArrModelList);
                } else {
                    showDropBoxCommonDialog(TAG_REQ_DEVICE_OS_LIST, mArrDeviceOsList);
                }

                break;
            case R.id.tv_model_type  :
                showDropBoxCommonDialog(TAG_REQ_DEVICE_TYPE_LIST, mArrDeviceTypeList);
            break;



            default:
                break;
        }

    }
}
