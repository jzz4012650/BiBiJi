package com.bibizhaoji.pocketsphinx;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.bibizhaoji.bibiji.G;
import com.bibizhaoji.bibiji.LockScreenActivity;

public class PocketSphinxService extends Service implements RecognitionListener {

    static {
	System.loadLibrary("pocketsphinx_jni");
    }

    private RecognizerTask recTask;
    private Thread recThread;
    private boolean listening;
    private long tStart;
    private long tEnd;

    @Override
    public IBinder onBind(Intent arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void onCreate() {
	super.onCreate();

	recTask = new RecognizerTask();
	recThread = new Thread(this.recTask);
	recTask.setRecognitionListener(this);
	recThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	tStart = System.currentTimeMillis();
	listening = true;
	recTask.start();
	G.isRecServiceRunning = true;
	return super.onStartCommand(intent, flags, startId);
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

    @Override
    public void onPartialResults(Bundle b) {
	final String hyp = b.getString("hyp");
	if (hyp == null) {
	    return;
	}
	if (hyp.indexOf(G.REC_WORD1) != -1 || hyp.indexOf(G.REC_WORD2) != -1) {
	    Log.d(G.LOG_TAG, "*********get rec_word:" + hyp);
	    Intent i = new Intent(this, LockScreenActivity.class);
	    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    this.startActivity(i);
	    this.onDestroy();
	}
	// if (hyp.equals(G.REC_WORD2)) {
	// Log.d(getClass().getName(), "*********get rec_word:" + hyp);
	// }

	// final PocketSphinxService that = this;
	// that.edit_text.post(new Runnable() {
	// public void run() {
	// that.edit_text.setText(hyp);
	// }
	// });
    }

    @Override
    public void onResults(Bundle b) {
	final String hyp = b.getString("hyp");
	Log.d(G.LOG_TAG, "|||||||||||recognizition finished:" + hyp);

	// final PocketSphinxService that = this;
	// this.edit_text.post(new Runnable() {
	// public void run() {
	// that.edit_text.setText(hyp);
	// Date end_date = new Date();
	// long nmsec = end_date.getTime() - that.start_date.getTime();
	// float rec_dur = (float) nmsec / 1000;
	// that.performance_text.setText(String.format(
	// "%.2f seconds %.2f xRT", that.speech_dur, rec_dur
	// / that.speech_dur));
	// Log.d(getClass().getName(), "Hiding Dialog");
	// that.rec_dialog.dismiss();
	// }
	// });
    }

    @Override
    public void onError(int err) {
	Log.d(G.LOG_TAG, "PocketSphinx got an error.");
	// final PocketSphinxService that = this;
	// that.edit_text.post(new Runnable() {
	// public void run() {
	// that.rec_dialog.dismiss();
	// }
	// });
    }

}
