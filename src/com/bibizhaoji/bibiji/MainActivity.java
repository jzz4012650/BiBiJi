package com.bibizhaoji.bibiji;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.bibizhaoji.bibiji.utils.Pref;
import com.bibizhaoji.pocketsphinx.PocketSphinxService;

public class MainActivity extends Activity implements OnClickListener {

    private Button mainSwticher;
    private Button nightModeSwitcher;
    private int[] mStartTime = { 0, 0 };// 默认00:00
    private int[] mEndTime = { 7, 0 };// 默认07:00

    private ImageView stateGif;
    private ImageView stateText;
    private AnimationDrawable gifAnim;

    private static final int STATE_OFF = 0;
    private static final int STATE_LISTENING = 1;
    private static final int STATE_ACTIVE = 2;
    private static final int STATE_STOP = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	mainSwticher = (Button) findViewById(R.id.main_switcher);
	nightModeSwitcher = (Button) findViewById(R.id.night_mode_switcher);
	stateGif = (ImageView) findViewById(R.id.gif_state);
	stateText = (ImageView) findViewById(R.id.text_state);

	mainSwticher.setOnClickListener(this);
	nightModeSwitcher.setOnClickListener(this);

	gifAnim = (AnimationDrawable) stateGif.getBackground();
	gifAnim.start();
	// 初始化配置文件
	Pref.getSharePrefenrences(this);
    }

    @Override
    protected void onStart() {
	super.onStart();
	if (Pref.isMainSwitcherOn()) {
	    mainSwticher.setBackgroundResource(R.drawable.main_switcher_on);
	    setState(STATE_LISTENING);
	} else {
	    mainSwticher.setBackgroundResource(R.drawable.main_switcher_off);
	    setState(STATE_OFF);
	}
	nightModeSwitcher
		.setBackgroundResource(Pref.isNightModeOn() ? R.drawable.night_mode_on
			: R.drawable.night_mode_off);
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	// 主服务开关
	case R.id.main_switcher:
	    if (Pref.isMainSwitcherOn()) {
		setState(STATE_OFF);
		Pref.setMainSwitcher(this, false);
		v.setBackgroundResource(R.drawable.main_switcher_off);
		Intent i = new Intent(this, PocketSphinxService.class);
		this.stopService(i);
	    } else {
		setState(STATE_LISTENING);
		Pref.setMainSwitcher(this, true);
		v.setBackgroundResource(R.drawable.main_switcher_on);
		Intent i = new Intent(this, PocketSphinxService.class);
		this.startService(i);
	    }
	    break;
	// 夜间模式开关
	case R.id.night_mode_switcher:
	    if (Pref.isNightModeOn()) {
		v.setBackgroundResource(R.drawable.night_mode_off);
		Pref.setNightMode(this, false);
	    } else {
		v.setBackgroundResource(R.drawable.night_mode_on);
		Pref.setNightMode(this, true);
		Intent i = new Intent(this, NightModeNoticeActivity.class);
		this.startActivity(i);
	    }
	}

    }

    private void setState(int state) {
	switch (state) {
	case STATE_OFF:
	    stateText.setBackgroundResource(R.drawable.bg_main_off);
	    stateGif.setBackgroundResource(R.drawable.state_off);
	    break;
	case STATE_LISTENING:
	    stateText.setBackgroundResource(R.drawable.bg_main_listening);
	    stateGif.setBackgroundResource(R.drawable.state_listening);
	    break;
	case STATE_ACTIVE:
	    stateText.setBackgroundResource(R.drawable.bg_main_active);
	    stateGif.setBackgroundResource(R.drawable.state_active);
	    break;
	case STATE_STOP:
	    stateText.setBackgroundResource(R.drawable.bg_main_stop);
	    stateGif.setBackgroundResource(R.drawable.state_stop);
	    break;
	default:
	    break;
	}
	gifAnim = (AnimationDrawable) stateGif.getBackground();
	gifAnim.start();
    }

    /**
     * 判断是否是工作时段
     * 
     * @param mStartTime2
     *            起始时间数组
     * @param mEndTime2
     *            结束时间数组
     * @return 是或否
     */
    public static boolean isWorkingTime(int[] mStartTime2, int[] mEndTime2) {

	if (Pref.isNightModeOn()) {
	    // 开启夜间免打扰模式，在夜间(00:00-07:00)server不允许运行
	    Calendar cal = Calendar.getInstance();// 当前日期
	    int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
	    int minute = cal.get(Calendar.MINUTE);// 获取分钟
	    int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
	    final int start = mStartTime2[0] * 60 + mStartTime2[1];// 起始时间
								   // 00:00的分钟数
	    final int end = mEndTime2[0] * 60 + mEndTime2[1];// 结束时间 07:00的分钟数

	    if (minuteOfDay >= start && minuteOfDay <= end) {
		System.out.println("在时间范围内");
		return false;
	    } else {
		System.out.println("在时间范围外");
		return true;
	    }
	} else {
	    // 没开启免打扰模式，默认server可以运行
	    return true;
	}

    }

}
