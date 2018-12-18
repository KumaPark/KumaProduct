package com.example.kuma.myapplication.activity;

/**
 * Created by Kuma on 2018-12-03.
 */

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuma.myapplication.BaseActivity;
import com.example.kuma.myapplication.Constance.Constance;
import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.request.ReqDeviceScheduleList;
import com.example.kuma.myapplication.Network.response.ResDeviceScheduleList;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.adapter.DeviceConditionScheduleListAdapter;
import com.example.kuma.myapplication.adapter.DeviceScheduleListAdapter;
import com.example.kuma.myapplication.data.ScheduleInfo;
import com.example.kuma.myapplication.data.ScheduleListDTO;
import com.example.kuma.myapplication.data.ScheduleListDayDTO;
import com.example.kuma.myapplication.data.ScheduleListLowDTO;
import com.example.kuma.myapplication.ui.dialog.ProductSelectDialog;
import com.google.zxing.integration.android.IntentIntegrator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
public class DeviceConditionActivity extends BaseActivity implements View.OnClickListener {

    private static final int TAG_REQ_SCHEDULE_LIST = 1000;

    private final static int DLG_PRODUCT_SELECT = 201;

    private LinearLayout mLlContents;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DeviceConditionScheduleListAdapter mDeviceScheduleListAdapter;

    private ArrayList<ScheduleListDTO> mArrScheduleListDTO = new ArrayList<>();
    private ArrayList<ScheduleInfo> mArrScheduleInfoListDTO = new ArrayList<>();
    private ArrayList<ScheduleListLowDTO> mArrScheduleListLowDTO = new ArrayList<>();
    private HashMap<String, Object> mDataset = new HashMap<>();

    private String mStrYear = "";
    private String mStrMonth = "";
    private String mStrDay = "";

    private TextView mTvMonth;
    private Calendar mCal;

    private boolean mViewTypeSeletedState = false;
    private boolean  onPauseState = false;

