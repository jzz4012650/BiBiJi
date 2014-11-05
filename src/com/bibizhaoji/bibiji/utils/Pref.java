package com.bibizhaoji.bibiji.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.util.Log;

/**
 * 配置文件
 * @author caiyingyuan
 * */
public class Pref {

	private static SharedPreferences mPrefs;
	public static String DONT_DISTURB_MODE_AT_NIGHT = "dont_disturb_mode_at_night";
	private static String BIBIJI_PREF = "BiBiJi_pref";

	private static void init(Context context) {
		mPrefs = context.getSharedPreferences(BIBIJI_PREF,
				android.content.Context.MODE_PRIVATE);
	}

	public static SharedPreferences getSharePrefenrences(Context context) {
		if (mPrefs == null) {
			init(context);
		}
		return mPrefs;
	}

	public static void applyEditor(Editor editor) {
		Log.d("SpeedUtils", "applyEditor");

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			editor.apply();
		} else {
			editor.commit();
		}
	}

	public static final void enableNoDisturbingModeOnlyNight(Context context,
			Boolean enable) {

		init(context);
		setBooleanSetting(DONT_DISTURB_MODE_AT_NIGHT, enable);
	}

	public static final boolean isNoDisturbingModeOnlyNight() {
		
		return getBooleanSetting(DONT_DISTURB_MODE_AT_NIGHT, false);
	}

	private static void setBooleanSetting(String key,
			boolean enable) {
		
		 applyEditor(mPrefs.edit().putBoolean(key, enable));
	}

	private static boolean getBooleanSetting(String key, boolean selected) {
		
		return mPrefs.getBoolean(key, selected);

	}

}
