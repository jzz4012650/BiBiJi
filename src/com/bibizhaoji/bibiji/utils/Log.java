package com.bibizhaoji.bibiji.utils;

/**
 * 添加debug开关
 * @author caiyingyuan
 * */
public class Log
{
    public static final boolean DEBUG_VERSION = true;
    
    public static final int VERBOSE = android.util.Log.VERBOSE;
    public static final int DEBUG = android.util.Log.DEBUG;
    public static final int INFO = android.util.Log.INFO;
    public static final int WARN = android.util.Log.WARN;
    public static final int ERROR = android.util.Log.ERROR;
    public static final int ASSERT = android.util.Log.ASSERT;
    
    public static int v(String tag, String msg)
    {
        return DEBUG_VERSION ? android.util.Log.v(tag, msg) : 0;
    }
    
    public static int v(String tag, String msg, Throwable tr)
    {
        return DEBUG_VERSION ? android.util.Log.v(tag, msg, tr) : 0;
    }
    
    public static int d(String tag, String msg)
    {
        return DEBUG_VERSION ? android.util.Log.d(tag, msg) : 0;
    }
    
    public static int d(String tag, String msg, Throwable tr)
    {
        return DEBUG_VERSION ? android.util.Log.d(tag, msg, tr) : 0;
    }
    
    public static int i(String tag, String msg)
    {
        return DEBUG_VERSION ? android.util.Log.i(tag, msg) : 0;
    }
    
    public static int i(String tag, String msg, Throwable tr)
    {
        return DEBUG_VERSION ? android.util.Log.i(tag, msg, tr) : 0;
    }
    
    public static int w(String tag, String msg)
    {
        return DEBUG_VERSION ? android.util.Log.w(tag, msg) : 0;
    }
    
    public static int w(String tag, String msg, Throwable tr)
    {
        return DEBUG_VERSION ? android.util.Log.w(tag, msg, tr) : 0;
    }
    
    public static int e(String tag, String msg)
    {
        return DEBUG_VERSION ? android.util.Log.e(tag, msg) : 0;
    }
    
    public static int e(String tag, String msg, Throwable tr)
    {
        return DEBUG_VERSION ? android.util.Log.e(tag, msg, tr) : 0;
    }
    
    public static String getStackTraceString(Throwable tr)
    {
        return DEBUG_VERSION ? android.util.Log.getStackTraceString(tr) : null;
    }
    
    public static boolean isLoggable(String tag, int level)
    {
        return DEBUG_VERSION ? android.util.Log.isLoggable(tag, level) : false;
    }
    
    public static int println(int priority, String tag, String msg)
    {
        return DEBUG_VERSION ? android.util.Log.println(priority, tag, msg) : 0;
    }
    
    public static int wtf(String tag, String msg)
    {
        return DEBUG_VERSION ? android.util.Log.wtf(tag, msg) : 0;
    }
    
    public static int wtf(String tag, String msg, Throwable tr)
    {
        return DEBUG_VERSION ? android.util.Log.wtf(tag, msg, tr) : 0;
    }
    
    public static int wtf(String tag, Throwable tr)
    {
        return DEBUG_VERSION ? android.util.Log.wtf(tag, tr) : 0;
    }
}
