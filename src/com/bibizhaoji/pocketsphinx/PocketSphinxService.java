package com.bibizhaoji.pocketsphinx;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.bibizhaoji.bibiji.G;
import com.bibizhaoji.bibiji.LockScreenActivity;
import com.bibizhaoji.bibiji.utils.Pref;

public class PocketSphinxService extends Service implements RecognitionListener {

    static {
	System.loadLibrary("pocketsphinx_jni");
    }

    private RecognizerTask recTask;
    private Thread recThread;
    private boolean listening;
    private long tStart;
    private long tEnd;
    private Context mContext;

    @Override
    public IBinder onBind(Intent arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void onCreate() {
	super.onCreate();
	Pref.getSharePrefenrences(this);

	mContext = this;
	recTask = new RecognizerTask(mContext);
	recThread = new Thread(this.recTask);
	recTask.setRecognitionListener(this);
	recThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	tStart = System.currentTimeMillis();
	listening = true;
	recTask.start();
	Pref.setMainSwitcher(mContext, true);
	return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
	tEnd = System.currentTimeMillis();
	float tDuration = (tEnd - tStart) / 1000;
	Log.d(G.LOG_TAG, String.format("time duration: %.2fs", tDuration));
	listening = listening ? false : true;
	recTask.stop();
	Pref.setMainSwitcher(mContext, false);
	super.onDestroy();
    }

    @Override
    public void onPartialResults(Bundle b) {
	final String hyp = b.getString("hyp");
	if (hyp == null) {
	    return;
	} else if (hyp.indexOf(G.REC_WORD1) != -1) {
	    Log.d(G.LOG_TAG, "*********get rec_word:" + hyp);
	    Intent i = new Intent(this, LockScreenActivity.class);
	    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    this.startActivity(i);
	    this.onDestroy();
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

}
