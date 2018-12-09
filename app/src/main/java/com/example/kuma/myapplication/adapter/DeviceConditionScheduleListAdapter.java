package com.example.kuma.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.DateUtility;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.data.ScheduleInfo;
import com.example.kuma.myapplication.data.ScheduleListDTO;
import com.example.kuma.myapplication.data.ScheduleListDayDTO;
import com.example.kuma.myapplication.data.ScheduleListLowDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Kuma on 2018-12-03.
 */

public class DeviceConditionScheduleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_DATE = 100;
    public static final int VIEW_TYPE_DEVICE = 101;

    private HashMap<String, Object> mDataset = new HashMap<>();
    private ArrayList<ScheduleListDTO> mDataListInfo = new ArrayList<>();
    private ArrayList<ScheduleListLowDTO> mArrScheduleListLowDTO = new ArrayList<>();
    private static OnItemClickListener mListener;

    private Context mContext;
    private LayoutInflater inflater;

    public interface OnItemClickListener {
        void onItemClick(ScheduleInfo data);
    }

    public class ViewHolderDate extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTvSunday;
        public TextView mTvMonday;
        public TextView mTvYuesday;
        public TextView mTvWednesday;
        public TextView mTvThursday;
        public TextView mTvFriday;
        public TextView mTvSaturday;

        public LinearLayout mllContents;

        public TextView tv_11;
//        public int[] mArrCompanyCntTvid = {R.id.tv_cnt_11,R.id.tv_cnt_12,  R.id.tv_cnt_13, R.id.tv_cnt_14, R.id.tv_cnt_15, R.id.tv_cnt_16, R.id.tv_cnt_17};
//        public int[] mArrInvalbCntTvid = {R.id.tv_cnt_21,R.id.tv_cnt_22,  R.id.tv_cnt_23, R.id.tv_cnt_24, R.id.tv_cnt_25, R.id.tv_cnt_26, R.id.tv_cnt_27};
//        public int[] mArrMoveCntTvid = {R.id.tv_cnt_31,R.id.tv_cnt_32,  R.id.tv_cnt_33, R.id.tv_cnt_34, R.id.tv_cnt_35, R.id.tv_cnt_36, R.id.tv_cnt_37};

        public TextView[]  mArrCompanyCntTv = new TextView[7];
        public TextView[]  mArrInvalbCntTv = new TextView[7];
        public TextView[]  mArrMoveCntTv = new TextView[7];

        public ViewHolderDate(View view) {
            super(view);

            mllContents = (LinearLayout) view.findViewById(R.id.ll_contents);
//            mTvSunday = (TextView) view.findViewById(R.id.tv_sunday);
//            mTvMonday = (TextView) view.findViewById(R.id.tv_monday);
//            mTvYuesday = (TextView) view.findViewById(R.id.tv_tuesday);
//            mTvWednesday = (TextView) view.findViewById(R.id.tv_wednesday);
//            mTvThursday = (TextView) view.findViewById(R.id.tv_thursday);
//            mTvFriday = (TextView) view.findViewById(R.id.tv_friday);
//            mTvSaturday = (TextView) view.findViewById(R.id.tv_saturday);
//            tv_11= (TextView) view.findViewById(R.id.tv_cnt_11);
//            for( int i= 0;  i < mArrCompanyCntTvid.length; i++)  {
//                KumaLog.d("+++++++++++++++++++++++++++++++++++++ asdkjashdlkjahsdlkjhsd");
//                mArrCompanyCntTv[i] = (TextView) view.findViewById(mArrCompanyCntTvid[i]);
//                mArrInvalbCntTv[i] = (TextView) view.findViewById(mArrInvalbCntTvid[i]);
//                mArrMoveCntTv[i] = (TextView) view.findViewById(mArrMoveCntTvid[i]);
//            }
        }

        public void bindData(int tag, ScheduleListDayDTO data) {
            if( tag == 0 ) {
//                LinearLayout llDateline = (LinearLayout) inflater.inflate(R.layout.view_date_line, null);
//                LinearLayout.LayoutParams viewLineFront_params
//                        = (LinearLayout.LayoutParams) llDateline.getLayoutParams();
//                viewLineFront_params.width = 0;
//                viewLineFront_params.weight = 2;
//                llDateline.setLayoutParams(viewLineFront_params);
//                mllContents.addView(llDateline);
            }

            LinearLayout llDateData = (LinearLayout) inflater.inflate(R.layout.view_test_data, null);

            mllContents.addView(llDateData);

            LinearLayout.LayoutParams llDateData_params
                    = (LinearLayout.LayoutParams) llDateData.getLayoutParams();
            llDateData_params.width = 0;
            llDateData_params.weight = 160;
            llDateData.setLayoutParams(llDateData_params);

            ((TextView) llDateData.findViewById(R.id.tv_date)).setText(data.diplayDay);
            ((TextView) llDateData.findViewById(R.id.tv_office_cnt)).setText(data.nCompanyCnt);
            ((TextView) llDateData.findViewById(R.id.tv_invalb_cnt)).setText(data.nInvalbCnt);
            ((TextView) llDateData.findViewById(R.id.tv_move_cnt)).setText(data.nMoveCnt);
        }

        public void setStateData(int tag, ScheduleListDayDTO data ){
            KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> tag  : " + tag);
        }

        @Override
        public void onClick(View view) {

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DeviceConditionScheduleListAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int position) {

        int viewType = getItem(position).getViewType();
        ScheduleListDTO info = getItem(position);
        RecyclerView.ViewHolder holder;
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_condition_schedule_date, parent, false);
        holder = new ViewHolderDate(itemView);
