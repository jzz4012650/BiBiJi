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

		// ��������ȫ������
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// ����JellyBean���ϰ�׿�汾����״̬���뵼����
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
		// ��Ⱦ����
		setContentView(R.layout.activity_lock_screen);
	}
	
	@Override
	protected void onStart() {
		playSound(0, 0.0F); // ��������
		super.onStart();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mediaPlayer.stop(); // ֹͣ����
		mediaPlayer.release(); // �ͷ�ý����Դ
		mediaPlayer = null; // �ͷ��ڴ�
		finish(); // ��������̨Activity�������η��ػص����Activity
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	/**
	 * ��������
	 * @param soundResourceId ������ԴID
	 * @param volume ����(0.0-1.0)
	 */
	private void playSound(int soundResourceId, float volume) {
		mediaPlayer = MediaPlayer.create(this, soundResourceId); // ���ò���Դ
		mediaPlayer.setVolume(volume, volume); // ��������
		mediaPlayer.setLooping(true); // ѭ������
		mediaPlayer.start(); // ��ʼ����
	}
	
	
	private void playAnimation() {
		
	}
}
