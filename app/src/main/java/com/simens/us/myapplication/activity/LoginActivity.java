package com.simens.us.myapplication.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.simens.us.myapplication.AppManager;
import com.simens.us.myapplication.BaseActivity;
import com.simens.us.myapplication.Constance.Constance;
import com.simens.us.myapplication.Network.MyVolley;
import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.request.ReqLogin;
import com.simens.us.myapplication.Network.response.ResLogin;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.R;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.Utils.SharedPref.SharedPref;

import org.json.JSONObject;

/**
 * Created by Kuma on 2017-12-12.
 */

public class LoginActivity extends BaseActivity {

    private EditText mEvID;
    private EditText mEvPw;
    private boolean  mBSaveState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEvID = (EditText) findViewById(R.id.ev_id);
        mEvPw = (EditText) findViewById(R.id.ev_pw);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reqResetUserInfo();
            }
        });


        mBSaveState = getAppManager().getShareDataManager().getBooleanPref(LoginActivity.this, SharedPref.PREF_ID_SAVE_STATE, false);
//        mEvID.setText("admin@lionskaphp.co.kr");
//        mEvPw.setText("qwerasdfzxcv123!");

        try {
            if( !TextUtils.isEmpty(getAppManager().getShareDataManager().getStringPref(LoginActivity.this, SharedPref.PREF_ID_SAVE)  ))  {
                mEvID.setText(getAppManager().getShareDataManager().getStringPref(LoginActivity.this, SharedPref.PREF_ID_SAVE));
            }
        }catch (Exception e){
            KumaLog.e(e.toString());
        }


        ((CheckBox)findViewById(R.id.cv_save_id)).setChecked(mBSaveState);
        ((CheckBox)findViewById(R.id.cv_save_id)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getAppManager().getShareDataManager().setBooleanPref(LoginActivity.this, SharedPref.PREF_ID_SAVE_STATE, isChecked);
            }
        });

//        mEvID.setText("jiji@cccc.co.kr");
//        mEvPw.setText("qwerasdfzxcv123!");
    }
    /**``
     * 로그인
     */
    private void reqResetUserInfo()
    {
        try {
            String strId = mEvID.getText().toString().trim();
            String strPw = mEvPw.getText().toString().trim();
            if( strId.length() == 0) {
                showSimpleMessagePopup("ID를 입력해주세요.");
                return;
            }

            if( strPw.length() == 0) {
                showSimpleMessagePopup("비밀번호를 입력해주세요.");
                return;
            }
            ReqLogin reqLogin = new ReqLogin();

            reqLogin.setTag(1);
            reqLogin.setUserId( strId );
            reqLogin.setPassword( strPw );
            requestProtocol(true, reqLogin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 로그인 결과
     */
    private void resResetUserInfo(ResLogin resprotocol)
    {
        KumaLog.d("++++++++++++resResetUserInfo++++++++++++++");
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {

            getAppManager().getShareDataManager().setStringPref(this, SharedPref.PREF_LOGIN_TOKEN, resprotocol.getToken());
//            permission : 권한 ex) 1 : 대리점, 32 : 임상, 64 : 관리자
            Constance.USER_NAME  = resprotocol.getName();
            Constance.USER_PERMISSION  = resprotocol.getPermisson();
            if( getAppManager().getShareDataManager().getBooleanPref(LoginActivity.this, SharedPref.PREF_ID_SAVE_STATE, false)  )  {
                getAppManager().getShareDataManager().setStringPref(LoginActivity.this, SharedPref.PREF_ID_SAVE, mEvID.getText().toString().trim());
            }
            KumaLog.d("++++++++++++Constance.USER_PERMISSION  ++++++++++++++"  + Constance.USER_PERMISSION);
            if(Constance.isAgency() ){
                move2OtherActivity(DeviceConditionActivity.class, true);
                return;
            }
            move2OtherActivity(MainActivity.class, true);
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
        resResetUserInfo((ResLogin)resProtocol);
    }



    private void networkTest(){
        RequestQueue queue = MyVolley.getInstance(this).getRequestQueue();
        JSONObject obj = new JSONObject();

        try {
            obj.put("test","testasadasd");
        } catch (Exception e) {

        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, ProtocolDefines.UrlConstance.URL_LOGIN, obj, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("test","response : " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("test","VolleyError : " + error);
                    }
                });

        // Access the RequestQueue through your singleton class.
        queue.add(jsObjRequest);
    }

    @Override
    public void onDialogResult(int nTag, int nResult, Dialog dialog) {

    }
}
