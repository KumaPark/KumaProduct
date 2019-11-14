package com.simens.us.myapplication.Utils.SharedPref;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

public class SharedPref {
	public static final int NODATA_INT = -1;
	public static final long NODATA_LONG = -1l;
	public static final String NODATA_STRING = "";

	public final static String PREF_LOGIN_TOKEN 		= "PREF_LOGIN_TOKEN";


	private SharedPreferences m_Pref = null;
	private Context m_Context = null;
	private SharedPreferences.Editor m_Editor = null;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
	public SharedPref(Context a_Context) {
		m_Context = a_Context;

		if (Build.VERSION.SDK_INT <= 10) {
			m_Pref = PreferenceManager.getDefaultSharedPreferences(m_Context.getApplicationContext());
		} else {
			m_Pref = m_Context.getSharedPreferences(getDefaultSharedPreferencesName(m_Context), Context.MODE_PRIVATE);
		}
	}

	String getDefaultSharedPreferencesName(Context context) {
		return context.getPackageName() + "_preferences";
	}

	public void SetUpdateMode() {
		if (m_Editor != null) {
			m_Editor.clear();
			m_Editor = null;
		}

		m_Editor = m_Pref.edit();
	}

	public void UpdateFinish() {
		m_Editor.apply();
	}

	public void deletePreferences() {
		SetUpdateMode();
		m_Editor.clear();
		UpdateFinish();
	}

	// Boolean
	public void setBooleanPref(String strName, boolean bValue) {
		m_Editor.putBoolean(strName, bValue);
	}

	public boolean getBooleanPref(String strName, boolean bDefault) {
		return m_Pref.getBoolean(strName, bDefault);
	}

	// String
	public void setStringPref(String strName, String strValue) {
		m_Editor.putString(strName, strValue);
	}

	public String getStringPref(String strName) {
		return m_Pref.getString(strName, NODATA_STRING);
	}

	public String getStringPref(String strName, String strValue) {
		return m_Pref.getString(strName, strValue);
	}

	// int
	public void setIntPref(String strName, int nValue) {
		m_Editor.putInt(strName, nValue);
	}

	public int getIntPref(String strName) {
		return m_Pref.getInt(strName, NODATA_INT);
	}

	public int getIntPref(String strName, int nDefault) {
		return m_Pref.getInt(strName, nDefault);
	}

	// long
	public void setLongPref(String strName, long nValue) {
		m_Editor.putLong(strName, nValue);
	}

	public long getLongPref(String strName) {
		return m_Pref.getLong(strName, NODATA_LONG);
	}

	public long getLongPref(String strName, long nDefault) {
		return m_Pref.getLong(strName, nDefault);
	}
}
