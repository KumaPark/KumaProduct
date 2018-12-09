package com.example.kuma.myapplication.Utils;

import android.content.Context;
import android.util.Log;

import com.example.kuma.myapplication.Constance.Constance;


public class KumaLog {
	
	private static String TAG_TITLE = "Kuma";

	public static void line()
	{
		if(Constance.LOG_STATE)
		{
			Log.v(TAG_TITLE, "=====================================================================================================================");
		}
	}

	public static void v(Context context, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.v(context.toString(), a_strLogMsg);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void v(Class c, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.v(c.getName(), a_strLogMsg);
		}
	}
	
	public static void v(String a_strLogTitle, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.v(a_strLogTitle, a_strLogMsg);
		}
	}
	
	public static void v(String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.v(TAG_TITLE, a_strLogMsg);
		}
	}
	
	public static void i(Context context, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.i(context.toString(), a_strLogMsg);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void i(Class c, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.i(c.getName(), a_strLogMsg);
		}
	}
	
	public static void i(String a_strLogTitle, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.i(a_strLogTitle, a_strLogMsg);
		}
	}
	
	public static void i(String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.i(TAG_TITLE, a_strLogMsg);
		}
	}
		
	public static void d(Context context, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.d(context.toString(), a_strLogMsg);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void d(Class c, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.d(c.getName(), a_strLogMsg);
		}
	}
	
	public static void d(String a_strLogTitle, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.d(a_strLogTitle, a_strLogMsg);
		}
	}
	
	public static void d(String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.d(TAG_TITLE, a_strLogMsg);
		}
	}
	
	public static void e(Context context, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.e(context.toString(), a_strLogMsg);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void e(Class c, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.e(c.getName(), a_strLogMsg);
		}
	}
	
	public static void e(String a_strLogTitle, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.e(a_strLogTitle, a_strLogMsg);
		}
	}
	
	public static void e(String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.e(TAG_TITLE, a_strLogMsg);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public static void w(Class c, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.w(c.getName(), a_strLogMsg);
		}
	}
	
	public static void w(String a_strLogTitle, String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.w(a_strLogTitle, a_strLogMsg);
		}
	}
	
	public static void w(String a_strLogMsg)
	{
		if(Constance.LOG_STATE)
		{
			Log.w(TAG_TITLE, a_strLogMsg);
		}
	}
	
}
