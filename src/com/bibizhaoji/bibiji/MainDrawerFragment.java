package com.bibizhaoji.bibiji;

import java.util.Calendar;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.bibizhaoji.bibiji.utils.Pref;
import com.bibizhaoji.bibiji.utils.ToastUtils;
import com.bibizhaoji.pocketsphinx.PocketSphinxService;

public class MainDrawerFragment extends Fragment implements OnClickListener {

    private ToggleButton serviceSwitcher;
	private ToggleButton noDisturbingSwitcher;
	private Context mContext;
	private TextView startTime;
	private TextView endTime;
	private int[] mStartTime = {5,0};//默认00:00
	private int[] mEndTime = {7,0};//默认07:00
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
	    Bundle bundle) {
	return inflater.inflate(R.layout.fragment_main, viewGroup, false);
    }
    

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        serviceSwitcher = (ToggleButton) getActivity().findViewById(R.id.service_switcher);
        noDisturbingSwitcher = (ToggleButton) getActivity().findViewById(R.id.NoDisturbing_switcher);
        startTime = (TextView)getActivity().findViewById(R.id.startTime);
        endTime = (TextView) getActivity().findViewById(R.id.endTime);
       
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        
        noDisturbingSwitcher.setOnClickListener(this);
        serviceSwitcher.setOnClickListener(this);
        //初始化配置文件
        Pref.getSharePrefenrences(mContext);
        noDisturbingSwitcher.setChecked(Pref.isNoDisturbingModeOnlyNight());
    }
    
    @Override
    public void onStart() {
	if (G.isRecServiceRunning) {
	    serviceSwitcher.setChecked(true);
	} else {
	    serviceSwitcher.setChecked(false);
	}
        super.onStart();
    }


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.startTime:
			createTimePickerDialog(mStartTime,startTime, 0, 0, true);
			break;
			
		case R.id.endTime:
			 
			createTimePickerDialog(mEndTime,endTime, 7, 0, true);
			 
			break;
			
		case R.id.service_switcher:
			if(isWorkingTime(mStartTime,mEndTime)){
				if (serviceSwitcher.isChecked()) {
				    Intent i = new Intent(getActivity(), PocketSphinxService.class);
				    getActivity().startService(i);
				} else {
				    Intent i = new Intent(getActivity(), PocketSphinxService.class);
				    getActivity().stopService(i);
				}
			}else{
				ToastUtils.show(mContext, "当前为夜间免打扰时间段!");
			}
			
			break;
			
		case R.id.NoDisturbing_switcher:
			if(noDisturbingSwitcher.isChecked()){
				Pref.enableNoDisturbingModeOnlyNight(mContext, true);
			}else{
				Pref.enableNoDisturbingModeOnlyNight(mContext, false);
			}
			break;
			
		default:
			break;
		}
	}
	
	private void createTimePickerDialog(final int[] time,final TextView edit,int hourOfDay,int minute,boolean is24HourView) {
		
		new TimePickerDialog(mContext, new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				edit.setText(hourOfDay + ":" + minute);
				time[0]=hourOfDay;
				time[1]=minute;
				
			}
			
		}, hourOfDay, minute, is24HourView).show();
		
		
	}


	public static boolean isWorkingTime(int[] mStartTime2, int[] mEndTime2){
		
		if(Pref.isNoDisturbingModeOnlyNight()){
			//开启夜间免打扰模式，在夜间(00:00-07:00)server不允许运行
		      
		      Calendar cal = Calendar.getInstance();// 当前日期
		      int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
		      int minute = cal.get(Calendar.MINUTE);// 获取分钟
		      int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
		      final int start = mStartTime2[0] * 60 + mStartTime2[1];// 起始时间 00:00的分钟数
		      final int end = mEndTime2[0] * 60 + mEndTime2[1];// 结束时间 07:00的分钟数
		      
		      if (minuteOfDay >= start && minuteOfDay <= end) {
		          System.out.println("在时间范围内");
		          return false;
		      } else {
		          System.out.println("在时间范围外");
		          return true;
		      }
		}else{
			//没开启免打扰模式，默认server可以运行
			return true;
		}
		
	}
	

}
