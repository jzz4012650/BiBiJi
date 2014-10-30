package com.bibizhaoji.bibiji;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.bibizhaoji.pocketsphinx.PocketSphinxService;

public class MainDrawerFragment extends Fragment {

    private ToggleButton serviceSwitcher;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
	    Bundle bundle) {
	return inflater.inflate(R.layout.fragment_main, viewGroup, false);
    }
    

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        serviceSwitcher = (ToggleButton) getActivity()
		.findViewById(R.id.service_switcher);
	serviceSwitcher.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		if (serviceSwitcher.isChecked()) {
		    Intent i = new Intent(getActivity(), PocketSphinxService.class);
		    getActivity().startService(i);
		} else {
		    Intent i = new Intent(getActivity(), PocketSphinxService.class);
		    getActivity().stopService(i);
		}
	    }
	});
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
}
