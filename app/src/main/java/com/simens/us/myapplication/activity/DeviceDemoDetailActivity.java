package com.simens.us.myapplication.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simens.us.myapplication.BaseActivity;
import com.simens.us.myapplication.Constance.Constance;
import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.request.ReqAgencyMemberList;
import com.simens.us.myapplication.Network.request.ReqDeviceScheduleEdit;
import com.simens.us.myapplication.Network.request.ReqDeviceScheduleInfo;
import com.simens.us.myapplication.Network.request.ReqDeviceStateEdit;
import com.simens.us.myapplication.Network.response.CommonResponse;
import com.simens.us.myapplication.Network.response.ResAgencyMemberList;
import com.simens.us.myapplication.Network.response.ResDeviceScheduleInfo;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.data.DropBoaxCommonDTO;
import com.simens.us.myapplication.data.ScheduleInfo;
import com.simens.us.myapplication.ui.dialog.CommonDialog;
import com.simens.us.myapplication.ui.dialog.DropBoxCommonDialog;
import com.simens.us.myapplication.ui.dialog.IDialogListener;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Kuma on 2018-03-07.
 */

public class DeviceDemoDetailActivity extends BaseActivity implements View.OnClickListener{
    private final static int TAG_START_DATE = 100;
    private final static int TAG_END_DATE = 101;

    private final static int TAG_REQ_DEVICE_INFO = 1000;
    private final static int TAG_REQ_DEVICE_EDIT = 1001;

    private final static int TAG_REQ_DESTINATION_LIST = 1003;
    private final static int TAG_REQ_AGENCY_LIST = 1004;
    private final static int TAG_REQ_AGENCY_MEMBER_LIST = 1005;

    private final static int TAG_REQ_DEVICE_STATE_EDIT = 1006;

    private final static int DLG_DEVICE_EDIT_REQ = 2001;
    private final static int DLG_DEVICE_RESULT = 2002;

    private int nStartYear = 0;
    private int nStartMonth = 0;
    private int nStartDay = 0;

    private int nEndYear = 0;
    private int nEndMonth = 0;
    private int nEndDay = 0;

    private String strReqStartDate = "";
    private String strReqEndDate = "";

    private String strStartDate = "";
    private String strEndDate = "";

    private TextView mTvStartDate;
    private TextView mTvEndDate;

    private TextView mTvProductSelect;

    private RelativeLayout mRlProductSn;
    private TextView mTvProductSn;

    private  Button mBtnEdit;

    private RelativeLayout mRlAgencySelect;
    private TextView mTvAgencySelect;

    private RelativeLayout mRlReciverSelect;
    private TextView mTvReciverSelect;

    private RelativeLayout mRlDestination;
    private TextView mTvDestination;


    private EditText mEvNote;

    private ScheduleInfo mScheduleInfo;
    private ScheduleInfo mDeviceScheduleInfo;

    private String mCurState = "";
    private String mPreState = "";

    private ArrayList<DropBoaxCommonDTO> mArrAgencyMemberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_demo_detail);

        Intent intent = getIntent();
        mScheduleInfo   =  (ScheduleInfo)intent.getSerializableExtra("detailData");

        init();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reqDeviceScheduleInfo();
            }
        }, 100);
    }

    private LinearLayout[] mLlStateLayout = new LinearLayout[5];
    private TextView[] mTvStateText= new TextView[5];

    private void init(){
        findViewById(R.id.iv_back ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppManager().removeActivity(DeviceDemoDetailActivity.this);
                finish();
            }
        });

        View view = findViewById(R.id.view_device_demo_detail);

        mTvStartDate = (TextView)view.findViewById(R.id.tv_start_date);
        mTvEndDate = (TextView)view.findViewById(R.id.tv_end_date);


        mTvProductSelect = (TextView)view.findViewById(R.id.tv_product_select);

        mRlProductSn = (RelativeLayout)view.findViewById(R.id.rl_product_sn);
        mTvProductSn = (TextView)view.findViewById(R.id.tv_product_sn);

