package com.example.kuma.myapplication.Utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.os.Trace;
import android.provider.Settings;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.PowerManager;
import android.telephony.TelephonyManager;

public class NetUtility {

	private static final int NETWORK_TYPE_LTE = 13;

	private static WifiLock m_wlWifiLock = null;
	private static PowerManager.WakeLock m_wlWakeLock = null;

	/**************************************************************************************************************
	 * static method
	 **************************************************************************************************************/
	public static boolean isEnableLTE(Context context) 
	{
        int nType = findNetworkType(context);
        if (nType == NETWORK_TYPE_LTE) {
            return true;
        }
        return false;
    }
	
	private static int findNetworkType(Context context) 
	{
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mgrTel.getNetworkType();
    }
	
	public static boolean isAirplaneModeOn(Context context)
	{
		return Settings.System.getInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) != 0;
	}



	public static boolean isEnableWifi(Context context)
	{
		KumaLog.e("++ NetStateManager.isEnableWifi()");

  	    WifiManager wifiMgr = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
  	    WifiInfo 	winfo 	= wifiMgr.getConnectionInfo();

  	    String strSSID = winfo.getSSID();
  	    int nWifiState = wifiMgr.getWifiState();

		KumaLog.i(">> strSSID = " + strSSID);
		KumaLog.i(">> nWifiState = " + nWifiState);
		KumaLog.i(">> WifiManager.WIFI_STATE_ENABLED = " + WifiManager.WIFI_STATE_ENABLED);
  	    
  	    boolean bEnabled = (nWifiState == WifiManager.WIFI_STATE_ENABLED && strSSID != null && !strSSID.equals(""));

		KumaLog.d("-- NetStateManager.isEnableWifi(" + bEnabled + ")");

  	    return bEnabled;
	}
	
	public static boolean isUsingMobile( Context context )
	{
		KumaLog.d("++ NetStateManager.isUsingMobile()");

		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		boolean isUsingMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

		KumaLog.d("-- NetStateManager.isUsingMobile(" + isUsingMobile + ")");
	    return isUsingMobile;
	}
	
	
	public static String getNetworkType(Context context)
	{
		KumaLog.d("++ NetStateManager.getNetworkType()");
		TelephonyManager mTeleMgr = (TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE );
		String strType = "unknown";
        int type = mTeleMgr.getNetworkType();
        switch(type) {
        case TelephonyManager.NETWORK_TYPE_1xRTT : 
        	strType = "1xrtt";
        	break;
        case TelephonyManager.NETWORK_TYPE_CDMA :
        	strType = "cdma";
        	break;
        case TelephonyManager.NETWORK_TYPE_EDGE :
        	strType = "edge";
        	break;
        case 14 : // 2.3 Constant TelephonyManager.NETWORK_TYPE_EHRPD
        	strType = "ehrpd";
        	break;
        case TelephonyManager.NETWORK_TYPE_EVDO_0 : 
        	strType = "evdo_0";
        	break;
        case TelephonyManager.NETWORK_TYPE_EVDO_A : 
        	strType = "evdo_a";
        	break;
        case TelephonyManager.NETWORK_TYPE_EVDO_B : 
        	strType = "evdo_b";
        	break;
        case TelephonyManager.NETWORK_TYPE_GPRS : 
        	strType = "gprs";
        	break;
        case TelephonyManager.NETWORK_TYPE_HSDPA : 
        	strType = "hsdpa";
        	break;
        case TelephonyManager.NETWORK_TYPE_HSPA : 
        	strType = "hspa";
        	break;
        case 15 : // 2.3 Constant TelephonyManager.NETWORK_TYPE_HSPAP
        	strType = "hspa+";
        	break;
        case TelephonyManager.NETWORK_TYPE_HSUPA : 
        	strType = "hsupa";
        	break;
        case TelephonyManager.NETWORK_TYPE_IDEN :
        	strType = "iden";
        	break;
        case 13 : // 2.3 Constant TelephonyManager.NETWORK_TYPE_LTE
        	strType = "lte";
        	break;
        case TelephonyManager.NETWORK_TYPE_UMTS :
        	strType = "umts";
        	break;
        }
        
        if(isEnableWifi(context)) {
        	strType = "wifi";
        }
		KumaLog.d("-- NetStateManager.getNetworkType() " + strType);
        return strType;
	}
	
	public static String getOperator(Context context)
	{
		KumaLog.d("++ NetStateManager.getOperator()");
		TelephonyManager mTeleMgr = (TelephonyManager)context.getSystemService( Context.TELEPHONY_SERVICE );
        String strOperator = mTeleMgr.getNetworkOperator();
        if(strOperator == null || strOperator.length() < 3) {
        	strOperator = "unknown";
        } else {
        	strOperator = strOperator.substring(0, 3) + "/" + strOperator.substring(3, strOperator.length());
        }
		KumaLog.d("-- NetStateManager.getOperator() " + strOperator);
        return strOperator;
	}
	
	public static String getSimOperator(Context context)
	{
		KumaLog.d("++ NetStateManager.getSimOperator()");
		TelephonyManager mTeleMgr = (TelephonyManager)context.getSystemService( Context.TELEPHONY_SERVICE );
        String strSimOperator = mTeleMgr.getSimOperator();
        if(strSimOperator == null || strSimOperator.length() < 3) {
        	strSimOperator = "unknown";
        } else {
        	strSimOperator = strSimOperator.substring(0, 3) + "/" + strSimOperator.substring(3, strSimOperator.length());
        }
		KumaLog.d("-- NetStateManager.getSimOperator() " + strSimOperator);
        return strSimOperator;
	}
	
	public synchronized static void acquireLockWifi(Context context)
	{
		KumaLog.d("++ NetStateManager.acquireLockWifi()");

		if( NetUtility.m_wlWifiLock == null )
		{
			// wi-fi lock
			WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			NetUtility.m_wlWifiLock = wm.createWifiLock("Downloader");
			NetUtility.m_wlWifiLock.setReferenceCounted(false);
			NetUtility.m_wlWifiLock.acquire();
		}

		if(NetUtility.m_wlWakeLock == null)
		{
			// wake lock
			PowerManager powerMgr = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
			NetUtility.m_wlWakeLock = powerMgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Downloader");
			NetUtility.m_wlWakeLock.acquire();
		}
	}

	public synchronized static void releaseWifi()
	{
		KumaLog.d("++ NetStateManager.releaseWifi()");

		try {
			if( NetUtility.m_wlWifiLock != null ) {
				NetUtility.m_wlWifiLock.release();
				NetUtility.m_wlWifiLock = null;
			}

			if( NetUtility.m_wlWakeLock != null ) {
				NetUtility.m_wlWakeLock.release();
				NetUtility.m_wlWakeLock = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static String getDeviceIpAddress()
	{
		KumaLog.d("++ DeviceUtility.getDeviceIpAddress()");

		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while ( en.hasMoreElements() ) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
				while( enumIpAddr.hasMoreElements() ) {
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						String strDeviceIpAddress = inetAddress.getHostAddress().toString();
						KumaLog.d("-- DeviceUtility.getDeviceIpAddress(" + strDeviceIpAddress +")" );
						return strDeviceIpAddress;
					}
				}
			}
		} catch (SocketException se) {
			KumaLog.d(">> " + se.toString());
			se.printStackTrace();
		} catch (Exception e){
			KumaLog.d(">> " + e.toString());
			e.printStackTrace();
		}

		KumaLog.d("-- DeviceUtility.getDeviceIpAddress()");
		return "";
	}
}
