package com.simens.us.myapplication.Utils.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtility {
	private static final String PREFERENCES_NAME = "com.kuma.product_manager";
	
	public static void set(Context context, String key, boolean value)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static void set(Context context, String key, int value)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static void set(Context context, String key, long value)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public static void set(Context context, String key, String value)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static boolean getBoolean(Context context, String key)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getBoolean(key, false);
	}
	
	public static int getInt(Context context, String key)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getInt(key, -1);
	}
	
	public static String getString(Context context, String key)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getString(key, "");
	}
	
	public static boolean getBoolean(Context context, String key, boolean bDefault)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getBoolean(key, bDefault);
	}
	
	public static int getInt(Context context, String key, int nDefault)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getInt(key, nDefault);
	}
	
	public static long getLong(Context context, String key, long lDefault)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getLong(key, lDefault);
	}
	
	public static String getString(Context context, String key, String strDefault )
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getString(key, strDefault);
	}
	
	public static boolean hasValue(Context context, String key)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.contains(key);
	}
	
	public static void removeKey(Context context, String key)
	{
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(key);
		editor.commit();
	}
}
