package com.lays.fote.activities;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.lays.fote.R;
import com.lays.fote.fragments.SettingsFragment;

@TargetApi(11)
public class SettingsNewActivity extends SherlockPreferenceActivity {
    
    // private static final String TAG = SettingsNewActivity.class.getSimpleName();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_settings);
	
	// Retains the background while scrolling
	ListView listView = (ListView) findViewById(android.R.id.list);
	listView.setCacheColorHint(Color.TRANSPARENT);
	
	// Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
    
    @Override
    public void onBackPressed() {
	super.onBackPressed();
	overridePendingTransition(R.anim.slide_right_incoming, R.anim.slide_right_outgoing);
    }
}
