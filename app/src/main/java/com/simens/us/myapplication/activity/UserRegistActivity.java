package com.simens.us.myapplication.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simens.us.myapplication.BaseActivity;
import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.request.ReqLogin;
import com.simens.us.myapplication.Network.request.ReqUserRegist;
import com.simens.us.myapplication.Network.response.ResUserRegist;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.DeviceUtils;
import com.simens.us.myapplication.Utils.KumaLog;

public class UserRegistActivity extends BaseActivity {

    private EditText mEvEmail;
    private EditText mEvTempPw;
    private EditText mEvPwNew;
    private EditText mEvPwConfirm;

    private Button mBtnConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regist);

        mEvEmail =  (EditText) findViewById(R.id.ev_email);
        mEvTempPw =  (EditText) findViewById(R.id.ev_temp_password);
        mEvPwNew =  (EditText) findViewById(R.id.ev_new_password);
        mEvPwConfirm =  (EditText) findViewById(R.id.ev_new_password_confirm);

        mBtnConfirm =  (Button) findViewById(R.id.btn_confirm);

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reqUserRegist();
            }
        });
    }
    /**
     * 사용자 등록
     */
    private void reqUserRegist()
    {
        try {
            String strEmail = mEvEmail.getText().toString().trim();
            String strPwTemp = mEvTempPw.getText().toString().trim();
            String strPwnew = mEvPwNew.getText().toString().trim();
            String strPwConfirm = mEvPwConfirm.getText().toString().trim();

            if( strEmail.length() == 0) {
                showSimpleMessagePopup("Email을 입력해주세요.");
                return;
            }

            if( strPwTemp.length() == 0) {
                showSimpleMessagePopup("임시비밀번호를 입력해주세요.");
                return;
            }

            if( strPwnew.length() < 15 ) {
                showSimpleMessagePopup("대소문자 특수문자 조합의 15이상의 새로운 비밀번호를 입력해주세요.");
                return;
            }

            if( strPwConfirm.length() < 15 ) {
                showSimpleMessagePopup("대소문자 특수문자 조합의 15이상의 확인 비밀번호를 입력해주세요.");
                return;
            }

            if( !strPwnew.equals(strPwConfirm) ) {
                showSimpleMessagePopup("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
                return;
            }

            ReqUserRegist reqUserRegist = new ReqUserRegist();

            reqUserRegist.setTag(1);
            reqUserRegist.setLoginId(strEmail);
            reqUserRegist.setMacAddr(DeviceUtils.getImei(this));
            reqUserRegist.setNewPw(strPwnew);
            reqUserRegist.setTempPw(strPwTemp);
            reqUserRegist.setRegId("111");
            requestProtocol(true, reqUserRegist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 로그인 결과
     */
    private void resUserRegist(ResUserRegist resprotocol)
    {
        KumaLog.d("++++++++++++resUserRegist++++++++++++++");
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {
// 약관 동의 받고 로그인 페이지 이동
            move2OtherActivity(LoginActivity.class, true);
        }  else {
            if( !TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }

    @Override
    public void onResponseProtocol(int nTag, ResponseProtocol resProtocol) {
        resUserRegist((ResUserRegist)resProtocol);
    }


    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {

    }
}
