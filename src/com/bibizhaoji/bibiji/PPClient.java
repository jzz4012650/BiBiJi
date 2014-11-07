package com.bibizhaoji.bibiji;

import android.database.CursorJoiner.Result;
import android.os.RemoteException;

import com.bibizhaoji.bibiji.aidl.IPPClient;
import com.bibizhaoji.bibiji.utils.Log;

public class PPClient extends IPPClient.Stub {

	// 识别的结果
	private int STATE_RESULT;

	@Override
	public boolean onResult(int result) throws RemoteException {
		STATE_RESULT = result;
		Log.d(G.LOG_TAG, "STATE_RESULT-->"+STATE_RESULT+"");
		return true;
	}

	@Override
	public int getResult() throws RemoteException {
		return STATE_RESULT;
		
	}

	
}
