package com.example.kuma.myapplication.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;

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
}
