package com.simens.us.myapplication.ui.dialog;

import android.app.Dialog;

/**
 * 다이얼로그 리스너
 * */
public interface IDialogListener {
    
    public void onDialogResult(int nTag, int nResult, Dialog dialog);
}
