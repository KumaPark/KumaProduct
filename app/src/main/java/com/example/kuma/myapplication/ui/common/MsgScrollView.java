package com.example.kuma.myapplication.ui.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ScrollView;

/**
 * Created by 재성 on 2016-10-05.
 * 작업공지 다이얼로그의 경우 메시지가 길어서 쓰게된 ScrollView
 * maxHeight 까지만 셋팅됨
 */
public class MsgScrollView extends ScrollView {
    public static final int maxHeight = 300; // 100dp

    public MsgScrollView(Context context) {
        super(context);
    }

    public MsgScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // default constructors

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(dpToPx(getResources(),maxHeight), MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    private int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }
}