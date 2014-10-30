package com.bibizhaoji.bibiji;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class LockScreenActivity extends Activity {

	private MediaPlayer mediaPlayer;
	
	@Override
	@TargetApi(Build.VERSION_CODES.KITKAT)
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置锁屏全屏弹窗
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// 设置JellyBean以上安卓版本隐藏状态栏与导航栏
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			int uiOptions;
			View decorView = getWindow().getDecorView();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
			} else {
				uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN;
			}
			decorView.setSystemUiVisibility(uiOptions);
		}
		// 渲染布局
		setContentView(R.layout.activity_lock_screen);
	}
	
	@Override
	protected void onStart() {
		playSound(0, 0.0F); // 播放声音
		super.onStart();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mediaPlayer.stop(); // 停止播放
		mediaPlayer.release(); // 释放媒体资源
		mediaPlayer = null; // 释放内存
		finish(); // 不保留后台Activity，避免多次返回回到这个Activity
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	/**
	 * 播放声音
	 * @param soundResourceId 声音资源ID
	 * @param volume 音量(0.0-1.0)
	 */
	private void playSound(int soundResourceId, float volume) {
		mediaPlayer = MediaPlayer.create(this, soundResourceId); // 设置播放源
		mediaPlayer.setVolume(volume, volume); // 设置音量
		mediaPlayer.setLooping(true); // 循环播放
		mediaPlayer.start(); // 开始播放
	}
	
	
	private void playAnimation() {
		
	}
}