    private ProductSelectDialog dlgProductChoide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicecondition_activity);

        init();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reqDeviceScheduleList();
            }
        }, 100);
    }

    private void init(){

        mTvMonth = (TextView) findViewById(R.id.tv_mon);
        mRecyclerView = (RecyclerView) findViewById(R.id.rsv_schedule_list);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        findViewById(R.id.iv_prev_month).setOnClickListener(this);
        findViewById(R.id.iv_next_month).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_filter).setOnClickListener(this);
    }
    /**
     * 데모 정보 조회 리스트
     */
    private void reqDeviceScheduleList() {
        try {
            if( mDataset != null ) {
                mDataset.clear();
            }
            if( mArrScheduleListDTO != null ) {
                mArrScheduleListDTO.clear();
            }
            if( mArrScheduleListLowDTO != null ) {
                mArrScheduleListLowDTO.clear();
            }

//            mDataset, mArrScheduleListDTO, mArrScheduleListLowDTO
            ReqDeviceScheduleList reqDeviceScheduleList = new ReqDeviceScheduleList(this);

            reqDeviceScheduleList.setTag(TAG_REQ_SCHEDULE_LIST);

            requestProtocol(true, reqDeviceScheduleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 데모 정보 조회 리스트 결과
     */
    private void resDeviceScheduleList(ResDeviceScheduleList resprotocol) {
        KumaLog.d("++++++++++++ >>>>>>> resDeviceScheduleList ++++++++++++++");
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resDeviceScheduleList 11  ++++++++++++++");
            if( mDeviceScheduleListAdapter == null ) {
                mDeviceScheduleListAdapter = new DeviceConditionScheduleListAdapter(DeviceConditionActivity.this,
                        new DeviceConditionScheduleListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ScheduleListDayDTO data) {
                        showProductChoiceDlg(data);
                    }
                });
                mRecyclerView.setAdapter(mDeviceScheduleListAdapter);
            }

            mArrScheduleInfoListDTO.addAll(resprotocol.getListData());
            for( int i = 0; i < resprotocol.getListData().size(); i++ ) {
                String key = resprotocol.getListData().get(i).getSerialNo();
                mDataset.put(key, resprotocol.getListData().get(i));
            }

            setType();

            mDeviceScheduleListAdapter.setItems(mDataset, mArrScheduleListDTO, mArrScheduleListLowDTO);
            mDeviceScheduleListAdapter.notifyDataSetChanged();
        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }


    private void showProductChoiceDlg(ScheduleListDayDTO data){
        if( dlgProductChoide == null ) {
            dlgProductChoide = new ProductSelectDialog(this);
        }

        dlgProductChoide.setDialogListener(DLG_PRODUCT_SELECT, this);
        dlgProductChoide.show();
        dlgProductChoide.setData(data);
    }

    /**
     * 현재 시간을 기준으로 View Type 셋팅
     */
    private void setType(){
        KumaLog.d("++++++++++++ setType ++++++++++++++");
        long now = System.currentTimeMillis();

        final Date date = new Date(now);

        //연,월,일을 따로 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        mStrYear = curYearFormat.format(date);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        mStrMonth = curMonthFormat.format(date);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        mStrDay = curDayFormat.format(date);
        setViewType(mStrYear, mStrMonth, mStrDay);
    }

    private void setViewType(String strYear, String strMonth, String  strDate){
        KumaLog.d(" setViewType >> " + mStrYear+ "  " + mStrMonth);


        mArrScheduleListLowDTO.clear();
        mArrScheduleListDTO.clear();
        mCal = Calendar.getInstance();

        mCal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, 1);
        KumaLog.d(" mStrYear >> " + mStrYear+ " mStrMonth >> " + mStrMonth);
        mTvMonth.setText(strYear +"년 " +strMonth + "월" );

        int dayNum = 0;

        //입력받은 날짜의 그달의 마지막일을 구한다.
        int endDay = mCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        KumaLog.d("endDay >> " + endDay);
        int nWeekCnt = 0;
        for( int i = 1; i <= endDay; i ++ ) {
            mCal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, i);
            dayNum = mCal.get(Calendar.DAY_OF_WEEK);
            KumaLog.d("i : " + i +" dayNum >> " + dayNum);
            if( dayNum  == 7 ) {
                ScheduleListLowDTO data =  new ScheduleListLowDTO();
                mArrScheduleListLowDTO.add(data);
                nWeekCnt++;
            }
            if( i ==  endDay && dayNum !=  7)  {
                ScheduleListLowDTO data =  new ScheduleListLowDTO();
                mArrScheduleListLowDTO.add(data);
                nWeekCnt++;
            }
        }
        KumaLog.d("nWeekCnt >> " + nWeekCnt);
        for( int i = 0; i < nWeekCnt; i ++ ) {
            ScheduleListDTO infoDate  = new ScheduleListDTO();
            infoDate.setViewType(DeviceScheduleListAdapter.VIEW_TYPE_DATE);
            infoDate.setLowNumber(i);
            mArrScheduleListDTO.add(infoDate);
        }

        setCurDateView();

        KumaLog.d("mArrScheduleListDTO size >> " + mArrScheduleListDTO.size());
    }

    private void setCurDateView(){

        long now = System.currentTimeMillis();

        final Date date = new Date(now);

        //연,월,일을 따로 저장

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);

        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        setDateView(mStrYear, mStrMonth, mStrDay);
    }

    private void setDateView(String strYear, String strMonth, String  strDate){

        mCal = Calendar.getInstance();

        mCal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, 1);

        int endDay = 1;

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        // 전달
        int nMinusDay = (dayNum - 2);

        setPrevMonthDate(strYear, strMonth,  strDate, nMinusDay);

        // 이번달

        //입력받은 날짜의 그달의 마지막일을 구한다.
        endDay = mCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        int nLow = 0;
        KumaLog.i(" DATE >>>>  " + strYear + strMonth+ strDate);
        for( int i = 1; i <= endDay; i ++ ) {
            mCal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, i);
            dayNum = mCal.get(Calendar.DAY_OF_WEEK);

            ScheduleListDayDTO data = new ScheduleListDayDTO();
            data.setYear(strYear);
            data.setMonth( strMonth );
            data.setnCompanyCnt( "" + i);
            data.setnMoveCnt( "" +  ( i + 1 ) );
            data.setnInvalbCnt( "" +  ( i + 2 ) );
            String strDay = "";
            if( i < 10 )  {
                strDay =  "0" + i;
            } else {
                strDay = "" + i;
            }
            data.setDiplayDay(i + "");
            String strDetailDate = strYear+ strMonth + strDay;
            setDateScheduleInfo(data, strDetailDate);
            data.setDay( strDay );
            mArrScheduleListLowDTO.get(nLow).getmArrScheduleListDayDTO().add(data);
            if( dayNum == 7 ) {
                nLow ++;
            }

