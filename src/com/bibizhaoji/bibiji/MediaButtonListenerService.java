package com.bibizhaoji.bibiji;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

public class MediaButtonListenerService extends Service {

    private AudioManager audioManager;
    private ComponentName componentName;

    @Override
    public void onCreate() {
	super.onCreate();

	Log.d(G.LOG_TAG, "service create!");
	audioManager = (AudioManager) this
		.getSystemService(Context.AUDIO_SERVICE);
	componentName = new ComponentName(getPackageName(),
		MediaButtonListener.class.getName());
	// ×¢²á¼àÌý
	audioManager.registerMediaButtonEventReceiver(componentName);
	
	
    }

    @Override
    public IBinder onBind(Intent arg0) {
	return null;
    }

    @Override
    public void onDestroy() {
	// È¡Ïû×¢²á¼àÌý
	audioManager.unregisterMediaButtonEventReceiver(componentName);
	super.onDestroy();
    }
}
