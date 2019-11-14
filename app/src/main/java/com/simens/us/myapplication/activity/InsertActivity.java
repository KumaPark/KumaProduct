package com.simens.us.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.simens.us.myapplication.BaseActivity;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.KumaLog;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Kuma on 2018-02-19.
 */

public class InsertActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }
    @Override
    public void onResponseProtocol(int nTag, ResponseProtocol resProtocol) {
    }


    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
    }
}