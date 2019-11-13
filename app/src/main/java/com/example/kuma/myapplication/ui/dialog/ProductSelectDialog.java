package com.example.kuma.myapplication.ui.dialog;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    private TextView mTvDate;
    private ImageView mIvSchedulePlus;
    private ImageView mIvSchedulePlusTo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        KumaLog.line();
        KumaLog.e("ProductSelectDialog onCreate");
        setContentView(R.layout.dlg_producr_select);

        mProductlist = (ListView)findViewById(R.id.lv_product_list);
        mTvDate = (TextView)findViewById(R.id.tv_date);
        mIvSchedulePlus = (ImageView)findViewById(R.id.iv_schedule_plus_from);
        mIvSchedulePlusTo = (ImageView)findViewById(R.id.iv_schedule_plus_dlg);



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

        findViewById(R.id.ll_product_dlg_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mIvSchedulePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KumaLog.e("ProductSelectDialog onClick");
            }
        });
    }

    public void setData(ScheduleListDayDTO data){
        KumaLog.e("ProductSelectDialog setData");
        if( mAdapter != null ) {
            mAdapter.setArrayList(data.getScheduleInfoList());
            mAdapter.notifyDataSetChanged();
        }
//        data.diplayDay
        mTvDate.setText(data.getMonth()+ "월 " + data.getDay() + "일");
//
    }

    @Override
    public void show() {
        super.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                float fromX = mIvSchedulePlus.getX();
                float fromY = mIvSchedulePlus.getY();
                float toX = mIvSchedulePlusTo.getX();
                float toY = mIvSchedulePlusTo.getY();

                float  resizeWidth_1 =  (float)mIvSchedulePlusTo.getWidth() / (float)mIvSchedulePlus.getWidth();
                float resizeHeight_2 = ((float)mIvSchedulePlusTo.getHeight() / (float)mIvSchedulePlus.getHeight());

                float  resizeWidth =  mIvSchedulePlusTo.getWidth() % mIvSchedulePlus.getWidth();
                float resizeHeight = mIvSchedulePlusTo.getHeight() % mIvSchedulePlus.getHeight();

                KumaLog.line();
                KumaLog.d(" fromX : " + fromX + " fromY : " + fromY);
                KumaLog.d(" toX : " + toX + " toY : " + toY);
                KumaLog.d(" resizeWidth_1 : " + resizeWidth_1 + " resizeHeight_2 : " + resizeHeight_2);
                KumaLog.d(" resizeWidth : " + resizeWidth + " resizeHeight : " + resizeHeight);
                mIvSchedulePlus.animate().translationY(toY - fromY).setDuration(500).withLayer().setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                mIvSchedulePlus.animate().translationX(toX - fromX).setDuration(500).withLayer();
                mIvSchedulePlus.animate().scaleX(resizeWidth_1).withLayer();
                mIvSchedulePlus.animate().scaleY(resizeHeight_2).withLayer();
            }
        }, 500);

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
