package com.simens.us.myapplication.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simens.us.myapplication.R;

/**
 * Created by 재성 on 2016-07-19.
 * 기본 적인 팝업
 */
public class CommonDialog extends BaseDialog implements View.OnClickListener {


    public static final int DLG_TYPE_NOTI = 0;
    public static final int DLG_TYPE_YES_NO = 1;


    public static final int RESULT_OK       = 101;
    public static final int RESULT_CANCEL   = 0;

    protected String              m_strConfirmMsg;
    protected String              m_strCloseMsg;
    protected String              m_strMsg;

    private int m_nDlgType = DLG_TYPE_NOTI;

    private boolean m_bHtmlState = false;

    public CommonDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_common);

        // Type 에 따른 버튼 생성

        if(m_nDlgType == DLG_TYPE_YES_NO)
        {
            findViewById(R.id.ll_dlg_confirm).setVisibility(View.GONE);
            findViewById(R.id.ll_dlg_yes_no).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_dlg_yes).setOnClickListener(this);
            findViewById(R.id.btn_dlg_no).setOnClickListener(this);
        }
        else
        {
            findViewById(R.id.ll_dlg_confirm).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_dlg_yes_no).setVisibility(View.GONE);
            findViewById(R.id.btn_dlg_confirm).setOnClickListener(this);
        }

        if(m_strMsg != null) {
            if( m_bHtmlState ) {
                setMsg();
            } else {
                ((TextView) findViewById(R.id.tv_msg)).setText(m_strMsg);
            }
        }


        if(m_strConfirmMsg != null) {
            Button ddd = (Button)findViewById(R.id.btn_dlg_yes);
            ddd.setText(m_strConfirmMsg);
        }

        if(m_strCloseMsg != null) {
            Button dasd = (Button)findViewById(R.id.btn_dlg_no);
            dasd.setText(m_strCloseMsg);
        }
    }

    public void setHtmlState(boolean state ) {
        m_bHtmlState = state;
    }

    private void setMsg(){
        ((TextView) findViewById(R.id.tv_msg)).setText(Html.fromHtml(m_strMsg));

    }
    public void setType(int nType) {
        m_nDlgType = nType;
    }

    public void setConfirmMsg(String strConfirmMsg)
    {
        m_strConfirmMsg = strConfirmMsg;
    }

    public void setCloseMsg(String strCloseMsg)
    {
        m_strCloseMsg = strCloseMsg;
    }

    public void setMessage(String strMsg)
    {
        if(isShowing() && strMsg != null)
            ((TextView)findViewById(R.id.tv_msg)).setText(strMsg);
        m_strMsg = strMsg;
    }

    public void setMessage(int nStringResId)
    {
        if(isShowing())
            ((TextView)findViewById(R.id.tv_msg)).setText(nStringResId);
        m_strMsg = getContext().getResources().getString(nStringResId);
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
        long lCurrentTime = System.currentTimeMillis();
        if((lCurrentTime - m_lBeforeClickedTime) < BaseDialog.CLICK_IGNORE_INTERVAL)
            return;
        m_lBeforeClickedTime = lCurrentTime;

        switch(v.getId())
        {
            case R.id.btn_dlg_confirm:
                setResult(RESULT_OK);
                break;
            case R.id.btn_dlg_yes:
                setResult(RESULT_OK);
                break;
            case R.id.btn_dlg_no:
                setResult(RESULT_CANCEL);
                break;

        }
        dismiss();
    }
}
