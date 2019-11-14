package com.simens.us.myapplication;

import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.app.Application;

import com.simens.us.myapplication.Utils.KumaLog;
import com.simens.us.myapplication.Utils.SharedPref.ShareDataManager;

public class AppManager extends Application {

	  ///////////////////////////////////////////////////////////////////////////////
    // Constants
    ///////////////////////////////////////////////////////////////////////////////

    public String m_strToken = "";


    ///////////////////////////////////////////////////////////////////////////////
    // Field
    ///////////////////////////////////////////////////////////////////////////////
    private ArrayList<Activity>         m_activities    = new ArrayList<Activity>();
    private Hashtable<String, Object>   m_Objects       = new Hashtable<String, Object>();

    private ShareDataManager m_objShareDataManager = new ShareDataManager();

    ///////////////////////////////////////////////////////////////////////////////
    // android.app.Application Overriding Methods
    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate()
    {
        super.onCreate();
        
        //EOFExcetpion 발생 문제로 적용
        System.setProperty("http.keepAlive", "false");

    }

//    public String getToken() {
//        return m_strToken;
//    }
//
//    public void setToken(String m_strToken) {
//        this.m_strToken = m_strToken;
//    }

    ///////////////////////////////////////////////////////////////////////////////
    // Activity Management Methods
    ///////////////////////////////////////////////////////////////////////////////
    public ShareDataManager getShareDataManager()
    {
        return m_objShareDataManager;
    }

    /**
     * 현재 쌓여있는 Activity 갯수
     * @return
     */
    public int getNumberOfAliveActivities()
    {
        return m_activities.size();
    }

    /**
     * MainActivity 의 생성 유무
     * @return boolean
     */
    public boolean isAliveMainActivity(){
        boolean state = false;
        try {
            if( getNumberOfAliveActivities() > 1) {
                for(int i = 0; i < m_activities.size(); i++) {
                    if( m_activities.get(i).toString().contains("MainActivity") ) {
                        state = true;
                        break;
                    }
                }
            }
        } catch ( Exception e) {

        }
        return state;
    }

    public void addActivity(Activity activity)
    {
        KumaLog.d("addActivity activity is " + activity);
        m_activities.add(activity);

        KumaLog.d("addActivity getScreenSize() is " +  getScreenSize());
    }
    
    public void removeActivity(Activity activity)
    {
        KumaLog.e("removeActivity activity is " +  activity);
        m_activities.remove(activity);
        KumaLog.e("removeActivity getScreenSize() is " +  getScreenSize());
    }

    public Activity getAliveActivity(int nIdx)
    {
        if(nIdx >= m_activities.size() && nIdx < 0)
            return null;

        return m_activities.get(nIdx);
    }

    /**
     * 가장 상위에 있는 Activity 반환
     * @return
     */
    public Activity getTopActivity()
    {
        int nSize = m_activities.size();
        return m_activities.get(nSize-1);
    }
    
    public Activity getBottomActivity()
    {
        return m_activities.get(0);
    }
    
    public void killActivity(Activity activity)
    {
        m_activities.remove(activity);
        activity.finish();
    }

    public void killActivity(int index)
    {
        Activity activity = m_activities.remove(index);
        activity.finish();
    }

    public void killAllActivity()
    {
        int nSize = m_activities.size();
        for(int i = 0; i < nSize; i++) {
            Activity activity = m_activities.get(i);
            activity.finish();
        }
        m_activities.clear();
    }
    
    public void goMainScreenActivity()
    {
        int nSize = m_activities.size();
        for(int i=nSize-1; i>0; i--) {
            Activity activity = m_activities.get(i);
            activity.finish();
            m_activities.remove(i);
        }
    }
    
    public int getScreenSize(){
    	int nSize = m_activities.size();

    	return nSize;
    }


   
    
    ///////////////////////////////////////////////////////////////////////////////
    // Global Object Management Methods
    ///////////////////////////////////////////////////////////////////////////////
    public boolean containsObject(String key)
    {
        return m_Objects.contains(key);
    }
    
    public void addObject(String key, Object value)
    {
        m_Objects.put(key, value);
    }
    
    public Object getObject(String key)
    {
        return m_Objects.get(key);
    }
    
    public Object getObject(String key, String clsName)
    {
        Object obj = m_Objects.get(key);
        if (obj != null && obj.getClass().getName().equals(clsName) == false)
        {
            obj = null;
        }
        return obj;
    }
    
    public void removeObject(String key)
    {
        m_Objects.remove(key);
    }
}
