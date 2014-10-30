package com.bibizhaoji.bibiji;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.bibizhaoji.pocketsphinx.PocketSphinxService;

public class MainActivity extends Activity implements
	NavigationDrawerFragment.NavigationDrawerCallbacks {

    // 侧滑层
    private NavigationDrawerFragment navigationDrawerFragment;
//    private MainDrawerFragment mainDrawerFragment;

    private CharSequence mTitle;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	// 初始化侧滑部分
	navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
		.findFragmentById(R.id.navigation_drawer);
	navigationDrawerFragment.setUp(R.id.navigation_drawer,
		(DrawerLayout) findViewById(R.id.drawer_layout));
	
	// 初始化主界面部分
	MainDrawerFragment mainDrawerFragment = new MainDrawerFragment();
	getFragmentManager().beginTransaction().replace(R.id.container, mainDrawerFragment).commit();
	
	mTitle = getTitle();
	context = this;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
	// update the main content by replacing fragments
//	FragmentManager fragmentManager = getFragmentManager();
//	fragmentManager
//		.beginTransaction()
//		.replace(R.id.container,
//			PlaceholderFragment.newInstance(position + 1)).commit();
    }

    public void onSectionAttached(int number) {
	switch (number) {
	case 1:
	    mTitle = getString(R.string.title_section1);
	    break;
	case 2:
	    mTitle = getString(R.string.title_section2);
	    break;
	case 3:
	    mTitle = getString(R.string.title_section3);
	    break;
	}
    }

    public void restoreActionBar() {
	ActionBar actionBar = getActionBar();
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	actionBar.setDisplayShowTitleEnabled(true);
	actionBar.setTitle(mTitle);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private ToggleButton serviceSwitcher;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlaceholderFragment newInstance(int sectionNumber) {
	    PlaceholderFragment fragment = new PlaceholderFragment();
	    Bundle args = new Bundle();
	    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
	    fragment.setArguments(args);
	    return fragment;
	}

	public PlaceholderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.fragment_main, container,
		    false);
	    serviceSwitcher = (ToggleButton) rootView
		    .findViewById(R.id.service_switcher);
	    serviceSwitcher.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    if (serviceSwitcher.isChecked()) {
			Intent i = new Intent(context,
				PocketSphinxService.class);
			context.startService(i);
		    } else {
			Intent i = new Intent(context,
				PocketSphinxService.class);
			context.stopService(i);
		    }
		}
	    });
	    return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    ((MainActivity) activity).onSectionAttached(getArguments().getInt(
		    ARG_SECTION_NUMBER));
	}
    }

}
