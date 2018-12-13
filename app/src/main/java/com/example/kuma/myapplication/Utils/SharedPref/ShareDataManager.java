package com.example.kuma.myapplication.Utils.SharedPref;

import android.content.Context;


public class ShareDataManager {

	public static final int LOGIN_STATE_LOGOUT = 0;
	public static final int LOGIN_STATE_LOGIN = 1;

	public static final int NODATA_INT = -1;
	public static final long NODATA_LONG = -1l;
	public static final String NODATA_STRING = "";
	
	
	public static final int NOTI_NONE = 0;
	public static final int NOTI_CURTAIN = 1;
	public static final int NOTI_POPUP = 2;

	public static final int ENABLE = 0;
	public static final int NON_ENABLE = 1;
	
	// Boolean
	public void setBooleanPref(Context context, String strKeyName, boolean bValue) {
		PreferencesUtility.set(context, strKeyName, bValue);
	}

	public boolean getBooleanPref(Context context, String strKeyName, boolean bDefault) {
		return PreferencesUtility.getBoolean(context, strKeyName, bDefault);
	}

	// String
	public void setStringPref(Context context, String strKeyName, String strValue) {
		PreferencesUtility.set(context, strKeyName, strValue);
	}

	public String getStringPref(Context context, String strKeyName) {
		return PreferencesUtility.getString(context, strKeyName, NODATA_STRING);
	}

	public String getStringPref(Context context, String strKeyName, String strDefault) {
		return PreferencesUtility.getString(context, strKeyName, strDefault);
	}

	// int
	public void setIntPref(Context context, String strKeyName, int nValue) {
		PreferencesUtility.set(context, strKeyName, nValue);
	}

	public int getIntPref(Context context, String strKeyName) {
		return PreferencesUtility.getInt(context, strKeyName, NODATA_INT);
	}

	public int getIntPref(Context context, String strKeyName, int nDefault) {
		return PreferencesUtility.getInt(context, strKeyName, nDefault);
	}

	// long
	public void setLongPref(Context context, String strKeyName, long nValue) {
		PreferencesUtility.set(context, strKeyName, nValue);
	}

	public long getLongPref( Context context, String strKeyName) {
		return PreferencesUtility.getLong(context, strKeyName, NODATA_LONG);
	}

	public long getLongPref(Context context, String strKeyName, long nDefault) {
		return PreferencesUtility.getLong(context, strKeyName, NODATA_LONG);
	}

}
