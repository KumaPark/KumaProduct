package com.simens.us.myapplication.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simens.us.myapplication.BaseActivity;
import com.simens.us.myapplication.Constance.Constance;
import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.request.ReqAgencyMemberList;
import com.simens.us.myapplication.Network.request.ReqDeviceAbleList;
import com.simens.us.myapplication.Network.request.ReqDeviceOsName;
import com.simens.us.myapplication.Network.request.ReqDeviceScheduleInsert;
import com.simens.us.myapplication.Network.response.CommonResponse;
import com.simens.us.myapplication.Network.response.ResAgencyMemberList;
import com.simens.us.myapplication.Network.response.ResDeviceAbleList;
import com.simens.us.myapplication.Network.response.ResDeviceOsName;
import com.simens.us.myapplication.Network.response.ResDeviceScheduleInfo;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.adapter.ItemSelectAdapter;
import com.simens.us.myapplication.data.DropBoaxCommonDTO;
import com.simens.us.myapplication.data.SelectItemInfo;
import com.simens.us.myapplication.ui.dialog.CommonDialog;
import com.simens.us.myapplication.ui.dialog.DropBoxCommonDialog;
import com.simens.us.myapplication.ui.dialog.IDialogListener;
import com.simens.us.myapplication.ui.dialog.ItemSelectDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DeviceScheduleInsertActivity extends BaseActivity implements View.OnClickListener {

    private final static int TAG_START_DATE = 100;
    private final static int TAG_END_DATE = 101;

    private final static int TAG_REQ_DESTINATION_LIST = 1001;
    private final static int TAG_REQ_DEVICE_LIST = 1002;
    private final static int TAG_REQ_DEVICE_KIND_LIST = 1003;
    private final static int TAG_REQ_DEVICE_SEARCH_LIST = 1004;

    private final static int TAG_REQ_AGENCY_LIST = 1005;
    private final static int TAG_REQ_AGENCY_MEMBER_LIST = 1006;


    private final static int TAG_REQ_DEVICE_SCHEDULE_ADD = 2000;

    private int nStartYear = 0;
    private int nStartMonth = 0;
    private int nStartDay = 0;

    private int nEndYear = 0;
    private int nEndMonth = 0;
    private int nEndDay = 0;

    private String strStartDate = "";
    private String strEndDate = "";

    private TextView mTvStartDate;
    private TextView mTvEndDate;

    private RelativeLayout mRlProductSelect;
    private TextView mTvProductSelect;

    private RelativeLayout mRlProductKindSelect;
    private TextView mTvProductKindSelect;

    private Button mBtnSearch;

    private  Button mBtnInsert;

    private RelativeLayout mRlAgencySelect;
    private TextView mTvAgencySelect;

    private RelativeLayout mRlReciverSelect;
    private TextView mTvReciverSelect;

    private RelativeLayout mRlDestination;
    private AutoCompleteTextView mTvDestination;

    private LinearLayout mLlChoiceDevice;
    private TextView mTvChoiceDevice;

    private EditText mEvNote;

//    private RelativeLayout mRlProbeSelect;
//    private TextView mTvProbeSelect;
//
//    private RelativeLayout mRlAccSelect;
//    private TextView mTvAccSelect;

    private ArrayList<DropBoaxCommonDTO> mArrAgencyMemberList = new ArrayList<>();
    private ArrayList<DropBoaxCommonDTO> mArrDeviceOsList = new ArrayList<>();
    private ArrayList<DropBoaxCommonDTO> mArrDeviceAbleList = new ArrayList<>();

    private ArrayList<DropBoaxCommonDTO> mArrKindList = new ArrayList<>();

    private String mStrKind = "M";

    private Handler  mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_schedule_insert);

        init();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reqDeviceOsName();
            }
        }, 100);

