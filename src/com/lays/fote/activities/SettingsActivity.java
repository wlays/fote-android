package com.lays.fote.activities;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.lays.fote.R;

public class SettingsActivity extends SherlockActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_settings);
	getSupportActionBar().hide();
    }
    
    @Override
    public void onBackPressed() {
	super.onBackPressed();
	overridePendingTransition(R.anim.slide_right_incoming, R.anim.slide_right_outgoing);
    }
}
