package com.qihoo.android.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * AndroidManifest.xml工具
 * 
 * @author wangpeihe
 *
 */
public final class AndroidManifest
{
    private AndroidManifest()
    {
    }
    
    /**
     * 获取应用包名
     * 
     * @param context
     * @return
     */
    public static String getPackageName(Context context)
    {
        return context.getPackageName();
    }
    
    /**
     * 获取应用名
     * 
     * @param context
     * @return
     */
    public static String getApplicationName(Context context)
    {
        try
        {
            PackageManager manager = context.getPackageManager();
            ApplicationInfo ai = manager.getApplicationInfo(context.getPackageName(), 0);
            
            return manager.getApplicationLabel(ai).toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取应用图标
     * 
     * @param context
     * @return
     */
    public static int getApplicationIcon(Context context)
    {
        try
        {
            PackageManager manager = context.getPackageManager();
            ApplicationInfo ai = manager.getApplicationInfo(context.getPackageName(), 0);
            
            return ai.icon;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 获取版本名
     * 
     * @param context
     * @return
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            
            return info.versionName;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return null;
        }
    }
    
    /**
     * 获取版本号
     * 
     * @param context
     * @return
     */
    public static int getVersionCode(Context context)
    {
        try
        {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return -1;
        }
    }
    
    /**
     * 获取MetaData数据
     * 
     * @param context
     *            上下文
     * @param key
     *            键
     * @return 值，不存在则返回null
     */
    public static String getMetaData(Context context, String key)
    {
        try
        {
            PackageManager manager = context.getPackageManager();
            ApplicationInfo ai = manager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            
            return ai.metaData.getString(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return null;
        }
    }
}
