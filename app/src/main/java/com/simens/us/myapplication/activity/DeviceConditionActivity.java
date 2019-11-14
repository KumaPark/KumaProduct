package com.simens.us.myapplication.activity;

/**
 * Created by Kuma on 2018-12-03.
 */

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.simens.us.myapplication.BaseActivity;
import com.simens.us.myapplication.Constance.Constance;
import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.request.ReqDeviceScheduleList;
import com.simens.us.myapplication.Network.response.ResDeviceScheduleList;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.adapter.DeviceConditionScheduleAdapter;
import com.simens.us.myapplication.adapter.DeviceConditionScheduleListAdapter;
import com.simens.us.myapplication.adapter.DeviceScheduleListAdapter;
import com.simens.us.myapplication.adapter.productSelectAdapter;
import com.simens.us.myapplication.data.ScheduleInfo;
import com.simens.us.myapplication.data.ScheduleListDTO;
import com.simens.us.myapplication.data.ScheduleListDayDTO;
import com.simens.us.myapplication.data.ScheduleListLowDTO;
import com.simens.us.myapplication.ui.dialog.ProductSelectDialog;
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

//    private RecyclerView mRecyclerView;

    private ListView mScheduleList;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //    private DeviceConditionScheduleListAdapter mDeviceScheduleListAdapter;
    private DeviceConditionScheduleAdapter mDeviceScheduleListAdapter_1;


    private ArrayList<ScheduleListDTO> mArrScheduleListDTO = new ArrayList<>();
    private ArrayList<ScheduleInfo> mArrScheduleInfoListDTO = new ArrayList<>();
    private ArrayList<ScheduleListLowDTO> mArrScheduleListLowDTO = new ArrayList<>();
    private HashMap<String, Object> mDataset = new HashMap<>();

    private String mStrYear = "";
    private String mStrMonth = "";
    private String mStrDay = "";

    private float originWidth = 0;
    private float originheight = 0;
    private float targetWidth = 0;
    private float targetheight = 0;
    private float originX = 0;
    private float originY = 0;
    private float targetX = 0;
    private float targetY = 0;

    private TextView mTvMonth;
    private Calendar mCal;

    private boolean mViewTypeSeletedState = false;
    private boolean  onPauseState = false;

//    private ProductSelectDialog dlgProductChoide;


    private productSelectAdapter mProductSelectAdapter;
    private ListView mProductlist;
    private TextView mTvDate;
    private ImageView mIvSchedulePlus;
    private ImageView mIvSchedulePlusTo;
    private View mVproductSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicecondition_activity);

        init();
        setCurDate();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reqDeviceScheduleList();
            }
        }, 100);
    }

    private void init(){

        mTvMonth = (TextView) findViewById(R.id.tv_mon);
//        mRecyclerView = (RecyclerView) findViewById(R.id.rsv_schedule_list);
        mScheduleList = (ListView)  findViewById(R.id.lv_schedule_list);

        mDeviceScheduleListAdapter_1 = new DeviceConditionScheduleAdapter(this,
                new DeviceConditionScheduleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ScheduleListDayDTO data) {
                        showProductChoiceDlg(data);
                    }
                });

        mScheduleList.setAdapter(mDeviceScheduleListAdapter_1);


