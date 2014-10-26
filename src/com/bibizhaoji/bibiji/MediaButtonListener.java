package com.bibizhaoji.bibiji;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class MediaButtonListener extends BroadcastReceiver {

    private static final String TAG = "MediaButtonReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
	Log.d(TAG, "button clicked!");
	turnOnScreen(context);
	// abortBroadcast();
	// setResultCode()
    }

    // 开启屏幕（按下电源键）
    private void turnOnScreen(Context context) {
	PowerManager pm = (PowerManager) context
		.getSystemService(Context.POWER_SERVICE);
	WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
		| PowerManager.ON_AFTER_RELEASE, "TAG");
	wakeLock.acquire();
	wakeLock.release();
    }

}
