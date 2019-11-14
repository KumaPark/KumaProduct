package com.simens.us.myapplication.ui.dialog;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.simens.us.myapplication.Constance.Constance;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.adapter.DropBoxCommonAdapter;
import com.simens.us.myapplication.data.DropBoaxCommonDTO;
import java.util.ArrayList;

public class DropBoxCommonDialog extends BaseDialog implements View.OnClickListener {


    public static final int DLG_TYPE_NOTI = 0;
    public static final int DLG_TYPE_YES_NO = 1;

    private int TAG_SELECTED_TYPE = Constance.TAG_CAPTURE_DEVICE;

    public static final int RESULT_OK       = 101;
    public static final int RESULT_CANCEL   = 0;

    private View mVSeleteedView;
    private Context mContext = null;

    private int nTag = 0;
    public DropBoxCommonDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int tag, DropBoaxCommonDTO data);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    private DropBoxCommonAdapter mAdapter;
    private ListView mCommonlist;
    private TextView mTvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_dropbox_select);

        mCommonlist = (ListView)findViewById(R.id.lv_common_list);
        mTvTitle = (TextView)findViewById(R.id.tv_title);

        mAdapter = new DropBoxCommonAdapter(mContext, new DropBoxCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DropBoaxCommonDTO data) {
                if( mListener != null ) {
                    mListener.onItemClick(nTag, data);
                }
                dismiss();
            }
        });


        mCommonlist.setAdapter(mAdapter);

        findViewById(R.id.ll_product_dlg_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public void setData(ArrayList<DropBoaxCommonDTO> data){
        KumaLog.e("ProductSelectDialog setData");
        if( mAdapter != null ) {
            mAdapter.setArrayList(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void  setDataTag(int tag){
        this.nTag = tag;
    }

    @Override
    public void show() {
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


    }
}
