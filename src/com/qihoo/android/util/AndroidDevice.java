package com.qihoo.android.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public final class AndroidDevice
{
    private AndroidDevice()
    {
    }
    
    /**
     * 获取IMEI标识，即设备号
     * 
     * @param context
     * @return
     */
    public static String getImei(Context context)
    {
        return getDeviceId(context);
    }
    
    /**
     * 获取设备号
     * 
     * @param context
     * @return
     */
    public static String getDeviceId(Context context)
    {
        try
        {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取SIM卡序列号
     * 
     * @param context
     * @return
     */
    public static String getSimSerialNumber(Context context)
    {
        try
        {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSimSerialNumber();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取设备序列号
     * 
     * @return
     */
    public static String getSerialNumber()
    {
        try
        {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            return (String) get.invoke(c, "ro.serialno");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取ANDROID ID
     * 
     * @param context
     * @return
     */
    public static String getAndroidId(Context context)
    {
        try
        {
            return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return null;
        }
    }
    
    /**
     * 获取ANDROID版本
     * 
     * @return
     */
    public static String getAndroidVersion()
    {
        return android.os.Build.VERSION.RELEASE;
    }
    
    /**
     * 获取手机型号
     * 
     * @return
     */
    public static String getAndroidModel()
    {
        return android.os.Build.MODEL;
    }
    
    /**
     * 获取屏幕分辨率
     * 
     * @param context
     * @return
     */
    public static String getScreenResolution(Context context)
    {
        try
        {
            DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
            return dm.widthPixels + "x" + dm.heightPixels;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取运营商信息
     * 
     * @param context
     * @return
     */
    public static String getNetworkOperatorName(Context context)
    {
        try
        {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getNetworkOperatorName();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取CPU名称
     * 
     * @return
     */
    public static String getCpuName()
    {
        try
        {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            
            br.close();
            br = null;
            fr.close();
            fr = null;
            
            return array[1];
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取SD卡的可用字节数
     * 
     * @return
     */
    public static long getSdCardAvailableBytes()
    {
        try
        {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state))
            {
                File sdcardDir = Environment.getExternalStorageDirectory();
                StatFs sf = new StatFs(sdcardDir.getPath());
                if (Build.VERSION.SDK_INT >= 18)
                {
                    Method method = sf.getClass().getMethod("getAvailableBytes");
                    Object obj = method.invoke(sf);
                    if (obj != null)
                    {
                        return Long.parseLong(obj.toString());
                    }
                    else
                    {
                        return 0;
                    }
                }
                else
                {
                    long blockSize = 0;
                    long availCount = 0;
                    Method method = sf.getClass().getMethod("getBlockSize");
                    Object obj = method.invoke(sf);
                    if (obj != null)
                    {
                        blockSize = Long.parseLong(obj.toString());
                    }
                    
                    method = sf.getClass().getMethod("getAvailableBlocks");
                    obj = method.invoke(sf);
                    if (obj != null)
                    {
                        availCount = Long.parseLong(obj.toString());
                    }
                    
                    return availCount * blockSize;
                }
            }
            else
            {
                return 0;
            }
        }
        catch (Exception ex)
        {
            return 0;
        }
    }
    
    /**
     * 获取制造商信息
     * 
     * @return
     */
    public static String getManufacturer()
    {
        return Build.MANUFACTURER;
    }
    
    /**
     * 获取品牌信息
     * 
     * @return
     */
    public static String getBrand()
    {
        return Build.BRAND;
    }
    
    /**
     * 获取主板信息
     * 
     * @return
     */
    public static String getBoard()
    {
        return Build.BOARD;
    }
    
    @SuppressLint("DefaultLocale")
    public static String getNetworkType(Context context)
    {
        try
        {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            String typeName = info.getTypeName().toLowerCase(); // WIFI/MOBILE
            
            if (typeName.equals("wifi"))
            {
            }
            else
            {
                typeName = info.getExtraInfo().toLowerCase() + "-" + info.getSubtypeName().toLowerCase();
            }
            
            return typeName;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取系统国家或地区
     * 
     * @return
     */
    public static String getCountry()
    {
        return Locale.getDefault().getCountry();
    }
    
    /**
     * 获取系统语言
     * 
     * @return
     */
    public static String getLanguage()
    {
        return Locale.getDefault().getLanguage();
    }
    
    /**
     * 获取Mac地址
     * 
     * @param context
     * @return
     */
    public static String getMac(Context context)
    {
        try
        {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wm ? null : wm.getConnectionInfo());
            return info.getMacAddress();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
