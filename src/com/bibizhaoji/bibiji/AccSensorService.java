package com.bibizhaoji.bibiji;

import com.bibizhaoji.bibiji.utils.Log;
import com.bibizhaoji.pocketsphinx.PocketSphinxService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class AccSensorService extends Service implements SensorEventListener {

	private Context mContext;
	private SensorManager sensorManager;
	private Sensor accSensor;
	// 上次检测时间
	private long lasttime;
	//速度阈值，当摇晃速度达到这值后产生作用
	private int shakeThreshold = 29;
	// 两次检测的时间间隔
	static final int UPDATE_INTERVAL = 1000 * 10;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// 加速度传感器
		accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//注册监听器
		sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		//获得x,y,z坐标  
		float x = event.values[SensorManager.DATA_X];
		float y = event.values[SensorManager.DATA_Y];
		float z = event.values[SensorManager.DATA_Z];
		
		//现在检测时间  
		long current = System.currentTimeMillis();
		
		//两次检测的时间间隔  
		long timeInterval = current - lasttime;
		
		//判断是否达到了检测时间间隔  
		if (timeInterval < UPDATE_INTERVAL) {
			return;
		}
		
		
		if (Math.abs(x) + Math.abs(y) + Math.abs(z) > shakeThreshold) {
			//达到速度阀值，发出提示
			lasttime = current;
			Log.d(G.LOG_TAG, "运动中......");
			Intent i = new Intent(mContext, PocketSphinxService.class);
			mContext.stopService(i);
			// onShake();
		} else {
			lasttime = current;
			try {
				Log.d(G.LOG_TAG, "手机静止......");
				Intent i = new Intent(mContext, PocketSphinxService.class);
				mContext.startService(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	}
}