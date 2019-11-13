package com.example.kuma.myapplication.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.example.kuma.myapplication.Utils.SharedPref.ShareDataManager;
import com.example.kuma.myapplication.Utils.SharedPref.SharedPref;

public class DeviceUtils {
    public static String getAppVersion(Context context){
        String version = "";
        try {
            PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = i.versionName;
        } catch(Exception e) {

        }
        return version;
    }

    public static String getToken(Context context){
        KumaLog.line();
        KumaLog.d(">>>>>>>>>>>>>>>>>>>>>> getToken");
        try {
            ShareDataManager m_objShareDataManager = new ShareDataManager();
            String strToken = m_objShareDataManager.getStringPref(context, SharedPref.PREF_LOGIN_TOKEN);
            KumaLog.d("strToken >> " + strToken);
            return strToken;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getImei(Context context){
//        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = manager.getConnectionInfo();
//        String address = info.getMacAddress();
//        Check_UDID(context);

        String Imei = "";
        try {
            TelephonyManager telManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            Imei = telManager.getImei();
        }catch (Exception e) {

        }

        return Imei;
    }

    public static void Check_UDID(Context context) {

        // ?�드??모델�?UDID 구하�?

//
//        Constance.PHONE_MODEL = Build.MODEL;

        Context tmpCtx = context.getApplicationContext();
//		WifiManager wfManager = ((WifiManager)tmpCtx.getSystemService(Context.WIFI_SERVICE));
        String str = ((WifiManager)tmpCtx.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();

        KumaLog.d( "Constance.MAC : " + str );
    }
}