//        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
        findViewById(R.id.iv_prev_month).setOnClickListener(this);
        findViewById(R.id.iv_next_month).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_filter).setOnClickListener(this);

        initSelecteProduct();
    }

    private void initSelecteProduct(){
        mVproductSelect = (View) findViewById(R.id.v_product_select);
        mProductlist = (ListView)mVproductSelect.findViewById(R.id.lv_product_list);
        mTvDate = (TextView)mVproductSelect.findViewById(R.id.tv_date);
        mIvSchedulePlus = (ImageView)findViewById(R.id.iv_schedule_plus);
        mIvSchedulePlusTo = (ImageView)mVproductSelect.findViewById(R.id.iv_schedule_plus_dlg);



        mProductSelectAdapter = new productSelectAdapter(this, new productSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ScheduleInfo data) {
                Intent intent = new Intent(DeviceConditionActivity.this,DeviceDemoDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailData",data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        mProductlist.setAdapter(mProductSelectAdapter);

        mVproductSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVproductSelect.setVisibility(View.GONE);
                moveAnim(false);
            }
        });

        mIvSchedulePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KumaLog.e("ProductSelectDialog onClick");
            }
        });

        mIvSchedulePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showSimpleMessagePopup("준비중입니다.");
                move2OtherActivity(DeviceScheduleInsertActivity.class);
            }
        });

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if( originWidth <= 0 ) {
            originWidth = (float) mIvSchedulePlus.getWidth();
            originheight = (float) mIvSchedulePlus.getHeight();

            targetWidth = (float) mIvSchedulePlusTo.getWidth();
            targetheight = (float) mIvSchedulePlusTo.getHeight();

            KumaLog.line();

            KumaLog.e(" originWidth : " + originWidth + " originheight : " + originheight);

            KumaLog.e(" targetheight : " + targetheight + " targetheight : " + targetheight);

            originX = mIvSchedulePlus.getX();
            originY = mIvSchedulePlus.getY();
            targetX = mIvSchedulePlusTo.getX();
            targetY = mIvSchedulePlusTo.getY();

            mVproductSelect.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    public void setDataProductSchedule(ScheduleListDayDTO data){
        KumaLog.e("ProductSelectDialog setData");
        if( mProductSelectAdapter != null ) {
            mProductSelectAdapter.setArrayList(data.getScheduleInfoList());
            mProductSelectAdapter.notifyDataSetChanged();
        }
//        data.diplayDay
        mTvDate.setText(data.getMonth()+ "월 " + data.getDay() + "일");
//
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

//            makeDummyData();

//            mDataset, mArrScheduleListDTO, mArrScheduleListLowDTO
            ReqDeviceScheduleList reqDeviceScheduleList = new ReqDeviceScheduleList(this);

            reqDeviceScheduleList.setTag(TAG_REQ_SCHEDULE_LIST);
            reqDeviceScheduleList.setMonth(mStrYear + "-" + mStrMonth);
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
            mArrScheduleInfoListDTO.clear();
            mDataset.clear();
//            if( mDeviceScheduleListAdapter == null ) {
//                mDeviceScheduleListAdapter = new DeviceConditionScheduleListAdapter(DeviceConditionActivity.this,
//                        new DeviceConditionScheduleListAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(ScheduleListDayDTO data) {
//                        showProductChoiceDlg(data);
//                    }
//                });
//                mRecyclerView.setAdapter(mDeviceScheduleListAdapter);
//            }

            mArrScheduleInfoListDTO.addAll(resprotocol.getListData());
            for( int i = 0; i < resprotocol.getListData().size(); i++ ) {
                String key = resprotocol.getListData().get(i).getId();
                mDataset.put(key, resprotocol.getListData().get(i));
            }

            setViewType(mStrYear, mStrMonth, mStrDay);

            KumaLog.d("mDataset Size  > "  +mDataset.size());
            KumaLog.d("mArrScheduleListDTO Size  > "  +mArrScheduleListDTO.size());
            KumaLog.d("mArrScheduleListLowDTO Size  > "  +mArrScheduleListLowDTO.size());

            mDeviceScheduleListAdapter_1.setItems(mDataset, mArrScheduleListDTO, mArrScheduleListLowDTO);
            mDeviceScheduleListAdapter_1.notifyDataSetChanged();
        } else {
            if (!TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }

    private void showProductChoiceDlg(ScheduleListDayDTO data){
//        if( dlgProductChoide == null ) {
//            dlgProductChoide = new ProductSelectDialog(this);
//        }
//
//        dlgProductChoide.setDialogListener(DLG_PRODUCT_SELECT, this);
//        dlgProductChoide.show();
//        dlgProductChoide.setData(data);

        setDataProductSchedule(data);
        mVproductSelect.setVisibility(View.VISIBLE);
        moveAnim(true);
    }

    private int nAnimDuration = 400;
    private void moveAnim(final boolean state){
        if( state ) {
            float resizeWidth =  targetWidth / originWidth;
            float resizeHeight = targetheight / originheight;

            KumaLog.line();

            KumaLog.d(" resizeWidth : " + resizeWidth + " resizeHeight : " + resizeHeight);
            KumaLog.d(" targetY - originY : " + (targetY - originY));

            mIvSchedulePlus.animate().translationY(targetY - originY).setDuration(nAnimDuration).withLayer();
            mIvSchedulePlus.animate().translationX(targetX - originX).setDuration(nAnimDuration).withLayer();
            mIvSchedulePlus.animate().scaleX(resizeWidth).setDuration(nAnimDuration).withLayer();
            mIvSchedulePlus.animate().scaleY(resizeHeight).setDuration(nAnimDuration).withLayer();
        } else {
            mIvSchedulePlus.animate().translationY(0).withLayer();
            mIvSchedulePlus.animate().translationX(0).withLayer();
            mIvSchedulePlus.animate().scaleX(1).withLayer();
            mIvSchedulePlus.animate().scaleY(1).withLayer();
        }
    }

    /**
     * 현재 시간을 기준으로 View Type 셋팅
     */
    private void setCurDate(){
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
            data.setDayNum(dayNum);
            mArrScheduleListLowDTO.get(nLow).getmArrScheduleListDayDTO().add(data);
            if( dayNum == 7 ) {
                nLow ++;
            }

//            KumaLog.d("i : " + i +" dayNum >> " + dayNum);
        }
    }

    private void setDateScheduleInfo(ScheduleListDayDTO data, String strDate){
        for( ScheduleInfo info : mArrScheduleInfoListDTO ) {
            String startDate = info.start.replaceAll("-", "");
            String endDate = info.end.replaceAll("-", "");
            try {
                if (Integer.parseInt(startDate) <= Integer.parseInt(strDate)
                        && Integer.parseInt(endDate) >= Integer.parseInt(strDate)) {
                    KumaLog.line();
                    KumaLog.d(" startDate : " + startDate);
                    KumaLog.d(" endDate : " + endDate);
                    KumaLog.d(" curDate : " + strDate);
                    KumaLog.line();
                    data.setScheduleInfoList(info);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

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
    public void onBackPressed() {
//        super.onBackPressed();
        if( mVproductSelect.getVisibility() != View.GONE) {
            mVproductSelect.setVisibility(View.GONE);
            moveAnim(false);
        } else {
            finish();
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
            if( mVproductSelect.getVisibility() != View.GONE) {
                mVproductSelect.setVisibility(View.GONE);
                moveAnim(false);
            }
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
//                if( dlgProductChoide.getObject() == null ) {
//                    return;
//                }
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

        reqDeviceScheduleList();
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

        reqDeviceScheduleList();
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
                finish();
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
