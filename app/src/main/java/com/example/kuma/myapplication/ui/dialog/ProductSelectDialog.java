package com.example.kuma.myapplication.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.kuma.myapplication.Constance.Constance;
import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.KumaLog;
import com.example.kuma.myapplication.activity.DeviceConditionActivity;
import com.example.kuma.myapplication.activity.DeviceDemoDetailActivity;
import com.example.kuma.myapplication.adapter.productSelectAdapter;
import com.example.kuma.myapplication.data.ScheduleInfo;
import com.example.kuma.myapplication.data.ScheduleListDayDTO;

public class ProductSelectDialog extends BaseDialog implements View.OnClickListener {


    public static final int DLG_TYPE_NOTI = 0;
    public static final int DLG_TYPE_YES_NO = 1;

    private int TAG_SELECTED_TYPE = Constance.TAG_CAPTURE_DEVICE;

    public static final int RESULT_OK       = 101;
    public static final int RESULT_CANCEL   = 0;

    private View mVSeleteedView;
    private Context mContext = null;
    public ProductSelectDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    private productSelectAdapter mAdapter;
    private ListView mProductlist;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        KumaLog.line();
        KumaLog.e("ProductSelectDialog onCreate");
        setContentView(R.layout.dlg_producr_select);

        mProductlist = (ListView)findViewById(R.id.lv_product_list);

        mAdapter = new productSelectAdapter(mContext, new productSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ScheduleInfo data) {
                Intent intent = new Intent(mContext,DeviceDemoDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailData",data);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                setResult(RESULT_OK);
                dismiss();
            }
        });
        mProductlist.setAdapter(mAdapter);
    }

    public void setData(ScheduleListDayDTO data){
        KumaLog.e("ProductSelectDialog setData");
        if( mAdapter != null ) {
            mAdapter.setArrayList(data.getScheduleInfoList());
            mAdapter.notifyDataSetChanged();
        }
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


    }
}
