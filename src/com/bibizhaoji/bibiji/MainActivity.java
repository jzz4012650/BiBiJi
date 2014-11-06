package com.bibizhaoji.bibiji;

import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.bibizhaoji.bibiji.utils.Pref;
import com.bibizhaoji.bibiji.utils.ToastUtils;
import com.bibizhaoji.pocketsphinx.PocketSphinxService;

public class MainActivity extends Activity implements OnClickListener {

    private ToggleButton serviceSwitcher;
    private ToggleButton noDisturbingSwitcher;
    private TextView startTime;
    private TextView endTime;
    private int[] mStartTime = { 0, 0 };// 默认00:00
    private int[] mEndTime = { 7, 0 };// 默认07:00

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	serviceSwitcher = (ToggleButton) findViewById(R.id.service_switcher);
	noDisturbingSwitcher = (ToggleButton) findViewById(R.id.NoDisturbing_switcher);
	startTime = (TextView) findViewById(R.id.startTime);
	endTime = (TextView) findViewById(R.id.endTime);

	startTime.setOnClickListener(this);
	endTime.setOnClickListener(this);

	noDisturbingSwitcher.setOnClickListener(this);
	serviceSwitcher.setOnClickListener(this);
	// 初始化配置文件
	Pref.getSharePrefenrences(this);
	noDisturbingSwitcher.setChecked(Pref.isNoDisturbingModeOnlyNight());
    }

    @Override
    protected void onStart() {
	super.onStart();
	if (G.isRecServiceRunning) {
	    serviceSwitcher.setChecked(true);
	} else {
	    serviceSwitcher.setChecked(false);
	}
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.startTime:
	    createTimePickerDialog(mStartTime, startTime, 0, 0, true);
	    break;
	case R.id.endTime:
	    createTimePickerDialog(mEndTime, endTime, 7, 0, true);
	    break;
	case R.id.service_switcher:
	    if (isWorkingTime(mStartTime, mEndTime)) {
		if (serviceSwitcher.isChecked()) {
		    Intent i = new Intent(this, PocketSphinxService.class);
		    this.startService(i);
		} else {
		    Intent i = new Intent(this, PocketSphinxService.class);
		    this.stopService(i);
		}
	    } else {
		ToastUtils.show(this, "当前为夜间免打扰时间段!");
	    }
	    break;
	case R.id.NoDisturbing_switcher:
	    if (noDisturbingSwitcher.isChecked()) {
		Pref.enableNoDisturbingModeOnlyNight(this, true);
	    } else {
		Pref.enableNoDisturbingModeOnlyNight(this, false);
	    }
	    break;
	}

    }

    /**
     * 创建时间选择控件
     * 
     * @param time
     * @param edit
     * @param hourOfDay
     * @param minute
     * @param is24HourView
     */
    private void createTimePickerDialog(final int[] time, final TextView edit,
	    int hourOfDay, int minute, boolean is24HourView) {

	new TimePickerDialog(this, new OnTimeSetListener() {

	    @Override
	    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		edit.setText(hourOfDay + ":" + minute);
		time[0] = hourOfDay;
		time[1] = minute;
	    }

	}, hourOfDay, minute, is24HourView).show();

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

	if (Pref.isNoDisturbingModeOnlyNight()) {
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
