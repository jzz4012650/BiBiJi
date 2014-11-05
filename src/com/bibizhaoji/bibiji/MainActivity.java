package com.bibizhaoji.bibiji;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 初始化侧滑部分
		NavigationDrawerFragment ndf = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		ndf.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		// 初始化主界面部分
		MainDrawerFragment mf = new MainDrawerFragment();
		getFragmentManager().beginTransaction()
				.replace(R.id.container, mf).commit();
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// INVAILD
	}

}
