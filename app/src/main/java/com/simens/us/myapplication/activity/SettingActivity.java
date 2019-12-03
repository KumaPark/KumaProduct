package com.simens.us.myapplication.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.simens.us.myapplication.BaseActivity;
import com.simens.us.myapplication.Constance.Constance;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.DeviceUtils;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ((TextView)findViewById(R.id.tv_user)).setText(Constance.USER_NAME);
        ((TextView)findViewById(R.id.tv_version)).setText(DeviceUtils.getAppVersion(this));

        findViewById(R.id.iv_back ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppManager().removeActivity(SettingActivity.this);
                finish();
            }
        });
        findViewById(R.id.ll_pass_change ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleMessagePopup("서비스 준비중입니다.");
            }
        });

    }
    @Override
    public void onResponseProtocol(int nTag, ResponseProtocol resProtocol) {
    }


    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
    }
}
