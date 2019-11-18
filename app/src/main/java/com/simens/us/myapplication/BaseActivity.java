package com.simens.us.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.simens.us.myapplication.Constance.Constance;
import com.simens.us.myapplication.Network.ProtocolDefines;
import com.simens.us.myapplication.Network.ProtocolListener;
import com.simens.us.myapplication.Network.request.ReqLogOut;
import com.simens.us.myapplication.Network.request.ReqVersion;
import com.simens.us.myapplication.Network.request.RequestJSON;
import com.simens.us.myapplication.Network.response.CommonResponse;
import com.simens.us.myapplication.Network.response.ResVersion;
import com.simens.us.myapplication.Network.response.ResponseProtocol;
import com.simens.us.myapplication.Utils.DeviceUtils;
import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.Utils.NetUtility;
import com.simens.us.myapplication.ui.dialog.CommonDialog;
import com.simens.us.myapplication.ui.dialog.CustomProgressbarView;
import com.simens.us.myapplication.ui.dialog.IDialogListener;

/**
 * Created by 재성 on 2016-07-12.
 */
public abstract class BaseActivity extends Activity implements ProtocolListener, IDialogListener {

    //////////////////////////////////////////////////////////////////////////////
    //상수 정의
    //////////////////////////////////////////////////////////////////////////////
    public static final int REQ_ID_RESET_USER_INFO = 9999;

    public static final int REQ_ID_JOB_NOTICE = 9998;

    public static final int DLG_ID_NOTI = 1001;

    // UI 간소화 모드 검사진행 Notification
    private final static int MESSAGE_ID = 12345;
    // UI 간소화 모드 검사결과 Notification
    private final static int MESSAGE_ID1 = 123456;

    //////////////////////////////////////////////////////////////////////////////
    //Member Field
    //////////////////////////////////////////////////////////////////////////////

    CommonDialog m_simpleMsgDialog;
    //////////////////////////////////////////////////////////////////////////////
    //Method
    //////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        ((AppManager)getApplication()).addActivity(this);

    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    protected AppManager getAppManager(){
        return ((AppManager)getApplication());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        KumaLog.e("onRestart");
    }

