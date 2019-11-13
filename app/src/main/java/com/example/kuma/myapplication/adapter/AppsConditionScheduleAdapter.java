package com.example.kuma.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.data.AppsScheduleListDTO;
import com.example.kuma.myapplication.data.AppsScheduleListDayDTO;
import com.example.kuma.myapplication.data.AppsScheduleListLowDTO;
import com.example.kuma.myapplication.data.ScheduleInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class AppsConditionScheduleAdapter extends BaseAdapter {

    public static final int VIEW_TYPE_DATE = 100;
    public static final int VIEW_TYPE_DEVICE = 101;

    private HashMap<String, Object> mDataset = new HashMap<>();
    private ArrayList<AppsScheduleListDTO> mDataListInfo = new ArrayList<>();
    private ArrayList<AppsScheduleListLowDTO> mArrScheduleListLowDTO = new ArrayList<>();
    private static OnItemClickListener mListener;

    private Context mContext;
    private LayoutInflater inflater;

    private int nPosition = -1;

    public interface OnItemClickListener {
        void onItemClick(AppsScheduleListDayDTO data);
    }
    public AppsConditionScheduleAdapter(Context context , OnItemClickListener listener){
        this.mContext = context;
        this.mListener = listener;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Adapter가 관리할 Data의 개수를 설정 합니다.
    @Override
    public int getCount() {
        return mDataListInfo.size();
    }

    // Adapter가 관리하는 Data의 Item 의 Position을 <객체> 형태로 얻어 옵니다.
    @Override
    public AppsScheduleListDTO getItem(int position) {
        return mDataListInfo.get(position);
    }

    // Adapter가 관리하는 Data의 Item 의 position 값의 ID 를 얻어 옵니다.
    @Override
    public long getItemId(int position) {
        return position;
    }

    // ListView의 뿌려질 한줄의 Row를 설정 합니다.
    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        View v = convertview;

        ViewHolder viewHolder  = null;
        nPosition =  position;
        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.view_condition_schedule_date, null);
            viewHolder.mllContents = (LinearLayout) v.findViewById(R.id.ll_contents);

            v.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)v.getTag();
        }
        KumaLog.d(" >>>>>>>>>>>>>>>>> getView >>>>>>>>>>>>>>>>>>>>>>>>>>> : ");
        viewHolder.mllContents.setTag(position);
        viewHolder.mllContents.removeAllViews();
        addDateView(viewHolder.mllContents);
        return v;
    }

    public void setItems(HashMap<String, Object> items, ArrayList<AppsScheduleListDTO> lineInfo, ArrayList<AppsScheduleListLowDTO> scheduleListLowDTO) {
        mDataset.clear();
        mDataset.putAll(items);

        mDataListInfo.clear();
        mDataListInfo.addAll(lineInfo);

        mArrScheduleListLowDTO.clear();
        mArrScheduleListLowDTO.addAll(scheduleListLowDTO);
    }
    private int nWeightCnt = 0;

    public void bindData(int tag, AppsScheduleListDayDTO data, LinearLayout llContents) {
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

        llContents.addView(llDateData);

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
            if( data.dayNum ==  1 ) {
                ((TextView) llDateData.findViewById(R.id.tv_date)).setTextColor(Color.parseColor("#FF0000"));
//                for( int i= 0;  i < mArrTv.length; i++)  {
//                    setBlankDate(mArrTv[i]);
//                }
            } else if( data.dayNum ==  7 ) {
                ((TextView) llDateData.findViewById(R.id.tv_date)).setTextColor(Color.parseColor("#489CFF"));
//                for( int i= 0;  i < mArrTv.length; i++)  {
//                    setBlankDate(mArrTv[i]);
//                }
            } else {
//                setStateData(mArrTv, data);
            }
            setStateData(mArrTv, data);
            ((TextView) llDateData.findViewById(R.id.tv_date)).setText(data.diplayDay);

        }
        llDateData.setTag(data);
        llDateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KumaLog.d(" >>>>>>>>>>>>>>>>> setOnClickListener >>>>>>>>>>>>>>>>>>>>>>>>>>> : ");
                if (mListener != null) {
                    AppsScheduleListDayDTO info = (AppsScheduleListDayDTO) view.getTag();

                    mListener.onItemClick(info);
                }
            }
        });
    }

    private void setBlankDate(TextView view){
        view.setText("");
        view.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    public void setStateData(TextView[]  mArrTv, AppsScheduleListDayDTO data){

        for( int i= 0;  i < mArrTv.length; i++)  {
            if(  i < data.getScheduleInfoList().size() ){
                mArrTv[i].setText(data.getScheduleInfoList().get(i).title);
                mArrTv[i].setTextColor(Color.parseColor("#ffffff"));
                String color  = "#aaaa2222";
                if(  !TextUtils.isEmpty(data.getScheduleInfoList().get(i).getColor())) {
                    color = data.getScheduleInfoList().get(i).getColor();
                }
                mArrTv[i].setBackgroundColor(Color.parseColor(color));
            } else {
                setBlankDate(mArrTv[i]);
            }

        }

        KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> diplayDay  : " +  data.diplayDay + " size >> "+  data.getScheduleInfoList().size());
    }
    private void addDateView(LinearLayout llContents) {

        int position = (Integer) llContents.getTag();

        KumaLog.d(" addDateView nPosition : " + nPosition + "  position > " + position);

        for (int i = 0; i < mArrScheduleListLowDTO.get(position).getmArrScheduleListDayDTO().size(); i++) {
            AppsScheduleListDayDTO data = mArrScheduleListLowDTO.get(position).getmArrScheduleListDayDTO().get(i);
            bindData(i, data, llContents);
        }
    }
    private void setStateData(RecyclerView.ViewHolder holder, int tag, AppsScheduleListDayDTO data ){
        KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> tag  : " + tag);
    }


    private void addDeviceArrowView(RecyclerView.ViewHolder holder, final ScheduleInfo info) {

//        KumaLog.d(" info serialNo : " + info.serialNo);
//        KumaLog.d(" productCode : " + info.productCode);
//        KumaLog.d(" startDate : " + info.startDate);
//        KumaLog.d(" endDate : " + info.endDate);
//        KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : ");

        boolean bBlankState = true;
        for (AppsScheduleListDayDTO data : mArrScheduleListLowDTO.get(nPosition).getmArrScheduleListDayDTO()) {
            KumaLog.d(" diplayDay : " + data.diplayDay);
//            ((ViewHolderDevice) holder).bindSetData((nPosition % 8),nPosition);
        }
        KumaLog.d(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : ");
    }


    /*
     * ViewHolder
     * getView의 속도 향상을 위해 쓴다.
     * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
     */
    class ViewHolder{
        public LinearLayout mllContents  = null;
    }
    private void free(){
        inflater = null;
        mContext = null;
    }
}
