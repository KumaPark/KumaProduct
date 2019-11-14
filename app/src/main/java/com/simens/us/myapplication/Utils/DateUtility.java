package com.simens.us.myapplication.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.text.TextUtils;
import android.text.format.DateFormat;

public class DateUtility {

	   public final static int FORMAT_YYYYMMDD             = 1;                                // 20131025
	    public final static int FORMAT_HHMMSS               = FORMAT_YYYYMMDD + 1;              // 132334
	    public final static int FORMAT_YYYYMMDDHHMMSS       = FORMAT_HHMMSS + 1;                // 20131025132334
	    public final static int FORMAT_YYYYMMDDDAY_ADD_TEXT = FORMAT_YYYYMMDDHHMMSS + 1;        // 2013년 10월 25일(금)
	    public final static int FORMAT_YYYYMMDD_ADD_TEXT    = FORMAT_YYYYMMDDDAY_ADD_TEXT + 1;  // 2013년 10월 25일
	    public final static int FORMAT_AMPMHHMM_ADD_TEXT    = FORMAT_YYYYMMDD_ADD_TEXT + 1;     // 오전/오후 01:23
	    public final static int FORMAT_HHMM                 = FORMAT_AMPMHHMM_ADD_TEXT + 1;     // 13:23
	    public final static int FORMAT_AMPM_TEXT            = FORMAT_HHMM + 1;                  // 오전/오후
	    
	    /**
	     * 현재 시간의 Calendar를 반환한다.<br>
	     * 한국 기준
	     * 
	     * @return 현재 시간의 Calendar 객체
	     */
	    public static Calendar getCurCalendar()
	    {
	        return Calendar.getInstance(TimeZone.getTimeZone("GMT+09:00"));
	    }
	    
	    /**
	     * 현재시간의 Date 객체를 반환한다.
	     * 한국기준 
	     * 
	     * @return 현재 시간의 Date 객체
	     */
	    public static Date getCurDate()
	    {
	        return new Date(getCurTimeMillis());
	    }
	    
	    /**
	     * 현재시간의 millisecond 를 반환한다.
	     * 한국기준 
	     * 
	     * @return 현재 시간의 millisecond
	     */
	    public static long getCurTimeMillis()
	    {
	        Calendar cal = getCurCalendar();
	        long lRawOffset = cal.getTimeZone().getRawOffset();
	        
	        Calendar sysCal = Calendar.getInstance();
	        long lSysRawOffset = sysCal.getTimeZone().getRawOffset();
	        
	        long lKoreanTime = sysCal.getTimeInMillis() + (lRawOffset - lSysRawOffset);
	        return lKoreanTime;
	    }
	    
	    /**
	     * yyyyMMdd -> yyyy년 MM월 dd일
	     * 20090621 -> 2009년 06월 21일
	     * @param strDate yyyyMMdd
	     * @return yyyy년 MM월 dd일
	     */
	    public static String changeDateType(String strDate)
	    {
	        KumaLog.d(">> TimeDate.changeDateType()");
	        return chageFormat("yyyyMMdd", strDate, "yyyy년 MM월 dd일");
	    }
	    
	    /**
	     * yyyyMMdd -> yyyy년 MM월 dd일
	     * 20090621 -> 2009년 06월 21일
	     * @param strDate yyyyMMdd
	     * @return yyyy년 MM월 dd일
	     */
	    public static String changeBitBoxDateType(String strDate)
	    {
			KumaLog.d(">> TimeDate.changeDateType()");
	        return chageFormat("yyyyMMddHHmmss", strDate, "yyyy년 MM월 dd일 HH:mm:ss");
	    }
	    
	    public static String changeBitBoxDateFormat(String strDate)
	    {
	    	if(strDate == null || strDate.length() < 8) return "0000년 00월 00일";
			KumaLog.d(">> TimeDate.changeDateType()");
	        return chageFormat("yyyyMMdd", strDate, "yyyy년 MM월 dd일");
	    }
	    
	    public static String changeBitBoxTimeFormat(String strTime)
	    {
	    	if(strTime == null || strTime.length() < 6) return "00:00:00";
			KumaLog.d(">> TimeDate.changeDateType()");
	        return chageFormat("HHmmss", strTime, "HH:mm:ss");
	    }
	    
	    /**
	     *  HHMMSS -> 오전/오후 xx:xx
	     *  132440 -> 오후 01:24
	     *  073020 -> 오전 07:30
	     *  @param strTimeHHMMSS
	     *  @return 오전/오후 HH:MM format
	     */
	    public static String changeToHalfTime(String strTime)
	    {
			KumaLog.d(">> TimeDate.changeToHalfTime()");
	        return chageFormat("HHmmss", strTime, "a hh:mm");
	    }
	    
