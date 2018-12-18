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
        void onItemClick(ScheduleListDayDTO data);
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
//        public int[] mArrTvid = {R.id.tv_office_cnt,R.id.tv_invalb_cnt,  R.id.tv_move_cnt};


        public TextView[]  mArrInvalbCntTv = new TextView[7];
        public TextView[]  mArrMoveCntTv = new TextView[7];

        public ViewHolderDate(View view) {
            super(view);

            if( mllContents != null )  {
                mllContents.removeAllViews();
            }  else {
                mllContents = (LinearLayout) view.findViewById(R.id.ll_contents);
            }

//            mTvSunday = (TextView) view.findViewById(R.id.tv_sunday);
//            mTvMonday = (TextView) view.findViewById(R.id.tv_monday);
//            mTvYuesday = (TextView) view.findViewById(R.id.tv_tuesday);
//            mTvWednesday = (TextView) view.findViewById(R.id.tv_wednesday);
//            mTvThursday = (TextView) view.findViewById(R.id.tv_thursday);
//            mTvFriday = (TextView) view.findViewById(R.id.tv_friday);
//            mTvSaturday = (TextView) view.findViewById(R.id.tv_saturday);
//            tv_11= (TextView) view.findViewById(R.id.tv_cnt_11);
//            for( int i= 0;  i < mArrTvid.length; i++)  {
//                mArrTv[i] = (TextView) view.findViewById(mArrTvid[i]);
//            }
        }

        public void bindData(int tag, ScheduleListDayDTO data) {

            mllContents.removeAllViews();

            KumaLog.d(" >>>>>>>>>>>>>>>>> bindData >>>>>>>>>>>>>>>>>>>>>>>>>>> : ");
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
            TextView[]  mArrTv = new TextView[3];
            mArrTv[0] = (TextView) llDateData.findViewById(R.id.tv_office_cnt);
            mArrTv[1] = (TextView) llDateData.findViewById(R.id.tv_invalb_cnt);
            mArrTv[2] = (TextView) llDateData.findViewById(R.id.tv_move_cnt);

            if( Integer.parseInt(data.diplayDay) == 0  ) {
                setBlankDate(((TextView) llDateData.findViewById(R.id.tv_date)));
                for( int i= 0;  i < mArrTv.length; i++)  {
                    setBlankDate(mArrTv[i]);
                }
            } else {
                ((TextView) llDateData.findViewById(R.id.tv_date)).setText(data.diplayDay);
                setStateData(mArrTv, data);
            }
            llDateData.setTag(data);
            llDateData.setOnClickListener(this);

        }

        private void setBlankDate(TextView view){
            view.setText("");
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        public void setStateData(TextView[]  mArrTv, ScheduleListDayDTO data){

            for( int i= 0;  i < mArrTv.length; i++)  {
                if(  i < data.getScheduleInfoList().size() ){
                    mArrTv[i].setText(data.getScheduleInfoList().get(i).productName);
                    mArrTv[i].setBackgroundColor(Color.parseColor(data.getScheduleInfoList().get(i).productColor));
                } else {
                    setBlankDate(mArrTv[i]);
                }

            }

            KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> diplayDay  : " +  data.diplayDay + " size >> "+  data.getScheduleInfoList().size());
        }

        @Override
        public void onClick(View view) {
            KumaLog.d("getAdapterPosition >> " + getAdapterPosition());
            if (mListener != null) {
                ScheduleListDayDTO info = (ScheduleListDayDTO) view.getTag();

                mListener.onItemClick(info);
            }
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
        KumaLog.d(" >>>>>>>>>>>>>>>>> onCreateViewHolder >>>>>>>>>>>>>>>>>>>>>>>>>>> : ");
        int viewType = getItem(position).getViewType();
        ScheduleListDTO info = getItem(position);
        RecyclerView.ViewHolder holder;
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_condition_schedule_date, parent, false);
        holder = new ViewHolderDate(itemView);
        ((ViewHolderDate) holder).mllContents.setTag(position);
        return holder;
    }

    private int nPosition = -1;

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        KumaLog.d(" >>>>>>>>>>>>>>>>> onBindViewHolder >>>>>>>>>>>>>>>>>>>>>>>>>>> : ");
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

//            if (Integer.parseInt(strartDate) == Integer.parseInt(strDate)) {
//
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
