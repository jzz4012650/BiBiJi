package com.bibizhaoji.bibiji;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.KeyEvent;

public class MediaButtonListener extends BroadcastReceiver {

	private static final String TAG = "MediaButtonReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "broadcast received!");
		
		KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_HEADSETHOOK:
			Intent i = new Intent(context, LockScreenActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
			break;

		default:
			break;
		}
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
