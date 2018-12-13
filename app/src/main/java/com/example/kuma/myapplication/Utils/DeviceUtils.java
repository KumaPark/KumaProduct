package com.example.kuma.myapplication.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;

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
}
