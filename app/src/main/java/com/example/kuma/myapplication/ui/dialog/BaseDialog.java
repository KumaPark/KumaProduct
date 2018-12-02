package com.example.kuma.myapplication.ui.dialog;



import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.example.kuma.myapplication.R;

public class BaseDialog extends Dialog
{
	
	public static long CLICK_IGNORE_INTERVAL = 500;
	
	//onClick 시 2중 입력방지
	protected long m_lBeforeClickedTime    = 0L;
	
	
	
    private IDialogListener  m_dlgListener;
    private int                 m_nResult       = 0;
    private int 				m_nTag = 0;
    private Object			m_obj;
    
    public void setDialogListener(int nTag, IDialogListener l)
    {
        m_dlgListener = l;
        m_nTag = nTag;
    }
    
    protected void setResult(int nResult)
    {
        m_nResult = nResult;
    }
    
    @Override
    public void dismiss()
    {
        if(m_dlgListener != null)
            m_dlgListener.onDialogResult(m_nTag, m_nResult, this);
        super.dismiss();
    }

    public void setObject(Object obj)
    {
    	m_obj = obj;
    }
    
    public Object getObject()
    {
    	return m_obj;
    }
    
    public BaseDialog(Context context)
    {
        super(context, R.style.Dialog);
        this.setCanceledOnTouchOutside(false);
    }
    
    protected void showToast(String strMsg, int duration)
    {
        Toast.makeText(getContext(), strMsg, duration).show();
    }
    
    protected void showToast(int nStrId, int duration)
    {
        Toast.makeText(getContext(), nStrId, duration).show();
    }
}
