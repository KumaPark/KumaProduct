package com.example.kuma.myapplication.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Kuma on 2017-12-13.
 */

public class MyBroadcastReceiver  extends BroadcastReceiver {
    static final String logTag = "SmsReceiver";
    static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            //Bundel 널 체크
            Bundle bundle = intent.getExtras();
            if (bundle == null) {
                return;
            }

            //pdu 객체 널 체크
            Object[] pdusObj = (Object[]) bundle.get("pdus");
            if (pdusObj == null) {
                return;
            }

            try{
                String number = "";
                String disName = "";
                String Msg = "";
                long time = 0;
                //message 처리
                SmsMessage[] smsMessages = new SmsMessage[pdusObj.length];
                for (int i = 0; i < pdusObj.length; i++) {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    disName= smsMessages[i].getDisplayOriginatingAddress();
                    number= smsMessages[i].getOriginatingAddress();

                    Msg= smsMessages[i].getMessageBody().toString();
                    time= smsMessages[i].getTimestampMillis();

                }
            }catch ( Exception e ) {
                e.printStackTrace();
            }

        }
    }
}
