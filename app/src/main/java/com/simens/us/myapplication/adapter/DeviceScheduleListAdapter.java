package com.simens.us.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.DateUtility;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.data.DeviceInfo;
import com.simens.us.myapplication.data.ScheduleInfo;
import com.simens.us.myapplication.data.ScheduleListDTO;
import com.simens.us.myapplication.data.ScheduleListDayDTO;
import com.simens.us.myapplication.data.ScheduleListLowDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Kuma on 2018-03-03.
 */

public class DeviceScheduleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_DATE = 100;
    public static final int VIEW_TYPE_DEVICE = 101;

    private HashMap<String, Object> mDataset = new HashMap<>();
    private ArrayList<ScheduleListDTO> mDataListInfo = new ArrayList<>();
    private ArrayList<ScheduleListLowDTO> mArrScheduleListLowDTO = new ArrayList<>();


    private Context mContext;
    private LayoutInflater inflater;
    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(ScheduleInfo data);
    }

    public static class ViewHolderDate extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTvSunday;
        public TextView mTvMonday;
        public TextView mTvYuesday;
        public TextView mTvWednesday;
        public TextView mTvThursday;
        public TextView mTvFriday;
        public TextView mTvSaturday;

        public ViewHolderDate(View view) {
            super(view);
            mTvSunday = (TextView) view.findViewById(R.id.tv_sunday);
            mTvMonday = (TextView) view.findViewById(R.id.tv_monday);
            mTvYuesday = (TextView) view.findViewById(R.id.tv_tuesday);
            mTvWednesday = (TextView) view.findViewById(R.id.tv_wednesday);
            mTvThursday = (TextView) view.findViewById(R.id.tv_thursday);
            mTvFriday = (TextView) view.findViewById(R.id.tv_friday);
            mTvSaturday = (TextView) view.findViewById(R.id.tv_saturday);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public class ViewHolderDevice extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        LinearLayout deviceArrowView;
        LinearLayout deviceViewHospital;

        public ViewHolderDevice(View view) {
            super(view);
            view.setOnClickListener(this);
//            mTextView = (TextView) view.findViewById(R.id.textview);
            deviceArrowView = (LinearLayout) view.findViewById(R.id.ll_device_schedule_arrow);
            deviceViewHospital = (LinearLayout) view.findViewById(R.id.ll_device_schedule_desr);
        }

        @Override
        public void onClick(View view) {
            KumaLog.d("getAdapterPosition >> " + getAdapterPosition());
            if (mListener != null) {
                int position = (int) deviceArrowView.getTag();
                String serialNo = mDataListInfo.get(position).getSerialNo();
                KumaLog.d(" serialNo : " + serialNo);
                KumaLog.d(" info position : " + position);
                final ScheduleInfo info = (ScheduleInfo) mDataset.get(serialNo);

                mListener.onItemClick(info);
            }
        }

        public void bindStartArrow() {
            LinearLayout arrowView1 = (LinearLayout) inflater.inflate(R.layout.view_arrow, null);

            deviceArrowView.addView(arrowView1);
            View arrowView1_arrow = (View) arrowView1.findViewById(R.id.v_arrow);

            arrowView1_arrow.setBackgroundResource(R.drawable.arrow_start);
            LinearLayout.LayoutParams params
                    = (LinearLayout.LayoutParams) arrowView1.getLayoutParams();
            params.width = 0;
            params.weight = 158;
            arrowView1.setLayoutParams(params);
        }

        public void bindLineArrow() {
            LinearLayout arrowView2 = (LinearLayout) inflater.inflate(R.layout.view_line, null);
            deviceArrowView.addView(arrowView2);
            View arrowView2_arrow = (View) arrowView2.findViewById(R.id.v_arrow);
            arrowView2_arrow.setBackgroundColor(Color.parseColor("#51AC0B"));

            LinearLayout.LayoutParams arrowView2_params
                    = (LinearLayout.LayoutParams) arrowView2.getLayoutParams();
            arrowView2_params.width = 0;
            arrowView2_params.weight = 158;
            arrowView2.setLayoutParams(arrowView2_params);
        }

        public void bindEndArrow() {
            LinearLayout arrowView1 = (LinearLayout) inflater.inflate(R.layout.view_arrow, null);
            deviceArrowView.addView(arrowView1);
            View arrowView1_arrow = (View) arrowView1.findViewById(R.id.v_arrow);

            arrowView1_arrow.setBackgroundResource(R.drawable.arrow_end);
            LinearLayout.LayoutParams params
                    = (LinearLayout.LayoutParams) arrowView1.getLayoutParams();
            params.width = 0;
            params.weight = 158;
            arrowView1.setLayoutParams(params);
        }

        public void bindBlankArrow() {
            int nBlankWeight = 158;

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout arrowView1 = (LinearLayout) inflater.inflate(R.layout.view_line, null);

            deviceArrowView.addView(arrowView1);

            View arrowView1_arrow = (View) arrowView1.findViewById(R.id.v_arrow);

            arrowView1_arrow.setBackgroundColor(Color.parseColor("#00000000"));
            LinearLayout.LayoutParams params
                    = (LinearLayout.LayoutParams) arrowView1.getLayoutParams();
            params.width = 0;
            params.weight = nBlankWeight;
            arrowView1.setLayoutParams(params);
        }

        public void bindBlankhospital() {
            int nBlankWeight = 158;

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout arrowView1 = (LinearLayout) inflater.inflate(R.layout.view_line, null);

            deviceViewHospital.addView(arrowView1);

            View arrowView1_arrow = (View) arrowView1.findViewById(R.id.v_arrow);

            arrowView1_arrow.setBackgroundColor(Color.parseColor("#00000000"));
            LinearLayout.LayoutParams params
                    = (LinearLayout.LayoutParams) arrowView1.getLayoutParams();
            params.width = 0;
            params.weight = nBlankWeight;
            arrowView1.setLayoutParams(params);
        }

        public void bindHospital(int weight, String hospital) {
            LinearLayout hospitalView = (LinearLayout) inflater.inflate(R.layout.view_hospital, null);
            deviceViewHospital.addView(hospitalView);

            LinearLayout.LayoutParams hospitalView_params
                    = (LinearLayout.LayoutParams) hospitalView.getLayoutParams();
            hospitalView_params.width = 0;
            hospitalView_params.weight = weight;
            hospitalView.setLayoutParams(hospitalView_params);

            TextView tvHospital = (TextView) hospitalView.findViewById(R.id.tv_hospital);
            tvHospital.setText(hospital);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DeviceScheduleListAdapter(Context context, OnItemClickListener listener) {
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
        if (viewType == VIEW_TYPE_DATE) {
            KumaLog.d("VIEW_TYPE_DATE >> ");
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_schedule_date, parent, false);
            holder = new ViewHolderDate(itemView);
//            ((ViewHolderDate) holder).deviceArrowView.setTag(info);
            return holder;
        } else {
            KumaLog.d("VIEW_TYPE_DEVICE >> ");
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_schedule_device, parent, false);
            holder = new ViewHolderDevice(itemView);
            ((ViewHolderDevice) holder).deviceArrowView.setTag(position);
//            holder.setIsRecyclable(false);
            return holder;
        }
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
            if (i == 0) {
                ((ViewHolderDate) holder).mTvSunday.setText(data.diplayDay);
            } else if (i == 1) {
                ((ViewHolderDate) holder).mTvMonday.setText(data.diplayDay);
            } else if (i == 2) {
                ((ViewHolderDate) holder).mTvYuesday.setText(data.diplayDay);
            } else if (i == 3) {
                ((ViewHolderDate) holder).mTvWednesday.setText(data.diplayDay);
            } else if (i == 4) {
                ((ViewHolderDate) holder).mTvThursday.setText(data.diplayDay);
            } else if (i == 5) {
                ((ViewHolderDate) holder).mTvFriday.setText(data.diplayDay);
            } else if (i == 6) {
                ((ViewHolderDate) holder).mTvSaturday.setText(data.diplayDay);
            }
        }
    }

    private void addDeviceArrowView(RecyclerView.ViewHolder holder, final ScheduleInfo info) {
//        KumaLog.d(" addDeviceArrowView  position : " + position);

//        KumaLog.d(" info serialNo : " + info.serialNo);
//        KumaLog.d(" productCode : " + info.productCode);
//        KumaLog.d(" startDate : " + info.startDate);
//        KumaLog.d(" endDate : " + info.endDate);
        KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : ");

        String strartDate = info.start.replaceAll("-", "");


        boolean bBlankState = true;
        for (ScheduleListDayDTO data : mArrScheduleListLowDTO.get(nPosition).getmArrScheduleListDayDTO()) {
//            KumaLog.d(" mArrScheduleListLowDTO : " + data.year + "-" + data.month + "-" + data.day);
//            KumaLog.d(" startDate : " + info.startDate + " position: " + position);
            String strDate = data.year + data.month + data.day;


            if (Integer.parseInt(strartDate) == Integer.parseInt(strDate)) {
//                KumaLog.d(" addDeviceArrowView  arrow_start position : " + position);
                ((ViewHolderDevice) holder).bindStartArrow();
                KumaLog.w("info  startDate : " + info.start);
                KumaLog.w(" startDate : " + strDate);
                bBlankState = false;
                nWeightCnt++;
            }

            try {
                if (Integer.parseInt(info.start.replaceAll("-", "")) < Integer.parseInt(strDate)
                        && Integer.parseInt(info.end.replaceAll("-", "")) > Integer.parseInt(strDate)) {
//                KumaLog.d(" addDeviceArrowView  view_line position : " + position);
                    ((ViewHolderDevice) holder).bindLineArrow();
                    bBlankState = false;
                    nWeightCnt++;
                }

            } catch (Exception e) {
                bBlankState  = true;
            }

            try {
                if (Integer.parseInt(info.end.replaceAll("-", "")) == Integer.parseInt(strDate)) {
//                KumaLog.d(" addDeviceArrowView  arrow_end position : " + position);
                    ((ViewHolderDevice) holder).bindEndArrow();
                    bBlankState = false;
                    nWeightCnt++;
                    ((ViewHolderDevice) holder).bindHospital((158 * nWeightCnt ), info.title);
                }
            } catch (Exception e) {
                bBlankState  = true;
            }


            if (bBlankState) {
//                KumaLog.d(" addDeviceBlankView : " + data.year + "-" + data.month + "-" + data.day);
                ((ViewHolderDevice) holder).bindBlankArrow();
                ((ViewHolderDevice) holder).bindBlankhospital();
                nWeightCnt =  0;
            } else {
                bBlankState = true;
            }
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

    /**
     * 캘린더 변수
     */

    private Calendar mCal;

    private void dddd(String strDate) {
        long now = System.currentTimeMillis();

        String strYear = DateUtility.chageFormat("yyyy-MM-dd", strDate, "yyyy");
        String strMonth = DateUtility.chageFormat("yyyy-MM-dd", strDate, "MM");
        String strDay = DateUtility.chageFormat("yyyy-MM-dd", strDate, "dd");

        final Date date = new Date(now);
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);

        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        mCal = Calendar.getInstance();
        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);

        //입력받은 날짜의 그달의 마지막일을 구한다.
        int endDay = mCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
    }

    private void setCurDateView() {
        long now = System.currentTimeMillis();

        final Date date = new Date(now);

        //연,월,일을 따로 저장

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);

        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        setDateView(curYearFormat.format(date), curMonthFormat.format(date), curDayFormat.format(date));
    }

    private void setDateView(String strYear, String strMonth, String strDate) {

        mCal = Calendar.getInstance();

        mCal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, 1);

        int endDay = 1;

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        // 전달
        int nMinusDay = (dayNum - 2);

        setPrevMonthDate(strYear, strMonth, strDate, nMinusDay);

        // 이번달

        //입력받은 날짜의 그달의 마지막일을 구한다.
        endDay = mCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= endDay; i++) {
            mCal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, i);
            dayNum = mCal.get(Calendar.DAY_OF_WEEK);
            KumaLog.d("i : " + i + " dayNum >> " + dayNum);
        }
    }

    private void setPrevMonthDate(String strYear, String strMonth, String strDate, int nMinusDay) {
        Calendar cal;
        cal = Calendar.getInstance();

        cal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 2, 1);

        int dayNum = 1;

        int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = (endDay - nMinusDay); i <= endDay; i++) {
            cal.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 2, i);
            dayNum = cal.get(Calendar.DAY_OF_WEEK);
            KumaLog.d("i : " + i + " dayNum >> " + dayNum);
        }
    }
}
