package com.simens.us.myapplication.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.simens.us.myapplication.Network.request.ReqAppsScheduleAdd;
import com.simens.us.myapplication.Network.request.ReqAppsScheduleDetail;
import com.simens.us.myapplication.Network.request.ReqAppsScheduleEdit;
import com.simens.us.myapplication.Network.request.ReqDeviceScheduleInsert;
import com.simens.us.myapplication.Network.response.CommonResponse;
import com.simens.us.myapplication.Network.response.ResAgencyMemberList;
import com.simens.us.myapplication.Network.response.ResAppsScheduleDetail;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.data.AppsScheduleInfo;
import com.simens.us.myapplication.data.DropBoaxCommonDTO;
import com.simens.us.myapplication.ui.dialog.CommonDialog;
import com.simens.us.myapplication.ui.dialog.DropBoxCommonDialog;
import com.simens.us.myapplication.ui.dialog.IDialogListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AppsScheduleInsertActivity extends BaseActivity implements View.OnClickListener {
    private final static int TAG_START_DATE = 100;
    private final static int TAG_END_DATE = 101;

    private static final int TAG_REQ_SCHEDULE_INSERT = 1000;
    private static final int TAG_REQ_AGENCY_MEMBER_LIST = 1001;

    private static final int TAG_REQ_DESTINATION_LIST = 2000;

    private RelativeLayout mRlReciver;
    private TextView mTvReciver;

    private TextView mTvStartDate;
    private TextView mTvEndDate;

    private RelativeLayout mRlDestination;
    private TextView mTvDestination;

    private EditText mEvDestination;
    private EditText mEvEtc;

    private Button mBtnConfirm;


    private LinearLayout[] mLlStateLayout = new LinearLayout[4];
    private TextView[] mTvStateText= new TextView[4];

    private int nStartYear = 0;
    private int nStartMonth = 0;
    private int nStartDay = 0;

    private int nEndYear = 0;
    private int nEndMonth = 0;
    private int nEndDay = 0;

    private String strStartDate = "";
    private String strEndDate = "";

    private String strReqStartDate = "";
    private String strReqEndDate = "";

    private String mPreState =  "";
    private String mCurState =  "";

    private ArrayList<DropBoaxCommonDTO> mArrAgencyMemberList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_apps_schedule_insert);


        init();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reqAgencyMemberList();
            }
        },100);
    }

    private void init(){
        findViewById(R.id.iv_back ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppManager().removeActivity(AppsScheduleInsertActivity.this);
                finish();
            }
        });

        View view = findViewById(R.id.view_apps_schedule_detail);

        mRlReciver = (RelativeLayout)view.findViewById(R.id.rl_reciver);
        mTvReciver = (TextView)view.findViewById(R.id.tv_reciver);

        mTvStartDate = (TextView) view.findViewById(R.id.tv_start_date );
        mTvEndDate = (TextView) view.findViewById(R.id.tv_end_date );

        mRlDestination = (RelativeLayout)view.findViewById(R.id.rl_destination);
        mTvDestination = (TextView)view.findViewById(R.id.tv_destination);

        mEvEtc = (EditText) view.findViewById(R.id.ev_etc );
        mEvDestination = (EditText) view.findViewById(R.id.ev_destination );

        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm );

        mBtnConfirm.setOnClickListener(this);
        mTvStartDate.setOnClickListener(this);
        mTvEndDate.setOnClickListener(this);
        mRlDestination.setOnClickListener(this);
        mRlReciver.setOnClickListener(this);

        mBtnConfirm.setText(getResources().getString(R.string.str_btn_insert));
        int[] mLlStateLayoutID = {R.id.ll_state, R.id.ll_state_1, R.id.ll_state_2, R.id.ll_state_3};
        int[] mTvStateTextID = {R.id.tv_state, R.id.tv_state_1, R.id.tv_state_2, R.id.tv_state_3};
        for(int i = 0; i < mLlStateLayoutID.length; i++ ){
            mLlStateLayout[i] = (LinearLayout) view.findViewById(mLlStateLayoutID[i] );
            mTvStateText[i] = (TextView) view.findViewById(mTvStateTextID[i] );
        }

        mCurState = (String)mTvStateText[0].getTag();

        if(Constance.USER_PERMISSION == 64) {
            changeEditState(true);
        }  else {
            changeEditState(false);
        }

    }
    private void changeEditState(boolean state) {
        KumaLog.d("+++++++++++++++ changeEditState >>>>>>>>>>>>>>>> " + state);
        if( !state ) {
            mBtnConfirm.setVisibility(View.GONE);

            mEvDestination.setEnabled(false);
            mEvDestination.setBackgroundResource(R.drawable.back_input_disable);

            mEvEtc.setEnabled(false);
            mEvEtc.setBackgroundResource(R.drawable.back_input_disable);

            mBtnConfirm.setClickable(false);

            mRlReciver.setClickable(false);
            mRlReciver.setBackgroundResource(R.drawable.back_input_disable);

            for(int i = 0; i < mLlStateLayout.length; i++ ){
                mTvStateText[i].setOnClickListener(null);
            }
        }  else {
            mBtnConfirm.setVisibility(View.VISIBLE);

            mEvEtc.setEnabled(true);
            mEvEtc.setBackgroundResource(R.drawable.back_input);


            mEvDestination.setEnabled(true);
            mEvDestination.setBackgroundResource(R.drawable.back_input);

            mBtnConfirm.setClickable(true);

            mRlReciver.setClickable(true);
            mRlReciver.setBackgroundResource(R.drawable.back_input);

            mTvStartDate.setClickable(true);
            mTvStartDate.setBackgroundResource(R.drawable.back_input);

            mTvEndDate.setClickable(true);
            mTvEndDate.setBackgroundResource(R.drawable.back_input);

            mRlDestination.setClickable(true);
            mRlDestination.setBackgroundResource(R.drawable.back_input);

            for(int i = 0; i < mLlStateLayout.length; i++ ){
                mTvStateText[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurState = (String)v.getTag();
                        Constance.changeScheduleView(mCurState, mLlStateLayout, mTvStateText);
                    }
                });
            }
        }
    }

    private void showDatePicker(final int nTag){
        final Calendar cal = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();


        DatePickerDialog dialog = new DatePickerDialog(AppsScheduleInsertActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                String msg = String.format("%d - %d - %d", year, month+1, date);

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
            minDate.set(nStartYear, nStartMonth, nStartDay);
            dialog.getDatePicker().setMinDate(minDate.getTime().getTime());
        } else {
            dialog.getDatePicker();
        }

        dialog.show();
    }

    private void showDropBoxCommonDialog(final int Tag, ArrayList<DropBoaxCommonDTO> data){
        DropBoxCommonDialog dlg = new DropBoxCommonDialog(this);

        dlg.setOnItemClickListener(new DropBoxCommonDialog.OnItemClickListener() {
            @Override
            public void onItemClick(int tag, DropBoaxCommonDTO data) {
                if( TAG_REQ_DESTINATION_LIST  == tag)  {
                    mTvDestination.setText(data.getName());
                    mTvDestination.setTag(data);
                } else if( TAG_REQ_AGENCY_MEMBER_LIST  == tag)  {
                    mTvReciver.setText(data.getName());
                    mTvReciver.setTag(data);
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

    /**
     * 본사 및 대리점 회원 조회
     */
    private void reqAgencyMemberList() {
        try {
            ReqAgencyMemberList reqAgencyMemberList = new ReqAgencyMemberList(this);

            reqAgencyMemberList.setTag(TAG_REQ_AGENCY_MEMBER_LIST);
            reqAgencyMemberList.setPk("0");

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
            KumaLog.d("++++++++++++ resAgencyMemberList  ++++++++++++++  ");
            mArrAgencyMemberList.clear();
            mArrAgencyMemberList.addAll(resprotocol.getmArrDropBoaxCommonDTO());


            if( mArrAgencyMemberList.size() == 0 )  {
                mTvReciver.setText("사원 없음");
                mTvReciver.setTag(-1);
            } else {
                mTvReciver.setText(mArrAgencyMemberList.get(0).getName());
                mTvReciver.setTag(mArrAgencyMemberList.get(0).getPk());
            }
        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }

    private void addSchedule(){
        CommonDialog commonDia = new CommonDialog(this);
        commonDia.setType(CommonDialog.DLG_TYPE_NOTI);
        commonDia.setDialogListener(1, new IDialogListener() {
            @Override
            public void onDialogResult(int nTag, int nResult, Dialog dialog) {
                if( nResult ==  CommonDialog.RESULT_OK) {
                    reqAppsScheduleAdd();
                }
            }
        });
        commonDia.setCancelable(false);
        commonDia.setMessage("등록하시겠습니까?");
        commonDia.show();
    }
    /**
     * Apps 스케줄 등록
     */
    private void reqAppsScheduleAdd() {
        try {
            ReqAppsScheduleAdd reqAppsScheduleAdd = new ReqAppsScheduleAdd(this);

            reqAppsScheduleAdd.setTag(TAG_REQ_SCHEDULE_INSERT);
            reqAppsScheduleAdd.setMemberPk(String.valueOf((Integer)mTvReciver.getTag()));
            reqAppsScheduleAdd.setTitle(mEvDestination.getText().toString().trim());
            reqAppsScheduleAdd.setStartDate(strReqStartDate);
            reqAppsScheduleAdd.setEndDate(strReqEndDate);
            reqAppsScheduleAdd.setKind(mCurState);
            reqAppsScheduleAdd.setContent(mEvEtc.getText().toString().trim());
            requestProtocol(true, reqAppsScheduleAdd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Apps 스케줄 등록
     */
    private void resAppsScheduleAdd(CommonResponse resprotocol) {
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resAppsScheduleEdit  ++++++++++++++");
            CommonDialog commonDia = new CommonDialog(this);
            commonDia.setType(CommonDialog.DLG_TYPE_NOTI);
            commonDia.setDialogListener(1, new IDialogListener() {
                @Override
                public void onDialogResult(int nTag, int nResult, Dialog dialog) {
                    if( nResult ==  CommonDialog.RESULT_OK) {
                        getAppManager().removeActivity(AppsScheduleInsertActivity.this);
                        finish();
                    }
                }
            });
            commonDia.setCancelable(false);
            commonDia.setMessage("등록 되었습니다.");
            commonDia.show();
        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }
    @Override
    public void onResponseProtocol(int nTag, ResponseProtocol resProtocol) {
        switch (nTag)  {
            case TAG_REQ_SCHEDULE_INSERT : {
                resAppsScheduleAdd((CommonResponse) resProtocol );
                break;
            }
            case TAG_REQ_AGENCY_MEMBER_LIST : {
                resAgencyMemberList((ResAgencyMemberList) resProtocol );
                break;
            }
            default:
                break;
        }
    }


    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_date:
                showDatePicker(TAG_START_DATE);
                break;
            case R.id.tv_end_date:
                showDatePicker(TAG_END_DATE);
                break;
            case R.id.rl_destination  :
                showDropBoxCommonDialog(TAG_REQ_DESTINATION_LIST, Constance.mArrDestinationList);
                break;
            case R.id.rl_reciver  :
                showDropBoxCommonDialog(TAG_REQ_AGENCY_MEMBER_LIST, mArrAgencyMemberList);
                break;
            case R.id.btn_confirm  :
                addSchedule();
                break;

            default:
                break;
        }
    }
}