	    /**
	     * yyyyMMddHHmmss -> yyyy-MM-dd HH:mm
	     * 20090621132440 -> 2009-06-21 13:24
	     * @param strDateTime yyyyMMddHHmmss
	     * @return yyyy-MM-dd HH:mm
	     */
	    public static String getDateTimeFormat(String strDateTime)
	    {
			KumaLog.d(">> TimeDate.getMyShopDateTimeFormat()");
	        return chageFormat("yyyyMMddHHmmss", strDateTime, "yyyy-MM-dd HH:mm");
	    }
	    
	    
	    /**
	     * yyyyMMddHHmmss -> yyyy-MM-dd HH:mm
	     * 20090621132440 -> 2009-06-21 13:24
	     * @param strDateTime yyyyMMddHHmmss
	     * @return yyyy-MM-dd HH:mm
	     */
	    public static String getDateTimeFormatIBM(String strDateTime)
	    {
			KumaLog.d(">> TimeDate.getMyShopDateTimeFormat()");
	        return chageFormat("yyyyMMddHHmmss", strDateTime, "yyyyMMddHHmm");
	    }
	    
	    
	    /**
	     * 시간의 Format을 변경한다.
	     * 
	     * @param strPrevFormat 기존 String Foramt
	     * @param strTime 시간
	     * @param strChangeFormat 변경할 Format
	     * @return
	     */
	    public static String chageFormat(String strPrevFormat, String strTime, String strChangeFormat)
	    {
			KumaLog.d(">> TimeDate.chageFormat()");
			KumaLog.d("++ strPrevFormat      = [%s]", strPrevFormat);
			KumaLog.d("++ strTime            = [%s]", strTime);
			KumaLog.d("++ strChangeFormat    = [%s]", strChangeFormat);
	        
	        if (TextUtils.isEmpty(strPrevFormat) || TextUtils.isEmpty(strChangeFormat)) {
	            return strPrevFormat;
	        }
	        
	        SimpleDateFormat format = new SimpleDateFormat(strPrevFormat, Locale.KOREAN);
	        SimpleDateFormat newFormat = new SimpleDateFormat(strChangeFormat, Locale.KOREAN);
	        Date da = null;
	        try {
	            da = format.parse(strTime);
	            return newFormat.format(da);
	            
	        } catch (ParseException e) {
				KumaLog.d(e.toString());
	            return strPrevFormat;
	            
	        } catch (Exception e) {
				KumaLog.d(e.toString());
	            return strPrevFormat;
	        }
	    }
	    
	    /**
	     * 시간을 전달받은 Format으로 변환하여 전달한다.
	     * 
	     * @param cal Calendar
	     * @param nFormatID Foramt ID
	     * @return
	     */
	    public static String getTime(Calendar cal, int nFormatID)
	    {
			KumaLog.d(">> TimeDate.getTime()");
	        
	        String format = makeDateFormat(nFormatID);
	        
	        if (TextUtils.isEmpty(format) || cal == null) {
	            return "";
	        }

	        CharSequence cs = DateFormat.format(format, cal);
	        if (cs == null || cs.length() <= 0) {
	            return "";
	        }
	        
	        String strCurTime = String.valueOf(cs);
			KumaLog.d("++ strCurTime = [%s]", strCurTime);
	        return strCurTime;
	    }
	    
	    /**
	     * 현재 시간을 Format의 형태로 변환하여 전달한다.
	     * 
	     * @param nFormatID Format ID
	     * @return
	     */
	    public static String getCurTime(int nFormatID)
	    {
	        return getTime(getCurCalendar(), nFormatID);
	    }
	    
	    /**
	     * Format ID에 맞는 Foramt을 반환한다.
	     * @param nFormatID
	     * @return
	     */
	    private static String makeDateFormat(int nFormatID)
	    {
	        String strFormat = "";
	        
	        switch(nFormatID) {
	            case FORMAT_YYYYMMDD:               strFormat = "yyyyMMdd";           break;
	            case FORMAT_HHMMSS:                 strFormat = "kkmmss";             break;
	            case FORMAT_YYYYMMDDHHMMSS:         strFormat = "yyyyMMddkkmmss";     break;
	            case FORMAT_YYYYMMDDDAY_ADD_TEXT:   strFormat = "yyyy년 MM월 dd일(E)"; break;
	            case FORMAT_YYYYMMDD_ADD_TEXT:      strFormat = "yyyy년 MM월 dd일";    break;
	            case FORMAT_AMPMHHMM_ADD_TEXT:      strFormat = "a hh:mm";            break;
	            case FORMAT_HHMM:                   strFormat = "kk:mm";              break;
	            case FORMAT_AMPM_TEXT:              strFormat = "a";                  break;
	        }
	        
	        return strFormat;
	    }
}