//            ((ViewHolderDate) holder).deviceArrowView.setTag(info);
        return holder;
    }

    private int nPosition = -1;

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String strModel = "";
        if (holder instanceof ViewHolderDate) {
            nPosition = mDataListInfo.get(position).getLowNumber();
            addDateView(holder, position);
        } else {
            String serialNo = mDataListInfo.get(position).getSerialNo();
            KumaLog.d(" serialNo : " + serialNo);
            KumaLog.d(" info position : " + position);
            final ScheduleInfo info = (ScheduleInfo) mDataset.get(serialNo);
            addDeviceArrowView(holder, info);
        }
    }

    private int nWeightCnt = 0;

    private void addDateView(RecyclerView.ViewHolder holder, int position) {
        KumaLog.d(" addDateView nPosition : " + nPosition);

        KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : ");

        for (int i = 0; i < mArrScheduleListLowDTO.get(nPosition).getmArrScheduleListDayDTO().size(); i++) {
            ScheduleListDayDTO data = mArrScheduleListLowDTO.get(nPosition).getmArrScheduleListDayDTO().get(i);
//            KumaLog.d(" mArrScheduleListLowDTO : " + data.year + "-" + data.month + "-" + data.day);
//            if (i == 0) {
//                ((ViewHolderDate) holder).mTvSunday.setText(data.diplayDay);
//            } else if (i == 1) {
//                ((ViewHolderDate) holder).mTvMonday.setText(data.diplayDay);
//            } else if (i == 2) {
//                ((ViewHolderDate) holder).mTvYuesday.setText(data.diplayDay);
//            } else if (i == 3) {
//                ((ViewHolderDate) holder).mTvWednesday.setText(data.diplayDay);
//            } else if (i == 4) {
//                ((ViewHolderDate) holder).mTvThursday.setText(data.diplayDay);
//            } else if (i == 5) {
//                ((ViewHolderDate) holder).mTvFriday.setText(data.diplayDay);
//            } else if (i == 6) {
//                ((ViewHolderDate) holder).mTvSaturday.setText(data.diplayDay);
//            }
            ((ViewHolderDate) holder).bindData(i, data);
//            ((ViewHolderDate) holder).setStateData( i, data);
        }
    }
    private void setStateData(RecyclerView.ViewHolder holder, int tag, ScheduleListDayDTO data ){
        KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> tag  : " + tag);
    }


    private void addDeviceArrowView(RecyclerView.ViewHolder holder, final ScheduleInfo info) {

//        KumaLog.d(" info serialNo : " + info.serialNo);
//        KumaLog.d(" productCode : " + info.productCode);
//        KumaLog.d(" startDate : " + info.startDate);
//        KumaLog.d(" endDate : " + info.endDate);
//        KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : ");

        boolean bBlankState = true;
        for (ScheduleListDayDTO data : mArrScheduleListLowDTO.get(nPosition).getmArrScheduleListDayDTO()) {
            KumaLog.d(" diplayDay : " + data.diplayDay);
//            ((ViewHolderDevice) holder).bindSetData((nPosition % 8),nPosition);
        }
        KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : ");
    }

    private ScheduleListDTO getItem(int position) {
        return mDataListInfo.get(position);
    }

    public void setItems(HashMap<String, Object> items, ArrayList<ScheduleListDTO> lineInfo, ArrayList<ScheduleListLowDTO> scheduleListLowDTO) {
        mDataset.clear();
        mDataset.putAll(items);

        mDataListInfo.clear();
        mDataListInfo.addAll(lineInfo);

        mArrScheduleListLowDTO.clear();
        mArrScheduleListLowDTO.addAll(scheduleListLowDTO);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataListInfo.size();
    }
}