//        mEvDeliver = (EditText) view.findViewById(R.id.ev_deliver );

        mRlAgencySelect = (RelativeLayout)view.findViewById(R.id.rl_agency_select);
        mTvAgencySelect = (TextView)view.findViewById(R.id.tv_agency_select);

        mRlReciverSelect = (RelativeLayout)view.findViewById(R.id.rl_reciver_name);
        mTvReciverSelect = (TextView)view.findViewById(R.id.tv_reciver_name);

        mRlDestination = (RelativeLayout)view.findViewById(R.id.rl_destination);
        mTvDestination = (TextView)view.findViewById(R.id.tv_destination);
        mEvNote = (EditText) view.findViewById(R.id.ev_note);

        mEvNote.setEnabled(false);

        mBtnEdit = (Button) view.findViewById(R.id.btn_confirm );

        ((TextView)view.findViewById(R.id.tv_writer)).setText(Constance.USER_NAME);

        int[] mLlStateLayoutID = {R.id.ll_state, R.id.ll_state_1, R.id.ll_state_2, R.id.ll_state_3, R.id.ll_state_4};
        int[] mTvStateTextID = {R.id.tv_state, R.id.tv_state_1, R.id.tv_state_2, R.id.tv_state_3, R.id.tv_state_4};
        for(int i = 0; i < mLlStateLayoutID.length; i++ ){
            mLlStateLayout[i] = (LinearLayout) view.findViewById(mLlStateLayoutID[i] );
            mTvStateText[i] = (TextView) view.findViewById(mTvStateTextID[i] );
        }
        mRlDestination.setOnClickListener(this);
        mRlDestination.setClickable(false);

        mBtnEdit.setOnClickListener(this);
        mBtnEdit.setClickable(false);

        mRlAgencySelect.setOnClickListener(this);
        mRlAgencySelect.setClickable(false);

        mRlReciverSelect.setOnClickListener(this);
        mRlReciverSelect.setClickable(false);
        if(Constance.isManager()) {
            changeEditState(true);
        }  else {
            mBtnEdit.setVisibility(View.GONE);
            changeEditState(false);
        }



        for(int i = 0; i < mLlStateLayout.length; i++ ){
            mTvStateText[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPreState = (String)v.getTag();

                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            CommonDialog commonDia = new CommonDialog(DeviceDemoDetailActivity.this);
                            commonDia.setType(CommonDialog.DLG_TYPE_YES_NO);
                            commonDia.setDialogListener(1, new IDialogListener() {
                                @Override
                                public void onDialogResult(int nTag, int nResult, Dialog dialog) {
                                    if( nResult ==  CommonDialog.RESULT_OK) {
                                        reqDeviceStateEdit(mPreState, mScheduleInfo.getId());
                                    }
                                }
                            });
                            commonDia.setCancelable(false);
                            commonDia.setMessage("장비 상태를 변경하시겠습니까?");
                            commonDia.show();
                        }
                    });

                }
            });
        }


        findViewById(R.id.tv_start_date).setOnClickListener(this);
        findViewById(R.id.tv_end_date).setOnClickListener(this);

    }

    private void changeEditState(boolean state) {
        KumaLog.line();
        KumaLog.d(" changeEditState >>>>>>>>>>>>>>   state  >>>    " + state);
//        mEvDeliver.setEnabled(state);

        if( state ) {
            mRlDestination.setClickable(true);
            mRlDestination.setBackgroundResource(R.drawable.back_input);

            mRlAgencySelect.setClickable(true);
            mRlAgencySelect.setBackgroundResource(R.drawable.back_input);

            mRlReciverSelect.setClickable(true);
            mRlReciverSelect.setBackgroundResource(R.drawable.back_input);

            mEvNote.setEnabled(true);
            mEvNote.setBackgroundResource(R.drawable.back_input);

            mBtnEdit.setClickable(true);
        }  else {
            mRlDestination.setClickable(false);
            mRlDestination.setBackgroundResource(R.drawable.back_input_disable);

            mRlAgencySelect.setClickable(false);
            mRlAgencySelect.setBackgroundResource(R.drawable.back_input_disable);

            mRlReciverSelect.setClickable(false);
            mRlReciverSelect.setBackgroundResource(R.drawable.back_input_disable);

            mEvNote.setEnabled(false);
            mEvNote.setBackgroundResource(R.drawable.back_input_disable);

            mBtnEdit.setClickable(false);

        }
    }

    private void showDatePicker(final int nTag){
        final Calendar cal = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();


        DatePickerDialog dialog = new DatePickerDialog(DeviceDemoDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                String msg = String.format("%d-%d-%d", year, month+1, date);

                if( TAG_START_DATE == nTag ) {
                    nStartYear = year;
                    nStartMonth = month;
                    nStartDay = date;
                    strStartDate = String.format("%d%d%d", year, month, date);

                    String strStartMonth =  "", strStartDay = "";

                    if( ( nStartMonth + 1 ) <  10 ) {
                        strStartMonth =  "0" +  ( nStartMonth + 1 );
                    } else {
                        strStartMonth =  "" + ( nStartMonth + 1 );
                    }

                    if( nStartDay <  10 ) {
                        strStartDay =  "0" +  ( nStartDay + 1 );
                    } else {
                        strStartDay =  "" + ( nStartDay + 1 );
                    }

                    strReqStartDate = nStartYear + "-" +  strStartMonth + "-" + strStartDay;

                    if(TextUtils.isEmpty(strEndDate)) {
                        strEndDate = strStartDate;
                    }

                    if( mTvEndDate.getText().length() > 0  || Integer.parseInt(strStartDate ) > Integer.parseInt(strEndDate )) {
                        mTvEndDate.setText("");
                    }
                    mTvEndDate.setBackgroundResource(R.drawable.back_input);
                    mTvStartDate.setText(msg);
                } else {
                    nEndYear = year;
                    nEndMonth = month;
                    nEndDay = date;
                    strEndDate = String.format("%d%d%d", year, month, date);
                    mTvEndDate.setText(msg);

                    String strEndMonth =  "", strEndDay = "";

                    if( ( nEndMonth + 1 ) <  10 ) {
                        strEndMonth =  "0" +  ( nEndMonth + 1 );
                    } else {
                        strEndMonth =  "" + ( nEndMonth + 1 );
                    }

                    if( nEndDay <  10 ) {
                        strEndDay =  "0" +  ( nEndDay + 1 );
                    } else {
                        strEndDay =  "" + ( nEndDay + 1 );
                    }

                    strReqEndDate = nEndYear + "-" + strEndMonth + "-" + strEndDay;

                }

                KumaLog.d(" DATE : >> " + msg);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

//        dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션

        if( nStartYear > 0 && TAG_START_DATE != nTag ) {
            minDate.set(nStartYear,nStartMonth,nStartDay);
            dialog.getDatePicker().setMinDate(minDate.getTime().getTime());
        } else {
            dialog.getDatePicker();
        }

        dialog.show();
    }

    /**
     * 데모 일정 수정
     */
    private void reqDeviceScheduleEdit()
    {
        KumaLog.d("++++++++++++ reqDeviceScheduleEdit  ++++++++++++++");
        try {
            ReqDeviceScheduleEdit reqReqDeviceScheduleEdit = new ReqDeviceScheduleEdit(this);
            int strDestinationPk = -1, strReceiverAgencyPk = -1, strReceiver = -1;
            if( (int)mTvDestination.getTag() < 0 ) {
                showSimpleMessagePopup("목적지 정보가 없어 수정이 불가능합니다.");
                return;
            }
            strDestinationPk = (int)mTvDestination.getTag();

            if( (int)mTvAgencySelect.getTag() < 0 ) {
                showSimpleMessagePopup("대리점 정보가 없어 수정이 불가능합니다.");
                return;
            }

            strReceiverAgencyPk = (int)mTvAgencySelect.getTag();

            if( (int)mTvReciverSelect.getTag() < 0 ) {
                showSimpleMessagePopup("사원 정보가 없어 수정이 불가능합니다.");
                return;
            }
            strReceiver = (int)mTvReciverSelect.getTag();

            reqReqDeviceScheduleEdit.setTag(TAG_REQ_DEVICE_EDIT);
            reqReqDeviceScheduleEdit.setPk(mScheduleInfo.getId());
            reqReqDeviceScheduleEdit.setDestinationPk(String.valueOf(strDestinationPk));
            reqReqDeviceScheduleEdit.setTitle(mTvDestination.getText().toString().trim());
            reqReqDeviceScheduleEdit.setStartDate(strReqStartDate);
            reqReqDeviceScheduleEdit.setEndDate(strReqEndDate);
            reqReqDeviceScheduleEdit.setReceiverAgencyPk(String.valueOf(strReceiverAgencyPk));
            reqReqDeviceScheduleEdit.setReceiver(String.valueOf(strReceiver));
            reqReqDeviceScheduleEdit.setMessage(mEvNote.getText().toString().trim());

            requestProtocol(true, reqReqDeviceScheduleEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 데모상세 정보
     */
    private void reqDeviceScheduleInfo() {
        try {
            ReqDeviceScheduleInfo reqDeviceScheduleInfo = new ReqDeviceScheduleInfo(this);

            reqDeviceScheduleInfo.setTag(TAG_REQ_DEVICE_INFO);
            reqDeviceScheduleInfo.setPk(mScheduleInfo.getId());
            requestProtocol(true, reqDeviceScheduleInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 데모상세 정보
     */
    private void resDeviceScheduleInfo(ResDeviceScheduleInfo resprotocol) {
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resDeviceScheduleInfo 11  ++++++++++++++");

            mDeviceScheduleInfo = resprotocol.getDeviceInfo();

            mTvProductSn.setText(resprotocol.getDeviceInfo().getSerialNo());
            mTvProductSelect.setText(resprotocol.getDeviceInfo().getTitle());
            mTvStartDate.setText( resprotocol.getDeviceInfo().getStartDate()  );
            mTvEndDate.setText( resprotocol.getDeviceInfo().getEndDate() );
            mTvAgencySelect.setText( Constance.getAgencyData(resprotocol.getDeviceInfo().getAgencyPk()).getName() );
            mTvAgencySelect.setTag(resprotocol.getDeviceInfo().getAgencyPk());

            mTvDestination.setText(Constance.getDestinationData(resprotocol.getDeviceInfo().getDestinationPk()).getName());
            mTvDestination.setTag(resprotocol.getDeviceInfo().getDestinationPk());


            mEvNote.setText(resprotocol.getDeviceInfo().getMessage());
            mCurState = resprotocol.getDeviceInfo().getState();
            Constance.changeStateView(resprotocol.getDeviceInfo().getState(), mLlStateLayout, mTvStateText);

            reqAgencyMemberList(mDeviceScheduleInfo.getAgencyPk());
        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }

    /**
     * 제품데모 상태변경
     */
    private void reqDeviceStateEdit(String  state, String pk) {
        try {
            ReqDeviceStateEdit reqDeviceStateEdit = new ReqDeviceStateEdit(this);

            reqDeviceStateEdit.setTag(TAG_REQ_DEVICE_STATE_EDIT);
            reqDeviceStateEdit.setPk(pk);
            reqDeviceStateEdit.setState(state);
            requestProtocol(true, reqDeviceStateEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 제품데모 상태변경
     */
    private void resDeviceStateEdit(CommonResponse resprotocol) {
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resDeviceStateEdit  ++++++++++++++");
            showSimpleMessagePopup("상태변경이 성공하였습니다.");
            mCurState = mPreState;
            Constance.changeStateView(mCurState, mLlStateLayout, mTvStateText);
        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }

    private boolean mbState = false;

    /**
     * 본사 및 대리점 회원 조회
     */
    private void reqAgencyMemberList(int pk) {
        try {
            ReqAgencyMemberList reqAgencyMemberList = new ReqAgencyMemberList(this);

            reqAgencyMemberList.setTag(TAG_REQ_AGENCY_MEMBER_LIST);
            reqAgencyMemberList.setPk(String.valueOf(pk));

            requestProtocol(true, reqAgencyMemberList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 본사 및 대리점 회원 조회
     */
    private void resAgencyMemberList(ResAgencyMemberList resprotocol) {
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resAgencyMemberList  ++++++++++++++ mbState >>> "  + mbState);
            mArrAgencyMemberList.clear();
            mArrAgencyMemberList.addAll(resprotocol.getmArrDropBoaxCommonDTO());
            if( !mbState ) {
                for(int i = 0; i < mArrAgencyMemberList.size();  i++ ) {
                    if( mArrAgencyMemberList.get(i).getPk() == mDeviceScheduleInfo.getReceiver()) {
                        mTvReciverSelect.setText(mArrAgencyMemberList.get(i).getName());
                        mTvReciverSelect.setTag(mArrAgencyMemberList.get(i).getPk());
                    }
                }
            } else if(mbState &&  mArrAgencyMemberList.size() > 0){
                mTvReciverSelect.setText(mArrAgencyMemberList.get(0).getName());
                mTvReciverSelect.setTag(mArrAgencyMemberList.get(0).getPk());
            }

            if( mArrAgencyMemberList.size() == 0 )  {
                mTvReciverSelect.setText("사원 없음");
                mTvReciverSelect.setTag(-1);
            }
            mbState = false;
        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
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
                if( TAG_REQ_DESTINATION_LIST  == tag)  {
                    mTvDestination.setText(data.getName());
                    mTvDestination.setTag(data.getPk());
                } else if( TAG_REQ_AGENCY_LIST  == tag)  {
                    mTvAgencySelect.setText(data.getName());
                    mTvAgencySelect.setTag(data.getPk());
                    mbState = true;
                    reqAgencyMemberList(data.getPk());
                } else if( TAG_REQ_AGENCY_MEMBER_LIST  == tag)  {
                    mTvReciverSelect.setText(data.getName());
                    mTvReciverSelect.setTag(data.getPk());
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
    private void showDialog(int nTag, String msg,  int nType){
        CommonDialog commonDia = new CommonDialog(this);
        commonDia.setType(nType);
        commonDia.setDialogListener(nTag,this);
        commonDia.setCancelable(false);
        commonDia.setMessage(msg);
        commonDia.show();
    }
    /**
     * 데모 일정 변경
     */
    private void resDeviceScheduleEdit(int tag, CommonResponse resprotocol)
    {
        KumaLog.d("++++++++++++resDeviceManage++++++++++++++");
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            String resultMsg = "수정되었습니다";
            showDialog(DLG_DEVICE_RESULT, resultMsg, CommonDialog.DLG_TYPE_NOTI);

//            changeEditState(false);
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

        switch (nTag)  {
            case TAG_REQ_DEVICE_EDIT : {
                resDeviceScheduleEdit(nTag, (CommonResponse)resProtocol );
                break;
            }
            case TAG_REQ_DEVICE_STATE_EDIT : {
                resDeviceStateEdit((CommonResponse)resProtocol);
                break;
            }
            case TAG_REQ_DEVICE_INFO : {
                resDeviceScheduleInfo((ResDeviceScheduleInfo)resProtocol);
                break;
            }
            case TAG_REQ_AGENCY_MEMBER_LIST : {
                resAgencyMemberList((ResAgencyMemberList)resProtocol);
                break;
            }

            default:
                break;
        }



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
                showDialog(DLG_DEVICE_EDIT_REQ, "수정하시겠습니까?", CommonDialog.DLG_TYPE_YES_NO);
                break;
            }
            case R.id.tv_start_date   : {
                showDatePicker(TAG_START_DATE );
                break;
            }
            case R.id.tv_end_date : {
                showDatePicker(TAG_END_DATE );
                break;
            }

            case R.id.rl_agency_select : {
                showDropBoxCommonDialog(TAG_REQ_AGENCY_LIST, Constance.mArrAgencyList);
                break;
            }

            case R.id.rl_reciver_name : {
                if( mArrAgencyMemberList.size() > 0  ) {
                    showDropBoxCommonDialog(TAG_REQ_AGENCY_MEMBER_LIST, mArrAgencyMemberList);
                }
                break;
            }
            case R.id.rl_destination : {
                showDropBoxCommonDialog(TAG_REQ_DESTINATION_LIST, Constance.mArrDestinationList);
                break;
            }
            default:
                break;
        }

    }
}
