package com.simens.us.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simens.us.myapplication.R;
import com.simens.us.myapplication.data.DropBoaxCommonDTO;
import com.simens.us.myapplication.data.ScheduleInfo;

import java.util.ArrayList;

public class DropBoxCommonAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private ArrayList<DropBoaxCommonDTO> infoList = new ArrayList<>();
    private ViewHolder viewHolder = null;
    private Context mContext = null;

    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(DropBoaxCommonDTO data);
    }
    public DropBoxCommonAdapter(Context c , OnItemClickListener listener){
        this.mContext = c;
        this.inflater = LayoutInflater.from(c);
        this.mListener = listener;
    }

    // Adapter가 관리할 Data의 개수를 설정 합니다.
    @Override
    public int getCount() {
        return infoList.size();
    }

    // Adapter가 관리하는 Data의 Item 의 Position을 <객체> 형태로 얻어 옵니다.
    @Override
    public DropBoaxCommonDTO getItem(int position) {
        return infoList.get(position);
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

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.view_dlg_dropbaox_common_list, null);
            viewHolder.rl_item = (RelativeLayout) v.findViewById(R.id.rl_item);

            viewHolder.tv_name = (TextView)v.findViewById(R.id.tv_name);

            v.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)v.getTag();
        }
        DropBoaxCommonDTO data = getItem(position);
        if(TextUtils.isEmpty(data.name))  {
            viewHolder.tv_name.setText(data.content);
        } else {
            viewHolder.tv_name.setText(data.name);
        }


        viewHolder.rl_item.setTag(data);
        viewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mListener != null) {
                    DropBoaxCommonDTO info = (DropBoaxCommonDTO)v.getTag();
                    mListener.onItemClick(info);
                }
            }
        });

        return v;
    }

    // Adapter가 관리하는 Data List를 교체 한다.
    // 교체 후 Adapter.notifyDataSetChanged() 메서드로 변경 사실을
    // Adapter에 알려 주어 ListView에 적용 되도록 한다.
    public void setArrayList(ArrayList<DropBoaxCommonDTO> arrays){
        this.infoList = arrays;
    }

    public ArrayList<DropBoaxCommonDTO> getArrayList(){
        return infoList;
    }

    /*
     * ViewHolder
     * getView의 속도 향상을 위해 쓴다.
     * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
     */
    class ViewHolder{
        public RelativeLayout rl_item = null;
        public TextView tv_name = null;
    }
    private void free(){
        inflater = null;
        infoList = null;
        viewHolder = null;
        mContext = null;
    }
}