//            KumaLog.d("i : " + i +" dayNum >> " + dayNum);
        }
    }

    private void setDateScheduleInfo(ScheduleListDayDTO data, String strDate){
        for( ScheduleInfo info : mArrScheduleInfoListDTO ) {
            String startDate = info.startDate.replaceAll("-", "");
            String endDate = info.endDate.replaceAll("-", "");
            KumaLog.line();
            KumaLog.d(" startDate : " + startDate);
            KumaLog.d(" endDate : " + endDate);
            KumaLog.d(" curDate : " + strDate);
            KumaLog.d(" serialNo : " + info.serialNo);

            try {
                if (Integer.parseInt(startDate) <= Integer.parseInt(strDate)
                        && Integer.parseInt(endDate) >= Integer.parseInt(strDate)) {
                    KumaLog.d(" Integer.parseInt(startDate) <= Integer.parseInt(strDate)\n" +
                            "                        && Integer.parseInt(endDate) >= Integer.parseInt(strDate) : " + info.serialNo);
                    data.setScheduleInfoList(info);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            KumaLog.line();
        }
//        mArrScheduleInfoListDTO
    }

    private void setPrevMonthDate(String strYear, String strMonth, String  strDate, int nMinusDay){
        Calendar cal;
        cal = Calendar.getInstance();

        cal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 2, 1);

        int dayNum = 1;

        int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM");

        String r = dateFormatter.format(cal.getTime());

        for( int i = (endDay  -  nMinusDay ) ; i <= endDay; i++ ) {
            cal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 2, i);

            dayNum = cal.get(Calendar.DAY_OF_WEEK);

            ScheduleListDayDTO data = new ScheduleListDayDTO();
            data.setYear(strYear);
            data.setMonth( r );

            String strDay = "0";

//            if( i < 10 )  {
//                strDay =  "0" + i;
//            } else {
//                strDay = "" + i;
//            }
            data.setDiplayDay(strDay);
            data.setDay( strDay );
            mArrScheduleListLowDTO.get(0).getmArrScheduleListDayDTO().add(data);
//            KumaLog.d("i : " + i +" dayNum >> " + dayNum);
        }
    }


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
            reqDeviceScheduleList();
        }
    }

    @Override
    public void onResponseProtocol(int nTag, ResponseProtocol resProtocol) {
        switch (nTag) {
            case TAG_REQ_SCHEDULE_LIST:
                resDeviceScheduleList((ResDeviceScheduleList)resProtocol);
                break;
            default:
                break;
        }
    }


    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
        switch (nTag){
            case DLG_PRODUCT_SELECT:
                if( dlgProductChoide.getObject() == null ) {
                    return;
                }
                break;
            default:
                break;
        }
    }

    private void prevMonth(){
        KumaLog.line();
        KumaLog.d("showPrevMonth >> ");
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance( );
        cal.set(Integer.parseInt(mStrYear), Integer.parseInt(mStrMonth) - 1, 1);
        cal.add ( cal.MONTH, -1 ); // 이전달

        setNewDate(cal);

        KumaLog.d(" mStrYear >> " + mStrYear+ " mStrMonth >> " + mStrMonth);

        setViewType(mStrYear, mStrMonth, mStrDay);
    }

    private void nextMonth(){
        KumaLog.line();
        KumaLog.d("nextMonth >> ");

        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance( );
        cal.set(Integer.parseInt(mStrYear), Integer.parseInt(mStrMonth) - 1, 1);
        cal.add ( cal.MONTH, + 1 ); //다음달

        setNewDate(cal);

        KumaLog.d(" mStrYear >> " + mStrYear+ " mStrMonth >> " + mStrMonth);

        setViewType(mStrYear, mStrMonth, mStrDay);
    }

    private void setNewDate(Calendar cal){
        SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");

        mStrYear = yearFormatter.format(cal.getTime());
        mStrMonth = monthFormatter.format(cal.getTime());
        mStrDay = dayFormatter.format(cal.getTime());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.iv_filter:
                break;
            case R.id.iv_prev_month:
                prevMonth();
                break;
            case R.id.iv_next_month:
                nextMonth();
                break;
            default:
                break;
        }

    }
}
