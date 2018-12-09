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
import android.widget.LinearLayout;

import com.example.kuma.myapplication.BaseActivity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
public class DeviceConditionActivity extends BaseActivity {

    private static final int TAG_REQ_SCHEDULE_LIST = 1000;

    private LinearLayout mLlContents;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DeviceConditionScheduleListAdapter mDeviceScheduleListAdapter;

    private ArrayList<ScheduleListDTO> mArrScheduleListDTO = new ArrayList<>();
    private ArrayList<ScheduleListLowDTO> mArrScheduleListLowDTO = new ArrayList<>();
    private HashMap<String, Object> mDataset = new HashMap<>();

    private String mStrYear = "";
    private String mStrMonth = "";
    private String mStrDay = "";

    private Calendar mCal;

    private boolean mViewTypeSeletedState = false;
    private boolean  onPauseState = false;

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
        mRecyclerView = (RecyclerView) findViewById(R.id.rsv_schedule_list);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
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
            ReqDeviceScheduleList reqDeviceScheduleList = new ReqDeviceScheduleList();

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
        KumaLog.d("++++++++++++ resDeviceScheduleList ++++++++++++++");
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resDeviceScheduleList 11  ++++++++++++++");
            if( mDeviceScheduleListAdapter == null ) {
                mDeviceScheduleListAdapter = new DeviceConditionScheduleListAdapter(DeviceConditionActivity.this, new DeviceConditionScheduleListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ScheduleInfo data) {
                        gotoDetail(data);
                    }
                });
                mRecyclerView.setAdapter(mDeviceScheduleListAdapter);
            }
            for( int i = 0; i < resprotocol.getListData().size(); i++ ) {
                String key = resprotocol.getListData().get(i).getSerialNo();
                mDataset.put(key, resprotocol.getListData().get(i));
            }

            if( mViewTypeSeletedState ) {
                setViewType(mStrYear, mStrMonth, mStrDay);
            } else{
                setType();
            }

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
    private void gotoDetail(ScheduleInfo data){
        Intent intent = new Intent(DeviceConditionActivity.this,DeviceDemoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailData",data);
        intent.putExtras(bundle);
        startActivity(intent);
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

        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);

        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        setViewType(curYearFormat.format(date), curMonthFormat.format(date), curDayFormat.format(date));
    }

    private void setViewType(String strYear, String strMonth, String  strDate){
        mCal = Calendar.getInstance();

        mCal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, 1);

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

        setDateView(curYearFormat.format(date), curMonthFormat.format(date), curDayFormat.format(date));
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

            data.setDay( strDay );
            mArrScheduleListLowDTO.get(nLow).getmArrScheduleListDayDTO().add(data);
            if( dayNum == 7 ) {
                nLow ++;
            }

            KumaLog.d("i : " + i +" dayNum >> " + dayNum);
        }
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

            String strDay = "";

            if( i < 10 )  {
                strDay =  "0" + i;
            } else {
                strDay = "" + i;
            }
            data.setDiplayDay(i + "");
            data.setDay( strDay );
            mArrScheduleListLowDTO.get(0).getmArrScheduleListDayDTO().add(data);
            KumaLog.d("i : " + i +" dayNum >> " + dayNum);
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
    }
}
