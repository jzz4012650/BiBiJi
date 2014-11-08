package com.bibizhaoji.bibiji;

import com.bibizhaoji.bibiji.utils.Log;
import com.bibizhaoji.pocketsphinx.PocketSphinxService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Intent i = new Intent(context, ClientAccSensorService.class);

		if (Intent.ACTION_SCREEN_OFF.equals(action)) { 
			// 锁屏
			Log.d(G.LOG_TAG, "SCREEN_OFF--->START SERVICE");
			context.startService(i);
			
		}else if (Intent.ACTION_SCREEN_ON.equals(action)) { 
			// 开屏
			Log.d(G.LOG_TAG, "SCREEN_ON--->END SERVICE");
			context.stopService(i);
			
		}else if (Intent.ACTION_USER_PRESENT.equals(action)) { 
            // 解锁
			Log.d(G.LOG_TAG, "解锁dfdfsdf--->END SERVICE");
			context.stopService(i);

        }
		
	}

}