//        makeDummyData();
    }

    private void init(){
        mTvStartDate = (TextView)findViewById(R.id.tv_start_date);
        mTvEndDate = (TextView)findViewById(R.id.tv_end_date);

        mRlProductSelect = (RelativeLayout)findViewById(R.id.rl_product_select);
        mTvProductSelect = (TextView)findViewById(R.id.tv_product_select);

        mRlProductKindSelect = (RelativeLayout)findViewById(R.id.rl_product_kind);
        mTvProductKindSelect = (TextView)findViewById(R.id.tv_product_kind);

        mRlAgencySelect = (RelativeLayout)findViewById(R.id.rl_agency_select);
        mTvAgencySelect = (TextView)findViewById(R.id.tv_agency_select);

        mRlReciverSelect = (RelativeLayout)findViewById(R.id.rl_reciver_name);
        mTvReciverSelect = (TextView)findViewById(R.id.tv_reciver_name);

        mRlDestination = (RelativeLayout)findViewById(R.id.rl_destination);
        mTvDestination = (AutoCompleteTextView)findViewById(R.id.tv_destination);

        mBtnSearch = (Button)findViewById(R.id.btn_search);
        mBtnInsert = (Button)findViewById(R.id.btn_demo_schedule_insert);

        mLlChoiceDevice = (LinearLayout)findViewById(R.id.ll_selected_device);
        mTvChoiceDevice = (TextView)findViewById(R.id.tv_selected_device);

        mEvNote = (EditText) findViewById(R.id.ev_note);

        mLlChoiceDevice.setVisibility(View.GONE);
        mTvChoiceDevice.setText("");

        findViewById(R.id.ll_start_date).setOnClickListener(this);
        findViewById(R.id.ll_end_date).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

        mTvDestination.setEnabled(false);
//        mRlDestination.setOnClickListener(this);
//        mRlDestination.setClickable(false);

        mRlProductSelect.setOnClickListener(this);
        mRlProductSelect.setClickable(false);

        mRlProductKindSelect.setOnClickListener(this);
        mRlProductKindSelect.setClickable(false);

        mBtnSearch.setOnClickListener(this);
        mBtnSearch.setClickable(false);

        mRlAgencySelect.setOnClickListener(this);
        mRlAgencySelect.setClickable(false);

        mRlReciverSelect.setOnClickListener(this);
        mRlReciverSelect.setClickable(false);

        mBtnInsert.setOnClickListener(this);
        mBtnInsert.setClickable(false);

//        mRlProbeSelect = (RelativeLayout)findViewById(R.id.rl_probe_select);
//        mTvProbeSelect = (TextView)findViewById(R.id.tv_probe_select);
//
//        mRlAccSelect = (RelativeLayout)findViewById(R.id.rl_acc_select);
//        mTvAccSelect = (TextView)findViewById(R.id.tv_acc_select);
//
//
//        mRlProbeSelect.setOnClickListener(this);
//        mRlProbeSelect.setClickable(false);
//        mRlAccSelect.setOnClickListener(this);
//        mRlAccSelect.setClickable(false);
        DropBoaxCommonDTO dataM = new DropBoaxCommonDTO();
        dataM.setModelKind("M");
        dataM.setName("장비");

        DropBoaxCommonDTO dataP = new DropBoaxCommonDTO();
        dataP.setModelKind("P");
        dataP.setName("프로브");

        DropBoaxCommonDTO dataA = new DropBoaxCommonDTO();
        dataA.setModelKind("A");
        dataA.setName("악세사리");

        mArrKindList.add(0, dataM);
        mArrKindList.add(1, dataP);
        mArrKindList.add(2, dataA);

        mTvStartDate.requestFocus();

        getAutoCompList();

        mTvDestination.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,  mListDestinationName ));

    }

    private  ArrayList<String> mListDestinationName  = new ArrayList<>();
    private void getAutoCompList(){
        for(int i = 0; i < Constance.mArrDestinationList.size(); i++) {
            mListDestinationName.add(Constance.mArrDestinationList.get(i).getName());
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
                } else if( TAG_REQ_DEVICE_LIST  == tag)  {
                    mTvProductSelect.setText(data.getName());
                    mTvProductSelect.setTag(data.getPk());
                    reqAgencyMemberList(data.getPk());
                } else if( TAG_REQ_DEVICE_KIND_LIST  == tag)  {
                    mTvProductKindSelect.setText(data.getName());
                    mTvProductKindSelect.setTag(data.getModelKind());
                    mStrKind = data.getModelKind();
                } else if( TAG_REQ_DEVICE_SEARCH_LIST  == tag)  {
                    mLlChoiceDevice.setVisibility(View.VISIBLE);
                    mTvChoiceDevice.setText(data.getContent());
                    mTvReciverSelect.setTag(data.getProductPk());
                } else if( TAG_REQ_AGENCY_LIST  == tag)  {
                    mTvAgencySelect.setText(data.getName());
                    mTvAgencySelect.setTag(data.getPk());
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

    }
    private void showDatePicker(final int nTag){
        final Calendar cal = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();


        DatePickerDialog dialog = new DatePickerDialog(DeviceScheduleInsertActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                String msg = String.format("%d-%d-%d", year, month+1, date);

                if( TAG_START_DATE == nTag ) {
                    nStartYear = year;
                    nStartMonth = month;
                    nStartDay = date;
                    strStartDate = String.format("%d%d%d", year, month, date);


                    if(TextUtils.isEmpty(strEndDate)) {
                        strEndDate = strStartDate;
                    }

                    if( mTvEndDate.getText().length() > 0  || Integer.parseInt(strStartDate ) > Integer.parseInt(strEndDate )) {
                        mTvEndDate.setText("");
                        disableSelectProduct();
                    }
                    mTvEndDate.setBackgroundResource(R.drawable.back_input);
                    mTvStartDate.setText(msg);
                } else {
                    nEndYear = year;
                    nEndMonth = month;
                    nEndDay = date;
                    strEndDate = String.format("%d%d%d", year, month, date);
                    mTvEndDate.setText(msg);
                    enableSelectProduct();
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

    private void enableSelectProduct(){
        mRlProductSelect.setClickable(true);
        mRlProductSelect.setBackgroundResource(R.drawable.back_input);

        mTvDestination.setEnabled(true);
        mRlDestination.setBackgroundResource(R.drawable.back_input);
//        mRlDestination.setClickable(true);
//        mRlDestination.setBackgroundResource(R.drawable.back_input);

        mRlProductKindSelect.setClickable(true);
        mRlProductKindSelect.setBackgroundResource(R.drawable.back_input);

        mRlAgencySelect.setClickable(true);
        mRlAgencySelect.setBackgroundResource(R.drawable.back_input);

        mRlReciverSelect.setClickable(true);
        mRlReciverSelect.setBackgroundResource(R.drawable.back_input);

        mEvNote.setEnabled(true);
        mEvNote.setBackgroundResource(R.drawable.back_input);

        mBtnSearch.setClickable(true);
        mBtnInsert.setClickable(true);

//        mRlProbeSelect.setClickable(true);
//        mRlProbeSelect.setBackgroundResource(R.drawable.back_input);
//
//        mRlAccSelect.setClickable(true);
//        mRlAccSelect.setBackgroundResource(R.drawable.back_input);
    }
    private void disableSelectProduct(){
        mRlProductSelect.setClickable(false);
        mRlProductSelect.setBackgroundResource(R.drawable.back_input_disable);

        mTvDestination.setEnabled(false);
        mRlDestination.setBackgroundResource(R.drawable.back_input_disable);

//        mRlDestination.setClickable(false);
//        mRlDestination.setBackgroundResource(R.drawable.back_input_disable);

        mRlProductKindSelect.setClickable(false);
        mRlProductKindSelect.setBackgroundResource(R.drawable.back_input_disable);
        mRlAgencySelect.setClickable(false);
        mRlAgencySelect.setBackgroundResource(R.drawable.back_input_disable);
        mRlReciverSelect.setClickable(false);
        mRlReciverSelect.setBackgroundResource(R.drawable.back_input_disable);

        mEvNote.setEnabled(false);
        mEvNote.setBackgroundResource(R.drawable.back_input_disable);

        mBtnSearch.setClickable(false);
        mBtnInsert.setClickable(false);

//        mRlProbeSelect.setClickable(false);
//        mRlProbeSelect.setBackgroundResource(R.drawable.back_input_disable);
//        mTvProbeSelect.setText("");
//
//        mRlAccSelect.setClickable(false);
//        mRlAccSelect.setBackgroundResource(R.drawable.back_input_disable);
//        mTvAccSelect.setText("");
    }
    ItemSelectDialog dlgItem;
    private void showItemSelectDialog(TextView view, List<SelectItemInfo> arrData){
        if( dlgItem == null ) {
            dlgItem = new ItemSelectDialog(this);
        }
        dlgItem.setDisPlayView(view);
        dlgItem.setData(arrData);
        dlgItem.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_start_date:
                showDatePicker(TAG_START_DATE);
                break;
            case R.id.ll_end_date:
                showDatePicker(TAG_END_DATE);
                break;
            case R.id.rl_product_select:
                if( mArrDeviceOsList.size() > 0  ) {
                    showDropBoxCommonDialog(TAG_REQ_DEVICE_LIST, mArrDeviceOsList);
                }
//                showItemSelectDialog(mTvProductSelect, mArrProductList);
                break;
            case R.id.rl_probe_select:
//                showItemSelectDialog(mTvProbeSelect, mArrProbeList);
                break;
            case R.id.rl_acc_select:
//                showItemSelectDialog(mTvAccSelect, mArrAccList);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_agency_select  :
                showDropBoxCommonDialog(TAG_REQ_AGENCY_LIST, Constance.mArrAgencyList);
                break;
            case R.id.rl_reciver_name  :
                if( mArrAgencyMemberList.size() > 0  ) {
                    showDropBoxCommonDialog(TAG_REQ_AGENCY_MEMBER_LIST, mArrAgencyMemberList);
                }
                break;
            case R.id.rl_destination  :
                showDropBoxCommonDialog(TAG_REQ_DESTINATION_LIST, Constance.mArrDestinationList);
                break;
            case R.id.rl_product_kind:
                showDropBoxCommonDialog(TAG_REQ_DEVICE_KIND_LIST, mArrKindList);
//                showItemSelectDialog(mTvAccSelect, mArrAccList);
                break;
            case R.id.btn_search:
                reqDeviceAbleList();
//                showItemSelectDialog(mTvAccSelect, mArrAccList);
                break;
            case R.id.btn_demo_schedule_insert:
                CommonDialog commonDia = new CommonDialog(this);
                commonDia.setType(CommonDialog.DLG_TYPE_YES_NO);
                commonDia.setDialogListener(1, new IDialogListener() {
                    @Override
                    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
                        if( nResult ==  CommonDialog.RESULT_OK) {
                            reqDeviceScheduleInsert();
                        }
                    }
                });
                commonDia.setMessage("등록하시겠습니까?");
                commonDia.show();

//                showItemSelectDialog(mTvAccSelect, mArrAccList);
                break;
            default:
                break;
        }
    }

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
            KumaLog.d("++++++++++++ resAgencyMemberList  ++++++++++++++  ");
            mArrAgencyMemberList.clear();
            mArrAgencyMemberList.addAll(resprotocol.getmArrDropBoaxCommonDTO());


            if( mArrAgencyMemberList.size() == 0 )  {
                mTvReciverSelect.setText("사원 없음");
                mTvReciverSelect.setTag(-1);
            } else {
                mTvReciverSelect.setText(mArrAgencyMemberList.get(0).getName());
                mTvReciverSelect.setTag(mArrAgencyMemberList.get(0).getPk());
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
     * 장비의OS버젼정보 조회
     */
    private void reqDeviceOsName() {
        try {
            ReqDeviceOsName reqDeviceOsName = new ReqDeviceOsName(this);

            reqDeviceOsName.setTag(TAG_REQ_DEVICE_LIST);

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


            if( mArrDeviceOsList.size() == 0 )  {
                mTvProductSelect.setText("등록된 장비가 없습니다.");
                mTvProductSelect.setTag(-1);
            } else {
                mTvProductSelect.setText(mArrDeviceOsList.get(0).getName());
                mTvProductSelect.setTag(mArrDeviceOsList.get(0).getPk());
                mTvProductKindSelect.setText(mArrKindList.get(0).getName());
                mTvProductKindSelect.setTag(mArrKindList.get(0).getModelKind());
                mStrKind =  (String)mArrKindList.get(0).getModelKind();
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
     * 데모 가능한 제품조회
     */
    private void reqDeviceAbleList() {
        try {

            ReqDeviceAbleList reqDeviceAbleList = new ReqDeviceAbleList(this);
//            token : 토큰
//            osPk : 장비모델 및 OS (장비모델 및 OS버젼)
//            kind : 제품종류 (M : 장비, P : 프로브 , A : 악세사리)
//            startDate : 데모시작예정일
//            endDate : 데모종료예정일

            reqDeviceAbleList.setTag(TAG_REQ_DEVICE_SEARCH_LIST);
            reqDeviceAbleList.setOsPk(String.valueOf((Integer)mTvProductSelect.getTag()));
            reqDeviceAbleList.setKind(mStrKind);
            reqDeviceAbleList.setStartDate(nStartYear + "-" + ( nStartMonth + 1 ) + "-" + nStartDay);
            reqDeviceAbleList.setEndDate(nEndYear + "-" + ( nEndMonth + 1) + "-" + nEndDay);
            requestProtocol(true, reqDeviceAbleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 데모 가능한 제품조회
     */
    private void resDeviceAbleList(ResDeviceAbleList resprotocol) {
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resDeviceAbleList  ++++++++++++++  ");
            mArrDeviceAbleList.clear();
            mArrDeviceAbleList.addAll(resprotocol.getmArrDropBoaxCommonDTO());

            if( mArrDeviceAbleList.size() == 0 )  {
                mLlChoiceDevice.setVisibility(View.VISIBLE);
                mTvChoiceDevice.setText("데모가능한 대상이 없습니다.");
                mTvChoiceDevice.setTag(-1);
            } else {
                showDropBoxCommonDialog(TAG_REQ_DEVICE_SEARCH_LIST,  mArrDeviceAbleList);
                mTvChoiceDevice.setText(mArrDeviceAbleList.get(0).getContent());
                mTvChoiceDevice.setTag(mArrDeviceAbleList.get(0).getProductPk());
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
     * 제품데모 정보등록
     */
    private void reqDeviceScheduleInsert() {
        try {

            ReqDeviceScheduleInsert reqDeviceScheduleInsert = new ReqDeviceScheduleInsert(this);
//            kind : 제품종류 (M : 장비, P : 프로브 , A : 악세사리)
//            productPk : 제품고유번호
//            startDate : 시작일
//            endDate : 종료일
//            destinationPk : 목적지 고유번호
//            receiverAgencyPk : 수산자 구분(본사 및 대리점고유번호)
//            receiver : 수신자 (회원고유번호)
//            message : 비고

            KumaLog.d("mTvChoiceDevice.getTag() >>>> " + mTvChoiceDevice.getTag());
            reqDeviceScheduleInsert.setTag(TAG_REQ_DEVICE_SCHEDULE_ADD);
            String strStartMonth =  "", strStartDay = "";

            if( ( nStartMonth + 1 ) <  10 ) {
                strStartMonth =  "0" +  ( nStartMonth + 1 );
            } else {
                strStartMonth =  "" + ( nStartMonth + 1 );
            }

            if( nStartDay <  10 ) {
                strStartDay =  "0" +  nStartDay;
            } else {
                strStartDay =  "" + nStartDay;
            }

            reqDeviceScheduleInsert.setStartDate(nStartYear + "-" + strStartMonth + "-" + strStartDay);

            String strEndMonth =  "", strEndDay = "";

            if( ( nEndMonth + 1 ) <  10 ) {
                strEndMonth =  "0" +  ( nEndMonth + 1 );
            } else {
                strEndMonth =  "" + ( nEndMonth + 1 );
            }

            if( nEndDay <  10 ) {
                strEndDay =  "0" +  nEndDay;
            } else {
                strEndDay =  "" + nEndDay;
            }

            reqDeviceScheduleInsert.setEndDate(nEndYear + "-" + strEndMonth + "-" + strEndDay);

            String strDestinationPk = "";

            if( mTvDestination.getText().toString().trim().length() < 0 ) {
                showSimpleMessagePopup("목적지 정보가 없어 수정이 불가능합니다.");
                return;
            }
            strDestinationPk = mTvDestination.getText().toString().trim();

            reqDeviceScheduleInsert.setKind(mStrKind);
            reqDeviceScheduleInsert.setDestinationPk(strDestinationPk);
            reqDeviceScheduleInsert.setReceiver(String.valueOf((Integer)mTvReciverSelect.getTag()));
            reqDeviceScheduleInsert.setReceiverAgencyPk(String.valueOf((Integer)mTvAgencySelect.getTag()));
            reqDeviceScheduleInsert.setProductPk(String.valueOf((Integer)mTvChoiceDevice.getTag()));
            reqDeviceScheduleInsert.setMessage(mEvNote.getText().toString().trim());
            requestProtocol(true, reqDeviceScheduleInsert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 제품데모 정보등록
     */
    private void resDeviceScheduleInsert(CommonResponse resprotocol) {
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            CommonDialog commonDia = new CommonDialog(this);
            commonDia.setType(CommonDialog.DLG_TYPE_NOTI);
            commonDia.setDialogListener(1, new IDialogListener() {
                @Override
                public void onDialogResult(int nTag, int nResult, Dialog dialog) {
                    if( nResult ==  CommonDialog.RESULT_OK) {
                        getAppManager().removeActivity(DeviceScheduleInsertActivity.this);
                        finish();
                    }
                }
            });
            commonDia.setCancelable(false);
            commonDia.setMessage("데모등록이 완료되었습니다.");
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
            case TAG_REQ_AGENCY_MEMBER_LIST : {
                resAgencyMemberList((ResAgencyMemberList)resProtocol);
                break;
            }
            case TAG_REQ_DEVICE_LIST : {
                resDeviceOsName((ResDeviceOsName)resProtocol);
                break;
            }
            case TAG_REQ_DEVICE_SEARCH_LIST : {
                resDeviceAbleList((ResDeviceAbleList)resProtocol);
                break;
            }
            case TAG_REQ_DEVICE_SCHEDULE_ADD : {
                resDeviceScheduleInsert((CommonResponse)resProtocol);
                break;
            }

            default:
                break;
        }
    }


    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
    }
}