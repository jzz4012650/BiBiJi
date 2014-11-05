package com.bibizhaoji.bibiji;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;

import com.bibizhaoji.crash.CrashHandler;

public class MyApp extends Application
{
    public static final String TAG = MyApp.class.getSimpleName();
	private ScreenBroadcastReceiver mScreenReceiver;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        mScreenReceiver = new ScreenBroadcastReceiver();
        startScreenBroadcastReceiver();
    }
    
    private void startScreenBroadcastReceiver(){  
        IntentFilter filter = new IntentFilter();  
        filter.addAction(Intent.ACTION_SCREEN_ON);  
        filter.addAction(Intent.ACTION_SCREEN_OFF);  
        registerReceiver(mScreenReceiver, filter);  
    }
    
    
}
