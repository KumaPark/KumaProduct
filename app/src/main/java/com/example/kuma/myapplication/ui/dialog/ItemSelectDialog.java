package com.example.kuma.myapplication.ui.dialog;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kuma.myapplication.Constance.Constance;
import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.activity.DeviceDemoDetailActivity;
import com.example.kuma.myapplication.adapter.DeviceScheduleListAdapter;
import com.example.kuma.myapplication.adapter.ItemSelectAdapter;
import com.example.kuma.myapplication.adapter.productSelectAdapter;
import com.example.kuma.myapplication.data.ScheduleInfo;
import com.example.kuma.myapplication.data.ScheduleListDayDTO;
import com.example.kuma.myapplication.data.SelectItemInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ItemSelectDialog extends BaseDialog implements View.OnClickListener {


    public static final int DLG_TYPE_NOTI = 0;
    public static final int DLG_TYPE_YES_NO = 1;

    private int TAG_SELECTED_TYPE = Constance.TAG_CAPTURE_DEVICE;

    public static final int RESULT_OK       = 101;
    public static final int RESULT_CANCEL   = 0;

    private View mVSeleteedView;
    private Context mContext = null;
    private TextView mTvDisplayView;
    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(ScheduleInfo data);
    }

    public ItemSelectDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    private ItemSelectAdapter mAdapter;
    private RecyclerView mItemlist;
    private TextView mTvTitle;
    private TextView mTvCancle;
    private TextView mTvSelect;

    private ItemSelectAdapter mItemSelectAdapter;

    HashMap<String, HashMap<String, SelectItemInfo>> arrSeletedListInfo = new HashMap<>();

    private List<SelectItemInfo> mArrItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        KumaLog.line();
        KumaLog.e("ProductSelectDialog onCreate");
        setContentView(R.layout.dlg_item_select);

        mItemlist = (RecyclerView)findViewById(R.id.lv_item_list);
        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvCancle = (TextView)findViewById(R.id.tv_cancle);
        mTvSelect = (TextView)findViewById(R.id.tv_select);

        mTvSelect.setOnClickListener(this);
        mTvCancle.setOnClickListener(this);

        mItemlist.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        if( mItemSelectAdapter == null ) {
            mItemSelectAdapter = new ItemSelectAdapter();
            mItemlist.setAdapter(mItemSelectAdapter);
        }
        mItemSelectAdapter.setData(mArrItemList);

        mItemSelectAdapter.setOnItemClickListener(new ItemSelectAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(SelectItemInfo info) {
                if( arrSeletedListInfo.containsKey(info.categoryName)) {
                    if( !arrSeletedListInfo.get(info.categoryName).containsKey(info.serialNo)) {
                        arrSeletedListInfo.get(info.categoryName).put(info.serialNo, info);
                    }
                } else {
                    HashMap<String, SelectItemInfo> arrChildInfo = new HashMap<>();
                    arrChildInfo.put(info.serialNo, info);
                    arrSeletedListInfo.put(info.categoryName, arrChildInfo);
                }
            }

            @Override
            public void onRemoveClick(SelectItemInfo info) {
                if( arrSeletedListInfo.containsKey(info.categoryName)) {
                    if( arrSeletedListInfo.get(info.categoryName).containsKey(info.serialNo)) {
                        arrSeletedListInfo.get(info.categoryName).remove(info.serialNo);
                    }
                }
            }
        });
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    public void setData(List<SelectItemInfo> itemList){
        mArrItemList.clear();
        mArrItemList.addAll(itemList);
    }

    public void setDisPlayView(TextView view){
        this.mTvDisplayView = view;
    }
    private void showSelectedData(){
        try {
            String strCategory = "";
            ArrayList<String> arrDisplay = new ArrayList<>();
            Set key = arrSeletedListInfo.keySet();
            KumaLog.line();
            for (Iterator iterator = key.iterator(); iterator.hasNext();) {
                String keyName = (String) iterator.next();
                HashMap<String, SelectItemInfo> arrValue = (HashMap<String, SelectItemInfo>) arrSeletedListInfo.get(keyName);
                Set valueKey = arrValue.keySet();
                arrDisplay.add(keyName);
                KumaLog.d("keyName >> " + keyName );
                for (Iterator valueArr = valueKey.iterator(); valueArr.hasNext();) {
                    String strValueKey = (String) valueArr.next();
                    SelectItemInfo valueData = (SelectItemInfo) arrValue.get(strValueKey);
                    KumaLog.d("categoryName >> " + valueData.categoryName + " serialNo >> " + valueData.serialNo);
                }
            }

            for(int i = 0; i < arrDisplay.size(); i++ ) {
                if( i == ( arrDisplay.size() - 1) ) {
                    strCategory += arrDisplay.get(i);
                } else {
                    strCategory += arrDisplay.get(i) + ", ";
                }
            }
            KumaLog.d("strCategory >> " + strCategory );

            mTvDisplayView.setText(strCategory);
            KumaLog.line();
        } catch (Exception e) {

        }

        dismiss();
    }

    @Override
    public void show() {
        arrSeletedListInfo.clear();
        if( mItemSelectAdapter != null ){
            mItemSelectAdapter.setData(mArrItemList);
            mItemSelectAdapter.notifyItemRangeChanged(0, mArrItemList.size());
        }
        super.show();
    }

    @Override
    public void onBackPressed()
    {
        setResult(RESULT_CANCEL);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.tv_select:
                showSelectedData();
                break;
            case R.id.tv_cancle:
                dismiss();
                break;
            default:
                break;
        }

    }
}
