package com.example.kuma.myapplication.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.kuma.myapplication.Utils.CallRecord;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kuma on 2017-12-13.
 */

public class IncomingCallBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "Kuma";
    private static String mLastState;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    static CallRecord callRecord;

    Context mContext;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        /** * http://mmarvick.github.io/blog/blog/lollipop-multiple-broadcastreceiver-call-state
         * / * 2번 호출되는 문제 해결 */
        mContext = context;

        if (callRecord != null) {
            Log.d(TAG, "+++++++++++++++++++ callRecord != null) +++++++++++++++++++ ");
        } else {
            Log.d(TAG, "+++++++++++++++++++ callRecord == null) +++++++++++++++++++ ");
        }

        try {
            Log.d(TAG, "+++++++++++++++++++ IncomingCallBroadcastReceiver 1111111111111111 +++++++++++++++++++ ");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state == null || state.equals(mLastState)) {
                return;
            } else {
                mLastState = state;
            }
        }catch ( Exception e) {
            Log.d(TAG, "+++++++++++++++++++ IncomingCallBroadcastReceiver Exception +++++++++++++++++++ ");
            return;
        }

        Log.d(TAG, "+++++++++++++++++++ IncomingCallBroadcastReceiver 2222222222222222222222222 +++++++++++++++++++ ");
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();


        if(action.equals("android.intent.action.PHONE_STATE")){
//            RINGING -> OFFHOOK -> IDLE
            String callState = bundle.getString(TelephonyManager.EXTRA_STATE);

            if(callState.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                stopRecord();
                Log.d(TAG, " EXTRA_STATE_IDLE ");

            } else if(callState.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Log.d(TAG, " EXTRA_STATE_RINGING INCOMMING NUMBER : " + bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER));
            }else if(callState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                try {
                    Log.d(TAG, " EXTRA_STATE_OFFHOOK CALL : " + intent.getExtras().getString("android.intent.extra.PHONE_NUMBER"));
                }catch (Exception e) {

                }
                Log.d(TAG, " EXTRA_STATE_OFFHOOK ");
                startRecord();
            }
        }else if(action.equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            Log.d(TAG, " OUTGOING CALL : " + intent.getExtras().getString("android.intent.extra.PHONE_NUMBER"));

            Log.d(TAG, " OUTGOING CALL : " + bundle.getString(Intent.EXTRA_PHONE_NUMBER));
        }
    }

    private void startRecord(){
        String formatDate = "";
        long now = System.currentTimeMillis();
        try {
            // 현재시간을 msec 으로 구한다.

            // 현재시간을 date 변수에 저장한다.
            Date date = new Date(now);
            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            // nowDate 변수에 값을 저장한다.
            formatDate = sdfNow.format(date);
        }catch (Exception e) {
            formatDate = String.valueOf(now);
        }
        Log.d(TAG, " formatDate : " + formatDate );

        try {
            if( callRecord != null ) {
                return;
            }
            callRecord = new CallRecord.Builder(mContext)
                    .setRecordFileName(formatDate)
                    .setRecordDirName(".incoming")
                    .setRecordDirPath(Environment.getExternalStorageDirectory().getPath()) // optional & default value
                    .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value
                    .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // optional & default value
                    .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION) // optional & default value
                    .setShowSeed(false) // optional & default value ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr
                    .build();
            callRecord.startCallRecordService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopRecord(){
        String formatDate = "";
        long now = System.currentTimeMillis();
        try {
            // 현재시간을 msec 으로 구한다.

            // 현재시간을 date 변수에 저장한다.
            Date date = new Date(now);
            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            // nowDate 변수에 값을 저장한다.
            formatDate = sdfNow.format(date);
        }catch (Exception e) {
            formatDate = String.valueOf(now);
        }

        callRecord.changeRecordFileName(formatDate);

        try {
            callRecord.enableSaveFile();
        }catch (Exception e) {
            Log.d(TAG, " callRecord.enableSaveFile(); Exception  : " );
        }
        try {
            callRecord.stopCallRecordService();
        }catch (Exception e) {
            Log.d(TAG, " callRecord.stopCallReceiver(); Exception  : " );
        }
        callRecord = null;
    }
}
