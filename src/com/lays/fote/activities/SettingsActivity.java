package com.lays.fote.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.ListPreference;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.lays.fote.FoteApplication;
import com.lays.fote.R;

public class SettingsActivity extends SherlockPreferenceActivity implements OnSharedPreferenceChangeListener {

    // private static final String TAG = SettingsActivity.class.getSimpleName();
    
    private SharedPreferences mSharedPreferences;
    private ListPreference mSorting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	FoteApplication.tracker.trackPageView("/SettingsOld");
	addPreferencesFromResource(R.xml.preferences);
	setContentView(R.layout.activity_settings);
	mSharedPreferences = getPreferenceScreen().getSharedPreferences();
	mSorting = (ListPreference) getPreferenceScreen().findPreference(FoteApplication.PREF_SORTING_KEY);
	
	// Retains the background while scrolling
	ListView listView = (ListView) findViewById(android.R.id.list);
	listView.setCacheColorHint(Color.TRANSPARENT);
    }

    @Override
    protected void onResume() {
	super.onResume();
	mSorting.setSummary(mSharedPreferences.getString(FoteApplication.PREF_SORTING_KEY, FoteApplication.PREF_SORTING_DEFAULT_VALUE));
	mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
	super.onPause();
	mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onBackPressed() {
	super.onBackPressed();
	overridePendingTransition(R.anim.slide_right_incoming, R.anim.slide_right_outgoing);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	if (key.equals(FoteApplication.PREF_SORTING_KEY)) {
	    mSorting.setSummary(sharedPreferences.getString(key, FoteApplication.PREF_SORTING_DEFAULT_VALUE));
	}
    }
}
