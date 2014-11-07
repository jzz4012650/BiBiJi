package com.bibizhaoji.bibiji;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NightModeNoticeActivity extends Activity implements OnClickListener {

    private Button closeBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_night_mode_notice);
	
	closeBtn = (Button) findViewById(R.id.close_btn);
	closeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.close_btn:
	    this.finish();
	    // 模拟返回键退出
//	    Instrumentation inst = new Instrumentation();
//	    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
	    break;
	}
    }
}
