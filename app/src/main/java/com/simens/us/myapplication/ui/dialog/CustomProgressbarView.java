package com.simens.us.myapplication.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.simens.us.myapplication.R;

/**
 * Created by wtsun on 2016-05-16.
 * SDK에서 쓰고 있는 로딩 팝업 애니메이션
 */
public class CustomProgressbarView extends Dialog {

    private Context mContent;
    private View m_View;

//    private TextView mTvDumyInfo;
    private ImageView mIgvAnimation;
    private AnimationDrawable animationDrawable;


    public CustomProgressbarView(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mContent = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.0f; // 투명도 0 ~ 1
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.view_progressbar_rp);

        mIgvAnimation = (ImageView) findViewById(R.id.igv_animation);

//        mTvDumyInfo.setText(info);

        animationDrawable = (AnimationDrawable) mIgvAnimation.getBackground();

    }

    public void startAnimation(){
        animationDrawable.start();
    }

    public void stopAnimation() {
        animationDrawable.stop();
    }

    public void setImageViewChange(){
//        mIgvAnimation.setBackgroundResource(R.drawable.loading_icon_phone);
    }
}
