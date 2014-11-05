package com.bibizhaoji.bibiji;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

/**
 * 接收到语音指令后，弹出于锁屏之上的界面
 * 
 * @author jinzhenzu
 *
 */
public class LockScreenActivity extends Activity {

	private MediaPlayer mediaPlayer;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设为锁屏全屏弹窗
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// 如果系统版本在JellyBean之上，隐藏虚拟按键和状态栏
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
		// 渲染界面
		setContentView(R.layout.activity_lock_screen);
	}

	@Override
	protected void onStart() {
		super.onStart();
		playSound(G.RINGTON, G.VOLUME);
	}

	@Override
	protected void onPause() {
		super.onPause();
		stopSound();
		// this.finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * 播放铃声
	 * 
	 * @param soundResourceId
	 *            声音资源ID
	 * @param volume
	 *            音量(0.0-1.0)
	 */
	private void playSound(int soundResourceId, float volume) {
		mediaPlayer = MediaPlayer.create(this, soundResourceId);
		mediaPlayer.setVolume(volume, volume);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
	}

	/**
	 * 停止播放铃声
	 */
	private void stopSound() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
}
