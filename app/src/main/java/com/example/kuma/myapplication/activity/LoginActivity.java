package com.example.kuma.myapplication.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kuma.myapplication.BaseActivity;
import com.example.kuma.myapplication.Network.MyVolley;
import com.example.kuma.myapplication.Network.ProtocolDefines;
import com.example.kuma.myapplication.Network.request.ReqLogin;
import com.example.kuma.myapplication.Network.response.ResLogin;
import com.example.kuma.myapplication.Network.response.ResponseProtocol;
import com.example.kuma.myapplication.R;
import com.example.kuma.myapplication.Utils.KumaLog;

import org.json.JSONObject;

/**
 * Created by Kuma on 2017-12-12.
 */

public class LoginActivity extends BaseActivity {

    private EditText mEvID;
    private EditText mEvPw;
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

    }
    /**
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
