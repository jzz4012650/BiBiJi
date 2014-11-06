package com.bibizhaoji.pocketsphinx;

import com.bibizhaoji.bibiji.G;
import com.bibizhaoji.bibiji.LockScreenActivity;
import com.bibizhaoji.bibiji.aidl.IRemoteRecognizerService;
import com.bibizhaoji.bibiji.utils.Pref;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteRecognizerService extends Service implements RecognitionListener {

	public static final String REMOTE_SERVICE_ACTION = "com.bibizhaoji.action.remote.PocketSphinxService" ;
	private RecognizerTask recTask;
	private Thread recThread;
	private boolean listening;
	private long tStart;
	private long tEnd;
	Context mContext;
	//识别不出来或为空
	private int STATE_NONE = 0;
	//识别出关键词
	private int STATE_MATCH = 1;
	//命中分词
	private int STATE_MARK = 2;
	
	//识别的结果
	private int STATE_RESULT;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(G.LOG_TAG, "RemoteRecognizerService created...");
		mContext = this;
		Log.d(G.LOG_TAG,"isNoDisturbingModeOnlyNight------>"+ Pref.isNoDisturbingModeOnlyNight());

		recTask = new RecognizerTask(mContext);
		recThread = new Thread(this.recTask);
		recTask.setRecognitionListener(this);
		recThread.start();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// 返回远程服务的实例
		Log.d(G.LOG_TAG, "RemoteRecognizerService bound...");

		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(G.LOG_TAG, "RemoteRecognizerService unbound...");

		return super.onUnbind(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(G.LOG_TAG, "RemoteRecognizerService start...");
		tStart = System.currentTimeMillis();
		listening = true;
		recTask.start();
		G.isRecServiceRunning = true;
		return super.onStartCommand(intent, flags, startId);

	}

	// 远程服务的实现
	IRemoteRecognizerService.Stub mBinder = new IRemoteRecognizerService.Stub() {

		@Override
		public int getState() throws RemoteException {

			return STATE_RESULT;
		}
	};

	@Override
	public void onPartialResults(Bundle b) {
		
		final String hyp = b.getString("hyp");
		if (hyp == null) {
			STATE_RESULT = STATE_NONE;
			return;
		}
		if (hyp.indexOf(G.REC_WORD1) != -1 ) {
			Log.d(G.LOG_TAG, "*********get rec_word:" + hyp);
			STATE_RESULT = STATE_MATCH;
			Intent i = new Intent(this, LockScreenActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(i);
			this.onDestroy();
		}else{
			STATE_RESULT = STATE_MARK;
		}
	}

	@Override
	public void onResults(Bundle b) {
		final String hyp = b.getString("hyp");
		Log.d(G.LOG_TAG, "|||||||||||recognizition finished:" + hyp);
	}

	@Override
	public void onError(int err) {
		Log.d(G.LOG_TAG, "PocketSphinx got an error.");		
	}

	@Override
	public void onDestroy() {
		tEnd = System.currentTimeMillis();
		float tDuration = (tEnd - tStart) / 1000;
		Log.d(G.LOG_TAG, String.format("time duration: %.2fs", tDuration));
		listening = listening ? false : true;
		recTask.stop();
		G.isRecServiceRunning = false;
		super.onDestroy();
	}

}