    @Override
    public void onBackPressed() {
        KumaLog.e("onBackPressed : is ");
        if(getAppManager().getScreenSize() == 1) {
            showAlterDialog("종료 하시겠습니까?");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

//    protected boolean isNotiCancleState(){
//        return getShareDataManager().getBooleanPref(this, SharedPref.PREF_NOTI_POPUP_CANCLE_STATE, false);
//    }
//
//    protected void setNotiCancelState(boolean state) {
//        getShareDataManager().setBooleanPref(this, SharedPref.PREF_NOTI_POPUP_CANCLE_STATE, state);
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        KumaLog.e("onSaveInstanceState");
    }

    private void showAlterDialog( String strMsg){
        CommonDialog patternSaveDlg = new CommonDialog(this);
        patternSaveDlg.setType(CommonDialog.DLG_TYPE_YES_NO);
        patternSaveDlg.setDialogListener(DLG_ID_NOTI, new IDialogListener() {
            @Override
            public void onDialogResult(int nTag, int nResult, Dialog dialog) {
                // TODO Auto-generated method stub
                if (nResult == CommonDialog.RESULT_OK) {
                    reqLogOut();
                }
            }
        });
        patternSaveDlg.setMessage(strMsg);
        patternSaveDlg.show();
    }

    /**
     * 로그아웃
     */
    private void reqLogOut()
    {
        try {
            ReqLogOut reqLogOut = new ReqLogOut(this);

            reqLogOut.setTag(22221);
            requestProtocol(true, reqLogOut);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 버젼조회 결과
     */
    private void resLogOut(CommonResponse resprotocol)
    {
        KumaLog.d("++++++++++++resVersion++++++++++++++");
        if ( resprotocol.getResult().equals(ProtocolDefines.NetworkDefine.NETWORK_SUCCESS)) {

        }  else {
            if( !TextUtils.isEmpty(resprotocol.getMsg())) {
                showSimpleMessagePopup(resprotocol.getMsg());
            } else {
                showSimpleMessagePopup();
            }
        }
    }
    private static boolean m_bAppBackGroundState = false;
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        m_bAppBackGroundState = true;
        KumaLog.w("+++++++++++++++++++++++++++ BASE ACTIVITY onPause ++++++++++++++++++++++++");
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        m_bAppBackGroundState = false;
        KumaLog.w("+++++++++++++++++++++++++++ BASE ACTIVITY onResume ++++++++++++++++++++++++");
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        KumaLog.w("+++++++++++++++++++++++++++ BASE ACTIVITY onStart ++++++++++++++++++++++++");
    }
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        KumaLog.w("+++++++++++++++++++++++++++ BASE ACTIVITY onStop ++++++++++++++++++++++++");
    }
    @Override
    protected void onDestroy()
    {
        // remove this activity from activity managing list of the application.
        AppManager appMgr = (AppManager)getApplication();
        appMgr.removeActivity(this);

        if(appMgr.getNumberOfAliveActivities() < 1)
        {

        }

        System.gc();

        super.onDestroy();
    }


    @SuppressLint("ShowToast")
    public void showToast(int resId)
    {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    @SuppressLint("ShowToast")
    public void showToast(String strMsg)
    {
        Toast.makeText(this, strMsg, Toast.LENGTH_LONG).show();
    }

    protected void gotoMarket(String strUrl){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        KumaLog.d("getPackageName() : " + getPackageName());
//        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
        intent.setData(Uri.parse(strUrl));
        startActivity(intent);
        finish();
    }

    public void showLoadingPopup()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(isFinishing())
                    return;

                if( mCustomProgressbarView == null ) {
                    mCustomProgressbarView = new CustomProgressbarView(BaseActivity.this);
                    mCustomProgressbarView.setCancelable(false);
                    mCustomProgressbarView.show();
                    mCustomProgressbarView.startAnimation();
                }
            }
        });
    }
    public void dismissLoadingPopup()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (mCustomProgressbarView != null &&  mCustomProgressbarView.isShowing()) {
                    mCustomProgressbarView.dismiss();
                    mCustomProgressbarView.stopAnimation();
                }
                mCustomProgressbarView = null;
            }
        });
    }

    public void showSimpleMessagePopup(int nMessageStringId)
    {
        String strMsg = getResources().getString(nMessageStringId);
        showSimpleMessagePopup(getResources().getString(R.string.str_dlg_title_system_noti), strMsg, false);
    }

    public void showSimpleMessagePopup(int nTiyleStringId, int nMessageStringId)
    {
        String strTitle = getResources().getString(nTiyleStringId);
        String strMsg = getResources().getString(nMessageStringId);
        showSimpleMessagePopup(strTitle, strMsg, false);
    }

    public void KillAllActivity(){
        getAppManager().killAllActivity();
    }

    public void showSimpleMessagePopup()
    {
        showSimpleMessagePopup(getResources().getString(R.string.str_dlg_title_system_noti), Constance.NETWORK_ERR, false);
    }

    public void showSimpleMessagePopup(String strMsg)
    {
        showSimpleMessagePopup(getResources().getString(R.string.str_dlg_title_system_noti), strMsg, false);
    }

    public void showSimpleMessagePopup(int nMessageStringId, boolean autoDismiss)
    {
        String strMsg = getResources().getString(nMessageStringId);
        showSimpleMessagePopup(getResources().getString(R.string.str_dlg_title_system_noti), strMsg, autoDismiss);
    }

    public void showSimpleMessagePopup(final String strTitle, final String strMsg, final boolean autoDismiss)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if(isFinishing()) return;
                if(m_simpleMsgDialog != null && m_simpleMsgDialog.isShowing()) return; // prevent duplicated showing.

                //TODO m_simpleMsgDialog = new Dialog();
                if( m_simpleMsgDialog != null )
                {
                    m_simpleMsgDialog = null;
                }
                m_simpleMsgDialog = new CommonDialog(BaseActivity.this);
                m_simpleMsgDialog.setMessage(strMsg);
                m_simpleMsgDialog.show();

                if(autoDismiss)
                    m_handlerForDismissSimpleMsgPopup.sendEmptyMessageDelayed(0, AUTO_DISMISS_TIME);
            }
        });
    }

    private static final long AUTO_DISMISS_TIME = 3000L;

    private Handler m_handlerForDismissSimpleMsgPopup = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(m_simpleMsgDialog != null && m_simpleMsgDialog.isShowing())
            {
                m_simpleMsgDialog.cancel();
                m_simpleMsgDialog.dismiss();
            }
        }
    };

    protected void gotoBrowser(String strUrlPath)
    {
        if( strUrlPath.length() <= 0)
        {
            showSimpleMessagePopup("빈 스트링");
            return;
        }

        try {
            Intent informationIntent = new Intent(Intent.ACTION_VIEW);
            informationIntent.setData(Uri.parse(strUrlPath));
            startActivity(informationIntent);
        } catch (Exception e) {
            e.printStackTrace();
            showSimpleMessagePopup( "알수 없는 오류");
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    //전체 Activity 관리 모듈
    //////////////////////////////////////////////////////////////////////////////


    public void move2OtherActivity(Class<?> cls)
    {
        move2OtherActivity(cls, false);
    }

    protected void move2OtherActivity(Class<?> cls, boolean finishThisActivity)
    {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        if(finishThisActivity) finish();
    }

    protected void move2OtherActivity(Context context, Class<?> cls, boolean finishThisActivity)
    {
        Intent intent = new Intent(this, cls);
        ((Activity) context).startActivity(intent);
        if(finishThisActivity) ((Activity) context).finish();
    }

    public void move2MainScreenActivity()
    {
        AppManager appMgr = (AppManager)getApplication();
        appMgr.goMainScreenActivity();
    }

    public void requestProtocol(RequestJSON protocol)
    {
        requestProtocol(true, protocol);
    }

    public void requestProtocol(boolean isShowProgress, RequestJSON protocol)
    {

//        비행기 탑승 모드 시 동작 안되도록 함!
        if(NetUtility.isAirplaneModeOn(this))
        {
            showSimpleMessagePopup("비행기 모드에서는 이용 할 수 없습니다. 네트워크 환경을 확인 후 다시 시도해주세요.");
            //비행기 탑승 모드 시 동작 안되도록 함!
            return;
        }
//
        if(isShowProgress)
        {
            showLoadingPopup();
        }

        protocol.request(this, this);
    }

    public abstract void onResponseProtocol(int nTag, ResponseProtocol resProtocol);



    private Handler m_handler = new Handler();

    String strNetworkMsg = "", strResultCode = "";

    @Override
    public void onResponse(final int nTag, final ResponseProtocol resProtocol) {
        dismissLoadingPopup();

        if( resProtocol == null )
        {
            String strErrMsg = Constance.NETWORK_ERR;
//
//            if( !DeviceUtility.isNetworkState(this)) {
//                strErrMsg = "데이터 접속이 차단되어 있습니다. Wi-Fi 네트워크를 연결하거나, 모바일 데이터 접속 허용 상태로 변경한 후 사용하시기 바랍니다.";
//                CommonDialog commonDia = new CommonDialog(this);
//                commonDia.setType(CommonDialog.DLG_TYPE_YES_NO);
//                commonDia.setDialogListener(DLG_ID_NOTI, new IDialogListener() {
//                    @Override
//                    public void onDialogResult(int nTag, int nResult, Dialog dialog) {
//                        // TODO Auto-generated method stub
//                        if (nResult == CommonDialog.RESULT_OK) {
//                            Intent inten1t = new Intent(Settings.ACTION_SETTINGS);
//                            startActivity(inten1t);
//                        }
//                    }
//                });
//                commonDia.setCancelable(false);
//                commonDia.setMessage(strErrMsg);
//                commonDia.show();
//                return;
//            }

//            if( Constance.isServerErrorCheck() ) {
//                reqJobNotice();
//                return;
//            }

            showSimpleMessagePopup(strErrMsg);
            return;
        }

        if(  nTag == REQ_ID_RESET_USER_INFO ){
//            resResetUserInfo((ResResetUserInfo) resProtocol);
        } else if(  nTag == REQ_ID_JOB_NOTICE ){
//            resJobNotice((ResJobNotice)resProtocol);
        }  else if(  nTag == 22221 ){

        } else {
            onResponseProtocol(nTag, resProtocol);
        }
    }


    CustomProgressbarView mCustomProgressbarView ;

}