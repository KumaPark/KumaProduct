package com.example.kuma.myapplication.activity;

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

/**
 * Created by Kuma on 2018-B02-19.
 */

public class DeviceDetailConditionActivity extends BaseActivity {

    private static final int TAG_REQ_SCHEDULE_LIST = 1000;

    private LinearLayout mLlContents;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DeviceScheduleListAdapter mDeviceScheduleListAdapter;

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
        setContentView(R.layout.activity_device_detail_condition_activity);

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

//        mLlContents = (LinearLayout) findViewById(R.id.ll_contents);
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout dateView = (LinearLayout) inflater.inflate(R.layout.view_schedule_date, null);
//        LinearLayout deviceView = (LinearLayout) inflater.inflate(R.layout.view_schedule_device, null);
//
//        LinearLayout deviceArrowView = (LinearLayout) deviceView.findViewById(R.id.ll_device_schedule_arrow);
//        LinearLayout deviceViewHospital = (LinearLayout) deviceView.findViewById(R.id.ll_device_schedule_desr);
//
//        LinearLayout arrowView1 = (LinearLayout) inflater.inflate(R.layout.view_arrow, null);
//        LinearLayout arrowView2 = (LinearLayout) inflater.inflate(R.layout.view_line, null);
//        LinearLayout arrowView3 = (LinearLayout) inflater.inflate(R.layout.view_arrow, null);
//
//        deviceArrowView.addView(arrowView1);
//        deviceArrowView.addView(arrowView2);
//        deviceArrowView.addView(arrowView3);
//
//        View arrowView1_arrow = (View) arrowView1.findViewById(R.id.v_arrow);
//
//        arrowView1_arrow.setBackgroundResource(R.drawable.arrow_start);
//        LinearLayout.LayoutParams params
//                = (LinearLayout.LayoutParams) arrowView1.getLayoutParams();
//        params.width = 0;
//        params.weight = 158;
//        arrowView1.setLayoutParams(params);
//
//
//        View arrowView2_arrow = (View) arrowView2.findViewById(R.id.v_arrow);
//        arrowView2_arrow.setBackgroundColor(Color.parseColor("#51AC0B"));
//
//        LinearLayout.LayoutParams arrowView2_params
//                = (LinearLayout.LayoutParams) arrowView2.getLayoutParams();
//        arrowView2_params.width = 0;
//        arrowView2_params.weight = 158;
//        arrowView2.setLayoutParams(arrowView2_params);
//
//
//        View arrowView3_arrow = (View) arrowView3.findViewById(R.id.v_arrow);
//        arrowView3_arrow.setBackgroundResource(R.drawable.arrow_end);
//        LinearLayout.LayoutParams arrowView3_params
//                = (LinearLayout.LayoutParams) arrowView3.getLayoutParams();
//        arrowView3_params.width = 0;
//        arrowView3_params.weight = 158;
//        arrowView3.setLayoutParams(arrowView3_params);
//
//        LinearLayout hospitalView = (LinearLayout) inflater.inflate(R.layout.view_hospital, null);
//        deviceViewHospital.addView(hospitalView);
//
//        LinearLayout.LayoutParams hospitalView_params
//                = (LinearLayout.LayoutParams) hospitalView.getLayoutParams();
//        hospitalView_params.width = 0;
//        hospitalView_params.weight = (158 * 3);
//        hospitalView.setLayoutParams(hospitalView_params);
//
//        TextView tvHospital = (TextView) hospitalView.findViewById(R.id.tv_hospital);
//        tvHospital.setText("잠실 24시 동물");
//
//
//        mLlContents.addView(dateView);
//        mLlContents.addView(deviceView);
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
        KumaLog.d("++++++++++++ resDeviceScheduleList ++++++++++++++");
        if (resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
            KumaLog.d("++++++++++++ resDeviceScheduleList 11  ++++++++++++++");
            if( mDeviceScheduleListAdapter == null ) {
                mDeviceScheduleListAdapter = new DeviceScheduleListAdapter(DeviceDetailConditionActivity.this, new DeviceScheduleListAdapter.OnItemClickListener() {
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
        Intent intent = new Intent(DeviceDetailConditionActivity.this,DeviceDemoDetailActivity.class);
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

            Iterator<String> iter = mDataset.keySet().iterator();

            while(iter.hasNext()) {
                String key = iter.next();
                ScheduleInfo value = (ScheduleInfo)mDataset.get(key);
                ScheduleListDTO infoDevice  = new ScheduleListDTO();
                infoDevice.setViewType(DeviceScheduleListAdapter.VIEW_TYPE_DEVICE);
                infoDevice.setDeviceName(value.getProductCode());
                infoDevice.setSerialNo(value.getSerialNo());
                mArrScheduleListDTO.add(infoDevice);
            }
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